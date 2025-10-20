package activities;

import android.content.Intent;
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
import java.util.Random;

public class MoodActivity extends AppCompatActivity {

    private TextView todayMoodText, quoteText;
    private MaterialButton moodHappy, moodNeutral, moodSad, moodAngry, btnMoodHistory;

    private FirebaseFirestore db;
    private CollectionReference moodsRef;
    private String todayDate;
    private String[] quotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);

        //  Initialize Firestore
        db = FirebaseFirestore.getInstance();
        moodsRef = db.collection("moods");

        //Get todays date
        todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        // Bind views
        todayMoodText = findViewById(R.id.todayMoodText);
        quoteText = findViewById(R.id.quoteText);

        moodHappy = findViewById(R.id.moodHappy);
        moodNeutral = findViewById(R.id.moodNeutral);
        moodSad = findViewById(R.id.moodSad);
        moodAngry = findViewById(R.id.moodAngry);
        btnMoodHistory = findViewById(R.id.btnMoodHistory);

        // Optional motivational quote helper
        quoteText.setText(QuoteHelper.getRandomQuote(this));

        // Load todays mood
        loadTodayMood();

        // Load quotes
        quotes = getResources().getStringArray(R.array.motivational_quotes);

        // show random quote
        showRandomQuote();

        // Mood button listeners
        moodHappy.setOnClickListener(v -> saveMood("😊"));
        moodNeutral.setOnClickListener(v -> saveMood("😐"));
        moodSad.setOnClickListener(v -> saveMood("😔"));
        moodAngry.setOnClickListener(v -> saveMood("😡"));

        // Open mood history
        btnMoodHistory.setOnClickListener(v ->
                startActivity(new Intent(this, MoodHistoryActivity.class)));
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
        // Reset alpha for all
        moodHappy.setAlpha(0.5f);
        moodNeutral.setAlpha(0.5f);
        moodSad.setAlpha(0.5f);
        moodAngry.setAlpha(0.5f);

        // Highlight selected
        switch (mood) {
            case "😊": moodHappy.setAlpha(1f); break;
            case "😐": moodNeutral.setAlpha(1f); break;
            case "😔": moodSad.setAlpha(1f); break;
            case "😡": moodAngry.setAlpha(1f); break;
        }
    }

    private void showRandomQuote() {
        if (quotes != null && quotes.length > 0) {
            int index = new Random().nextInt(quotes.length);
            quoteText.setText(quotes[index]);
        }
    }
}
