package com.example.playlist;

public class ItemObject {

    String title;
    String artist;
    String year;

    String sold;
    String country;
    String company;
    String price;



    public ItemObject(String title, String artist, String year) {

        this.title = title;
        this.artist = artist;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public ItemObject(String title, String artist, String year, String sold, String country, String company, String price) {
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.sold = sold;
        this.country = country;
        this.company = company;
        this.price = price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
    public String getSold() {
        return sold;
    }

    public void setSold(String sold) {
        this.sold = sold;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


}
