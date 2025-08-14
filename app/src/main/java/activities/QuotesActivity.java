package activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login.R;

import java.util.Random;

public class QuotesActivity extends AppCompatActivity {

    private TextView quoteText;
    private Button newQuoteButton;
    private String[] quotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);

        quoteText = findViewById(R.id.quoteText);
        newQuoteButton = findViewById(R.id.newQuoteButton);

        // Load quotes from strings.xml
        quotes = getResources().getStringArray(R.array.motivational_quotes);

        // Show a random quote initially
        showRandomQuote();

        // Button click → show a new random quote
        newQuoteButton.setOnClickListener(v -> showRandomQuote());
    }

    private void showRandomQuote() {
        int index = new Random().nextInt(quotes.length);
        quoteText.setText(quotes[index]);
    }
}
