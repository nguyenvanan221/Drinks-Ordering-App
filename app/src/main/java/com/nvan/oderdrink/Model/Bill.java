package com.nvan.oderdrink.Model;

import java.util.ArrayList;

public class Bill {
    String id;
    Integer total;
    String time;
    public ArrayList<Drinks> listDrinks;
    public ArrayList<Integer>listQuantity;

    public Bill() {
        this.total = 0;
        this.id = "";
        this.time = "";
        this.listDrinks = new ArrayList<Drinks>();
        this.listQuantity = new ArrayList<Integer>();
    }

    public Bill(String id, Integer total, String time, ArrayList<Drinks> listDrinks, ArrayList<Integer> listQuantity) {
        this.id = id;
        this.total = 0;
        this.time = time;
        this.listDrinks = new ArrayList<Drinks>();
        this.listQuantity = new ArrayList<Integer>();
    }

    public void AddNewDrink(Drinks drinks, Integer quantity){
        if (quantity <=0){
            return;
        }
        boolean isExist = false;
        for (int i=0; i<listDrinks.size(); i++){
            if (listDrinks.get(i).getId().equals(drinks.getId())){
                listQuantity.set(i, listQuantity.get(i) + quantity);
                isExist = true;
                break;
            }
        }
        if (!isExist){
            listDrinks.add(drinks);
            listQuantity.add(quantity);
        }

        this.total += drinks.getPrice() * quantity;

    }

    public void UpdateQuantity(Drinks drinks, Integer quantity){
        int pos = -1;
        for (int i=0; i<listDrinks.size(); i++){
            if (listDrinks.get(i).getId().equals(drinks.getId())){
                pos = i;
                break;
            }
        }

        if (pos == -1 && quantity <=0){
            return;
        }
        if (pos == -1 && quantity >0){
            AddNewDrink(drinks,quantity);
            return;
        }

        if (pos != -1){
            listQuantity.set(pos, quantity + listQuantity.get(pos));
        }

        if (quantity <=0){
            listQuantity.remove(pos);
            listDrinks.remove(listDrinks.get(pos));
        }

        this.UpdateTotal();
    }

    public void UpdateTotal(){
        this.total =0;
        for (int i=0; i<listDrinks.size(); i++){
            this.total += listDrinks.get(i).getPrice() *listQuantity.get(i);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setListDrinks(ArrayList<Drinks> listDrinks) {
        this.listDrinks = listDrinks;
    }

    public void setListQuantity(ArrayList<Integer> listQuantity) {
        this.listQuantity = listQuantity;
    }
}
