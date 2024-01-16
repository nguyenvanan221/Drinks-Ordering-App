package com.nvan.oderdrink.Repository;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nvan.oderdrink.Model.Category;
import com.nvan.oderdrink.Model.Drinks;
import com.nvan.oderdrink.listener.IFirebaseCallbackListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {

    private IFirebaseCallbackListener<List<Category>> iFirebaseCallbackListener;
    static DatabaseReference categoryRef = FirebaseDatabase.getInstance().getReference("Category");

    public CategoryRepository(IFirebaseCallbackListener<List<Category>> iFirebaseCallbackListener) {
        this.iFirebaseCallbackListener = iFirebaseCallbackListener;
    }

    public void loadData(){
        ArrayList<Category> listCategory = new ArrayList<>();
        categoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Category category = dataSnapshot.getValue(Category.class);
                    listCategory.add(category);
                }
                iFirebaseCallbackListener.onFirebaseLoadSucces(listCategory);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                iFirebaseCallbackListener.onFirebaseLoadFailed(error.getMessage());
            }
        });
    }
}
