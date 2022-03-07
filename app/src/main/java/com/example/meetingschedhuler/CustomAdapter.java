package com.example.meetingschedhuler;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    Context context;
    ArrayList<Contact> contacts;
    MyDatabaseHelper myDatabaseHelper;




    CustomAdapter(Context context, ArrayList<Contact> contacts){
        this.context= context;
        this.contacts = contacts;
        myDatabaseHelper = new MyDatabaseHelper(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.contact_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        initializeComponents(holder, position);

        ///////////////////////////////////////////////////////////////////////////////////////////
        //Collapsable Social Links
        holder.btn_collapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!holder.isSocialMediaOpen){
                    holder.btn_collapse.setImageResource(R.drawable.ic_square_up_caret);
                    holder.layout_socialMedias.setVisibility(View.VISIBLE);
                    holder.isSocialMediaOpen = true;
                }
                else {
                    holder.btn_collapse.setImageResource(R.drawable.ic_square_down_caret);
                    holder.layout_socialMedias.setVisibility(View.GONE);
                    holder.isSocialMediaOpen = false;
                }
            }
        });
        ///////////////////////////////////////////////////////////////////////////////////////////
        // call to number
        holder.btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String CallingNumber = contacts.get(position).get_cellPhone().toString();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(("tel:"+ CallingNumber)));
                context.startActivity(intent);
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////
        //Send SMS
        holder.btn_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SendSMS.class);
                intent.putExtra("PhoneNumber", String.valueOf(contacts.get(position).get_cellPhone()));
                context.startActivity(intent);

            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////
        //Send SMS

        holder.btn_favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               int i = contacts.get(position).get_is_favourite();
               if(i == 0){
                   holder.btn_favourite.setImageResource(R.drawable.ic_favourite_selected);
                   contacts.get(position).set_is_favourite(1);
               }else {
                   holder.btn_favourite.setImageResource(R.drawable.ic_favourite);
                   contacts.get(position).set_is_favourite(0);

               }
              long result = myDatabaseHelper.toggleContactType(contacts.get(position).get_id(), ContactType.FAVOURITE);
               if(result == 1){
                   Toast.makeText(view.getContext(), "The selected contact saved as favourite", Toast.LENGTH_SHORT).show();
               }
               else {
                   Toast.makeText(view.getContext(), "Some error occurred ", Toast.LENGTH_SHORT).show();

               }
            }
        });

        holder.btn_important.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = contacts.get(position).get_is_important();
                if(i == 0){
                    holder.btn_important.setImageResource(R.drawable.ic_important_selected);
                    contacts.get(position).set_is_important(1);
                }else {
                    holder.btn_important.setImageResource(R.drawable.ic_important);
                    contacts.get(position).set_is_important(0);

                }
                long result = myDatabaseHelper.toggleContactType(contacts.get(position).get_id(), ContactType.IMPORTANT);
                if(result == 1){
                    Toast.makeText(view.getContext(), "The selected contact saved as important", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(view.getContext(), "Some error occurred ", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void initializeComponents(MyViewHolder holder, int position) {
        holder.contactName.setText(contacts.get(position).get_firstName() + " " + contacts.get(position).get_lastName());
        holder.cellphone.setText(contacts.get(position).get_cellPhone());

        if(contacts.get(position).get_is_favourite() ==1){
            holder.btn_favourite.setImageResource(R.drawable.ic_favourite_selected);
        }
        else {
            holder.btn_favourite.setImageResource(R.drawable.ic_favourite);
        }
        if(contacts.get(position).get_is_important() ==1){
            holder.btn_important.setImageResource(R.drawable.ic_important_selected);
        }
        else {
            holder.btn_important.setImageResource(R.drawable.ic_important);
        }


    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView contactName, cellphone;
        LinearLayout mainLayout;
        LinearLayout layout_socialMedias;
        ImageButton btn_favourite, btn_important, btn_collapse, btn_call, btn_sms;
        Boolean isSocialMediaOpen = false;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            contactName = itemView.findViewById(R.id.contact_fullName);
            cellphone = itemView.findViewById(R.id.contact_number);
            mainLayout = itemView.findViewById(R.id.main_layout);

            btn_favourite = itemView.findViewById(R.id.btn_favourite);
            btn_important = itemView.findViewById(R.id.btn_important);

            btn_collapse = itemView.findViewById(R.id.btn_collapse);
            btn_call = itemView.findViewById(R.id.contact_call);
            btn_sms = itemView.findViewById(R.id.contact_sms);

            layout_socialMedias = itemView.findViewById(R.id.linearLayout_socialMedias);


        }
    }
}
