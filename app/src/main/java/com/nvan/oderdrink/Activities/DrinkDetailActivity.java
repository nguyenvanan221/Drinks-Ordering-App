package com.nvan.oderdrink.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.nvan.oderdrink.Fragment.CartFragment;
import com.nvan.oderdrink.MainActivity;
import com.nvan.oderdrink.Model.Drinks;
import com.nvan.oderdrink.R;
import com.nvan.oderdrink.ViewModel.CartViewModel;
import com.nvan.oderdrink.ViewModel.DrinkDetailViewModel;
import com.nvan.oderdrink.databinding.ActivityDetailDrinksBinding;

import java.util.List;

public class DrinkDetailActivity extends AppCompatActivity {
    private ActivityDetailDrinksBinding binding;
    private DrinkDetailViewModel drinkDetailViewModel;
    private CartViewModel cartViewModel;

    private List<Drinks> drinkList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initBinding();
        initViewModel();
        getDataIntent();
        initListener();
        initObserve();

        cartViewModel.loadDrinkToCart();

    }

    private void initObserve() {
        cartViewModel.getMutableDrinkList().observe(this, drinksList -> {
            drinkList = drinksList;
        });
    }

    private void initViewModel() {
        drinkDetailViewModel = new ViewModelProvider(this).get(DrinkDetailViewModel.class);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
    }

    private void initListener() {
        binding.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer temp = Integer.parseInt( (String) binding.tvquantity.getText() );
                temp += 1;
                binding.tvquantity.setText( temp.toString() );

            }
        });

        binding.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer temp = Integer.parseInt( (String) binding.tvquantity.getText() );
                temp = (temp > 1) ? temp-1 : 1;
                binding.tvquantity.setText( temp.toString() );
            }
        });

        binding.imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        binding.imgcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtra("openCartFragment", true);
                v.getContext().startActivity(intent);
            }
        });

        binding.btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String drinkId = getIntent().getStringExtra("drinkId");
                String drinkName = (String) binding.tvname.getText();
                Integer drinkPrice = Integer.parseInt( (String) binding.tvprice.getText() );
                String drinkImg = getIntent().getStringExtra("drinkImg");
                Integer quantity = Integer.parseInt( (String) binding.tvquantity.getText() );
                drinkDetailViewModel.addDrinkToCart(drinkId,drinkName,drinkPrice, drinkImg, quantity);
            }
        });
    }

    private void initBinding() {
        binding = ActivityDetailDrinksBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void getDataIntent() {
        binding.tvname.setText(getIntent().getStringExtra("drinkName"));

        Glide.with(this).load(getIntent().getStringExtra("drinkImg")).into(binding.imgdrink);
        binding.tvprice.setText((getIntent().getStringExtra("drinkPrice")) );
        binding.tvdes.setText(getIntent().getStringExtra("drinkDescreoption"));
    }

}
