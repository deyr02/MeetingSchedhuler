package com.example.meetingschedhuler;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class ContactDetails extends AppCompatActivity {

    Contact contactDetails;
    Integer _id;
    MyDatabaseHelper myDatabaseHelper;
    TextView txt_firstName, txt_lastName, txt_cellphone, txt_email, txt_facebook, txt_linkedin,
        txt_instagram, txt_website, txt_streetAddress, txt_suburb, txt_city, txt_postal_code, txt_country;

    Switch sw_faviourite, sw_important;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        initializeAndBindData();

        sw_faviourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                long result = myDatabaseHelper.toggleContactType(contactDetails.get_id(), ContactType.FAVOURITE);
                if(result == 1){
                    Toast.makeText(ContactDetails.this, "The selected contact saved as favourite", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(ContactDetails.this, "Some error occurred ", Toast.LENGTH_SHORT).show();

                }

            }
        });

        sw_important.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                long result = myDatabaseHelper.toggleContactType(contactDetails.get_id(), ContactType.IMPORTANT);
                if(result == 1){
                    Toast.makeText(ContactDetails.this, "The selected contact saved as Important", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(ContactDetails.this, "Some error occurred ", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    private void initializeAndBindData() {
        txt_firstName = findViewById(R.id.txt_firstName);
        txt_lastName = findViewById(R.id.txt_lastName);
        txt_cellphone = findViewById(R.id.txt_cellphone);
        txt_email = findViewById(R.id.txt_email);
        txt_facebook = findViewById(R.id.txt_facebook);
        txt_linkedin = findViewById(R.id.txt_linkedin);
        txt_instagram = findViewById(R.id.txt_instagram);
        txt_website = findViewById(R.id.txt_website);
        txt_streetAddress = findViewById(R.id.txt_streetAddress);
        txt_suburb = findViewById(R.id.txt_suburb);
        txt_city = findViewById(R.id.txt_city);
        txt_postal_code = findViewById(R.id.txt_postalCode);
        txt_country = findViewById(R.id.txt_country);

        sw_faviourite = findViewById(R.id.switch_favourite);
        sw_important = findViewById(R.id.switch_important);

        myDatabaseHelper = new MyDatabaseHelper(ContactDetails.this);
        if(getIntent().hasExtra("_id")){
            _id = Integer.parseInt( getIntent().getStringExtra("_id"));
        }
        contactDetails = myDatabaseHelper.getContactDetail(_id);
        ActionBar ab = getSupportActionBar();
        if(ab!= null){
            ab.setTitle(contactDetails.get_firstName()+ " "+ contactDetails.get_lastName());
        }
        txt_firstName.setText(isDataAvailable( contactDetails.get_firstName()));
        txt_lastName.setText(isDataAvailable(contactDetails.get_lastName()));
        txt_cellphone.setText(isDataAvailable(contactDetails.get_cellPhone()));
        txt_email.setText(isDataAvailable(contactDetails.get_email()));
        txt_facebook.setText(isDataAvailable(contactDetails.get_facebook()));
        txt_linkedin.setText(isDataAvailable(contactDetails.get_linkedIn()));
        txt_instagram.setText(isDataAvailable(contactDetails.get_instagram()));
        txt_website.setText(isDataAvailable(contactDetails.get_website()));
        txt_streetAddress.setText(isDataAvailable(contactDetails.get_streetAddress()));
        txt_suburb.setText(isDataAvailable(contactDetails.get_suburb()));
        txt_city.setText(isDataAvailable(contactDetails.get_city()));
        txt_postal_code.setText(isDataAvailable(contactDetails.get_postalCOde()));
        txt_country.setText(isDataAvailable(contactDetails.get_country()));

        if(contactDetails.get_is_favourite() == 0){
            sw_faviourite.setChecked(false);
        }
        else {
            sw_faviourite.setChecked(true);
        }
        if(contactDetails.get_is_important() == 0){
            sw_important.setChecked(false);
        }
        else {
            sw_important.setChecked(true);
        }

    }

    private  String isDataAvailable(String value){
        if(value.length() == 0){
            return "No value available";
        }
        else {
            return value;
        }
    }
}