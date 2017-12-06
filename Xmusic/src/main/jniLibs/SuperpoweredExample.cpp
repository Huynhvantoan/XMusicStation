#include "SuperpoweredExample.h"
#include <SuperpoweredSimple.h>
#include <SuperpoweredCPU.h>
#include <jni.h>
#include <stdio.h>
#include <android/log.h>
#include <stdlib.h>
#include <SLES/OpenSLES.h>
#include <SLES/OpenSLES_AndroidConfiguration.h>
#include <SuperpoweredAudioBuffers.h>
#include <SuperpoweredAnalyzer.h>
#include <string.h>

#define LOG_TAG "SuperpoweredExample"
bool isPlayerAInit, isPlayerBInit;
static void playerEventCallbackA(void *clientData, SuperpoweredAdvancedAudioPlayerEvent event, void * __unused value) {
    if (event == SuperpoweredAdvancedAudioPlayerEvent_LoadSuccess) {
    	SuperpoweredAdvancedAudioPlayer *playerA = *((SuperpoweredAdvancedAudioPlayer **)clientData);
        playerA->setBpm(126.0f);
        playerA->setFirstBeatMs(353);
        playerA->setPosition(playerA->firstBeatMs, false, false);
        isPlayerAInit=true;
    };
}

static void playerEventCallbackB(void *clientData, SuperpoweredAdvancedAudioPlayerEvent event, void * __unused value) {
    if (event == SuperpoweredAdvancedAudioPlayerEvent_LoadSuccess) {
    	SuperpoweredAdvancedAudioPlayer *playerB = *((SuperpoweredAdvancedAudioPlayer **)clientData);
        playerB->setBpm(123.0f);
        playerB->setFirstBeatMs(40);
        playerB->setPosition(playerB->firstBeatMs, false, false);
        isPlayerBInit=true;
    };
}

static bool audioProcessing(void *clientdata, short int *audioIO, int numberOfSamples, int __unused samplerate) {
	return ((SuperpoweredExample *)clientdata)->process(audioIO, (unsigned int)numberOfSamples);
}

bool SuperpoweredExample::isInit() {
    return isPlayerAInit&&isPlayerBInit;
}


double SuperpoweredExample::getPosition() {
    if(isPlayerAInit)
        return playerA->positionSeconds;
    return 0;
}
double SuperpoweredExample::getPositionMs() {
    if(isPlayerAInit)
        return playerA->positionMs;
    return 0;
}

bool SuperpoweredExample::isSongCompleted() {
    return isPlayerAInit && (playerA->durationSeconds == playerA->positionSeconds);
}

double SuperpoweredExample::getDuration() {
    if(isPlayerAInit)
        return playerA->durationSeconds;
    return 0;
}
double SuperpoweredExample::getDurationMs() {
    if(isPlayerAInit)
        return playerA->durationMs;
    return 0;
}
bool SuperpoweredExample::isPlaying() {
    if(isPlayerAInit)
        return playerA->playing;
    return false;
}

SuperpoweredExample::SuperpoweredExample(unsigned int samplerate, unsigned int buffersize, char *paramPath[]) : activeFx(0), crossValue(0.0f), volB(0.0f), volA(1.0f * headroom) {
    isPlayerAInit=false;
    isPlayerBInit=false;
    stereoBuffer = (float *)memalign(16, (buffersize + 16) * sizeof(float) * 2);
    playerA = new SuperpoweredAdvancedAudioPlayer(&playerA , playerEventCallbackA, samplerate, 0);
    playerA->open(paramPath[0]);
    playerB = new SuperpoweredAdvancedAudioPlayer(&playerB, playerEventCallbackB, samplerate, 0);
    playerB->open(paramPath[1]);
    playerA->syncMode = playerB->syncMode = SuperpoweredAdvancedAudioPlayerSyncMode_TempoAndBeat;
    audioSystem = new SuperpoweredAndroidAudioIO(samplerate, buffersize, false, true, audioProcessing, this, -1, SL_ANDROID_STREAM_MEDIA, buffersize * 2);
}

SuperpoweredExample::~SuperpoweredExample() {
    delete audioSystem;
    delete playerA;
    delete playerB;
    free(stereoBuffer);
}

void SuperpoweredExample::onPlayPause(bool play) {
    if (!play) {
        playerA->pause();
        playerB->pause();
    } else {
        bool masterIsA = (crossValue <= 0.5f);
        playerA->play(!masterIsA);
        playerB->play(masterIsA);
    };
    SuperpoweredCPU::setSustainedPerformanceMode(play); // <-- Important to prevent audio dropouts.
    __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, "onPlayPause  => %d ",play);
}

