package com.example.meetingschedhuler;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
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

        initializeComponents(holder, holder.getAdapterPosition());

        ///////////////////////////////////////////////////////////////////////////////////////////
        //details view
        holder.btn_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ContactDetails.class);
                intent.putExtra("ISMainUser", 0);
                intent.putExtra("_id", String.valueOf(contacts.get(holder.getAdapterPosition()).get_id()));
                context.startActivity(intent);



            }
        });




        ///////////////////////////////////////////////////////////////////////////////////////
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
                String CallingNumber = contacts.get(holder.getAdapterPosition()).get_cellPhone().toString();
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
                intent.putExtra("PhoneNumber", String.valueOf(contacts.get(holder.getAdapterPosition()).get_cellPhone()));
                context.startActivity(intent);

            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////
        //Toggle Favourite

        holder.btn_favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               int i = contacts.get(holder.getAdapterPosition()).get_is_favourite();
               if(i == 0){
                   holder.btn_favourite.setImageResource(R.drawable.ic_favourite_selected);
                   contacts.get(holder.getAdapterPosition()).set_is_favourite(1);
               }else {
                   holder.btn_favourite.setImageResource(R.drawable.ic_favourite);
                   contacts.get(holder.getAdapterPosition()).set_is_favourite(0);

               }
              long result = myDatabaseHelper.toggleContactType(contacts.get(holder.getAdapterPosition()).get_id(), ContactType.FAVOURITE);
               if(result == 1){
                   Toast.makeText(view.getContext(), "The selected contact saved as favourite", Toast.LENGTH_SHORT).show();
               }
               else {
                   Toast.makeText(view.getContext(), "Some error occurred ", Toast.LENGTH_SHORT).show();

               }
            }
        });
        ///////////////////////////////////////////////////////////////////////////////////////////
        //Toggle Important
        holder.btn_important.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = contacts.get(holder.getAdapterPosition()).get_is_important();
                if(i == 0){
                    holder.btn_important.setImageResource(R.drawable.ic_important_selected);
                    contacts.get(holder.getAdapterPosition()).set_is_important(1);
                }else {
                    holder.btn_important.setImageResource(R.drawable.ic_important);
                    contacts.get(holder.getAdapterPosition()).set_is_important(0);

                }
                long result = myDatabaseHelper.toggleContactType(contacts.get(holder.getAdapterPosition()).get_id(), ContactType.IMPORTANT);
                if(result == 1){
                    Toast.makeText(view.getContext(), "The selected contact saved as important", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(view.getContext(), "Some error occurred ", Toast.LENGTH_SHORT).show();

                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////
        //Send Email
        holder.btn_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent email_intent = new Intent( context, SendEmail.class);
                email_intent.putExtra("EmailAddress", String.valueOf(contacts.get(holder.getAdapterPosition()).get_email()));
                context.startActivity(email_intent);
            }
        });


        ///////////////////////////////////////////////////////////////////////////////////////////
        //Facebook
        holder.btn_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(contacts.get(holder.getAdapterPosition()).get_facebook()));
                    context.startActivity(browserIntent);
                }
                catch ( Exception ex){
                    Toast.makeText(context, "The facebook profile can not be open", Toast.LENGTH_SHORT).show();
                }

            }
        });
        ///////////////////////////////////////////////////////////////////////////////////////////
        //linkedIn
        holder.btn_linkedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(contacts.get(holder.getAdapterPosition()).get_linkedIn()));
                    context.startActivity(browserIntent);
                }
                catch ( Exception ex){
                    Toast.makeText(context, "The LinkedIn profile can not be open", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ///////////////////////////////////////////////////////////////////////////////////////////
        //Instagram
        holder.btn_instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(contacts.get(holder.getAdapterPosition()).get_instagram()));
                    context.startActivity(browserIntent);
                }
                catch ( Exception ex){
                    Toast.makeText(context, "The Instagram profile can not be open", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ///////////////////////////////////////////////////////////////////////////////////////////
        //website
        holder.btn_website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(contacts.get(holder.getAdapterPosition()).get_website()));
                    context.startActivity(browserIntent);
                }
                catch ( Exception ex){
                    Toast.makeText(context, "The website link can not be open", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ContactLocation.class);
                String address = formatAddress( new String[]{contacts.get(holder.getAdapterPosition()).get_streetAddress(),
                        contacts.get(holder.getAdapterPosition()).get_suburb(),
                        contacts.get(holder.getAdapterPosition()).get_city(),
                        contacts.get(holder.getAdapterPosition()).get_country()});
                intent.putExtra("Address", address);
                intent.putExtra("Name", contacts.get(holder.getAdapterPosition()).get_firstName());
                context.startActivity(intent);

            }
        });


    }

    //checks  null
    //helper method to format address
    public String formatAddress(String[] value){
        String output ="";
        for(Integer i =0; i<value.length; i++){
           if(value[i] != ""){
               output += value[i]+",";
           }
        }
        if(output != ""){
            StringBuilder sb = new StringBuilder(output);
            sb.deleteCharAt(sb.length()-1);
            output= sb.toString()+".";
        }
        return  output;
    }


    private void initializeComponents(MyViewHolder holder, int position) {
        holder.contactName.setText("Name: "+contacts.get(holder.getAdapterPosition()).get_firstName() + " " + contacts.get(holder.getAdapterPosition()).get_lastName());
        holder.cellphone.setText("Cell: "+contacts.get(holder.getAdapterPosition()).get_cellPhone());

        //profile_image
        if(contacts.get(holder.getAdapterPosition()).get_profileImage() != null){
            holder.iv_main_profile_image.setImageBitmap(contacts.get(holder.getAdapterPosition()).get_profileImage());
        }

        //favourite button
        if(contacts.get(holder.getAdapterPosition()).get_is_favourite() ==1){
            holder.btn_favourite.setImageResource(R.drawable.ic_favourite_selected);
        }
        else {
            holder.btn_favourite.setImageResource(R.drawable.ic_favourite);
        }

        //Important contact
        if(contacts.get(holder.getAdapterPosition()).get_is_important() ==1){
            holder.btn_important.setImageResource(R.drawable.ic_important_selected);
        }
        else {
            holder.btn_important.setImageResource(R.drawable.ic_important);
        }


        //if there is there is no contact type(email, facebook, etc) are not available then hide
        //the collapse button


        //full address (postal code removed intentionally)
        String  address = contacts.get(holder.getAdapterPosition()).get_streetAddress() +
                contacts.get(holder.getAdapterPosition()).get_suburb()+
                contacts.get(holder.getAdapterPosition()).get_city()+
                contacts.get(holder.getAdapterPosition()).get_country();

        if(
                contacts.get(position).get_cellPhone().toString().length() == 0 &&
                contacts.get(position).get_email().length() == 0 &&
                        contacts.get(holder.getAdapterPosition()).get_facebook().length() == 0 &&
                        contacts.get(holder.getAdapterPosition()).get_linkedIn().length() == 0 &&
                        contacts.get(holder.getAdapterPosition()).get_instagram().length() == 0 &&
                        contacts.get(holder.getAdapterPosition()).get_website().length() == 0 &&
                        address.length() == 0
        ){
            holder.btn_collapse.setVisibility(View.GONE);
        }
        else {
            holder.btn_collapse.setVisibility(View.VISIBLE);

            if(contacts.get(holder.getAdapterPosition()).get_email().length() != 0 ){
                holder.btn_email.setVisibility(View.VISIBLE);
            }
            if(contacts.get(holder.getAdapterPosition()).get_facebook().length() != 0 ){
                holder.btn_facebook.setVisibility(View.VISIBLE);
            }
            if(contacts.get(holder.getAdapterPosition()).get_linkedIn().length() != 0 ){
                holder.btn_linkedIn.setVisibility(View.VISIBLE);
            }
            if(contacts.get(holder.getAdapterPosition()).get_instagram().length() != 0 ){
                holder.btn_instagram.setVisibility(View.VISIBLE);
            }
            if(contacts.get(holder.getAdapterPosition()).get_website().length() != 0 ){
                holder.btn_website.setVisibility(View.VISIBLE);
            }
            if(address.length() != 0){
                holder.btn_location.setVisibility(View.VISIBLE);
            }
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
        ImageButton btn_favourite, btn_important, btn_collapse, btn_call, btn_sms,
                btn_email, btn_facebook, btn_linkedIn, btn_instagram, btn_website,
                btn_location, btn_details;
        ImageView iv_main_profile_image ;
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

            btn_email = itemView.findViewById(R.id.contact_email);
            btn_facebook = itemView.findViewById(R.id.contact_facebook);
            btn_linkedIn = itemView.findViewById(R.id.contact_linkedIn);
            btn_instagram = itemView.findViewById(R.id.contact_instagram);
            btn_website = itemView.findViewById(R.id.contact_website);
            btn_location = itemView.findViewById(R.id.contact_location);
            btn_details = itemView.findViewById(R.id.btn_details);
            iv_main_profile_image =  itemView.findViewById(R.id.iv_main_profile_image);

            layout_socialMedias = itemView.findViewById(R.id.linearLayout_socialMedias);


        }
    }
}
