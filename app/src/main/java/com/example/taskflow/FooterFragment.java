package com.example.taskflow;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FooterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FooterFragment extends Fragment {
    ImageButton imageButtonHome;
    ImageButton imageButtonCalendar;
    ImageButton imageButtonRegisterTask;

    public FooterFragment() {
        // Required empty public constructor
    }
    public static FooterFragment newInstance(String param1, String param2) {
        FooterFragment fragment = new FooterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_footer, container, false);
        imageButtonHome = view.findViewById(R.id.imageButtonHome);
        imageButtonCalendar = view.findViewById(R.id.imageButtonCalendar);
        imageButtonRegisterTask = view.findViewById(R.id.imageButtonRegisterTask);

        imageButtonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity currentActivity = getActivity();
                if (!currentActivity.getClass().getSimpleName().equals(HomeActivity.class.getSimpleName())){
                    currentActivity.finish();
                    Intent homeIntent = new Intent(currentActivity, HomeActivity.class);
                    startActivity(homeIntent);
                }
            }
        });
        imageButtonCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity currentActivity = getActivity();
                if (!currentActivity.getClass().getSimpleName().equals(CalendarActivity.class.getSimpleName())){
                    currentActivity.finish();
                    Intent calendarIntent = new Intent(currentActivity, CalendarActivity.class);
                    startActivity(calendarIntent);
                }
            }
        });
        imageButtonRegisterTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity currentActivity = getActivity();
                Intent registerTaskIntent = new Intent(currentActivity, RegisterTaskActivity.class);
                startActivity(registerTaskIntent);
            }
        });

        return view;
    }
}