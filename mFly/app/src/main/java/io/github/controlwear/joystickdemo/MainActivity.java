package io.github.controlwear.joystickdemo;

import io.github.controlwear.joystickdemo.data.MSP;
import io.github.controlwear.virtual.joystick.android.JoystickView;

import android.annotation.SuppressLint;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    public int msg;                   //Values to store x and y co-ordinates
    public int values[] = new int[5]; // array to store the values of roll,pitch,yaw;
    public Button mb;
    public ImageView wifi_symb;
    public boolean armed_f;
    public boolean connect;

    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running =false;
    public boolean connected =false;
    public WifiManager wifi;
    public WifiConfiguration conf;
    public Context context;
    public Button arm;
    public TimerTask ws;
    public Button conn;
    public android.os.Handler customHandler;

    // ImageView wifiStatus;
    public byte[] buff = new byte[1024];
    public String SSID = "edu_drone";
    public String Password = "fltdev@123";
    private ScanResult[] wifiScanResultList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activity_main);

        wifi_symb= (ImageView) findViewById(R.id.wifi_sym);

        armed_f= false;
        msg =0;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        arm = (Button) findViewById(R.id.arm_button);
        conn = (Button) findViewById(R.id.wifi);
        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("%s");
        chronometer.setBase(SystemClock.elapsedRealtime());

        mb = (Button) findViewById(R.id.menu);
        mb.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                Intent in=new Intent(MainActivity.this,MenuActivity.class);
                startActivity(in);
            }
        });


        final JoystickView joystickLeft = (JoystickView) findViewById(R.id.joystickView_left);
        joystickLeft.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {
                if(running) {
                    Client c = new Client();
                    int temp;
                    values[0] = (joystickLeft.getNormalizedX() * 10) + 1000;
                    values[1] = 2000 - (joystickLeft.getNormalizedY() * 10);
                    msg = 200;
                    c.run();
                }
            }
        });


        final JoystickView joystickRight = (JoystickView) findViewById(R.id.joystickView_right);
        joystickRight.setOnMoveListener(new JoystickView.OnMoveListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onMove(int angle, int strength) {
                if(running) {
                    Client c = new Client();
                    int temp;
                    values[2] = (joystickRight.getNormalizedX() * 10) + 1000;
                    values[3] = 2000 - (joystickRight.getNormalizedY() * 10);
                    msg = 200;
                    c.run();
                }
            }
        });
    }



    public void startChronometer(View v) {
        View x = v;
        if (!running && connected) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            values[4] = 2000;
            arm.setBackgroundResource(R.drawable.armgreen);
            running = true;
        }
        else
            resetChronometer(x);
    }

    public void resetChronometer(View v) {
        if (running && connected) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            arm.setBackgroundResource(R.drawable.armred);
            values[4] = 1000;
            running = false;
        }
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }

    public void wifi_connect(View v) {
        wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (!connected) {
            turnWifiOnOff(context, true);
            connected = true;
            Toast.makeText(getApplicationContext(), "Connecting...", Toast.LENGTH_SHORT).show();
            //attempting a connection with drone...

            conf = new WifiConfiguration();
            conf.SSID = "\"" + SSID + "\"";
            conf.preSharedKey = "\"" + Password + "\"";
            conf.status = WifiConfiguration.Status.ENABLED;
            conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            conf.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            int netId = wifi.addNetwork(conf);
            wifi.disconnect();
            wifi.enableNetwork(netId, true);
            WifiInfo wifiInfo = wifi.getConnectionInfo();
            String ssid = wifiInfo.getSSID();
            if (SSID == ssid)
            { Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_SHORT).show();
                connected = true;
                conn.setBackgroundResource(R.drawable.connectgreen);
                customHandler = new android.os.Handler();
                customHandler.postDelayed(updateTimerThread, 0);
            }
            else{
                Toast.makeText(getApplicationContext(), "Unable to Connect", Toast.LENGTH_SHORT).show();
            }
        } else {
            turnWifiOnOff(context, false);
            connected = false;
            conn.setBackgroundResource(R.drawable.connectred);
            Toast.makeText(getApplicationContext(), "Disconnected!", Toast.LENGTH_SHORT).show();
            wifi_symb.setImageResource(R.drawable.wifi_empty);
        }
    }

    private void signal_strength()
    {
        for (ScanResult result : wifiScanResultList) {
            int signalLevel = result.level;

            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            int numberOfLevels = 5;
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();

            int level = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), numberOfLevels);

            wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//Level of a Scan Result
            List<ScanResult> wifiList = wifiManager.getScanResults();
            for (ScanResult scanResult : wifiList) {
                level = WifiManager.calculateSignalLevel(scanResult.level, 5);
            }

// Level of current connection
            int rssi = wifiManager.getConnectionInfo().getRssi();
            level = WifiManager.calculateSignalLevel(rssi, 5);

            if (level <= 0 && level >= -50) {
                wifi_symb.setImageResource(R.drawable.wifi_full);

            } else if (level < -50 && level >= -70) {
                wifi_symb.setImageResource(R.drawable.wifi75);

            } else if (level < -70 && level >= -80) {
                wifi_symb.setImageResource(R.drawable.wifi50);


            } else if (level < -80 && level >= -100) {
                wifi_symb.setImageResource(R.drawable.wifi25);

            } else {
                wifi_symb.setImageResource(R.drawable.wifi_empty);
            }
        }
    }
    private Runnable updateTimerThread = new Runnable()
    {
        public void run()
        {
            //write here whaterver you want to repeat
            signal_strength();
            customHandler.postDelayed(this, 2000);
        }
    };
    private void init()
    {
        for(int i=0;i<4;i++)
            values[i]=900;
    }
    private void turnWifiOnOff(Context context, boolean isOnOff)
    {
        wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(isOnOff);
    }

    public class Client implements Runnable{
        private final static String SERVER_ADDRESS = "192.168.4.1";
        private final static int SERVER_PORT = 4665;
        private MSP m = new MSP();
        public void run(){
            InetAddress serverAddr;
            DatagramPacket packet;
            DatagramSocket socket;

            if(msg!=0)
                try {
                    String str = m.get_data(msg,5*(Integer.SIZE/8),values);
                    buff = str.getBytes();
                    serverAddr = InetAddress.getByName(SERVER_ADDRESS);
                    socket = new DatagramSocket();
                    packet = new DatagramPacket(buff,buff.length,serverAddr,SERVER_PORT);
                    socket.send(packet);
                    socket.close();

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


        }
    }

}