package activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class TaskActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private ArrayList<TaskModel> taskList;
    private TextView quoteText; // TextView for motivational quote

    private FirebaseFirestore db;
    private CollectionReference tasksRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        recyclerView = findViewById(R.id.recyclerView);
        FloatingActionButton fabAddTask = findViewById(R.id.fabAddTask);
        quoteText = findViewById(R.id.quoteText); // Initialize quote TextView
        quoteText.setText(QuoteHelper.getRandomQuote(this)); // Set random quote

        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);

        db = FirebaseFirestore.getInstance();
        tasksRef = db.collection("tasks");

        loadTasks();

        fabAddTask.setOnClickListener(v -> showAddTaskDialog());
    }

    // Show dialog to enter new task
    private void showAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Task");

        final EditText input = new EditText(this);
        input.setHint("Enter task name");
        builder.setView(input);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String taskName = input.getText().toString().trim();
            if (!taskName.isEmpty()) {
                addTaskToFirestore(taskName);
            } else {
                Toast.makeText(this, "Task cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    // Load tasks from FireStore
    private void loadTasks() {
        tasksRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            taskList.clear();
            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                TaskModel task = doc.toObject(TaskModel.class);
                taskList.add(task);
            }
            taskAdapter.notifyDataSetChanged();
        }).addOnFailureListener(e ->
                Toast.makeText(this, "Error loading tasks", Toast.LENGTH_SHORT).show()
        );
    }

    // Add new task to FireStore
    private void addTaskToFirestore(String task) {
        tasksRef.add(new TaskModel(task))  // <- uses TaskModel(String title)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show();
                    loadTasks();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error adding task", Toast.LENGTH_SHORT).show()
                );
    }

}