void SuperpoweredExample::setPosition(int milliseconds) {
    if(isPlayerAInit)
        return playerA->setPosition(milliseconds, false,true);
}

void SuperpoweredExample::onVolume(float volume) {
    crossValue = float(0) * 0.01f;
    if (crossValue < 0.01f) {
        volA = volume;
        volB = 0.0f;
        __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, "volA  => %f ",volA);
    } else if (crossValue > 0.99f) {
        volA = 0.0f;
        volB = volume;
        __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, "volB  => %f ",volB);
    } else { // constant power curve
        volA = cosf(float(M_PI_2) * crossValue) * headroom;
        volB = cosf(float(M_PI_2) * (1.0f - crossValue)) * headroom;
    };
    __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, "onCrossfader  => %f ",volA);
}

double SuperpoweredExample::detectFileBPM(const char *path) {
    SuperpoweredDecoder *decoder = new SuperpoweredDecoder();
    __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, "path  => %s ",path);
    const char *openError = decoder->open(path);
    //__android_log_print(ANDROID_LOG_ERROR, LOG_TAG, "openError  => %s ",openError);
    if (openError != NULL) {
        delete decoder;
        return 0;
    }
    SuperpoweredOfflineAnalyzer *analyzer = new SuperpoweredOfflineAnalyzer(decoder->samplerate, 0,
                                                                            (int) decoder->durationSeconds);
// Create a buffer for the 16-bit integer samples coming from the decoder.

    short int *intBuffer = (short int *) malloc(
            decoder->samplesPerFrame * 2 * sizeof(short int) + 32768);
// Create a buffer for the 32-bit floating point samples required by the effect.
    float *floatBuffer = (float *) malloc(decoder->samplesPerFrame * 2 * sizeof(float) + 32768);

// Processing.
    while (true) {
        // Decode one frame. samplesDecoded will be overwritten with the actual decoded number of samples.
        unsigned int samplesDecoded = decoder->samplesPerFrame;
        if (decoder->decode(intBuffer, &samplesDecoded) == SUPERPOWEREDDECODER_ERROR) break;
        if (samplesDecoded < 1) break;

        // Convert the decoded PCM samples from 16-bit integer to 32-bit floating point.
        SuperpoweredShortIntToFloat(intBuffer, floatBuffer, samplesDecoded);

        // Submit samples to the analyzer.
        analyzer->process(floatBuffer, samplesDecoded);
    };

// Get the result.
    unsigned char *averageWaveform = NULL, *lowWaveform = NULL, *midWaveform = NULL, *highWaveform = NULL, *peakWaveform = NULL, *notes = NULL;
    int waveformSize, overviewSize, keyIndex;
    char *overviewWaveform = NULL;
    float loudpartsAverageDecibel, peakDecibel, bpm, averageDecibel, beatgridStartMs = 0;
    analyzer->getresults(&averageWaveform, &peakWaveform, &lowWaveform, &midWaveform, &highWaveform,
                         &notes, &waveformSize, &overviewWaveform, &overviewSize, &averageDecibel,
                         &loudpartsAverageDecibel, &peakDecibel, &bpm, &beatgridStartMs, &keyIndex);
    //__android_log_print(ANDROID_LOG_ERROR, LOG_TAG, "bpm  => %f ",bpm);
    __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, "loudpartsAverageDecibel  => %f ",loudpartsAverageDecibel);
    __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, "peakDecibel  => %f ",peakDecibel);
    __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, "averageDecibel  => %f ",averageDecibel);
// Cleanup.
    delete decoder;
    delete analyzer;
    free(intBuffer);
    free(floatBuffer);

    //Test
    //playerA->setBpm(bpm);

    if (averageWaveform) free(averageWaveform);
    if (lowWaveform) free(lowWaveform);
    if (midWaveform) free(midWaveform);
    if (highWaveform) free(highWaveform);
    if (peakWaveform) free(peakWaveform);
    if (notes) free(notes);
    if (overviewWaveform) free(overviewWaveform);
    return averageDecibel;
}

