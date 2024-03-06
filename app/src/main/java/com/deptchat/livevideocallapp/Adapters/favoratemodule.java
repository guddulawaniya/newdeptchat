package com.deptchat.livevideocallapp.Adapters;

public class favoratemodule {
   private String name,image,video;

    public favoratemodule(String name, String image, String video) {
        this.name = name;
        this.image = image;
        this.video = video;
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
