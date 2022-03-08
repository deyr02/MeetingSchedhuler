package com.example.meetingschedhuler;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class ContactDetails extends AppCompatActivity {

    Contact contactDetails;
    Integer _id;
    Boolean isEditModeEnabled = false;
    MyDatabaseHelper myDatabaseHelper;
    EditText txt_firstName, txt_lastName, txt_cellphone, txt_email, txt_facebook, txt_linkedin,
        txt_instagram, txt_website, txt_streetAddress, txt_suburb, txt_city, txt_postal_code, txt_country;

    Switch sw_faviourite, sw_important;

    ImageButton btn_edit, btn_delete;
    ImageView iv_profile_image_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        initializeAndBindData();



        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isEditModeEnabled = !isEditModeEnabled;
                enableField(isEditModeEnabled);
                if(isEditModeEnabled){
                    btn_edit.setImageResource(R.drawable.ic_save);
                }
                else {
                    btn_edit.setImageResource(R.drawable.ic_edit);
                    updateContactDetails();

                }
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                confirmDialogue();
            }
        });




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

        iv_profile_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               openGallery(view);
            }
        });

    }

    public void openGallery(View view){
        Intent intent = new Intent(ContactDetails.this, ImageGallery.class);
        intent.putExtra("ContactID", contactDetails.get_id());
        startActivity(intent);
    }

    private void updateContactDetails() {


        if(
                nullValidation(txt_firstName, "First Name") &&
                        nullValidation(txt_lastName, "Last Name") &&
                        nullValidation(txt_cellphone, "Phone Number")
        ) {

            Contact contact = new Contact(
                    contactDetails.get_id(),
                    txt_firstName.getText().toString(),
                    txt_lastName.getText().toString(),
                    txt_cellphone.getText().toString(),
                    txt_email.getText().toString(),
                    txt_facebook.getText().toString(),
                    txt_linkedin.getText().toString(),
                    txt_instagram.getText().toString(),
                    txt_website.getText().toString(),
                    sw_faviourite.isChecked()? 1: 0,
                    sw_important.isChecked()? 1:0,
                    0,
                    txt_streetAddress.getText().toString(),
                    txt_suburb.getText().toString(),
                    txt_city.getText().toString(),
                    txt_postal_code.getText().toString(),
                    txt_country.getText().toString()
            );

            long result = myDatabaseHelper.updateContactDetails(contact);
            if (result == -1){
                Toast.makeText(ContactDetails.this, "Somme error occurred during updating contact details", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(ContactDetails.this,
                       contact.get_facebook(),
                        ///"Contact Details updated successfully",
                        Toast.LENGTH_SHORT).show();

            }
        }
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

        btn_edit = findViewById(R.id.btn_Edit);
        btn_edit = findViewById(R.id.btn_Edit);
        btn_delete = findViewById(R.id.btn_delete);

        iv_profile_image_view = findViewById(R.id.profile_image_view);

        myDatabaseHelper = new MyDatabaseHelper(ContactDetails.this);
        if(getIntent().hasExtra("_id")){
            _id = Integer.parseInt( getIntent().getStringExtra("_id"));
        }
        contactDetails = myDatabaseHelper.getContactDetail(_id);
        ActionBar ab = getSupportActionBar();
        if(ab!= null){
            ab.setTitle(contactDetails.get_firstName()+ " "+ contactDetails.get_lastName());
        }


        enableField(false);

        txt_firstName.setText(contactDetails.get_firstName());
        txt_lastName.setText(contactDetails.get_lastName());
        txt_cellphone.setText(contactDetails.get_cellPhone());
        txt_email.setText(contactDetails.get_email());
        txt_facebook.setText(contactDetails.get_facebook());
        txt_linkedin.setText(contactDetails.get_linkedIn());
        txt_instagram.setText(contactDetails.get_instagram());
        txt_website.setText(contactDetails.get_website());
        txt_streetAddress.setText(contactDetails.get_streetAddress());
        txt_suburb.setText(contactDetails.get_suburb());
        txt_city.setText(contactDetails.get_city());
        txt_postal_code.setText(contactDetails.get_postalCOde());
        txt_country.setText(contactDetails.get_country());

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

    private void enableField(Boolean value){
        txt_firstName.setEnabled(value);
        txt_lastName.setEnabled(value);
        txt_cellphone.setEnabled(value);
        txt_email.setEnabled(value);
        txt_facebook.setEnabled(value);
        txt_linkedin.setEnabled(value);
        txt_instagram.setEnabled(value);
        txt_website.setEnabled(value);
        txt_streetAddress.setEnabled(value);
        txt_suburb.setEnabled(value);
        txt_city.setEnabled(value);
        txt_postal_code.setEnabled(value);
        txt_country.setEnabled(value);
    }

   private  void confirmDialogue(){
       AlertDialog.Builder builder = new AlertDialog.Builder(this);
       builder.setTitle("Delete "+ contactDetails.get_firstName() + " " +  contactDetails.get_lastName() +  " ?");
       builder.setMessage("Are You Sure you want to delete " + contactDetails.get_firstName() + " "+ contactDetails.get_lastName() + " ?");
       builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialogInterface, int i) {

               myDatabaseHelper.deleteContactDetail(contactDetails.get_id());
               Intent intent = new Intent(ContactDetails.this, MainActivity.class);
               startActivity(intent);
           }
       });

       builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialogInterface, int i) {

           }
       });
       builder.create().show();
   }

    private  Boolean nullValidation(EditText field, String FiledName){
        if(field.getText().toString().trim().length() == 0){
            Toast.makeText(ContactDetails.this, FiledName + " can not be null", Toast.LENGTH_SHORT).show();
            return false;
        }
        return  true;
    }
}