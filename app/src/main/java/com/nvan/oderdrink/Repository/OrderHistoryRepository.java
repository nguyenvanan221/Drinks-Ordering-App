package com.nvan.oderdrink.Repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nvan.oderdrink.Model.Drinks;
import com.nvan.oderdrink.Model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryRepository {
    private final DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference("Orders");

    public void getOrder(String userId, OnGetDrinkListener listener){
        orderRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Order> orderList = new ArrayList<>();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Order order = dataSnapshot.getValue(Order.class);
                    orderList.add(order);
                }
                listener.onGetOrderSucces(orderList);
                Log.d("order", String.valueOf(orderList.size()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public interface OnGetDrinkListener{
        void onGetOrderSucces(List<Order> ordersList);
    }
}
