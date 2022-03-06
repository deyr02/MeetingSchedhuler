package com.example.meetingschedhuler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddContact extends AppCompatActivity {

    EditText txt_firstName, txt_lastName, txt_cellPhone, txt_email, txt_facebook, txt_linkedin,
            txt_instagram, txt_website,  txt_streetAddress, txt_suburb, txt_city, txt_postalCode, txt_country;

    Button add_contact;
    MyDatabaseHelper myDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        initializeFields();
        myDatabaseHelper = new MyDatabaseHelper(AddContact.this);

        add_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveContact();
            }
        });


    }

    private void saveContact() {

        if(
                nullValidation(txt_firstName, "First Name") &&
                nullValidation(txt_lastName, "Last Name") &&
                        nullValidation(txt_cellPhone, "Phone Number")
        ){
            Contact contact = new Contact(
                    0,
                    txt_firstName.getText().toString().trim(),
                    txt_lastName.getText().toString().trim(),
                    txt_cellPhone.getText().toString().trim(),
                    txt_email.getText().toString().trim(),
                    txt_firstName.getText().toString().trim(),
                    txt_linkedin.getText().toString().trim(),
                    txt_instagram.getText().toString().trim(),
                    txt_website.getText().toString().trim(),
                    0,
                    0,
                    0,
                    txt_streetAddress.getText().toString().trim(),
                    txt_suburb.getText().toString().trim(),
                    txt_city.getText().toString().trim(),
                    txt_postalCode.getText().toString().trim(),
                    txt_country.getText().toString().trim()
            );

            long result = myDatabaseHelper.addContact(contact);

            if (result == -1){
                Toast.makeText(AddContact.this, "Somme error occured during  saving contact details", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(AddContact.this, "New Contact added successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initializeFields() {

        txt_firstName = findViewById(R.id.txt_firstName);
        txt_lastName = findViewById(R.id.txt_lastName);
        txt_cellPhone = findViewById(R.id.txt_phoneNumber);
        txt_email = findViewById(R.id.txt_email);
        txt_facebook = findViewById(R.id.txt_facebookProfile);
        txt_linkedin = findViewById(R.id.txt_linkedInProfile);
        txt_instagram = findViewById(R.id.txt_instagramProfile);
        txt_website = findViewById(R.id.txt_website);
        txt_streetAddress = findViewById(R.id.txt_streetAddress);
        txt_suburb = findViewById(R.id.txt_suburb);
        txt_postalCode = findViewById(R.id.txt_postalCode);
        txt_city = findViewById(R.id.txt_city);
        txt_country = findViewById(R.id.txt_country);


        add_contact = findViewById(R.id.btn_add_contact);
    }

    private  Boolean nullValidation(EditText field, String FiledName){
        if(field.getText().toString().trim().length() == 0){
            Toast.makeText(AddContact.this, FiledName + " can not be null", Toast.LENGTH_SHORT).show();
            return false;
        }
        return  true;
    }
}