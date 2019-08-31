package io.github.controlwear.joystickdemo;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class help extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }


    public void BackToMenu(View view) {
        Intent in = new Intent(help.this, MenuActivity.class);
        startActivity(in);
    }

    public void goToContack(View view) {
        Intent in = new Intent(help.this, Contact_us.class);
        startActivity(in);
    }

    public void goToFaq(View view) {
        Intent in = new Intent(help.this, faq.class);
        startActivity(in);
    }

    public void goToTutorials(View view) {
        Intent in = new Intent(help.this, Tutorials.class);
        startActivity(in);
    }
}
