package activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.R;

import java.util.List;

public class MoodHistoryAdapter extends RecyclerView.Adapter<MoodHistoryAdapter.ViewHolder> {

    private final List<MoodModel> moodList;

    public MoodHistoryAdapter(List<MoodModel> moodList) {
        this.moodList = moodList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mood_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MoodModel mood = moodList.get(position);
        holder.txtDate.setText(mood.getDate());
        holder.txtMood.setText(mood.getMood());
    }

    @Override
    public int getItemCount() {
        return moodList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtDate, txtMood;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txtMoodDate);
            txtMood = itemView.findViewById(R.id.txtMoodEmoji);
        }
    }
}
