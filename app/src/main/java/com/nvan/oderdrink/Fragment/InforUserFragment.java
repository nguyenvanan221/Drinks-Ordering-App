package com.nvan.oderdrink.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nvan.oderdrink.databinding.ActivityInfoUserBinding;

public class InforUserFragment extends Fragment {
    ActivityInfoUserBinding binding;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initBinding();
        initListener();
        return view;
    }

    private void initListener() {
        // Logout
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // thông tin user
        binding.tvinfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // lich su dat hang
        binding.tvhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initBinding() {
        binding = ActivityInfoUserBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
    }
}
