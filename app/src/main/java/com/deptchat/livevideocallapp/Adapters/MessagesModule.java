package com.deptchat.livevideocallapp.Adapters;

public class MessagesModule {

 String message ;
 int id,messageimage;

    public MessagesModule(String message, int id) {
        this.message = message;
        this.id = id;
    }

    public MessagesModule(int id, int messageimage) {
        this.id = id;
        this.messageimage = messageimage;
    }

    public MessagesModule(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMessageimage() {
        return messageimage;
    }

    public void setMessageimage(int messageimage) {
        this.messageimage = messageimage;
    }
}
