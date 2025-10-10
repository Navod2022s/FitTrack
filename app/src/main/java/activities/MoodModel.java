package activities;

public class MoodModel {
    private String date;
    private String mood;

    public MoodModel() {
        // Required for Firestore
    }

    public MoodModel(String date, String mood) {
        this.date = date;
        this.mood = mood;
    }

    public String getDate() {
        return date;
    }

    public String getMood() {
        return mood;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }
}
