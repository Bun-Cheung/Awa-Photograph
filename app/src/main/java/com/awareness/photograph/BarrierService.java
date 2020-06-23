package com.awareness.photograph;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;

import com.huawei.hms.kit.awareness.barrier.BarrierStatus;

public class BarrierService extends IntentService {
    private final String TAG = getClass().getSimpleName();

    public BarrierService() {
        super("Awa-photograph");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager == null) {
                return;
            }
            Notification notification = new Notification.Builder(this, Utils.FORE_GROUND_SERVICE_CHANNEL_ID).build();
            startForeground(1, notification);
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null) {
            Log.i(TAG, "intent is null");
            return;
        }
        BarrierStatus barrierStatus = BarrierStatus.extract(intent);
        String label = barrierStatus.getBarrierLabel();
        int status = barrierStatus.getPresentStatus();
        if (status == BarrierStatus.TRUE) {
            Log.i(TAG, label + " barrier status is true");
            String content = "You are in the same condition as your collected photo: " + label
                    + ". Don't miss it and take the photo!";
            Utils.sendNotification(this, content);
        } else {
            Log.i(TAG, label + " barrier status is false or unknown");
        }
    }
}
