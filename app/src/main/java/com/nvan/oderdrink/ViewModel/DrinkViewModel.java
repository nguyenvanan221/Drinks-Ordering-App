package com.nvan.oderdrink.ViewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nvan.oderdrink.Model.Drinks;
import com.nvan.oderdrink.Repository.DrinkRepository;
import com.nvan.oderdrink.listener.IFirebaseCallbackListener;

import java.util.List;

public class DrinkViewModel extends ViewModel implements IFirebaseCallbackListener<List<Drinks>> {
    private final MutableLiveData<List<Drinks>> mutableDrinkList = new MutableLiveData<List<Drinks>>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final DrinkRepository drinkRepository;

    public DrinkViewModel() {
        drinkRepository = new DrinkRepository(this);
    }

    public void loadData(){
        drinkRepository.loadData();
    }

    public void loadDrinkByCategory(String category){
        drinkRepository.loadDrinkByCategory(category);
    }

    public MutableLiveData<List<Drinks>> getMutableDrinkList() {
        return mutableDrinkList;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    @Override
    public void onFirebaseLoadSucces(List<Drinks> items) {
        mutableDrinkList.setValue(items);
    }

    @Override
    public void onFirebaseLoadFailed(String message) {
        errorMessage.setValue(message);
    }
}
