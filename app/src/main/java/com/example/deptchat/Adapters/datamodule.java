package com.example.deptchat.Adapters;

public class datamodule {
    int image;
    String name, count, location;

    public datamodule(int image, String name, String count, String location) {
        this.image = image;
        this.name = name;
        this.count = count;
        this.location = location;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
