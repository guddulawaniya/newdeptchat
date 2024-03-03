package com.example.deptchat.Adapters;

public class YourDataModel {

    private String text;
    private String img;
    private String video;

    // Getters and setters


    public YourDataModel(String text, String img, String video) {
        this.text = text;
        this.img = img;
        this.video = video;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return "https://gedgetsworld.in/PM_Kisan_Yojana" + img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getVideo() {
        return "https://gedgetsworld.in/PM_Kisan_Yojana" + video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
