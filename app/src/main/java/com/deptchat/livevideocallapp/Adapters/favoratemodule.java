package com.deptchat.livevideocallapp.Adapters;

public class favoratemodule {
   private String  name,image,video;
   int id;

    public favoratemodule(int id, String name, String image, String video) {
        this.name = name;
        this.image = image;
        this.video = video;
        this.id = id;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
