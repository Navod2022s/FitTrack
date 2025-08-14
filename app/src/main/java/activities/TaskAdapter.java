package activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private ArrayList<TaskModel> taskList;

    public TaskAdapter(ArrayList<TaskModel> taskList) {
        this.taskList = taskList;
    }  // avoiding some error from Lint mistakenly thinks TaskAdapter will be created via reflection
    public TaskAdapter() {taskList = new ArrayList<>();}

    // Single ViewHolder with TextView + CheckBox
    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskText;
        CheckBox taskCheckBox;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskText = itemView.findViewById(R.id.taskText);
            taskCheckBox = itemView.findViewById(R.id.taskCheckBox);
        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskModel task = taskList.get(position);
        holder.taskText.setText(task.getTitle());

        // CheckBox state
        holder.taskCheckBox.setChecked(task.isCompleted());

        holder.taskCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setCompleted(isChecked);
            // Optionally: update FireStore here if you want persistence
            // tasksRef.document(taskId).update("completed", isChecked);
            // Update Firestore
            FirebaseFirestore.getInstance()
                    .collection("tasks")
                    .document(task.getTaskId())
                    .update("completed", isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
