package com.trinoeg8.trino.oneloginexampleapp.Clases;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import com.trinoeg8.trino.oneloginexampleapp.CompaniesAdapter;
import com.trinoeg8.trino.oneloginexampleapp.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Trino on 17/01/2015.
 */
public class Company {
    private int id;
    private String name;
    private String icon_url;
    private String category;
    private Bitmap image;
    private CompaniesAdapter adapter;
    public Company(){

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
        return image;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void loadImage(CompaniesAdapter adapter) {
        this.adapter = adapter;
        if (this.icon_url != null && !icon_url.equals("")) {
            new ImageLoadTask().execute(icon_url);
        }
    }
    private class ImageLoadTask extends AsyncTask<String, String, Bitmap> {

        @Override
        protected void onPreExecute() {
        }
        protected Bitmap doInBackground(String... param) {
            try {
                URL url = new URL(param[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap icon = BitmapFactory.decodeStream(input);
                return icon;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onProgressUpdate(String... progress) {
        }

        protected void onPostExecute(Bitmap icon) {
            if (icon != null) {
                image = icon;
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}
