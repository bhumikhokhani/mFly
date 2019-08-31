package io.github.controlwear.joystickdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

    public class Contact_us extends help {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.contact_us);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

            ((Button) findViewById(R.id.btnOK)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String to = ((EditText)findViewById(R.id.txtTo)).getText().toString();
                    String sub = ((EditText)findViewById(R.id.txtSubject)).getText().toString();
                    String mess = ((EditText)findViewById(R.id.txtMessage)).getText().toString();
                    Intent mail = new Intent(Intent.ACTION_SEND);
                    mail.putExtra(Intent.EXTRA_EMAIL,new String[]{to});
                    mail.putExtra(Intent.EXTRA_SUBJECT, sub);
                    mail.putExtra(Intent.EXTRA_TEXT, mess);
                    mail.setType("message/rfc822");
                    startActivity(Intent.createChooser(mail, "Send email via:"));
                }
            });
        }
    }
