package com.nvan.oderdrink.Model;

import java.util.List;

public class Order {
    private String id;

    private List<Drinks> drinksList;

    private String orderDate;
    private Integer total;
    private String userId;
    private String status;
    private String shippingAddress;

    public Order() {
    }

    public Order(List<Drinks> drinksList, String orderDate, Integer total, String status, String shippingAddress) {
        this.drinksList = drinksList;
        this.orderDate = orderDate;
        this.total = total;
        this.status = status;
        this.shippingAddress = shippingAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Drinks> getDrinksList() {
        return drinksList;
    }

    public void setDrinksList(List<Drinks> drinksList) {
        this.drinksList = drinksList;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
