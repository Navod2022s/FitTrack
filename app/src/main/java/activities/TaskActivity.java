package activities;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.R;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class TaskActivity extends AppCompatActivity {

    private TaskAdapter taskAdapter;
    private List<Task> taskList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        RecyclerView taskRecyclerView = findViewById(R.id.taskRecyclerView);
        FloatingActionButton addTaskButton = findViewById(R.id.addTaskButton);

        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskRecyclerView.setAdapter(taskAdapter);

        addTaskButton.setOnClickListener(v -> {
            Task newTask = new Task("New Task", "Task Description", false);
            taskList.add(newTask);
            taskAdapter.notifyItemInserted(taskList.size() - 1);
            Toast.makeText(this, "Task Added", Toast.LENGTH_SHORT).show();
        });
    }
}
