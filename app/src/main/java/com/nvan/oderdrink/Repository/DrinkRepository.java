package com.nvan.oderdrink.Repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nvan.oderdrink.Model.Drinks;
import com.nvan.oderdrink.listener.IFirebaseCallbackListener;

import java.util.ArrayList;
import java.util.List;

public class DrinkRepository {

    private IFirebaseCallbackListener<List<Drinks>> iFirebaseCallbackListener;
    static DatabaseReference drinkRef = FirebaseDatabase.getInstance().getReference("Drinks");

    public DrinkRepository(IFirebaseCallbackListener<List<Drinks>> iFirebaseCallbackListener) {
        this.iFirebaseCallbackListener = iFirebaseCallbackListener;
    }

    public void loadData(){
        ArrayList<Drinks> listDrinks = new ArrayList<>();
        drinkRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Drinks drinks = dataSnapshot.getValue(Drinks.class);
                    listDrinks.add(drinks);
                }
                iFirebaseCallbackListener.onFirebaseLoadSucces(listDrinks);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                iFirebaseCallbackListener.onFirebaseLoadFailed(error.getMessage());
            }
        });
    }

    public static void insert(Context context, Drinks drink){
        drinkRef.push().setValue(drink, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null){
                    Toast.makeText(context, "Add drink success", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Add drink fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void loadDrinkByCategory(String category){
        ArrayList<Drinks> listDrinks = new ArrayList<>();
        drinkRef.orderByChild("category").equalTo(category).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Drinks drinks = dataSnapshot.getValue(Drinks.class);
                    listDrinks.add(drinks);
                }
                iFirebaseCallbackListener.onFirebaseLoadSucces(listDrinks);
                Log.d("this", String.valueOf(listDrinks.size()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                iFirebaseCallbackListener.onFirebaseLoadFailed(error.getMessage());
            }
        });
    }

}
