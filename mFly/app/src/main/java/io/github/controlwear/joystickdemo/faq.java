package io.github.controlwear.joystickdemo;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class faq extends help {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

    }

    public void BackTohelp(View view) {
        Intent in = new Intent(faq.this, help.class);
        startActivity(in);
    }
}
