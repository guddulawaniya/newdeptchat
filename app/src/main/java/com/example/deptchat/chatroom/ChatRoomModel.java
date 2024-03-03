package com.example.deptchat.chatroom;

public class ChatRoomModel {
    public static final int TEXT_TYPE = 0;
    public int count;
    public String text;
    public int type;

    public ChatRoomModel(int i, String str, int i2) {
        this.type = i;
        this.text = str;
        this.count = i2;
    }

}
