package io.github.richardtin.sensortest;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SensorListActivity extends AppCompatActivity {
    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private View mContentView;
    private RecyclerView mSensorListView;
    private List<Sensor> mSensorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sensor_list);

        mContentView = findViewById(R.id.fullscreen_content);
        mSensorListView = (RecyclerView) findViewById(R.id.sensor_list_view);
        mSensorListView.setLayoutManager(new LinearLayoutManager(this));

        initSensorList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSensorList != null && !mSensorList.isEmpty()) {
            mContentView.setVisibility(View.GONE);
            mSensorListView.setAdapter(new SensorListAdapter(mSensorList));
        } else {
            mSensorListView.setVisibility(View.GONE);
        }
    }

    private void initSensorList() {
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
    }

}
