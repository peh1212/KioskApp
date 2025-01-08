package com.example.kioskapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class Fragment2 extends Fragment {
    private MenuActivity activity;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MenuActivity) {
            activity = (MenuActivity) context;
        }
    }

    public static Fragment2 newInstance(MenuActivity activity) {
        Fragment2 fragment = new Fragment2();
        fragment.activity = activity;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, container, false);


        ImageButton buttonAddItem9 = view.findViewById(R.id.buttonAddItem9);
        buttonAddItem9.setOnClickListener(v -> {
            if (getActivity() instanceof MenuActivity) {
                ((MenuActivity) getActivity()).buttonAddItem(v);
            }
        });

        ImageButton buttonAddItem10 = view.findViewById(R.id.buttonAddItem10);
        buttonAddItem10.setOnClickListener(v -> {
            if (getActivity() instanceof MenuActivity) {
                ((MenuActivity) getActivity()).buttonAddItem(v);
            }
        });

        ImageButton buttonAddItem11 = view.findViewById(R.id.buttonAddItem11);
        buttonAddItem11.setOnClickListener(v -> {
            if (getActivity() instanceof MenuActivity) {
                ((MenuActivity) getActivity()).buttonAddItem(v);
            }
        });

        ImageButton buttonAddItem12 = view.findViewById(R.id.buttonAddItem12);
        buttonAddItem12.setOnClickListener(v -> {
            if (getActivity() instanceof MenuActivity) {
                ((MenuActivity) getActivity()).buttonAddItem(v);
            }
        });

        ImageButton buttonAddItem13 = view.findViewById(R.id.buttonAddItem13);
        buttonAddItem13.setOnClickListener(v -> {
            if (getActivity() instanceof MenuActivity) {
                ((MenuActivity) getActivity()).buttonAddItem(v);
            }
        });

        ImageButton buttonAddItem14 = view.findViewById(R.id.buttonAddItem14);
        buttonAddItem14.setOnClickListener(v -> {
            if (getActivity() instanceof MenuActivity) {
                ((MenuActivity) getActivity()).buttonAddItem(v);
            }
        });

        ImageButton buttonAddItem15 = view.findViewById(R.id.buttonAddItem15);
        buttonAddItem15.setOnClickListener(v -> {
            if (getActivity() instanceof MenuActivity) {
                ((MenuActivity) getActivity()).buttonAddItem(v);
            }
        });

        ImageButton buttonAddItem16 = view.findViewById(R.id.buttonAddItem16);
        buttonAddItem16.setOnClickListener(v -> {
            if (getActivity() instanceof MenuActivity) {
                ((MenuActivity) getActivity()).buttonAddItem(v);
            }
        });

        return view;
    }
}