package com.nvan.oderdrink.Model;

import android.os.Parcel;
import android.os.Parcelable;


public class Drinks implements Parcelable {

    private String category;
    private String id;

    private String name;

    private Integer price;

    private String image;

    private String descreption;

    private Integer quantity;

    public Drinks() {
    }

    public Drinks(String category, String name, Integer price, String image) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public Drinks(String name, Integer price, String image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public Drinks(String id, String name, Integer price, String image, Integer quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
    }
    public Drinks(String name,String id, String image, int price,String category, String descreption) {
        this.name = name;
        this.id = id;
        this.image = image;
        this.price = price;
        this.category = category;
        this.descreption = descreption;
    }

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDescreption() {
        return descreption;
    }

    public void setDescreption(String descreption) {
        this.descreption = descreption;
    }
    protected Drinks(Parcel in) {
        name = in.readString();
        id = in.readString();
        image = in.readString();
        price = in.readInt();
        category = in.readString();
        descreption = in.readString();
    }

    public static final Creator<Drinks> CREATOR = new Creator<Drinks>() {
        @Override
        public Drinks createFromParcel(Parcel in) {
            return new Drinks(in);
        }

        @Override
        public Drinks[] newArray(int size) {
            return new Drinks[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(id);
        dest.writeString(image);
        dest.writeInt(price);
        dest.writeString(category);
        dest.writeString(descreption);
    }
}
