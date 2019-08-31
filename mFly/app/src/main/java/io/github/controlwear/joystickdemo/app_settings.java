package io.github.controlwear.joystickdemo;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class app_settings extends MenuActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_settings);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

    }

    public void BackToMenu(View view) {
        Intent in = new Intent(app_settings.this, MenuActivity.class);
        startActivity(in);
    }
}