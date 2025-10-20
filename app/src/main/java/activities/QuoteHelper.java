package activities;

import android.content.Context;

import com.example.login.R;

import java.util.Random;

public class QuoteHelper {

    private static final String[] QUOTES = {
            "Keep going, you're doing great!",
            "Small progress is still progress.",
            "Your mood doesn't define your potential.",
            "Every day is a new beginning.",
            "Breathe. You’ve got this!"
    };

    public static String getRandomQuote(Context context) {
        Random random = new Random();
        return QUOTES[random.nextInt(QUOTES.length)];
    }
}
