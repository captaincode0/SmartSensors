package com.example.captaincode.smartsensors;

import android.app.ListActivity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ListSensors extends ListActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(android.R.layout.list_content);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        Log.d("Number of sensors detected", String.valueOf(sensorList.size()));
        SensorAdapter adapter = new SensorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                sensorList
        );

        setListAdapter(adapter);
    }

    private class SensorAdapter extends ArrayAdapter<Sensor>{
        private int textViewId;

        public SensorAdapter(Context context, int tvid, List<Sensor> sensors){
            super(context, tvid, sensors);
            this.textViewId = tvid;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null)
                convertView = getLayoutInflater().inflate(this.textViewId, null);

            Sensor s = getItem(position);

            TextView text = (TextView) convertView.findViewById(android.R.id.text1);
            text.setText(s.getName());
            return convertView;
        }
    }
}
