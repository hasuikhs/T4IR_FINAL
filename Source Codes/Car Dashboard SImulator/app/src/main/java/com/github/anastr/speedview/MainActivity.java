package com.github.anastr.speedview;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.anastr.speedviewlib.TubeSpeedometer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TubeSpeedometer distanceMeter, engineOilMeter,coolerMeter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_main);

        distanceMeter = findViewById(R.id.speedometer1);
        engineOilMeter = findViewById(R.id.speedometer2);
        coolerMeter = findViewById(R.id.speedometer3);

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                String setResult = (String)bundle.getSerializable("result");
                Log.d("UDPMsg", setResult);
                if(setResult.contains("Distance")){
                    String[] distance = setResult.split("#");
                    float data = Float.parseFloat(distance[1]);
                    distanceMeter.speedTo(data);
                } else if(setResult.contains("EngineOil")){
                    String[] engineOil = setResult.split("#");
                    float data = Float.parseFloat(engineOil[1]);
                    engineOilMeter.speedTo(data);
                } else if(setResult.contains("Cooler")){
                    String[] cooler = setResult.split("#");
                    float data = Float.parseFloat(cooler[1]);
                    coolerMeter.speedTo(data);
                }
            }
        };
        Server server = new Server(handler);
        Thread thread = new Thread(server);
        thread.start();
    }
}
