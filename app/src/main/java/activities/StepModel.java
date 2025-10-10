package activities;

public class StepModel {
    private String date;
    private int steps;

    public StepModel() { } // Required for Firestore

    public StepModel(String date, int steps) {
        this.date = date;
        this.steps = steps;
    }

    public String getDate() { return date; }
    public int getSteps() { return steps; }
    public void setDate(String date) { this.date = date; }
    public void setSteps(int steps) { this.steps = steps; }
}
