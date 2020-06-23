package com.awareness.photograph;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.DateFormat;
import android.icu.util.Calendar;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.awareness.photograph.dao.PhotoDetailDao;
import com.awareness.photograph.db.AppDatabase;
import com.awareness.photograph.entity.PhotoDetail;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Utils {
    static final String CHANNEL_ID = "Awa-photograph";
    static final String FORE_GROUND_SERVICE_CHANNEL_ID = "Foreground service channel";

    public static String formatTimestamp(long timestamp) {
        DateFormat format = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
        return format.format(new Date(timestamp));
    }

    static double roundingValue(double var) {
        return new BigDecimal(var).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    static long parseTimeOfDay(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(timestamp));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return timestamp - calendar.getTimeInMillis();
    }

    static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            if (manager == null) {
                return;
            }
            CharSequence name = "Awareness-Photograph";
            String description = "notify the user that the current condition is the same as " +
                    "the condition of the photo in favorites";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(description);
            manager.createNotificationChannel(channel);

            CharSequence serviceChannelName = "Foreground service channel";
            NotificationChannel serviceChannel = new NotificationChannel(FORE_GROUND_SERVICE_CHANNEL_ID,
                    serviceChannelName, NotificationManager.IMPORTANCE_MIN);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    static void sendNotification(Context context, String content) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentIntent(pendingIntent)
                .setContentTitle("Awareness Notification")
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setAutoCancel(true);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(0, builder.build());
    }

    static List<PhotoDetail> getAllFromDB(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        return db.photoDetailDao().getAll();
    }

    static void insertToDB(Context context, PhotoDetail photoDetail) {
        PhotoDetailDao dao = AppDatabase.getDatabase(context).photoDetailDao();
        AppDatabase.databaseExecutor.execute(() -> {
            dao.insert(photoDetail);
        });
    }

    static void deleteDBData(Context context, PhotoDetail photoDetail) {
        PhotoDetailDao dao = AppDatabase.getDatabase(context).photoDetailDao();
        AppDatabase.databaseExecutor.execute(() -> {
            dao.delete(photoDetail);
        });
    }

    static void updateDBData(Context context, PhotoDetail photoDetail) {
        PhotoDetailDao dao = AppDatabase.getDatabase(context).photoDetailDao();
        AppDatabase.databaseExecutor.execute(() -> {
            dao.update(photoDetail);
        });
    }

    public static Bitmap decodeImage(Context context, String photoPath) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        File photoFile = new File(photoPath);
        if (photoFile.exists()) {
            bitmap = BitmapFactory.decodeFile(photoPath, options);
        } else {
            InputStream is = null;
            try {
                is = context.getAssets().open(photoPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (is != null) {
                bitmap = BitmapFactory.decodeStream(is);
            }
        }
        return bitmap;
    }
}
