package com.nvan.oderdrink.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nvan.oderdrink.Model.Order;
import com.nvan.oderdrink.Repository.OrderHistoryRepository;

import java.util.List;

public class OrderHistoryViewModel extends ViewModel {
    private final MutableLiveData<List<Order>> mutableOrderList = new MutableLiveData<>();
    private OrderHistoryRepository orderHistoryRepository = new OrderHistoryRepository();

    public OrderHistoryViewModel() {
    }

    public void getOrder(String userId){
        orderHistoryRepository.getOrder(userId, new OrderHistoryRepository.OnGetDrinkListener() {
            @Override
            public void onGetOrderSucces(List<Order> ordersList) {
                mutableOrderList.setValue(ordersList);
            }
        });
    }

    public MutableLiveData<List<Order>> getMutableOrderList() {
        return mutableOrderList;
    }
}
