package com.awareness.photograph;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.awareness.photograph.adapter.MainPhotoListAdapter;
import com.awareness.photograph.adapter.RecommendSceneAdapter;
import com.awareness.photograph.mockdata.MainPhotoListData;
import com.awareness.photograph.mockdata.SceneData;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    private RecyclerView mSceneRv;
    private RecyclerView mMainPhotoRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        fetchData();
    }

    private void initView() {
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        decorView.setSystemUiVisibility(option);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setNavigationBarColor(Color.WHITE);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        mSceneRv = findViewById(R.id.recycle_view_scene);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mSceneRv.setLayoutManager(linearLayoutManager);

        mMainPhotoRv = findViewById(R.id.recycle_view_main_photo);
        mMainPhotoRv.setLayoutManager(new LinearLayoutManager(this));
    }

    private void fetchData() {
        RecommendSceneAdapter adapter = new RecommendSceneAdapter(SceneData.getMockData());
        adapter.setOnItemClickListener(position -> {
            Intent intent = new Intent(this, PhotoDetailActivity.class);
            startActivity(intent);
        });
        mSceneRv.setAdapter(adapter);
        mMainPhotoRv.setAdapter(new MainPhotoListAdapter(MainPhotoListData.getMockData()));
    }
}
