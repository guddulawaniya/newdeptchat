
package com.deptchat.livevideocallapp.Adapters;
import java.util.List;

public class Chatdatamodule {
    private String image;
    private String id;
    private String text;

    public String getImage() {
        return "https://gedgetsworld.in/PM_Kisan_Yojana/image_app/"+image;
    }
    public void setImage(String value) { this.image = value; }

    public String getid() { return id; }
    public void setid(String value) { this.id = value; }

    public String getText() { return text; }
    public void setText(String value) { this.text = value; }
}
