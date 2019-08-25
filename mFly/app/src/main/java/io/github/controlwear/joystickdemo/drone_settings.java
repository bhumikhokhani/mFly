package io.github.controlwear.joystickdemo;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class drone_settings extends MenuActivity { Button b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.drone_settings);
        b2 = (Button) findViewById(R.id.tuts_img);
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent in = new Intent(drone_settings.this, MenuActivity.class);
                startActivity(in);
            }
        });
    }
}