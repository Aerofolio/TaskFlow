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
import com.example.taskflow.model.Comment;
import com.example.taskflow.model.User;
import com.example.taskflow.utils.PrefsUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CommentsFragment extends Fragment {
    private RecyclerView recyclerView;
    private EditText inputComment;
    private Button sendButton;
    private List<Comment> commentList;
    private CommentAdapter adapter;

//    private User currentUser = new User("João Victor");

    public CommentsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.comments_fragment, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewComments);
        inputComment = view.findViewById(R.id.inputComment);
        sendButton = view.findViewById(R.id.sendButton);

        commentList = new ArrayList<>();
//        commentList.add(new Comment(new User("Maria Oliveira"),
//                "Esse app está me ajudando muito!", "05/05/2025 10:15"));
//
//        commentList.add(new Comment(new User("Carlos Souza"),
//                "Ficou excelente, parabéns!", "05/05/2025 11:30"));
//
//        commentList.add(new Comment(new User("Ana Lima"),
//                "Sugestão: adicionar notificações.", "05/05/2025 12:45"));
        adapter = new CommentAdapter(commentList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        sendButton.setOnClickListener(v -> {
            String text = inputComment.getText().toString().trim();
            if (!text.isEmpty()) {
//                String timestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
//                        .format(new Date());

//                SharedPreferences prefs = getContext().getSharedPreferences(PrefsUtils.APP_PREFS, MODE_PRIVATE);
//                int loggedUserId = prefs.getInt(PrefsUtils.USER_ID, 0);
//
//                Comment newComment = new Comment(currentUser, text, timestamp);
//                commentList.add(newComment);
                adapter.notifyItemInserted(commentList.size() - 1);
                recyclerView.scrollToPosition(commentList.size() - 1);
                inputComment.setText("");
            }
        });

        return view;
    }
}
