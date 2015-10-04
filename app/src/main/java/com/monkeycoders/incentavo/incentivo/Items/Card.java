package com.monkeycoders.incentavo.incentivo.Items;

public class Card {
    private int id;
    private String name;
    private String url_image;
    private Double price;
    private boolean is_avaible;

    public Card(){

    }

    public Card(String name, String url_image, Double price, boolean is_avaible) {
        this.name = name;
        this.url_image = url_image;
        this.price = price;
        this.is_avaible = is_avaible;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    public boolean is_avaible() {
        return is_avaible;
    }

    public void setIs_avaible(boolean is_avaible) {
        this.is_avaible = is_avaible;
    }
}
