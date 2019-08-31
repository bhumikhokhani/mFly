package io.github.controlwear.joystickdemo;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;




public class drone_settings extends MenuActivity {

    Button RotorTL;
    Button RotorTR;
    Button RotorBL;
    Button RotorBR;

    ImageView top_left;
    ImageView top_right;
    ImageView bottom_left;
    ImageView bottom_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drone_settings);

        RotorTL=findViewById(R.id.RotorTL);
        top_left=findViewById(R.id.top_left);

        RotorTL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Not Connected",Toast.LENGTH_SHORT).show();
//                RotateAnimation TL = new RotateAnimation(0,-1080, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
//                TL.setDuration(3000);
//
//                top_left.setAnimation(TL);
//                top_left.startAnimation(TL);

            }
        });

        RotorTR=findViewById(R.id.RotorTR);
        top_right=findViewById(R.id.top_right);

        RotorTR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RotateAnimation TR = new RotateAnimation(0,1080, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
                TR.setDuration(3000);

                top_right.setAnimation(TR);
                top_right.startAnimation(TR);

            }
        });

        RotorBL=findViewById(R.id.RotorBL);
        bottom_left=findViewById(R.id.bottom_left);

        RotorBL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RotateAnimation BL = new RotateAnimation(0,-1080, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
                BL.setDuration(3000);

                bottom_left.setAnimation(BL);
                bottom_left.startAnimation(BL);

            }
        });

        RotorBR=findViewById(R.id.RotorBR);
        bottom_right=findViewById(R.id.bottom_right);

        RotorBR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RotateAnimation BR = new RotateAnimation(0,1080, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
                BR.setDuration(3000);

                bottom_right.setAnimation(BR);
                bottom_right.startAnimation(BR);

            }
        });
    }

    public void BackToMenu(View view) {
        Intent in = new Intent(drone_settings.this, MenuActivity.class);
        startActivity(in);
    }
}