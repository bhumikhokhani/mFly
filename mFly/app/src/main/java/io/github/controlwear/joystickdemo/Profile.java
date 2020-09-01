package io.github.controlwear.joystickdemo;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class Profile extends MenuActivity {
    private static final String USER_NAME = "username.txt";

    Button mButton;
    Button time;
    EditText mEdit;
    TextView mText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        View v=new View (this);
        mEdit = (EditText) findViewById(R.id.editText1);
        mText = (TextView) findViewById(R.id.textView1);
        load(v);

        mButton = (Button) findViewById(R.id.button_save);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String text = mEdit.getText().toString();
                mText.setText("Pilot " + mEdit.getText().toString());
                save(view);
            }
        });
    }
    public void save(View view) {
        String text = mEdit.getText().toString();
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(USER_NAME, MODE_PRIVATE);
            fos.write(text.getBytes());
            mEdit.getText().clear();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void load(View view){
        FileInputStream fis = null;
        try {
            fis = openFileInput(USER_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null){
                sb.append(text).append("\n");

            }

            mText.setText("Pilot "+sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void BackToMenu(View view) {
        Intent in = new Intent(Profile.this, MenuActivity.class);
        startActivity(in);
    }

}