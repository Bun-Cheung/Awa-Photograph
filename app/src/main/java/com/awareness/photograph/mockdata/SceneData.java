package com.awareness.photograph.mockdata;

import com.awareness.photograph.R;
import com.awareness.photograph.entity.RecommendScene;

import java.util.ArrayList;
import java.util.List;

public class SceneData {
    private static final List<RecommendScene> SCENE_LIST = new ArrayList<>();

    static {
        RecommendScene sceneA = new RecommendScene("Beautiful beach", R.drawable.picture1,155);
        RecommendScene sceneB = new RecommendScene("Beautiful flower",R.drawable.picture2,60);
        RecommendScene sceneC = new RecommendScene("Ancient Villages",R.drawable.picture3,400);

        SCENE_LIST.add(sceneA);
        SCENE_LIST.add(sceneB);
        SCENE_LIST.add(sceneC);
    }

    public static List<RecommendScene> getMockData() {
        return SCENE_LIST;
    }
}
