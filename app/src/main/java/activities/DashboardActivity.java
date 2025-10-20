package activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardActivity extends AppCompatActivity {
    private Button taskButton, moodButton,stepButton,logoutButton ;
    private TextView welcomeText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();





       // Initialize Button
        taskButton = findViewById(R.id.taskButton);
        moodButton = findViewById(R.id.moodButton);
        stepButton = findViewById(R.id.stepButton);
        logoutButton = findViewById(R.id.logoutButton);
        welcomeText = findViewById(R.id.welcomeText);

        // if no user direct to Login
        if (user == null) {
            startActivity(new Intent(this, Loginactivity.class));
            finish();
            return;
        }


        String email = user.getEmail();
        if (email != null && email.contains("@")) {
            String name = email.substring(0, email.indexOf("@"));
            welcomeText.setText("Welcome, " + capitalize(name) + "!");
        } else {
            welcomeText.setText("Welcome!");
        }

        // Setup buttons to Activities
        taskButton.setOnClickListener(v ->
                startActivity(new Intent(this, TaskActivity.class)));
        moodButton.setOnClickListener(v ->
                startActivity(new Intent(this,MoodActivity.class)));
        stepButton.setOnClickListener(v ->
                startActivity(new Intent(this, StepCounterActivity.class)));


        logoutButton.setOnClickListener(v -> {
            auth.signOut();
            startActivity(new Intent(DashboardActivity.this,Loginactivity.class));
            finish();
        });
    }
    // Helper method to capitalize text
    private String capitalize(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}
