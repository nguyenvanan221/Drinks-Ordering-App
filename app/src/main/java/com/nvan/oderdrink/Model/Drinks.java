package com.nvan.oderdrink.Model;

public class Drinks {

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
}
