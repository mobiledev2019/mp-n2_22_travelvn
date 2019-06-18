package com.example.home;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.HttpResponse;
import com.example.R;
import com.example.bottomnavigation.FadePageTransformer;
import com.example.controller.RequestHandler;
import com.example.model.URLjson;
import com.example.travelevent.Constants;
import com.google.android.gms.ads.internal.gmsg.HttpClient;
import com.squareup.picasso.Request;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
//import com.example.bottomnavigation.R;

public class Travel extends AppCompatActivity implements View.OnClickListener{
    private Context mContext;
    public ArrayList<String> imageURL;
    private RecyclerView viewImageTravel;
    private TextView textViewInfo;
    private TextView textViewPlace;
    private TextView textViewName;
    private String info;
    private String place;
    private String name;
    private Button buttonChoose;
    private Button buttonUpload;
    private ImageView imageView;
    public static final String UPLOAD_URL = "http://192.168.43.199/upload.php";
    public static final String UPLOAD_KEY = "image";

    private int PICK_IMAGE_REQUEST = 1;
    private Uri filePath;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        imageURL = intent.getStringArrayListExtra("IMAGE_TRAVEL");
        info = intent.getStringExtra("INFORMATION");
        place = intent.getStringExtra("PLACE");
        name = intent.getStringExtra("NAME");

        viewImageTravel = findViewById(R.id.image_travel);
        RecyclerView.LayoutManager layoutCategories = new LinearLayoutManager(getApplicationContext());
        viewImageTravel.setLayoutManager(layoutCategories);

        ImageTravelAdapter imageTravel = new ImageTravelAdapter(getApplicationContext(), imageURL);
        viewImageTravel.setAdapter(imageTravel);
        textViewInfo = findViewById(R.id.information);
//        textViewPlace = findViewById(R.id.place);
        textViewName = findViewById(R.id.name);
        textViewInfo.setText(info);
//        textViewPlace.setText(place);
        textViewName.setText(name);
        init();

    }



    void init(){
        buttonChoose = findViewById(R.id.choose);
        buttonUpload = findViewById(R.id.upload);

        imageView = findViewById(R.id.imageUpload);

        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
    }
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(){
        class UploadImage extends AsyncTask<Bitmap,Void,String>{
            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                loading = ProgressDialog.show(mContext, "Uploading Image", "Please wait...",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String,String> data = new HashMap<>();
//                data.put(UPLOAD_KEY, uploadImage);
//                data.put("name",getFileName(filePath));

                data.put("travel_id",""+6);
                data.put(UPLOAD_KEY, uploadImage);

                String result = rh.postRequest(URLjson.UPLOAD,data);
                Log.d("ket qua", result);
                return result;
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }

    @Override
    public void onClick(View v) {
        if (v == buttonChoose) {
            showFileChooser();
        }
        if(v == buttonUpload){
            if(filePath!=null) {
                uploadImage();
            } else {
                Toast.makeText(mContext,"Select Image",Toast.LENGTH_LONG).show();
            }
        }
    }
    String getFileName(Uri uri){
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}
