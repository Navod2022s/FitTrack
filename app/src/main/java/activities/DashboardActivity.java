package activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login.R;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        taskButton = findViewById(R.id.taskButton);
        moodButton = findViewById(R.id.moodButton);
        stepButton = findViewById(R.id.stepButton);
        logoutButton = findViewById(R.id.logoutButton);




        logoutButton.setOnClickListener(v -> {
        });
    }
}
