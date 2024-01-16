package com.nvan.oderdrink.Activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nvan.oderdrink.Adapter.CategoryAdapter;
import com.nvan.oderdrink.Adapter.DrinkAdapter;
import com.nvan.oderdrink.Model.Category;
import com.nvan.oderdrink.ViewModel.CategoryViewModel;
import com.nvan.oderdrink.ViewModel.DrinkViewModel;
import com.nvan.oderdrink.databinding.ActivityHomepageBinding;
import com.nvan.oderdrink.databinding.ListDrinksItemBinding;

public class HomepageActivity extends AppCompatActivity {
    private ActivityHomepageBinding binding;
    private DrinkViewModel drinkViewModel;
    private CategoryViewModel categoryViewModel;
    private String selectedCategory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        initBinding();
        initViewModel();
        initObserve();
        initViews();

        categoryViewModel.loadData();
        //drinkViewModel.loadDrinkByCategory("coffee");
    }

    private void initViews() {
        binding.rcvdrinkitem.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rcvcategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void initObserve() {
        drinkViewModel.getErrorMessage().observe(this, errorMessage -> {
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        });
        drinkViewModel.getMutableDrinkList().observe(this, listDrink -> {
            DrinkAdapter drinkAdapter = new DrinkAdapter(HomepageActivity.this, listDrink);
            binding.rcvdrinkitem.setAdapter(drinkAdapter);
        });

        categoryViewModel.getErrorMessage().observe(this, errorMessage -> {
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        });
        categoryViewModel.getMutableCategoryList().observe(this, listCategory -> {
            CategoryAdapter categoryAdapter = new CategoryAdapter(HomepageActivity.this, listCategory, new CategoryAdapter.OnCategoryClickListener() {
                @Override
                public void onCategoryClick(Category category) {
                    selectedCategory = category.getName();
                    drinkViewModel.loadDrinkByCategory(selectedCategory);
                }
            });
            binding.rcvcategory.setAdapter(categoryAdapter);
        });
    }

    // khoi tao view-model
    private void initViewModel() {
        drinkViewModel = new ViewModelProvider(this).get(DrinkViewModel.class);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
    }

    private void initBinding() {
        binding = ActivityHomepageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
