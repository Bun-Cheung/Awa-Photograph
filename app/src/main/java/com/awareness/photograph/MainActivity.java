package com.awareness.photograph;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.awareness.photograph.adapter.PhotoDetailAdapter;
import com.awareness.photograph.entity.PhotoDetail;
import com.awareness.photograph.entity.SimpleWeatherInfo;
import com.awareness.photograph.presetdata.PhotoDetailData;
import com.awareness.photograph.presetdata.WeatherDescription;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.huawei.hms.kit.awareness.Awareness;
import com.huawei.hms.kit.awareness.barrier.AmbientLightBarrier;
import com.huawei.hms.kit.awareness.barrier.AwarenessBarrier;
import com.huawei.hms.kit.awareness.barrier.BarrierUpdateRequest;
import com.huawei.hms.kit.awareness.barrier.LocationBarrier;
import com.huawei.hms.kit.awareness.barrier.TimeBarrier;
import com.huawei.hms.kit.awareness.status.AmbientLightStatus;
import com.huawei.hms.kit.awareness.status.WeatherStatus;
import com.huawei.hms.kit.awareness.status.weather.Situation;
import com.huawei.hms.kit.awareness.status.weather.WeatherSituation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private final static int LOCATION_REQUEST_CODE = 920;
    private final static int CAMERA_REQUEST_CODE = 940;
    private final static int REQUEST_IMAGE_CAPTURE = 1;
    private final String TAG = getClass().getSimpleName();
    private String mCurrentPhotoPath;
    private List<PhotoDetail> mPhotoDetailList = new ArrayList<>();

    private RecyclerView mMainPhotoRv;
    private ProgressBar mProgressBar;
    private TextView mProcessTv;
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
        mProgressBar = findViewById(R.id.pb_loading);
        mProcessTv = findViewById(R.id.tv_loading);
    }

    private void fetchData() {
        if (mPhotoDetailList.size() == 0) {
            mPhotoDetailList.addAll(PhotoDetailData.getPresetData());
        }
        List<PhotoDetail> storedList = Utils.getAllFromDB(this);
        if (storedList.size() == 0) {
            Log.i(TAG, "no data in db");
        } else {
            removeInvalidData(storedList);
            mPhotoDetailList.addAll(storedList);
        }
        mPhotoDetailAdapter = new PhotoDetailAdapter(mPhotoDetailList);
        mPhotoDetailAdapter.setOnClickCollectionListener((view, position) -> {
            PhotoDetail photoDetail = mPhotoDetailList.get(position);
            if (photoDetail.isCollected()) {
                deleteAwarenessBarrier(photoDetail.getLabel());
                ((ImageButton) view).setImageResource(R.drawable.ic_collect_empty);
                photoDetail.setCollected(false);
                Utils.updateDBData(this, photoDetail);
            } else {
                showLabelSettingDialog(view, photoDetail);
            }
        });
        mMainPhotoRv.setAdapter(mPhotoDetailAdapter);
    }

    private void showLabelSettingDialog(View iconView, PhotoDetail photoDetail) {
        AlertDialog alertDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Label setting");
        View view = LayoutInflater.from(this).inflate(R.layout.layout_label_setting, null);
        builder.setView(view);
        final EditText editText = view.findViewById(R.id.edit_label);
        builder.setPositiveButton("OK", null);
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            Log.i(TAG, "cancel collecting");
        });
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String label = editText.getText().toString();
            if (label.equals("")) {
                showToast("label cannot be empty.");
            } else {
                photoDetail.setLabel(label);
                ((ImageButton) iconView).setImageResource(R.drawable.ic_collect_fill);
                photoDetail.setCollected(true);
                Utils.updateDBData(this, photoDetail);
                addAwarenessBarrier(photoDetail);
                alertDialog.dismiss();
            }
        });
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
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
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
                break;
            case LOCATION_REQUEST_CODE:
                for (int result : grantResults) {
                    if (result == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(this, "grant Location Permission failed", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                break;
            default:
                break;
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
        showLoadingView();
        PhotoDetail photoDetail = new PhotoDetail();
        photoDetail.setPhotoPath(path);
        photoDetail.setPhoto(Utils.decodeImage(this, path));
        photoDetail.setTimestamp(System.currentTimeMillis());
        attachLocationInfo(photoDetail);
        attachWeatherIfo(photoDetail);
        attachLightInfo(photoDetail);
    }

    private void attachLocationInfo(PhotoDetail photoDetail) {
        //get location info
        Awareness.getCaptureClient(this).getLocation()
                .addOnSuccessListener(locationResponse -> {
                    Location location = locationResponse.getLocation();
                    photoDetail.setLatitude(Utils.roundingValue(location.getLatitude()));
                    photoDetail.setLongitude(Utils.roundingValue(location.getLongitude()));
                    if (photoDetail.isInfoFilled()) {
                        addItemToView(photoDetail);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "get Location failed");
                    showToast("get location failed");
                    e.printStackTrace();
                    hideLoadingView();
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
                    showToast("get weather failed");
                    e.printStackTrace();
                    hideLoadingView();
                });
    }

    private void attachLightInfo(PhotoDetail photoDetail) {
        //get light intensity info
        Awareness.getCaptureClient(this).getLightIntensity()
                .addOnSuccessListener(ambientLightResponse -> {
                    AmbientLightStatus status = ambientLightResponse.getAmbientLightStatus();
                    float lightIntensity = status.getLightIntensity();
                    photoDetail.setLightIntensity((float) Utils.roundingValue(lightIntensity));
                    if (photoDetail.isInfoFilled()) {
                        addItemToView(photoDetail);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "get light intensity failed");
                    showToast("get LightIntensity failed");
                    e.printStackTrace();
                    hideLoadingView();
                });
    }

    private void addItemToView(PhotoDetail photoDetail) {
        hideLoadingView();
        mPhotoDetailList.add(photoDetail);
        mPhotoDetailAdapter.notifyItemInserted(mPhotoDetailList.size() - 1);
        mMainPhotoRv.scrollToPosition(mPhotoDetailList.size() - 1);
        Utils.insertToDB(this, photoDetail);
    }

    private void addAwarenessBarrier(PhotoDetail photoDetail) {
        //construct Barriers according to the condition of photo, and register the Barrier to HMS Awareness Kit
        //when the condition was matched,notify user to take the photo

        double radius = 300;
        long timeOfDuration = 5000;
        //When the user enter the area and stays in the area for the specified time period(in this sample is 5000ms)
        //the barrier status is TRUE.
        AwarenessBarrier locationBarrier = LocationBarrier.stay(photoDetail.getLatitude(),
                photoDetail.getLongitude(), radius, timeOfDuration);

        long timeOfDayOfPhoto = Utils.parseTimeOfDay(photoDetail.getTimestamp());
        long halfHourMillis = 30 * 60 * 1000;
        long startTimeOfDay = timeOfDayOfPhoto - halfHourMillis;
        long endTimeOfDay = timeOfDayOfPhoto + halfHourMillis;
        //When the time is in the specified time period of a specified time zone,the Barrier status is TRUE.
        //In this sample ,if the photo was taken at 16:00, the time period of this TimeBarrier will be set from 15:30 to 16:30.
        AwarenessBarrier timeBarrier = TimeBarrier.duringPeriodOfDay(TimeZone.getDefault(), startTimeOfDay, endTimeOfDay);

        float lightIntensity = photoDetail.getLightIntensity();
        float minLightIntensity = Math.max(0, lightIntensity - 1000);
        //When the illuminance is within the range specified by [min,max),the barrier status is TRUE.
        AwarenessBarrier lightBarrier = AmbientLightBarrier.range(minLightIntensity, lightIntensity + 1000);

        //Integrate barriers by logic AND operation.
        AwarenessBarrier combinedBarrier = AwarenessBarrier.and(locationBarrier, timeBarrier, lightBarrier);
        PendingIntent pendingIntent;
        Intent intent = new Intent(this, BarrierService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            pendingIntent = PendingIntent.getForegroundService(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        } else {
            pendingIntent = PendingIntent.getService(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        }
        BarrierUpdateRequest request = new BarrierUpdateRequest.Builder()
                .addBarrier(photoDetail.getLabel(), combinedBarrier, pendingIntent)
                .build();
        Awareness.getBarrierClient(this).updateBarriers(request)
                .addOnSuccessListener(aVoid -> showToast("add Awareness Barrier success"))
                .addOnFailureListener(e -> {
                    showToast("add Awareness Barrier failed");
                    Log.e(TAG, "add barrier failed");
                    e.printStackTrace();
                });
    }

    private void deleteAwarenessBarrier(String label) {
        BarrierUpdateRequest request = new BarrierUpdateRequest.Builder().deleteBarrier(label).build();
        Awareness.getBarrierClient(this).updateBarriers(request)
                .addOnSuccessListener(aVoid -> Log.i(TAG, "delete barrier success"))
                .addOnFailureListener(e -> {
                    Log.e(TAG, "delete barrier failed");
                    e.printStackTrace();
                });
    }

    private void removeInvalidData(List<PhotoDetail> photoDetailList) {
        Iterator<PhotoDetail> iterator = photoDetailList.iterator();
        PhotoDetail photoDetail;
        while (iterator.hasNext()) {
            photoDetail = iterator.next();
            Bitmap photo = Utils.decodeImage(this, photoDetail.getPhotoPath());
            if (photo == null) {
                Utils.deleteDBData(this, photoDetail);
                if (photoDetail.getLabel() != null) {
                    deleteAwarenessBarrier(photoDetail.getLabel());
                }
                iterator.remove();
            }
            photoDetail.setPhoto(photo);
        }
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    private void showLoadingView() {
        mProgressBar.setVisibility(View.VISIBLE);
        mProcessTv.setVisibility(View.VISIBLE);
    }

    private void hideLoadingView() {
        mProgressBar.setVisibility(View.GONE);
        mProcessTv.setVisibility(View.GONE);
    }
}
