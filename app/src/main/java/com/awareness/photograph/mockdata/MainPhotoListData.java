package com.awareness.photograph.mockdata;

import com.awareness.photograph.R;

import java.util.ArrayList;
import java.util.List;

public class MainPhotoListData {
    private static final List<Integer> PHOTO_LIST = new ArrayList<>();
    static {
        PHOTO_LIST.add(R.drawable.picture4);
        PHOTO_LIST.add(R.drawable.picture5);
        PHOTO_LIST.add(R.drawable.picture6);
    }

    public static List<Integer> getMockData() {
        return PHOTO_LIST;
    }
}
