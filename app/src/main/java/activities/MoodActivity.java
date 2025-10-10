package activities;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MoodActivity extends AppCompatActivity {

    private TextView todayMoodText, quoteText;
    private MaterialButton moodHappy, moodNeutral, moodSad, moodAngry;

    private FirebaseFirestore db;
    private CollectionReference moodsRef;
    private String todayDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();
        moodsRef = db.collection("moods");

        // Current date
        todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        // Bind views
        todayMoodText = findViewById(R.id.todayMoodText);
        quoteText = findViewById(R.id.quoteText);
        quoteText.setText(QuoteHelper.getRandomQuote(this));

        moodHappy = findViewById(R.id.moodHappy);
        moodNeutral = findViewById(R.id.moodNeutral);
        moodSad = findViewById(R.id.moodSad);
        moodAngry = findViewById(R.id.moodAngry);

        // Load previously saved mood
        loadTodayMood();

        // Button actions
        moodHappy.setOnClickListener(v -> saveMood("happy"));
        moodNeutral.setOnClickListener(v -> saveMood("neutral"));
        moodSad.setOnClickListener(v -> saveMood("sad"));
        moodAngry.setOnClickListener(v -> saveMood("angry"));
    }

    private void loadTodayMood() {
        moodsRef.whereEqualTo("date", todayDate)
                .get()
                .addOnSuccessListener(query -> {
                    if (!query.isEmpty()) {
                        for (QueryDocumentSnapshot doc : query) {
                            String mood = doc.getString("mood");
                            highlightMoodButton(mood);
                        }
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error loading today's mood", Toast.LENGTH_SHORT).show());
    }

    private void saveMood(String mood) {
        moodsRef.add(new MoodModel(todayDate, mood))
                .addOnSuccessListener(doc -> {
                    Toast.makeText(this, "Mood saved!", Toast.LENGTH_SHORT).show();
                    highlightMoodButton(mood);
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error saving mood", Toast.LENGTH_SHORT).show());
    }

    private void highlightMoodButton(String mood) {
        // Reset all button opacity
        moodHappy.setAlpha(0.5f);
        moodNeutral.setAlpha(0.5f);
        moodSad.setAlpha(0.5f);
        moodAngry.setAlpha(0.5f);

        // Highlight selected
        switch (mood) {
            case "happy":
                moodHappy.setAlpha(1f);
                break;
            case "neutral":
                moodNeutral.setAlpha(1f);
                break;
            case "sad":
                moodSad.setAlpha(1f);
                break;
            case "angry":
                moodAngry.setAlpha(1f);
                break;
        }
    }
}
