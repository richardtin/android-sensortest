package io.github.richardtin.sensortest;

import android.content.ComponentName;
import android.content.Intent;
import android.hardware.Sensor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SensorListAdapter extends RecyclerView.Adapter<SensorListAdapter.ViewHolder> {

    private static final String TAG = SensorListAdapter.class.getSimpleName();

    private List<Sensor> mSensorList;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView sensorNameLabel;
        private ViewHolderOnClickListener listener;

        public ViewHolder(View itemView, ViewHolderOnClickListener listener) {
            super(itemView);
            this.listener = listener;
            itemView.setOnClickListener(this);
            sensorNameLabel = (TextView) itemView.findViewById(R.id.sensor_name);
        }

        public TextView getSensorNameLabel() {
            return sensorNameLabel;
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(view, getAdapterPosition(), this);
        }

        public interface ViewHolderOnClickListener {
            void onItemClick(View view, int position, ViewHolder viewHolder);
        }

    }

    public SensorListAdapter(List<Sensor> sensorList) {
        mSensorList = sensorList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sensor_item, parent, false);
        return new ViewHolder(view, new ViewHolder.ViewHolderOnClickListener() {
            @Override
            public void onItemClick(View view, int position, ViewHolder viewHolder) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("io.github.richardtin.sensortest", "io.github.richardtin.sensortest.SensorMonitorActivity"));
                intent.putExtra("sensor_type", mSensorList.get(position).getType());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.getSensorNameLabel().setText(mSensorList.get(position).getStringType().substring(15));
    }

    @Override
    public int getItemCount() {
        return mSensorList.size();
    }
}
