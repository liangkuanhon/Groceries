package com.example.groceries.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.groceries.R;
import com.example.groceries.databinding.FragmentAddBinding;
import com.example.groceries.databinding.FragmentProfileBinding;

public class AddFragment extends Fragment {

    private FragmentAddBinding b;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        b = FragmentAddBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

}