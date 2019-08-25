package io.github.controlwear.joystickdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;




public class app_settings extends MenuActivity {    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.back_bt);
        b1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent in=new Intent(app_settings.this,MenuActivity.class);
                startActivity(in);
           }
        });}
}