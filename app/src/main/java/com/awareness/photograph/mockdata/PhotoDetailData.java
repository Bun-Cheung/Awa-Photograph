package com.awareness.photograph.mockdata;

import com.awareness.photograph.R;
import com.awareness.photograph.Utils;
import com.awareness.photograph.entity.PhotoDetail;
import com.awareness.photograph.entity.SimpleWeatherInfo;

import java.util.ArrayList;
import java.util.List;

public class PhotoDetailData {
    private static final List<PhotoDetail> PHOTO_DETAIL_LIST = new ArrayList<>();

    static {
        PhotoDetail.Builder builder = new PhotoDetail.Builder();
        builder.photo(R.drawable.picture5).date(Utils.getCurrentDate()).collectCount(13)
                .praiseCount(70).commentCount(17).site("Beautiful beach");
        SimpleWeatherInfo weatherInfo = new SimpleWeatherInfo(25, "Sunny", 4, 9);
        builder.weatherInfo(weatherInfo);
        PHOTO_DETAIL_LIST.add(builder.build());

        builder.photo(R.drawable.picture6).date(Utils.getCurrentDate()).collectCount(30)
                .praiseCount(150).commentCount(30).site("Beautiful beach");
        PHOTO_DETAIL_LIST.add(builder.build());

        builder.photo(R.drawable.picture7).date(Utils.getCurrentDate()).collectCount(80)
                .praiseCount(250).commentCount(30).site("Beautiful beach");
        PHOTO_DETAIL_LIST.add(builder.build());
    }

    public static List<PhotoDetail> getPhotoDetailList() {
        return PHOTO_DETAIL_LIST;
    }
}
