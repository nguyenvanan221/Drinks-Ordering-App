package com.nvan.oderdrink.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nvan.oderdrink.Adapter.CategoryAdapter;
import com.nvan.oderdrink.Adapter.DrinkAdapter;
import com.nvan.oderdrink.Model.Category;
import com.nvan.oderdrink.ViewModel.CategoryViewModel;
import com.nvan.oderdrink.ViewModel.DrinkViewModel;
import com.nvan.oderdrink.databinding.ActivityHomepageBinding;

public class HomePageFragment extends Fragment {

    private ActivityHomepageBinding binding;
    private DrinkViewModel drinkViewModel;
    private CategoryViewModel categoryViewModel;
    private String selectedCategory;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initBinding();
        initViewModel();
        initObserve();
        initViews();

        categoryViewModel.loadData();
        return view;
    }

    private void initViews() {
        binding.rcvdrinkitem.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rcvcategory.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void initObserve() {
        drinkViewModel.getErrorMessage().observe(this.getViewLifecycleOwner(), errorMessage -> {
            Toast.makeText(this.getContext(), errorMessage, Toast.LENGTH_SHORT).show();
        });
        drinkViewModel.getMutableDrinkList().observe(this.getViewLifecycleOwner(), listDrink -> {
            DrinkAdapter drinkAdapter = new DrinkAdapter(this.getContext(), listDrink);
            binding.rcvdrinkitem.setAdapter(drinkAdapter);
        });

        categoryViewModel.getErrorMessage().observe(this.getViewLifecycleOwner(), errorMessage -> {
            Toast.makeText(this.getContext(), errorMessage, Toast.LENGTH_SHORT).show();
        });
        categoryViewModel.getMutableCategoryList().observe(this.getViewLifecycleOwner(), listCategory -> {
            CategoryAdapter categoryAdapter = new CategoryAdapter(this.getContext(), listCategory, new CategoryAdapter.OnCategoryClickListener() {
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
        view = binding.getRoot();
    }
}
