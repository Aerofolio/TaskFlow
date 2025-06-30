package com.example.taskflow;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskflow.adapters.CommentAdapter;
import com.example.taskflow.adapters.UserAdapter;
import com.example.taskflow.database.AppDatabase;
import com.example.taskflow.model.Comment;
import com.example.taskflow.model.CommentWithUser;
import com.example.taskflow.model.Task;
import com.example.taskflow.model.User;
import com.example.taskflow.utils.PrefsUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CommentsFragment extends Fragment {
    private int taskId;
    private RecyclerView recyclerView;
    private EditText inputComment;
    private Button sendButton;
    private List<Comment> commentList;
    private CommentAdapter adapter;
    private AppDatabase db;

//    private User currentUser = new User("João Victor");

    public CommentsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = AppDatabase.getInstance(requireContext());
        if (getArguments() != null) {
            taskId = getArguments().getInt("TASK_ID", -1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.comments_fragment, container, false);

        adapter = new CommentAdapter(new ArrayList<>());
        recyclerView = view.findViewById(R.id.recyclerViewComments);
        inputComment = view.findViewById(R.id.inputComment);
        sendButton = view.findViewById(R.id.sendButton);
        sendButton.setOnClickListener(v -> {
            String text = inputComment.getText().toString().trim();
            if (!text.isEmpty()) {
                SharedPreferences prefs = getContext().getSharedPreferences(PrefsUtils.APP_PREFS, MODE_PRIVATE);
                int userId = prefs.getInt(PrefsUtils.USER_ID, -1);
                if (userId == -1) {
                    return;
                }

                String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                        .format(new Date());

                Comment newComment = new Comment(taskId, userId, text, timestamp);

                new Thread(() -> {

                    db.commentDao().insert(newComment);

                    List<CommentWithUser> updatedComments = db.commentDao().getCommentsWithUsersForTask(taskId);

                    requireActivity().runOnUiThread(() -> {
                        adapter.setComments(updatedComments);
                        adapter.notifyDataSetChanged();
                        recyclerView.scrollToPosition(updatedComments.size() - 1);
                        inputComment.setText("");
                    });
                }).start();
            }
        });

        loadCommentsFromDatabase();

        return view;
    }

    private void loadCommentsFromDatabase() {
        new Thread(() -> {
            List<CommentWithUser> comments = db.commentDao().getCommentsWithUsersForTask(taskId);

            if (comments != null && !comments.isEmpty()) {

                adapter = new CommentAdapter(comments);

                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(adapter);
            }
        }).start();
    }
}
