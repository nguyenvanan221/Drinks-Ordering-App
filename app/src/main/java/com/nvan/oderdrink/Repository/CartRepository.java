package com.nvan.oderdrink.Repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nvan.oderdrink.Model.Drinks;
import com.nvan.oderdrink.Model.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartRepository {
    private DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("Cart");
    private DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference("Orders");

    public void addToCart(Drinks drink, String userId) {
//        cartRef.child(userId).child(drink.getId())
        cartRef.child(drink.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // ton tai trong gio
                if (dataSnapshot.exists()) {
                    int currentQuantity = dataSnapshot.child("quantity").getValue(Integer.class);
                    cartRef.child(drink.getId()).child("quantity").setValue(currentQuantity + drink.getQuantity());
                } else {
                    cartRef.child(drink.getId()).setValue(drink);
//                    cartRef.child(userId).child(drink.getId()).setValue(drink);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void removeFromCart(String drinkId) {
        cartRef.child(drinkId).removeValue();
    }

    public void updateQuantity(String drinkId, int quantityChange) {

        cartRef.child(drinkId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int currentQuantity = dataSnapshot.child("quantity").getValue(Integer.class);
                    int newQuantity = currentQuantity + quantityChange;

                    if (newQuantity >= 0) {
                        cartRef.child(drinkId).child("quantity").setValue(newQuantity);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getDrinks(OnGetDrinkListener listener){
        cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer totalPrice = 0;
                List<Drinks> drinkList = new ArrayList<>();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Drinks drink = dataSnapshot.getValue(Drinks.class);
                    drinkList.add(drink);
                    totalPrice += drink.getPrice() * drink.getQuantity();
                }
                listener.onGetDrinkSucces(drinkList);
                Log.d("cart", String.valueOf(drinkList.size()));
                listener.onGetTotalPrice(totalPrice);
                Log.d("cart", String.valueOf(totalPrice));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onGetDrinkFailed(error.getMessage());
            }
        });

    }

    public void updateTotalPrice(OnGetDrinkListener listener){
        cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer total = 0;
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Drinks drink = dataSnapshot.getValue(Drinks.class);
                    total += drink.getPrice() * drink.getQuantity();
                }

                listener.onGetTotalPrice(total);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void placeOrder(Order order, String userId, OnOrderPlacedListener listener) {
        String orderId = orderRef.push().getKey();
        order.setId(orderId);
        orderRef.child(userId).child(orderId).setValue(order).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                listener.onOrderPlaced();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onError(e.getMessage());
            }
        });

        // Xóa toàn bộ giỏ hàng sau khi đặt hàng
        //cartRef.removeValue();

        //listener.onOrderPlaced();
    }

    public interface OnGetDrinkListener {
        void onGetDrinkSucces(List<Drinks> drinksList);
        void onGetDrinkFailed(String errorMessage);
        void onGetTotalPrice(Integer totalPrice);
    }

    public interface OnOrderPlacedListener {
        void onOrderPlaced();

        void onError(String errorMessage);
    }
}
