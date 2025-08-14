package activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.login.R;

public class Home extends AppCompatActivity {
    Button startWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        startWorkout = findViewById(R.id.startWorkout);
        startWorkout.setOnClickListener(v -> {
            startActivity(new Intent(Home.this, DashboardActivity.class));
        });

    }
}