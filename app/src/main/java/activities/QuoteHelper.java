package activities;

import android.content.Context;

import java.util.Random;

public class QuoteHelper {

    private static final String[] quotes = {
            "Believe in yourself!",
            "Every day is a new opportunity.",
            "Stay positive, work hard, make it happen.",
            "Success is a journey, not a destination.",
            "Don’t watch the clock; do what it does. Keep going.",
            "Your limitation—it’s only your imagination."
    };

    public static String getRandomQuote(Context context) {
        Random random = new Random();
        int index = random.nextInt(quotes.length);
        return quotes[index];
    }
}
