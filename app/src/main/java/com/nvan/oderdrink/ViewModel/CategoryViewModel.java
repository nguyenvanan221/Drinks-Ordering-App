package com.nvan.oderdrink.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nvan.oderdrink.Model.Category;
import com.nvan.oderdrink.Model.Drinks;
import com.nvan.oderdrink.Repository.CategoryRepository;
import com.nvan.oderdrink.listener.IFirebaseCallbackListener;

import java.util.List;

public class CategoryViewModel extends ViewModel implements IFirebaseCallbackListener<List<Category>> {
    private final MutableLiveData<List<Category>> mutableCategoryList = new MutableLiveData<List<Category>>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final CategoryRepository categoryRepository;

    public CategoryViewModel() {
        categoryRepository = new CategoryRepository(this);
    }

    public void loadData(){
        categoryRepository.loadData();
    }

    public MutableLiveData<List<Category>> getMutableCategoryList() {
        return mutableCategoryList;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    @Override
    public void onFirebaseLoadSucces(List<Category> items) {
        mutableCategoryList.setValue(items);
    }

    @Override
    public void onFirebaseLoadFailed(String message) {
        errorMessage.setValue(message);
    }
}