bool SuperpoweredExample::process(short int *output, unsigned int numberOfSamples) {
    bool masterIsA = (crossValue <= 0.5f);
    double masterBpm = masterIsA ? playerA->currentBpm : playerB->currentBpm;
    double msElapsedSinceLastBeatA = playerA->msElapsedSinceLastBeat; // When playerB needs it, playerA has already stepped this value, so save it now.

    bool silence = !playerA->process(stereoBuffer, false, numberOfSamples, volA, masterBpm, playerB->msElapsedSinceLastBeat);
    if (playerB->process(stereoBuffer, !silence, numberOfSamples, volB, masterBpm, msElapsedSinceLastBeatA)) silence = false;
    // The stereoBuffer is ready now, let's put the finished audio into the requested buffers.
    if (!silence) SuperpoweredFloatToShortInt(stereoBuffer, output, numberOfSamples);
    return !silence;
}

static SuperpoweredExample *example = NULL;

#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wreturn-stack-address"
extern "C" JNIEXPORT void Java_com_nct_xmusicstation_jni_AppJNI_SuperpoweredExample(JNIEnv *javaEnvironment, jobject __unused self, jint samplerate, jint buffersize,jobjectArray urlArray) {
    char *urlPath[2];
    for (int n = 0; n < 2; n++){
        jstring jStr = (jstring) javaEnvironment->GetObjectArrayElement(urlArray, n);
        const char *str=javaEnvironment->GetStringUTFChars(jStr, 0);
        urlPath[n]=strdup(str);
        javaEnvironment->ReleaseStringUTFChars(jStr, str);
    }

    if(example!=NULL) delete example;
    example = new SuperpoweredExample((unsigned int)samplerate, (unsigned int)buffersize,urlPath);
    //javaEnvironment->ReleaseStringUTFChars(jpath1,path1);
}
#pragma clang diagnostic pop

extern "C" JNIEXPORT void Java_com_nct_xmusicstation_jni_AppJNI_onPlayPause(JNIEnv * __unused javaEnvironment, jobject __unused obj, jboolean play) {
	example->onPlayPause(play);
}

extern "C" JNIEXPORT void Java_com_nct_xmusicstation_jni_AppJNI_onVolume(JNIEnv * __unused javaEnvironment, jobject __unused obj,jfloat volume) {
	example->onVolume(volume);
}

extern "C" JNIEXPORT double Java_com_nct_xmusicstation_jni_AppJNI_detectFileBPM(JNIEnv * __unused javaEnvironment, jobject __unused obj, jstring value) {
    const char *path = javaEnvironment->GetStringUTFChars(value, JNI_FALSE);
    if(example!=NULL)
        return example->detectFileBPM(path);
    return 0;
}

extern "C" JNIEXPORT void Java_com_nct_xmusicstation_jni_AppJNI_setPosition(JNIEnv * __unused javaEnvironment, jobject __unused obj,jint value) {
    if(example!=NULL)
         example->setPosition(value);
}

extern "C" JNIEXPORT double Java_com_nct_xmusicstation_jni_AppJNI_getPosition(JNIEnv * __unused javaEnvironment, jobject __unused obj) {
    if(example!=NULL)
        return example->getPosition();
    return 0;
}

extern "C" JNIEXPORT double Java_com_nct_xmusicstation_jni_AppJNI_getPositionMs(JNIEnv * __unused javaEnvironment, jobject __unused obj) {
    if(example!=NULL)
        return example->getPositionMs();
    return 0;
}

extern "C" JNIEXPORT bool Java_com_nct_xmusicstation_jni_AppJNI_isSongCompleted(JNIEnv * __unused javaEnvironment, jobject __unused obj) {
    if(example!=NULL)
        return example->isSongCompleted();
    return false;
}

extern "C" JNIEXPORT double Java_com_nct_xmusicstation_jni_AppJNI_getDuration(JNIEnv * __unused javaEnvironment, jobject __unused obj) {
    if(example!=NULL)
        return example->getDuration();
    return 0;
}

extern "C" JNIEXPORT double Java_com_nct_xmusicstation_jni_AppJNI_getDurationMs(JNIEnv * __unused javaEnvironment, jobject __unused obj) {
    if(example!=NULL)
        return example->getDurationMs();
    return 0;
}

extern "C" JNIEXPORT bool Java_com_nct_xmusicstation_jni_AppJNI_isPlaying(JNIEnv * __unused javaEnvironment, jobject __unused obj) {
    if(example!=NULL)
        return example->isPlaying();
    return false;
}

extern "C" JNIEXPORT bool Java_com_nct_xmusicstation_jni_AppJNI_isInit(JNIEnv * __unused javaEnvironment, jobject __unused obj) {
    if(example!=NULL) return example->isInit();
    return false;
}
