package com.awareness.photograph.entity;

public class PhotoDetail {
    private int photo;
    private String date;
    private SimpleWeatherInfo weatherInfo;
    private String site;
    private int collectCount;
    private int praiseCount;
    private int commentCount;

    private PhotoDetail(Builder builder) {
        photo = builder.photo;
        date = builder.date;
        weatherInfo = builder.weatherInfo;
        site = builder.site;
        collectCount = builder.collectCount;
        praiseCount = builder.praiseCount;
        commentCount = builder.commentCount;
    }

    public int getPhoto() {
        return photo;
    }

    public String getDate() {
        return date;
    }

    public SimpleWeatherInfo getWeatherInfo() {
        return weatherInfo;
    }

    public String getSite() {
        return site;
    }

    public int getCollectCount() {
        return collectCount;
    }

    public int getPraiseCount() {
        return praiseCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public static final class Builder {
        private int photo;
        private String date;
        private SimpleWeatherInfo weatherInfo;
        private String site;
        private int collectCount;
        private int praiseCount;
        private int commentCount;

        public Builder() {
        }

        public Builder photo(int val) {
            photo = val;
            return this;
        }

        public Builder date(String val) {
            date = val;
            return this;
        }

        public Builder weatherInfo(SimpleWeatherInfo val) {
            weatherInfo = val;
            return this;
        }

        public Builder site(String val) {
            site = val;
            return this;
        }

        public Builder collectCount(int val) {
            collectCount = val;
            return this;
        }

        public Builder praiseCount(int val) {
            praiseCount = val;
            return this;
        }

        public Builder commentCount(int val) {
            commentCount = val;
            return this;
        }

        public PhotoDetail build() {
            return new PhotoDetail(this);
        }
    }
}
