package com.nvan.oderdrink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.nvan.oderdrink.Fragment.CartFragment;
import com.nvan.oderdrink.Fragment.ContactFragment;
import com.nvan.oderdrink.Fragment.HomePageFragment;
import com.nvan.oderdrink.Fragment.InforUserFragment;
import com.nvan.oderdrink.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initBinding();
        initChooseItemNavi();

        getSupportFragmentManager().beginTransaction().replace(binding.fragmentcontainer.getId(), new HomePageFragment()).commit();

        if (getIntent().getBooleanExtra("openCartFragment", false)) {
            getSupportFragmentManager().beginTransaction().replace(binding.fragmentcontainer.getId(), new CartFragment()).commit();
        }
    }

    private void initBinding() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void initChooseItemNavi() {
        binding.bottomNavigation.setOnItemSelectedListener(item -> {

            Fragment selectedFragment = null;
            switch (item.getItemId()){
                case R.id.home:
                    selectedFragment = new HomePageFragment();
                    break;
                case R.id.cart:
                    selectedFragment = new CartFragment();
                    break;
                case R.id.contact:
                    selectedFragment = new ContactFragment();
                    break;
                case R.id.user:
                    selectedFragment = new InforUserFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(binding.fragmentcontainer.getId(), selectedFragment).commit();
            }
            return true;
        });
    }

}