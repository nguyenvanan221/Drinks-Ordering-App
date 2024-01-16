package com.nvan.oderdrink.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nvan.oderdrink.Adapter.CartAdapter;
import com.nvan.oderdrink.ViewModel.CartViewModel;
import com.nvan.oderdrink.databinding.ActivityListOrderBinding;

public class CartFragment extends Fragment {
    private ActivityListOrderBinding binding;
    private CartViewModel cartViewModel;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initBinding();
        initViewModel();
        initObserve();
        initViews();
        initListener();

        cartViewModel.loadDrinkToCart();

        return view;
    }

    private void initListener() {
        String shippingAddress = "ha noi";
        String userId = "1";
        binding.btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartViewModel.placeOrder(cartViewModel.getMutableDrinkList().getValue(), cartViewModel.getTotal().getValue(), shippingAddress, userId);
                Log.d("Cart frag", "dat hang");
            }
        });
    }

    private void initViews() {
        binding.rcvorder.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    private void initObserve() {
        cartViewModel.getMutableDrinkList().observe(this.getViewLifecycleOwner(), drinkList-> {
            CartAdapter cartAdapter = new CartAdapter(this.getContext(), drinkList, cartViewModel);
            binding.rcvorder.setAdapter(cartAdapter);
        });

        cartViewModel.getError().observe(this.getViewLifecycleOwner(), errorMessage ->{
            Toast.makeText(this.getContext(), errorMessage, Toast.LENGTH_SHORT).show();
        });

        cartViewModel.getTotal().observe(this.getViewLifecycleOwner(), totalPrice -> {
            binding.tvtotalprice.setText("" + totalPrice);
        });
    }

    private void initViewModel() {
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
    }

    private void initBinding() {
        binding = ActivityListOrderBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
    }
}
