package com.example.espage14.accelerometer;

import android.Manifest;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.EventListener;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer{

    TextView z_axis = null;
    TextView x_axis = null;
    TextView y_axis = null;
    TextView lat = null;
    TextView lon = null;
    AccelerometerHandler ah = null;
    LocationHandler loc = null;
    private static final int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSIONS_ACCESS_FINE_LOCATION);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        z_axis = (TextView) findViewById(R.id.Z_gravity_view);
        x_axis = (TextView) findViewById(R.id.X_gravity_view);
        y_axis = (TextView) findViewById(R.id.Y_gravity_view);
        lat = (TextView) findViewById(R.id.Lat);
        lon = (TextView) findViewById(R.id.Long);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ah = new AccelerometerHandler(this);
        ah.addObserver(this);
        loc = new LocationHandler(this);
        loc.addObserver(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        lat.setText(Float.toString(getPreferences(MODE_PRIVATE).getFloat("LAT", (float) 0.0)));
        lon.setText(Float.toString(getPreferences(MODE_PRIVATE).getFloat("LONG", (float) 0.0)));
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPreferences(MODE_PRIVATE).edit().putFloat("LAT",Float.parseFloat(lat.getText().toString())).apply();
        getPreferences(MODE_PRIVATE).edit().putFloat("LONG",Float.parseFloat(lon.getText().toString())).apply();
    }

    @Override
    public void update(Observable observable, Object o) {
        if(observable instanceof AccelerometerHandler){
            float [] xyz = (float []) o;
            z_axis.setText(Float.toString(xyz[2]));
            x_axis.setText(Float.toString(xyz[0]));
            y_axis.setText(Float.toString(xyz[1]));
        }
        if(observable instanceof LocationHandler){
            double [] ll = (double [])o;
            lat.setText(Double.toString(ll[0]));
            lon.setText(Double.toString(ll[1]));
        }


    }
}
