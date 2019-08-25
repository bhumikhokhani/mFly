package io.github.controlwear.joystickdemo;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;

public class MenuActivity extends MainActivity {
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        b1 = (Button) findViewById(R.id.tuts_img);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MenuActivity.this, drone_settings.class);
                startActivity(in);
            }
        });
        Button b2;
        b2 = (Button) findViewById(R.id.contact_img);
        b2.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                Intent in = new Intent(MenuActivity.this, app_settings.class);
                startActivity(in);
            }
        });

        Button b3;
        b3 = (Button) findViewById(R.id.button1);
        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent in = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(in);
            }
        });
    }}
