package activities;

public class MoodModel {
    private String date;
    private String mood;

    public MoodModel() {} // Firestore needs empty constructor

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
}
