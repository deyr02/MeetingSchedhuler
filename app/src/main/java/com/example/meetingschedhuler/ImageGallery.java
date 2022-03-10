package com.example.meetingschedhuler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ImageGallery extends AppCompatActivity {

    private  static  int PICK_IMAGE_REQUEST = 100;
    private Uri imageFilePath;
    private Bitmap imageToStore;
    private ImageView  testImageView;
    private FloatingActionButton btn_add_image;
    private RecyclerView recyclerView;
    private  ImageAdapter imageAdapter;

    ArrayList<Image> allImages;
    Integer _contactId;
    private  MyDatabaseHelper myDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_gallery);
        myDatabaseHelper = new MyDatabaseHelper(ImageGallery.this);
        btn_add_image = findViewById(R.id.btn_add_image);
        recyclerView = findViewById(R.id.image_recycle_view);

        if(getIntent().hasExtra("ContactID")){
            _contactId = Integer.parseInt( getIntent().getStringExtra("ContactID"));
        }


        allImages = myDatabaseHelper.getAllImages(_contactId);
        imageAdapter = new ImageAdapter( ImageGallery.this,this, allImages);
        recyclerView.setAdapter(imageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ImageGallery.this));

        btn_add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage(view);



            }
        });
    }

    public  void  chooseImage(View objectView){
        try{
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        }
        catch (Exception ex){
            Toast.makeText(ImageGallery.this, "Some error.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try{
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
                imageFilePath = data.getData();
                imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(), imageFilePath);
                Image image = new Image(null, _contactId, 0, imageToStore );
              long result =  myDatabaseHelper.addImage(image);
              if(result ==-1){
                  Toast.makeText(ImageGallery.this, "Some error occurred during uploading image.", Toast.LENGTH_SHORT).show();
              }
              else {
                  Toast.makeText(ImageGallery.this, "Image Uploaded successfully.", Toast.LENGTH_SHORT).show();
                  recreate();
              }
            }
        }
        catch (Exception ex){
            Toast.makeText(ImageGallery.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



}