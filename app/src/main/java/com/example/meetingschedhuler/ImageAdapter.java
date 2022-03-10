package com.example.meetingschedhuler;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyImageHolder> {
    Activity activity;
    Context context;
    ArrayList<Image>images;
    MyDatabaseHelper myDatabaseHelper;
    ViewGroup _parent;


    public ImageAdapter(Activity activity, Context context, ArrayList<Image> images) {
        this.context = context;
        this.images = images;
        this.activity = activity;
        myDatabaseHelper = new MyDatabaseHelper(context);


    }

    @NonNull
    @Override
    public ImageAdapter.MyImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        this._parent = parent;
        View view = inflater.inflate(R.layout.image_row, parent, false);
        return new MyImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.MyImageHolder holder, int position) {
            initializeComponents(holder, position);
            holder.btn_delete_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick( View view) {
                    confirmDialogue(holder, holder.getAdapterPosition());

                }
            });
            holder.chk_setProfileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(holder.chk_setProfileImage.isChecked()){
                        changeProfileImage(holder.getAdapterPosition());


                    }

                }
            });
    }

    private void changeProfileImage(int position) {
        long result = myDatabaseHelper.setProfileImage(images.get(position).get_id(), images.get(position).get_contactId());

        if (result == -1){
            Toast.makeText(context, "Some error occurred during changing profile image?", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, " The profile image has been changed successfully.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, ImageGallery.class);
            intent.putExtra("ContactID",String.valueOf(images.get(position).get_contactId()));
            activity.startActivityForResult(intent, 1);
            activity.onBackPressed();


        }
    }


    private  void confirmDialogue(MyImageHolder imageHolder, Integer position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete Image");
        builder.setMessage("Are You Sure you want to delete the image?" );
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                long result = myDatabaseHelper.deleteImage(images.get(position).get_id());
                if (result == -1){
                    Toast.makeText(context, "Some error occurred during delete image?", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, "The image deleted successfully.", Toast.LENGTH_SHORT).show();
                    ViewGroup.LayoutParams params = imageHolder.linearLayout.getLayoutParams();
                    params.height =0;
                    params.width =0;
                    imageHolder.linearLayout.setLayoutParams(params);
                }
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
    private void initializeComponents(MyImageHolder holder, int position) {
        holder.iv_imageBox.setImageBitmap(images.get(position).getImage());
        holder.chk_setProfileImage.setChecked(
                images.get(position).getIsProfilePicture() == 1? true:false
        );
    }

    @Override
    public int getItemCount() {
        return  images.size();
    }

    public class MyImageHolder extends RecyclerView.ViewHolder {
        ImageButton btn_delete_image;
        CheckBox chk_setProfileImage;
        ImageView iv_imageBox;
        LinearLayout linearLayout;

        public MyImageHolder(@NonNull View itemView) {
            super(itemView);
            btn_delete_image = itemView.findViewById(R.id.btn_delete_image);
            chk_setProfileImage = itemView.findViewById(R.id.chk_profile_pic);
            iv_imageBox= itemView.findViewById(R.id.iv_picture);
            linearLayout = itemView.findViewById(R.id.imageLayout);
        }
    }
}
