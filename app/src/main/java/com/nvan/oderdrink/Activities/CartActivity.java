package com.nvan.oderdrink.Activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nvan.oderdrink.Adapter.CartAdapter;
import com.nvan.oderdrink.ViewModel.CartViewModel;
import com.nvan.oderdrink.databinding.ActivityListOrderBinding;

public class CartActivity extends AppCompatActivity {
    private ActivityListOrderBinding binding;
    private CartViewModel cartViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initBinding();
        initViewModel();
        initObserve();
        initViews();

        cartViewModel.loadDrinkToCart();
    }

    private void initViews() {
        binding.rcvorder.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initObserve() {
        cartViewModel.getMutableDrinkList().observe(this, drinkList-> {
            CartAdapter cartAdapter = new CartAdapter(CartActivity.this, drinkList, cartViewModel);
            binding.rcvorder.setAdapter(cartAdapter);
        });

        cartViewModel.getError().observe(this, errorMessage ->{
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        });

        cartViewModel.getTotal().observe(this, totalPrice -> {
            binding.tvtotalprice.setText("" + totalPrice + " VND");
        });
    }

    private void initViewModel() {
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
    }

    private void initBinding() {
        binding = ActivityListOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
