package com.example.meetingschedhuler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SendEmail extends AppCompatActivity {

    TextView txt_email;
    EditText txt_subject, txt_email_body;
    Button btn_send;
    String EmailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        txt_email = findViewById(R.id.txt_email_address);
        txt_subject = findViewById(R.id.txt_email_subject);
        txt_email_body = findViewById(R.id.txt_email_body);
        btn_send = findViewById(R.id.btn_send_email);

        if(getIntent().hasExtra("EmailAddress")){
            EmailAddress = getIntent().getStringExtra("EmailAddress");
        }

        txt_email.setText(EmailAddress);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(txt_subject.getText().toString().length() == 0){
                    Toast.makeText(SendEmail.this, "Please input subject.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(txt_email_body.getText().toString().length() == 0){
                    Toast.makeText(SendEmail.this, "Email body is empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{EmailAddress});
                i.putExtra(Intent.EXTRA_SUBJECT, txt_subject.getText());
                i.putExtra(Intent.EXTRA_TEXT   , txt_email_body.getText());
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(SendEmail.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}