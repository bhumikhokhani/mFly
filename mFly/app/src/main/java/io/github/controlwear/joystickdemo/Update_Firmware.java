package io.github.controlwear.joystickdemo;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Spinner;


public class Update_Firmware extends MainActivity {
    private Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_firmware);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

    }




    public void BackToMenu(View view) {
        Intent in = new Intent(Update_Firmware.this, MenuActivity.class);
        startActivity(in);
    }

    public void doToast(View view){
        Toast.makeText(getApplicationContext(),"Not Connected.Firmware Coming Soon.",Toast.LENGTH_LONG).show();
    }


}
