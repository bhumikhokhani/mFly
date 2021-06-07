package io.github.controlwear.controller;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;

public class MenuActivity extends MainActivity {
   private Button mb1;
   private Button mb2;
   private Button mb3;
   private Button mb4;
   private Button mb5;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


        mb1 = (Button) findViewById(R.id.mdsbutton);
        mb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, drone_settings.class);
                startActivity(intent);
            }
        });
        mb2 = (Button) findViewById(R.id.masbutton);
        mb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activitysettings();
            }
            public void activitysettings() {
                Intent intent = new Intent(MenuActivity.this, app_settings.class);
                startActivity(intent);
            }
        });

        mb3 = (Button) findViewById(R.id.mpbutton);
        mb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile();
            }
            public void profile() {
                Intent intent = new Intent(MenuActivity.this, Profile.class);
                startActivity(intent);
            }
        });

        mb4 = (Button) findViewById(R.id.mufbutton);
        mb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_firmware();
            }
            public void update_firmware() {
                Intent intent = new Intent(MenuActivity.this, Update_Firmware.class);
                startActivity(intent);
            }
        });

        mb5 = (Button) findViewById(R.id.mhelpbutton);
        mb5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHelp();
            }
            public void openHelp() {
                Intent intent = new Intent(MenuActivity.this, Help.class);
                startActivity(intent);
            }
        });
    }

    public void goTOProfile(View view) {
        Intent intent = new Intent(MenuActivity.this, Profile.class);
        startActivity(intent);
    }

    public void BackToMain(View view) {
        Intent in = new Intent(MenuActivity.this, MainActivity.class);
        startActivity(in);
    }
}