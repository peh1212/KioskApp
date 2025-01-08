package com.example.kioskapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class Fragment3 extends Fragment {
    private MenuActivity activity;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MenuActivity) {
            activity = (MenuActivity) context;
        }
    }

    public static Fragment3 newInstance(MenuActivity activity) {
        Fragment3 fragment = new Fragment3();
        fragment.activity = activity;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_3, container, false);


        ImageButton buttonAddItem17 = view.findViewById(R.id.buttonAddItem17);
        buttonAddItem17.setOnClickListener(v -> {
            if (getActivity() instanceof MenuActivity) {
                ((MenuActivity) getActivity()).buttonAddItem(v);
            }
        });

        ImageButton buttonAddItem18 = view.findViewById(R.id.buttonAddItem18);
        buttonAddItem18.setOnClickListener(v -> {
            if (getActivity() instanceof MenuActivity) {
                ((MenuActivity) getActivity()).buttonAddItem(v);
            }
        });

        ImageButton buttonAddItem19 = view.findViewById(R.id.buttonAddItem19);
        buttonAddItem19.setOnClickListener(v -> {
            if (getActivity() instanceof MenuActivity) {
                ((MenuActivity) getActivity()).buttonAddItem(v);
            }
        });

        ImageButton buttonAddItem20 = view.findViewById(R.id.buttonAddItem20);
        buttonAddItem20.setOnClickListener(v -> {
            if (getActivity() instanceof MenuActivity) {
                ((MenuActivity) getActivity()).buttonAddItem(v);
            }
        });

        ImageButton buttonAddItem21 = view.findViewById(R.id.buttonAddItem21);
        buttonAddItem21.setOnClickListener(v -> {
            if (getActivity() instanceof MenuActivity) {
                ((MenuActivity) getActivity()).buttonAddItem(v);
            }
        });

        ImageButton buttonAddItem22 = view.findViewById(R.id.buttonAddItem22);
        buttonAddItem22.setOnClickListener(v -> {
            if (getActivity() instanceof MenuActivity) {
                ((MenuActivity) getActivity()).buttonAddItem(v);
            }
        });

        ImageButton buttonAddItem23 = view.findViewById(R.id.buttonAddItem23);
        buttonAddItem23.setOnClickListener(v -> {
            if (getActivity() instanceof MenuActivity) {
                ((MenuActivity) getActivity()).buttonAddItem(v);
            }
        });

        ImageButton buttonAddItem24 = view.findViewById(R.id.buttonAddItem24);
        buttonAddItem24.setOnClickListener(v -> {
            if (getActivity() instanceof MenuActivity) {
                ((MenuActivity) getActivity()).buttonAddItem(v);
            }
        });

        return view;
    }
}