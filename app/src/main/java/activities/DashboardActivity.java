package activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login.R;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity {

    private Button taskButton, moodButton, stepButton, logoutButton;
    private TextView quoteText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize buttons
        taskButton = findViewById(R.id.taskButton);
        moodButton = findViewById(R.id.moodButton);
        stepButton = findViewById(R.id.stepButton);
        logoutButton = findViewById(R.id.logoutButton);

        // Initialize quote TextView at the top
        quoteText = findViewById(R.id.quoteText);
        quoteText.setText(QuoteHelper.getRandomQuote(this));

        // Open TaskActivity
        taskButton.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, TaskActivity.class)));

        // Open MoodActivity
        moodButton.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, MoodActivity.class)));

        // Open StepCounterActivity
        stepButton.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, StepCounterActivity.class)));

        // Logout button
        logoutButton.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut(); // Sign out from Firebase
            finishAffinity(); // Close all activities
        });
    }
}
