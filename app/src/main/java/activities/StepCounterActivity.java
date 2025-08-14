package activities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login.R;
import com.google.android.material.progressindicator.CircularProgressIndicator;

public class StepCounterActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor stepSensor;

    private TextView stepCountView, distanceView, caloriesView;
    private CircularProgressIndicator stepProgress;

    private int stepCount = 0;
    private final int STEP_GOAL = 10000;           // Huawei default goal

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);

        stepCountView = findViewById(R.id.stepCountView);
        distanceView = findViewById(R.id.distanceView);
        caloriesView = findViewById(R.id.caloriesView);
        stepProgress = findViewById(R.id.stepProgress);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            if (stepSensor == null) {
                Log.e("StepCounter", "Step Counter Sensor not available!");
                stepCountView.setText("No Sensor");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (stepSensor != null) {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (stepSensor != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            stepCount = (int) event.values[0]; // total since reboot
            updateUI(stepCount);
        }
    }

    private void updateUI(int steps) {
        stepCountView.setText(String.valueOf(steps));

        // Update progress circle
        stepProgress.setProgress(Math.min(steps, STEP_GOAL));

        // Approx conversions
        float distanceKm = steps * 0.0008f; // ~0.8m per step
        float calories = steps * 0.04f;     // ~0.04 kcal per step

        distanceView.setText(String.format("%.2f km", distanceKm));
        caloriesView.setText(String.format("%.1f kcal", calories));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}
