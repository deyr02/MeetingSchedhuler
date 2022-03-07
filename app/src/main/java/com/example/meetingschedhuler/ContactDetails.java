package com.example.meetingschedhuler;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ContactDetails extends AppCompatActivity {

    Contact contactDetails;
    Integer _id;
    MyDatabaseHelper myDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        myDatabaseHelper = new MyDatabaseHelper(ContactDetails.this);
        if(getIntent().hasExtra("_id")){
            _id = Integer.parseInt( getIntent().getStringExtra("_id"));
        }

        contactDetails = myDatabaseHelper.getContactDetail(_id);

        ActionBar ab = getSupportActionBar();
        if(ab!= null){
            ab.setTitle(contactDetails.get_firstName()+ " "+ contactDetails.get_lastName());
        }
    }
}