package com.example.kioskapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class Fragment1 extends Fragment {
    private MenuActivity activity;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MenuActivity) {
            activity = (MenuActivity) context;
        }
    }

    public static Fragment1 newInstance(MenuActivity activity) {
        Fragment1 fragment = new Fragment1();
        fragment.activity = activity;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, container, false);


        ImageButton buttonAddItem1 = view.findViewById(R.id.buttonAddItem1);
        buttonAddItem1.setOnClickListener(v -> {
            if (getActivity() instanceof MenuActivity) {
                ((MenuActivity) getActivity()).buttonAddItem(v);
            }
        });

        ImageButton buttonAddItem2 = view.findViewById(R.id.buttonAddItem2);
        buttonAddItem2.setOnClickListener(v -> {
            if (getActivity() instanceof MenuActivity) {
                ((MenuActivity) getActivity()).buttonAddItem(v);
            }
        });

        ImageButton buttonAddItem3 = view.findViewById(R.id.buttonAddItem3);
        buttonAddItem3.setOnClickListener(v -> {
            if (getActivity() instanceof MenuActivity) {
                ((MenuActivity) getActivity()).buttonAddItem(v);
            }
        });

        ImageButton buttonAddItem4 = view.findViewById(R.id.buttonAddItem4);
        buttonAddItem4.setOnClickListener(v -> {
            if (getActivity() instanceof MenuActivity) {
                ((MenuActivity) getActivity()).buttonAddItem(v);
            }
        });

        ImageButton buttonAddItem5 = view.findViewById(R.id.buttonAddItem5);
        buttonAddItem5.setOnClickListener(v -> {
            if (getActivity() instanceof MenuActivity) {
                ((MenuActivity) getActivity()).buttonAddItem(v);
            }
        });

        ImageButton buttonAddItem6 = view.findViewById(R.id.buttonAddItem6);
        buttonAddItem6.setOnClickListener(v -> {
            if (getActivity() instanceof MenuActivity) {
                ((MenuActivity) getActivity()).buttonAddItem(v);
            }
        });

        ImageButton buttonAddItem7 = view.findViewById(R.id.buttonAddItem7);
        buttonAddItem7.setOnClickListener(v -> {
            if (getActivity() instanceof MenuActivity) {
                ((MenuActivity) getActivity()).buttonAddItem(v);
            }
        });

        ImageButton buttonAddItem8 = view.findViewById(R.id.buttonAddItem8);
        buttonAddItem8.setOnClickListener(v -> {
            if (getActivity() instanceof MenuActivity) {
                ((MenuActivity) getActivity()).buttonAddItem(v);
            }
        });

        return view;
    }
}