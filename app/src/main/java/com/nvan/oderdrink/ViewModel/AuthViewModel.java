package com.nvan.oderdrink.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthViewModel extends ViewModel {
    private MutableLiveData<String> userId = new MutableLiveData<>();
    private FirebaseAuth firebaseAuth;

    public AuthViewModel() {
        firebaseAuth = FirebaseAuth.getInstance();
        updateUserId();
    }

    public MutableLiveData<String> getUserId() {
        return userId;
    }

    public void updateUserId() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            userId.setValue(user.getUid());
        } else {
            userId.setValue(null);
        }
    }
}
