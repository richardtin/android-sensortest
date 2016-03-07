package io.github.richardtin.sensortest;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SensorMonitorActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "SensorMonitorActivity";
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private TextView mSensorNameLabel;
    private TextView mSensorValueLabel;
    private Button mCloseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_monitor);

        int sensorType = getIntent().getIntExtra("sensor_type", Sensor.TYPE_ALL);
        Log.d(TAG, "sensorType = " + sensorType);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorType != Sensor.TYPE_ALL) mSensor = mSensorManager.getDefaultSensor(sensorType);

        mSensorNameLabel = (TextView) findViewById(R.id.sensor_name_label);
        mSensorValueLabel = (TextView) findViewById(R.id.sensor_value_label);
        mCloseButton = (Button) findViewById(R.id.close_button);
        if (mCloseButton != null) mCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mSensor != null) {
            if (mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL)) {
                if (mSensorNameLabel != null) {
                    mSensorNameLabel.setText(mSensor.getName());
                }
            } else {
                Log.d(TAG, "Fail to register listener to sensor [" + mSensor.getStringType() + "]");
            }
        } else {
            if (mSensorNameLabel != null) {
                mSensorNameLabel.setText("No Sensor");
            }
            Log.d(TAG, "! ! ! Sensor not exist ! ! !");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this); // Unregisters listener for all sensors.
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        String sensorEventValue;

        if (mSensorValueLabel != null) {
            switch (sensorEvent.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                case Sensor.TYPE_MAGNETIC_FIELD:
                case Sensor.TYPE_GYROSCOPE:
                case Sensor.TYPE_GRAVITY:
                case Sensor.TYPE_LINEAR_ACCELERATION:
                case Sensor.TYPE_ROTATION_VECTOR:
                case Sensor.TYPE_GAME_ROTATION_VECTOR:
                case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR:
                    sensorEventValue = "values[0]=" + sensorEvent.values[0] + "\n" +
                            "values[1]=" + sensorEvent.values[1] + "\n" +
                            "values[2]=" + sensorEvent.values[2];
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
                case Sensor.TYPE_GYROSCOPE_UNCALIBRATED:
                    sensorEventValue = "values[0]=" + sensorEvent.values[0] + "\n" +
                            "values[1]=" + sensorEvent.values[1] + "\n" +
                            "values[2]=" + sensorEvent.values[2] + "\n" +
                            "values[3]=" + sensorEvent.values[3] + "\n" +
                            "values[4]=" + sensorEvent.values[4] + "\n" +
                            "values[5]=" + sensorEvent.values[5];
                    break;
                case Sensor.TYPE_LIGHT:
                case Sensor.TYPE_PRESSURE:
                case Sensor.TYPE_PROXIMITY:
                case Sensor.TYPE_AMBIENT_TEMPERATURE:
                case Sensor.TYPE_HEART_RATE:
                case Sensor.TYPE_RELATIVE_HUMIDITY:
                case Sensor.TYPE_STEP_COUNTER:
                case Sensor.TYPE_STEP_DETECTOR:
                    sensorEventValue = "values[0]=" + sensorEvent.values[0];
                    break;
                default:
                    sensorEventValue = "Not support";
                    break;
            }
            mSensorValueLabel.setText(sensorEventValue);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
