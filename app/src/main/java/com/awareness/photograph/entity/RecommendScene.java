package com.awareness.photograph.entity;

public class RecommendScene {
    private String sceneName;
    private int image;
    private int checkInCount;

    public RecommendScene(String sceneName, int image, int checkInCount) {
        this.sceneName = sceneName;
        this.image = image;
        this.checkInCount = checkInCount;
    }

    public String getSceneName() {
        return sceneName;
    }

    public int getImage() {
        return image;
    }

    public int getCheckInCount() {
        return checkInCount;
    }
}
