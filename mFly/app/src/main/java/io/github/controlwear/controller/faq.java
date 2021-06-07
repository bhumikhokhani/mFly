package io.github.controlwear.controller;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class faq extends Help {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

    }

    public void BackTohelp(View view) {
        Intent in = new Intent(faq.this, Help.class);
        startActivity(in);
    }
}
