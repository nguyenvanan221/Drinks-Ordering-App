package com.nvan.oderdrink.ViewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nvan.oderdrink.Model.Drinks;
import com.nvan.oderdrink.Repository.CartRepository;

import java.util.List;

public class DrinkDetailViewModel extends ViewModel {
    private final MutableLiveData<List<Drinks>> mutableCart = new MutableLiveData<>();
    private final CartRepository cartRepository;

    public MutableLiveData<List<Drinks>> getMutableCart() {
        return mutableCart;
    }

    public DrinkDetailViewModel() {
        cartRepository = new CartRepository();
    }

    public void addDrinkToCart(String drinkId, String drinkName, int drinkPrice, String drinkImg, int quantity, String userId){
        Drinks drink = new Drinks(drinkId, drinkName, drinkPrice, drinkImg, quantity);
        cartRepository.addToCart(drink, userId);
    }

}
