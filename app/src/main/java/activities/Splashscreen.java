// Splash Screen
package activities;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.login.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        // Delay for 2 seconds (splash screen time)
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = auth.getCurrentUser();

            if (currentUser != null) {
                //  User already logged in → go to Dashboard
                Intent intent = new Intent(Splashscreen.this, DashboardActivity.class);
                startActivity(intent);
            } else {
                //  Not logged in → go to Login
                Intent intent = new Intent(Splashscreen.this, Loginactivity.class);
                startActivity(intent);
            }
            finish();
        }, 2000);
    }
}
