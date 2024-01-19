package com.nvan.oderdrink.ViewModel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

    public void addDrinkToCart(String drinkId, String drinkName, int drinkPrice, String drinkImg,
                               int quantity, String userId, Context context){
        Drinks drink = new Drinks(drinkId, drinkName, drinkPrice, drinkImg, quantity);
        cartRepository.addToCart(drink, userId);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Đã thêm đồ uống vào giỏ hàng. Vui lòng kiểm tra giỏ hàng.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
