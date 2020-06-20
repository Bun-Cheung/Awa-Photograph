package com.awareness.photograph.presetdata;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.awareness.photograph.R;

import java.util.ArrayList;
import java.util.List;

public class MainPhotoListData {
    private static final List<Bitmap> PHOTO_LIST = new ArrayList<>();

    public static List<Bitmap> getMockData(Context context) {
        if (PHOTO_LIST.isEmpty()) {
            init(context);
        }
        return PHOTO_LIST;
    }

    private static void init(Context context) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Resources res = context.getResources();
        PHOTO_LIST.add(BitmapFactory.decodeResource(res, R.drawable.picture4, options));
        PHOTO_LIST.add(BitmapFactory.decodeResource(res, R.drawable.picture5, options));
        PHOTO_LIST.add(BitmapFactory.decodeResource(res, R.drawable.picture6, options));
    }
}
