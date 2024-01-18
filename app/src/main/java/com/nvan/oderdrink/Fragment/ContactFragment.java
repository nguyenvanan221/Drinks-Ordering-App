package com.nvan.oderdrink.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nvan.oderdrink.databinding.ActivityContactBinding;

public class ContactFragment extends Fragment {
    ActivityContactBinding binding;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initBinding();
        return view;
    }

    private void initBinding() {
        binding = ActivityContactBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
    }
}
