package activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login.R;

public class DashboardActivity extends AppCompatActivity {

    Button taskButton, moodButton, stepButton, logoutButton;
    TextView quoteText; // TextView for motivational quote

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize buttons
        taskButton = findViewById(R.id.taskButton);
        moodButton = findViewById(R.id.moodButton);
        stepButton = findViewById(R.id.stepButton);
        logoutButton = findViewById(R.id.logoutButton);

        // Initialize quote TextView
        quoteText = findViewById(R.id.quoteText);
        quoteText.setText(QuoteHelper.getRandomQuote(this)); // Set a random quote

        // Task button
        taskButton.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, TaskActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Opening Task List", Toast.LENGTH_SHORT).show();
        });

        // Mood tracker
        moodButton.setOnClickListener(v -> {
            startActivity(new Intent(DashboardActivity.this, MoodActivity.class));
        });

        // Step counter
        /* stepButton.setOnClickListener(v -> {
            startActivity(new Intent(DashboardActivity.this, StepCounterActivity.class));
        }); */

        // Logout
        logoutButton.setOnClickListener(v -> {
            finishAffinity(); // Closes all activities
        });
    }
}
