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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StepCounterActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor stepSensor;

    private TextView stepCountView, distanceView, caloriesView, quoteText;
    private CircularProgressIndicator stepProgress;

    private int stepCount = 0;
    private final int STEP_GOAL = 10000;

    private FirebaseFirestore db;
    private CollectionReference stepsRef;
    private String todayDate;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);

        stepCountView = findViewById(R.id.stepCountView);
        distanceView = findViewById(R.id.distanceView);
        caloriesView = findViewById(R.id.caloriesView);
        stepProgress = findViewById(R.id.stepProgress);
        quoteText = findViewById(R.id.quoteText);

        quoteText.setText(QuoteHelper.getRandomQuote(this));

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            if (stepSensor == null) {
                Log.e("StepCounter", "Step Counter Sensor not available!");
                stepCountView.setText("No Sensor");
            }
        }

        db = FirebaseFirestore.getInstance();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        stepsRef = db.collection("steps").document(userId).collection("dailySteps");

        todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        loadTodaySteps();
    }

    private void loadTodaySteps() {
        stepsRef.whereEqualTo("date", todayDate)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    stepCount = 0;
                    for (var doc : queryDocumentSnapshots) {
                        stepCount = doc.getLong("steps").intValue();
                    }
                    updateUI(stepCount);
                })
                .addOnFailureListener(e -> Log.e("StepCounter", "Error loading steps: " + e.getMessage()));
    }

    private void saveSteps() {
        stepsRef.add(new StepModel(todayDate, stepCount))
                .addOnSuccessListener(docRef -> Log.d("StepCounter", "Steps saved"))
                .addOnFailureListener(e -> Log.e("StepCounter", "Error saving steps: " + e.getMessage()));
    }

    private void updateUI(int steps) {
        stepCountView.setText(String.valueOf(steps));
        stepProgress.setProgress(Math.min(steps, STEP_GOAL));
        float distanceKm = steps * 0.0008f;
        float calories = steps * 0.04f;
        distanceView.setText(String.format("%.2f km", distanceKm));
        caloriesView.setText(String.format("%.1f kcal", calories));
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
            saveSteps(); // Save steps when leaving activity
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            stepCount = (int) event.values[0];
            updateUI(stepCount);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}
