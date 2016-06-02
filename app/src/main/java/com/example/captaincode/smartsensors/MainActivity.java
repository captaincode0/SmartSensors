package com.example.captaincode.smartsensors;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    private TextView tvAcl;
    private TextView tvOri;
    private TextView tvMag;
    private TextView tvProx;
    private TextView tvLum;
    private TextView tvTem;
    private TextView tvGrav;
    private TextView tvDect;
    private TextView tvGir;
    private TextView tvPress;

    private Button btnlist;
    private Button btnclear;

    private float max_x = 0;
    private float max_y = 0;
    private float max_z = 0;

    private SensorManager sensorManager;
    DecimalFormat twodecimal = new DecimalFormat("###.###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        this.btnlist = (Button) findViewById(R.id.btnlist);
        this.btnclear = (Button) findViewById(R.id.btnclean);

        this.tvAcl = (TextView) findViewById(R.id.tvacl);
        this.tvOri = (TextView) findViewById(R.id.tvor);
        this.tvMag = (TextView) findViewById(R.id.tvmag);
        this.tvProx = (TextView) findViewById(R.id.tvprox);
        this.tvLum = (TextView) findViewById(R.id.tvlum);
        this.tvTem = (TextView) findViewById(R.id.tvtemp);
        this.tvGrav = (TextView) findViewById(R.id.tvgrav);
        this.tvDect = (TextView) findViewById(R.id.tvdetect);
        this.tvGir = (TextView) findViewById(R.id.tvgiro);
        this.tvPress = (TextView) findViewById(R.id.tvpression);

        this.sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        this.btnlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getBaseContext(), ListSensors.class);
                startActivity(intent);
            }
        });


    }

    protected void startSensors(){
        this.sensorManager.registerListener(
                this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL
        );

        this.sensorManager.registerListener(
                this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_NORMAL
        );

        this.sensorManager.registerListener(
                this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR),
                SensorManager.SENSOR_DELAY_NORMAL
        );

        this.sensorManager.registerListener(
                this,
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_NORMAL
        );

        this.sensorManager.registerListener(
                this,
                sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),
                SensorManager.SENSOR_DELAY_NORMAL
        );

        this.sensorManager.registerListener(
                this,
                sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
                SensorManager.SENSOR_DELAY_NORMAL
        );

        this.sensorManager.registerListener(
                this,
                sensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE),
                SensorManager.SENSOR_DELAY_NORMAL
        );

        this.sensorManager.registerListener(
                this,
                sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),
                SensorManager.SENSOR_DELAY_NORMAL
        );

        this.sensorManager.registerListener(
                this,
                sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE),
                SensorManager.SENSOR_DELAY_NORMAL
        );
    }

    protected void stopSensors(){
        sensorManager.unregisterListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        );
        sensorManager.unregisterListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)
        );
        sensorManager.unregisterListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        );
        sensorManager.unregisterListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        );
        sensorManager.unregisterListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        );
        sensorManager.unregisterListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE)
        );
        sensorManager.unregisterListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
        );
        sensorManager.unregisterListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
        );
        sensorManager.unregisterListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
        );
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        String text = "\n\nSensor: ";

        synchronized (this){
            Log.d("sensor", event.sensor.getName());

            switch(event.sensor.getType()){
                case Sensor.TYPE_ORIENTATION:
                    text += "Orientation\n";
                    text += "\n azimut :"+getDirection(event.values[0]);
                    text += "\n y: "+event.values[1];
                    text += "\n z: "+event.values[2];
                    tvOri.setText(text);
                    break;

                case Sensor.TYPE_ACCELEROMETER:
                    if(event.values[0] > max_x)
                        max_x = event.values[0];
                    if(event.values[1] > max_y)
                        max_y = event.values[1];
                    if(event.values[2] > max_z)
                        max_z = event.values[2];

                    text += "Acelerometer\n";
                    text += "\n x: "+twodecimal.format(event.values[0])+" m/s - max: "+twodecimal.format(max_x);
                    text += "\n y: "+twodecimal.format(event.values[1])+" m/s - max: "+twodecimal.format(max_y
                    );
                    text += "\n z: "+twodecimal.format(event.values[2])+" m/s - max: "+twodecimal.format(max_z);
                    tvAcl.setText(text);

                    if(event.values[0] > 15
                            || event.values[1] > 15
                            || event.values[2] > 15){
                        tvDect.setBackgroundColor(Color.parseColor("#cf091c"));
                        tvDect.setText("Vibration detected");
                    }
                    break;
                case Sensor.TYPE_ROTATION_VECTOR:
                    text += "Rotation Vector\n";
                    text += "\n x: "+event.values[0];
                    text += "\n y: "+event.values[1];
                    text += "\n z: "+event.values[2];

                    Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
                    int rotation = display.getRotation();

                    if(rotation == 0)
                        text += "\n\n Pos: Vertical";
                    else if(rotation == 1)
                        text += "\n\n Pos: Horizontal Left";
                    else if(rotation == 3)
                        text += "\n\n Pos: Horizontal right";

                    text += "\n\n display: "+ rotation;
                    tvGir.setText(text);

                    break;
                case Sensor.TYPE_GRAVITY:
                    text += "Gravity\n";
                    text += "\n x: "+event.values[0];
                    text += "\n y: "+event.values[1];
                    text += "\n z: "+event.values[2];

                    tvGrav.setText(text);
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    text += "Magnetic field\n";
                    text += "\n"+event.values[0]+" uT";

                    tvMag.setText(text);
                    break;
                case Sensor.TYPE_PROXIMITY:
                    text += "Proximity\n";
                    text += "\n "+event.values[0];
                    tvProx.setText(text);

                    if(event.values[0] == 0){
                        tvDect.setBackgroundColor(Color.parseColor("#cf091"));
                        tvDect.setText("Proximity Detected");
                    }
                    break;
                case Sensor.TYPE_LIGHT:
                    text += "Luminosity\n";
                    text += "\n "+event.values[0]+" Lux";

                    tvLum.setText(text);
                    break;
                case Sensor.TYPE_PRESSURE:
                    text += "Pressure\n";
                    text += "\n "+event.values[0]+" mBar \n\n";
                    tvPress.setText(text);
                    break;
                case Sensor.TYPE_TEMPERATURE:
                    text += "Temperature\n";
                    text += "\n "+event.values[0]+ " °C";
                    tvTem.setText(text);
                    break;
            }
        }
    }

    private String getDirection(float values) {
        String txtDirection = "";
        if (values < 22)
            txtDirection = "N";
        else if (values >= 22 && values < 67)
            txtDirection = "NE";
        else if (values >= 67 && values < 112)
            txtDirection = "E";
        else if (values >= 112 && values < 157)
            txtDirection = "SE";
        else if (values >= 157 && values < 202)
            txtDirection = "S";
        else if (values >= 202 && values < 247)
            txtDirection = "SO";
        else if (values >= 247 && values < 292)
            txtDirection = "O";
        else if (values >= 292 && values < 337)
            txtDirection = "NO";
        else if (values >= 337)
            txtDirection = "N";

        return txtDirection;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop(){
        startSensors();
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        stopSensors();
        super.onDestroy();
    }

    @Override
    protected void onPause(){
        stopSensors();
        super.onPause();
    }

    @Override
    protected void onRestart(){
        startSensors();
        super.onRestart();
    }

    @Override
    protected void onResume(){
        super.onResume();
        startSensors();
    }
}
