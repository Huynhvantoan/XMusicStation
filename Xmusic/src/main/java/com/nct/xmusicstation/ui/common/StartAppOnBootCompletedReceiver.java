
package com.nct.xmusicstation.ui.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.nct.xmusicstation.ui.main.MainActivity;

/**
 * Created by Toan.IT on 4/24/17.
 * Email:Huynhvantoan.itc@gmail.com
 */

public class StartAppOnBootCompletedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}
