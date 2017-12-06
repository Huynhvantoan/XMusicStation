#ifndef Header_SuperpoweredExample
#define Header_SuperpoweredExample

#include <math.h>
#include <pthread.h>

#include "SuperpoweredExample.h"
#include <SuperpoweredAdvancedAudioPlayer.h>
#include <AndroidIO/SuperpoweredAndroidAudioIO.h>
#include <SuperpoweredDecoder.h>

#define HEADROOM_DECIBEL 3.0f
static const float headroom = powf(10.0f, -HEADROOM_DECIBEL * 0.025f);

class SuperpoweredExample {
public:
    SuperpoweredExample(unsigned int samplerate, unsigned int buffersize, char *urlArray[]);
	~SuperpoweredExample();
	bool process(short int *output, unsigned int numberOfSamples);
	void onPlayPause(bool play);
	bool isInit();
	double getPosition();
	double getPositionMs();
	bool isSongCompleted();
	double getDuration();
	double getDurationMs();
	bool isPlaying();
	void onVolume(float volume);
    void setPosition(int milliseconds);
    double detectFileBPM(const char *path);

private:
    SuperpoweredAndroidAudioIO *audioSystem;
    SuperpoweredAdvancedAudioPlayer *playerA, *playerB;
    float *stereoBuffer;
    unsigned char activeFx;
    float crossValue,volA,volB;
};

#endif
