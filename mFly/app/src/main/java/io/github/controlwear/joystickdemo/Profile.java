package io.github.controlwear.joystickdemo;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.widget.EditText;
import android.widget.TextView;


    public class Profile extends MenuActivity {

        Button mButton;
        EditText mEdit;
        TextView mText;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_profile);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            mButton = (Button) findViewById(R.id.button1);

            mButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    mEdit = (EditText) findViewById(R.id.editText1);
                    mText = (TextView) findViewById(R.id.textView1);
                    mText.setText("Pilot " + mEdit.getText().toString());
                }
            }); }

        public void BackToMenu(View view) {
            Intent in = new Intent(Profile.this, MenuActivity.class);
            startActivity(in);
        }
    }