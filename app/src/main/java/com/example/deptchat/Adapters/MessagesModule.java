package com.example.deptchat.Adapters;

public class MessagesModule {

    String message;
    int uId;
    String image;

    public MessagesModule(String message, int uId) {
        this.message = message;
        this.uId = uId;
    }

    public MessagesModule(String image) {
        this.image = image;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
