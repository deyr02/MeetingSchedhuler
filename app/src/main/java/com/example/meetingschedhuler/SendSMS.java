package com.example.meetingschedhuler;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SendSMS extends AppCompatActivity {
    String phoneNumber;
    TextView txt_number;
    EditText txt_message;
    Button btn_send_sms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);
        if(getIntent().hasExtra("PhoneNumber")){
            phoneNumber = getIntent().getStringExtra("PhoneNumber");
        }

        txt_number = findViewById(R.id.sendSMS_phoneNumber);
        txt_number.setText(phoneNumber);

        txt_message = findViewById(R.id.sendSMS_Message);
        btn_send_sms = findViewById(R.id.sndSMs_btn_send);

        btn_send_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){

                        String Message = txt_message.getText().toString().trim();

                        try{

                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(phoneNumber, null, Message, null, null);

                            Toast.makeText(SendSMS.this, "Message Send", Toast.LENGTH_SHORT).show();

                        }
                        catch (Exception ex){
                            ex.printStackTrace();
                            Toast.makeText(SendSMS.this, "Message failed", Toast.LENGTH_SHORT).show();

                        }
                    }
                    else {
                        requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
                    }
                }
            }
        });
    }
}