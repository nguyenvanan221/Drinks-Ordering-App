package com.nvan.oderdrink.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.nvan.oderdrink.Login;
import com.nvan.oderdrink.MainActivity;
import com.nvan.oderdrink.ViewModel.AuthViewModel;
import com.nvan.oderdrink.databinding.ActivityInfoUserBinding;
import com.nvan.oderdrink.databinding.ActivityMainBinding;

public class InforUserFragment extends Fragment {
    ActivityInfoUserBinding binding;
    ActivityMainBinding mainBinding;

    AuthViewModel authViewModel;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initBinding();
        initListener();
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        return view;
    }

    private void initListener() {
        // Logout
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(), Login.class);
                startActivity(intent);
                authViewModel.getUserId().setValue(null);
            }
        });

        // thông tin user
        binding.tvinfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new profile();
                getActivity().getSupportFragmentManager().beginTransaction().replace(mainBinding.fragmentcontainer.getId(), fragment).commit();
                Log.d("user", "infor");
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
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
    }
}
