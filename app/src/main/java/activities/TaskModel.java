package activities;

public class TaskModel {
    private String title;

    public TaskModel() {
        // Required for Firestore
    }

    public TaskModel(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
