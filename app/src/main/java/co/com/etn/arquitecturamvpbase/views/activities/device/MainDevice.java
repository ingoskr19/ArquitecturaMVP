package co.com.etn.arquitecturamvpbase.views.activities.device;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.helper.Constants;
import co.com.etn.arquitecturamvpbase.helper.Permissions;
import co.com.etn.arquitecturamvpbase.views.adapters.PhotoAdapter;

public class MainDevice extends AppCompatActivity {

    private ImageView audio;
    private ImageView camera;
    private ImageView gallery;
    private RecyclerView photoItems;
    private LinearLayout llPhotoItems;
    private PhotoAdapter adapter;
    private ArrayList<String> arrayFiles = new ArrayList<>();
    private File photoFile;
    private ImageView video;
    private ImageView barcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_device);
        init();
        setListeners();
        callAdapter();
        observerLayoutGetSize();
    }

    private void observerLayoutGetSize() {
        final ViewTreeObserver observer = llPhotoItems.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llPhotoItems.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                adapter.setSize(llPhotoItems.getWidth(),llPhotoItems.getHeight());
                adapter.setFiles(arrayFiles);
                adapter.notifyDataSetChanged();

            }
        });
    }

    private void callAdapter() {
        adapter = new PhotoAdapter(getApplicationContext());
        adapter.setFiles(arrayFiles);
        photoItems.setAdapter(adapter);

    }

    private void setListeners() {
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCamera();
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGallery();
            }
        });
        audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainDevice.this, AudioActivity.class);
                startActivity(intent);
            }
        });

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainDevice.this, VideoActivity.class);
                startActivity(intent);
            }
        });

        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainDevice.this, CodeReaderActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showCamera() {
        if(Permissions.isGrantedPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            showCameraIntent();
        } else {
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE};
            Permissions.verifyPermissions(this,permissions);
        }
    }

    private void showCameraIntent() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = null;

        if(takePicture.resolveActivity(getPackageManager()) != null) {
            try {
                photoFile = createPhotoFile();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (photoFile != null){
            Uri photoUri = FileProvider.getUriForFile(this,"co.com.etn.arquitecturamvpbase",photoFile);

            List<ResolveInfo> resolveInfoList = getPackageManager().queryIntentActivities(takePicture,
                    PackageManager.MATCH_DEFAULT_ONLY);

            for (ResolveInfo resolveInfo: resolveInfoList){
                String packageName = resolveInfo.activityInfo.packageName;
                grantUriPermission(packageName,photoUri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            takePicture.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
            super.startActivityForResult(takePicture,Constants.CAMERA_CAPTURE);
        }
    }

    private File createPhotoFile() throws IOException {
        String imageFileName = Constants.PREFIX_FILE_IMAGE +
                new SimpleDateFormat(Constants.FORMAT_DATE_FILE).format(new Date());

        File storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if(storageDir != null && !storageDir.exists()){
            boolean result = storageDir.mkdir();
            if (!result) {
                return null;
            }
        }

        return File.createTempFile(imageFileName,Constants.SUFFIX_FILE_NAME,storageDir);
    }

    private void showGallery() {
        if(Permissions.isGrantedPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            showGalleryIntent();
        } else {
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
            Permissions.verifyPermissions(this,permissions);
        }
    }

    private void showGalleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        if(Build.VERSION.SDK_INT < Constants.GALLERY_KITKAT_LOWER){
          startActivityForResult(intent, Constants.GALLERY_KITKAT_LOWER);

        } else {
            String[] types = {"image/*"};
            intent.putExtra(Intent.EXTRA_MIME_TYPES,types);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
            startActivityForResult(intent,Constants.GALLERY);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == Constants.GALLERY_KITKAT_LOWER && data !=null && data.getData() != null){
                resultGalleryKitKatLower(data.getData());
        }

        if (requestCode == Constants.GALLERY) {
            resultGalleryGallery(data);
        }

        if (requestCode == Constants.CAMERA_CAPTURE) {
            resultCameraCapture(data);
        }

        super.onActivityResult(requestCode,resultCode,data);
    }

    private void resultCameraCapture(Intent data) {
        if(photoFile!=null){
            Log.e("photoFile",photoFile.getPath());
            setArrayFilesName(photoFile.getPath());
            adapter.notifyDataSetChanged();
        }
    }

    @TargetApi(16)
    private void resultGalleryGallery(Intent data) {
        if(data != null && data.getClipData() != null) {
            ClipData clipData = data.getClipData();
            for (int i = 0; i < clipData.getItemCount(); i++) {
                grantUriPermission(getPackageName(), clipData.getItemAt(i).getUri(), Intent.FLAG_GRANT_READ_URI_PERMISSION);
                setArrayFilesName(clipData.getItemAt(i).getUri().toString());
            }
        }else if(data != null){
            resultGalleryKitKatLower(data.getData());
        }
        adapter.notifyDataSetChanged();
    }

    private void resultGalleryKitKatLower(Uri data) {
        if (data != null) {
            grantUriPermission(getPackageName(), data, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            setArrayFilesName(data.toString());
            adapter.notifyDataSetChanged();
        }
    }

    private void setArrayFilesName(String filename) {
        arrayFiles.add(filename);
        adapter.setFiles(arrayFiles);
    }

    private void init() {
        barcode = (ImageView) findViewById(R.id.device_barcode);
        video = (ImageView) findViewById(R.id.device_video);
        audio = (ImageView) findViewById(R.id.device_audio);
        camera = (ImageView) findViewById(R.id.device_camera);
        gallery = (ImageView) findViewById(R.id.device_gallery);
        photoItems = (RecyclerView) findViewById(R.id.device_rv_items);
        llPhotoItems = (LinearLayout) findViewById(R.id.devide_llphotoitems);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        photoItems.setLayoutManager(layoutManager);
    }


}
