package com.awareness.photograph;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.awareness.photograph.adapter.PhotoDetailAdapter;
import com.awareness.photograph.entity.PhotoDetail;
import com.awareness.photograph.entity.SimpleWeatherInfo;
import com.awareness.photograph.presetdata.PhotoDetailData;
import com.awareness.photograph.presetdata.WeatherDescription;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.huawei.hms.kit.awareness.Awareness;
import com.huawei.hms.kit.awareness.status.AmbientLightStatus;
import com.huawei.hms.kit.awareness.status.WeatherStatus;
import com.huawei.hms.kit.awareness.status.weather.Situation;
import com.huawei.hms.kit.awareness.status.weather.WeatherSituation;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static int LOCATION_REQUEST_CODE = 920;
    private final static int CAMERA_REQUEST_CODE = 940;
    private final static int REQUEST_IMAGE_CAPTURE = 1;
    private final String TAG = getClass().getSimpleName();
    private String mCurrentPhotoPath;
    private List<PhotoDetail> mPhotoDetailList;

    private RecyclerView mMainPhotoRv;
    private PhotoDetailAdapter mPhotoDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        checkLocationPermission();
        fetchData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCurrentPhotoPath != null) {
            File tmp = new File(mCurrentPhotoPath);
            if (tmp.exists() && tmp.length() == 0) {
                if (tmp.delete()) {
                    Log.i(TAG, "delete empty tmp file success");
                }
            } else {
                attachAwarenessInfoToPhoto(mCurrentPhotoPath);
                mCurrentPhotoPath = null;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

        mMainPhotoRv = findViewById(R.id.recycle_view_main_photo);
        mMainPhotoRv.setLayoutManager(new LinearLayoutManager(this));
        FloatingActionButton shootFab = findViewById(R.id.fab_shoot);
        shootFab.setOnClickListener(v -> {
            checkCameraPermission();
        });
        mMainPhotoRv.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (oldScrollY < scrollY && shootFab.getVisibility() == View.VISIBLE) {
                shootFab.hide();
            } else if (oldScrollY > scrollY && shootFab.getVisibility() != View.VISIBLE) {
                shootFab.show();
            }
        });
    }

    private void fetchData() {
        mPhotoDetailList = PhotoDetailData.getPhotoDetailList();
        mPhotoDetailAdapter = new PhotoDetailAdapter(mPhotoDetailList);
        mPhotoDetailAdapter.setOnClickCollectionListener((view, position) -> {
            PhotoDetail photoDetail = mPhotoDetailList.get(position);
            if (photoDetail.isCollected()) {
                ((ImageButton) view).setImageResource(R.drawable.ic_collect_empty);
                photoDetail.setCollected(false);
            } else {
                ((ImageButton) view).setImageResource(R.drawable.ic_collect_fill);
                photoDetail.setCollected(true);
            }
        });
        mMainPhotoRv.setAdapter(mPhotoDetailAdapter);
    }

    private void checkLocationPermission() {
        String[] permissions;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION};
            ActivityCompat.requestPermissions(this, permissions, LOCATION_REQUEST_CODE);
        } else {
            permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this, permissions, LOCATION_REQUEST_CODE);
        }
    }

    private void checkCameraPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        } else {
            dispatchTackPictureIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean permissionDenied = false;
        if (requestCode == CAMERA_REQUEST_CODE) {
            for (int result : grantResults) {
                if (result == PackageManager.PERMISSION_DENIED) {
                    permissionDenied = true;
                    Toast.makeText(this, "grant Permission failed", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
            if (!permissionDenied) {
                dispatchTackPictureIntent();
            }
        } else if (requestCode == LOCATION_REQUEST_CODE) {
            for (int result : grantResults) {
                if (result == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "grant Location Permission failed", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
    }

    private void dispatchTackPictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                Log.e(TAG, "create image file failed");
            }
            if (photoFile != null) {
                Uri photoUri = FileProvider.getUriForFile(this,
                        "com.awareness.photograph.provider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timestamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void attachAwarenessInfoToPhoto(String path) {
        PhotoDetail photoDetail = new PhotoDetail();
        photoDetail.setPhotoPath(path);
        photoDetail.setTimestamp(System.currentTimeMillis());
        attachLocationInfo(photoDetail);
        attachWeatherIfo(photoDetail);
        attachLightInfo(photoDetail);
    }

    private void attachLocationInfo(PhotoDetail photoDetail) {
        Awareness.getCaptureClient(this).getLocation()
                .addOnSuccessListener(locationResponse -> {
                    Location location = locationResponse.getLocation();
                    photoDetail.setLatitude(Utils.formatDouble(location.getLatitude()));
                    photoDetail.setLongitude(Utils.formatDouble(location.getLongitude()));
                    if (photoDetail.isInfoFilled()) {
                        addItemToView(photoDetail);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "get Location failed");
                    e.printStackTrace();
                });
    }

    private void attachWeatherIfo(PhotoDetail photoDetail) {
        //get weather info
        Awareness.getCaptureClient(this).getWeatherByDevice()
                .addOnSuccessListener(weatherStatusResponse -> {
                    WeatherStatus weatherStatus = weatherStatusResponse.getWeatherStatus();
                    WeatherSituation weatherSituation = weatherStatus.getWeatherSituation();
                    Situation situation = weatherSituation.getSituation();
                    SimpleWeatherInfo simpleWeatherInfo = new SimpleWeatherInfo();
                    simpleWeatherInfo.setTemperature(situation.getTemperatureC());
                    simpleWeatherInfo.setUvIndex(situation.getUvIndex());
                    simpleWeatherInfo.setWindSpeed(situation.getWindSpeed());
                    String weatherStr = WeatherDescription.getCnWeatherDesc(situation.getCnWeatherId());
                    if (weatherStr == null) {
                        weatherStr = WeatherDescription.getWeatherDesc(situation.getWeatherId());
                    }
                    simpleWeatherInfo.setWeather(weatherStr);
                    photoDetail.setWeatherInfo(simpleWeatherInfo);
                    if (photoDetail.isInfoFilled()) {
                        addItemToView(photoDetail);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "get weather failed");
                    e.printStackTrace();
                });
    }

    private void attachLightInfo(PhotoDetail photoDetail) {
        Awareness.getCaptureClient(this).getLightIntensity()
                .addOnSuccessListener(ambientLightResponse -> {
                    AmbientLightStatus status = ambientLightResponse.getAmbientLightStatus();
                    photoDetail.setLightIntensity(Utils.formatDouble(status.getLightIntensity()));
                    if (photoDetail.isInfoFilled()) {
                        addItemToView(photoDetail);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "get light intensity failed");
                    e.printStackTrace();
                });
    }

    private void addItemToView(PhotoDetail photoDetail) {
        mPhotoDetailList.add(photoDetail);
        mPhotoDetailAdapter.notifyItemInserted(mPhotoDetailList.size() - 1);
    }
}
