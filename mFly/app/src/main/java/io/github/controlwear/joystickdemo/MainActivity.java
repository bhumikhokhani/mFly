package io.github.controlwear.joystickdemo;

import io.github.controlwear.virtual.joystick.android.JoystickView;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;




public class MainActivity extends AppCompatActivity {

    public Integer x1, y1, x2, y2; //Values to store x and y co-ordinates
    public String ch, ch2; //These store channel no.'s in string form.
    public String XV;
    public String YV;
    public Button b1;
    public Button wifi;
    public RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue = Volley.newRequestQueue(this);


       wifi = (Button) findViewById(R.id.wifi);
        wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWifiSettings();
            }
        });

            b1 = (Button) findViewById(R.id.menu);
        b1.setOnClickListener(new View.OnClickListener() {


                    public void onClick(View v) {
                        Intent in=new Intent(MainActivity.this,MenuActivity.class);
                        startActivity(in);
                    }
            });


        final JoystickView joystickLeft = (JoystickView) findViewById(R.id.joystickView_left);
        joystickLeft.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {

                x1 = (joystickLeft.getNormalizedX() * 10) + 1000;
                y1 = 2000 - (joystickLeft.getNormalizedY() * 10);

                XV = String.valueOf(x1);
                YV = String.valueOf(y1);
                ch = Integer.toString(1);
                ch2 = Integer.toString(0);
                run();
            }
        });


        final JoystickView joystickRight = (JoystickView) findViewById(R.id.joystickView_right);
        joystickRight.setOnMoveListener(new JoystickView.OnMoveListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onMove(int angle, int strength) {

                x2 = (joystickRight.getNormalizedX() * 10) + 1000;
                y2 = 2000 - (joystickRight.getNormalizedY() * 10);

                XV = String.valueOf(x2);
                YV = String.valueOf(y2);
                ch2 = Integer.toString(2);
                ch = Integer.toString(3);
                run();
            }
        });
    }

    public void openWifiSettings() {

        final Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        final ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.wifi.WifiSettings");
        intent.setComponent(cn);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


        public void run() {

            String url;
            url = "http://192.168.4.1/feed";
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Log.d("Response", response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            String response = null;
                            Log.d("Error.Response", error.getMessage());
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("channel", ch);
                    params.put("value", XV);
                    params.put("channel2", ch2);
                    params.put("value2", YV);

                    return params;
                }};



            queue.add(postRequest);
            }

        }




