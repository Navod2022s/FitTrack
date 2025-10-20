package activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MoodHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MoodHistoryAdapter adapter;
    private List<MoodModel> moodList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_history);

        recyclerView = findViewById(R.id.recyclerViewMoodHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        moodList = new ArrayList<>();
        adapter = new MoodHistoryAdapter(moodList);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        loadMoodHistory();
    }

    private void loadMoodHistory() {
        db.collection("moods").orderBy("date")
                .get()
                .addOnSuccessListener(query -> {
                    moodList.clear();
                    for (QueryDocumentSnapshot doc : query) {
                        MoodModel mood = doc.toObject(MoodModel.class);
                        moodList.add(mood);
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error fetching mood history", Toast.LENGTH_SHORT).show());
    }
}
