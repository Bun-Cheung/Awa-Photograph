package com.awareness.photograph;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.awareness.photograph.adapter.PhotoDetailAdapter;
import com.awareness.photograph.mockdata.PhotoDetailData;

public class PhotoDetailActivity extends AppCompatActivity {

    private RecyclerView mPhotoDetailRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        initView();
        fetchData();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Scene Name");
        setSupportActionBar(toolbar);
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        decorView.setSystemUiVisibility(option);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setNavigationBarColor(Color.WHITE);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mPhotoDetailRv = findViewById(R.id.recycle_view_photo_detail);
        mPhotoDetailRv.setLayoutManager(new LinearLayoutManager(this));
    }

    private void fetchData() {
        mPhotoDetailRv.setAdapter(new PhotoDetailAdapter(PhotoDetailData.getPhotoDetailList()));
    }
}
