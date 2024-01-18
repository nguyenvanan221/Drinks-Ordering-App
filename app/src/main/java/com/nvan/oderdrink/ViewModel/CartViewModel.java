package com.nvan.oderdrink.ViewModel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nvan.oderdrink.Model.Drinks;
import com.nvan.oderdrink.Model.Order;
import com.nvan.oderdrink.Repository.CartRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CartViewModel extends ViewModel {
    private final MutableLiveData<List<Drinks>> mutableDrinkList = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    private final MutableLiveData<Integer> total = new MutableLiveData<>();
    private final CartRepository cartRepository = new CartRepository();

    public CartViewModel() {
        total.setValue(0);
    }
    public void loadDrinkToCart(){
        cartRepository.getDrinks(new CartRepository.OnGetDrinkListener() {
            @Override
            public void onGetDrinkSucces(List<Drinks> drinkList) {
                mutableDrinkList.setValue(drinkList);
            }

            @Override
            public void onGetDrinkFailed(String errorMessage) {
                error.setValue(errorMessage);
            }

            @Override
            public void onGetTotalPrice(Integer totalPrice) {
                total.setValue(totalPrice);
            }
        });
    }

    public void removeDrinkFromCart(String drinkId, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc muốn xóa sản phẩm khỏi giỏ hàng?");
        Log.d("cart repo", "tao dialog");

        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cartRepository.removeFromCart(drinkId);

                loadDrinkToCart();
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public MutableLiveData<List<Drinks>> getMutableDrinkList() {
        return mutableDrinkList;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public MutableLiveData<Integer> getTotal() {
        return total;
    }

    public void increaseQuantity(Drinks drink) {
        cartRepository.updateQuantity(drink.getId(), 1);

        total.setValue(total.getValue() + drink.getPrice());
    }

    public void decreaseQuantity(Drinks drink) {
        cartRepository.updateQuantity(drink.getId(), -1);
        total.setValue(total.getValue() - drink.getPrice());
    }

    public void placeOrder(List<Drinks> drinkList, int totalPrice, String shippingAddress, String userId) {
        Order order = new Order();
        order.setDrinksList(drinkList);
        order.setTotal(totalPrice);
        order.setShippingAddress(shippingAddress);
        order.setStatus("Processing");
        order.setOrderDate(getCurrentDateTime());

        cartRepository.placeOrder(order, userId, new CartRepository.OnOrderPlacedListener() {
            @Override
            public void onOrderPlaced() {
                Log.d("Cart view", "Dat hang thanh cong");
            }

            @Override
            public void onError(String errorMessage) {
                Log.d("Cart view", "Dat hang that bai");
            }
        });
       
    }

    public static String getCurrentDateTime() {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date currentDateTime = Calendar.getInstance().getTime();
        return dateTimeFormat.format(currentDateTime);
    }

}
