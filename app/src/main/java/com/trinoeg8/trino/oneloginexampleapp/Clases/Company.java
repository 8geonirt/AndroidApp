package com.trinoeg8.trino.oneloginexampleapp.Clases;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.trinoeg8.trino.oneloginexampleapp.R;

/**
 * Created by Trino on 17/01/2015.
 */
public class Company {
    private int id;
    private String name;
    private String icon_url;
    private String url;
    private String category;
    private Bitmap image;
    private Context context;
    public Company(){

    }
    public void setUrl(String url){
        this.url = url;
    }
    public String getUrl(){
        return this.url;
    }
    public void setContext(Context context){
        this.context = context;
    }
    public Context getContext(){
        return context;
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

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getCategory() {
        return category;
    }
    public Bitmap getImage() {
        if(image==null){
            image = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
        }
        return image;
    }
    public void setCategory(String category) {
        this.category = category;
    }
}
