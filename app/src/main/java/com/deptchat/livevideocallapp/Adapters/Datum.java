package com.deptchat.livevideocallapp.Adapters;

import java.util.Comparator;
import java.util.UUID;


public class Datum {
    public static Comparator<Datum> yojanaModelComparator = (o1, o2) -> {
        Integer id1, id2;
        id1 = Integer.valueOf(o1.getId());
        id2 = Integer.valueOf(o2.getId());
        return id1.compareTo(id2);

    };
    private String id;
    private String Image;
    private String Title;
    private String Date;
    private String Time;
    private String url;
    private String pinned;
    private String enableDisable;
    private String sentence;

    public Datum(String id, String image, String title, String date, String time, String url, String pinned) {
        this.id = id;
        Image = image;
        Title = title;
        Date = date;
        Time = time;
        this.url = url;
        this.pinned = pinned;
    }

    public Datum(String id, String sentence) {
        this.id = id;
        this.sentence = sentence;
    }

    public Datum(String id, String image, String title, String date, String time, String url, String pinned, String enableDisable) {
        this.id = id;
        Image = image;
        Title = title;
        Date = date;
        Time = time;
        this.url = url;
        this.pinned = pinned;
        this.enableDisable = enableDisable;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return "https://gedgetsworld.in/PM_Kisan_Yojana/Kisan_Yojana_Images/"+Image;
    }

    public String getTitle() {
        return Title;
    }

    public String getDate() {
        return Date;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getTime() {
        return Time;
    }

    public String getUrl() {
        return url;
    }

    public String getPinned() {
        return pinned;
    }
    public String getEnableDisable() {
        return enableDisable;
    }
}