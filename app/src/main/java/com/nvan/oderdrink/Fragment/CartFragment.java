package com.nvan.oderdrink.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.nvan.oderdrink.Adapter.CartAdapter;
import com.nvan.oderdrink.ViewModel.AuthViewModel;
import com.nvan.oderdrink.ViewModel.CartViewModel;
import com.nvan.oderdrink.databinding.ActivityListOrderBinding;
import com.nvan.oderdrink.databinding.DialogNhapDiaChiBinding;

public class CartFragment extends Fragment {
    private ActivityListOrderBinding binding;
    private CartViewModel cartViewModel;
    private View view;
    private DialogNhapDiaChiBinding addressBinding;
    private AuthViewModel authViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initBinding();
        initViewModel();
        initObserve();
        initViews();
        initListener();

        cartViewModel.loadDrinkToCart(authViewModel.getUserId().getValue());

        return view;
    }

    private void initListener() {
        binding.btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showBottomDialog();

            }
        });

        addressBinding.edtaddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (! hasFocus){
                    InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(addressBinding.edtaddress.getWindowToken(), 0);
                }
            }
        });
    }

    private void initViews() {
        binding.rcvorder.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    private void initObserve() {
        cartViewModel.getMutableDrinkList().observe(this.getViewLifecycleOwner(), drinkList-> {
            CartAdapter cartAdapter = new CartAdapter(this.getContext(), drinkList, cartViewModel, authViewModel);
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
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
    }

    private void initBinding() {
        binding = ActivityListOrderBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        addressBinding = DialogNhapDiaChiBinding.inflate((getLayoutInflater()));
    }

    public void showBottomDialog(){

        final Dialog dialog = new Dialog(this.getContext(),
                android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(addressBinding.getRoot());
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;

        dialog.getWindow().setAttributes(lp);

        addressBinding.btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shippingAddress = addressBinding.edtaddress.getText().toString();
                cartViewModel.placeOrder(cartViewModel.getMutableDrinkList().getValue(), cartViewModel.getTotal().getValue(),
                        shippingAddress, authViewModel.getUserId().getValue(), requireContext());
                Log.d("Cart frag", "dat hang");

                dialog.dismiss();
            }
        });

        addressBinding.btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        dialog.show();
    }
}
