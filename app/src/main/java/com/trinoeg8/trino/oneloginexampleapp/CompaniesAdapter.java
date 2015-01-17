package com.trinoeg8.trino.oneloginexampleapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.trinoeg8.trino.oneloginexampleapp.Clases.Company;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Trino on 17/01/2015.
 */
public class CompaniesAdapter extends ArrayAdapter<Company> {
    private ArrayList<Company> companies;
    private Context context;
    private String tempCategory ="";
    public CompaniesAdapter(Context context, int textViewResourceId, ArrayList<Company> companies) {
        super(context, textViewResourceId, companies);
        this.companies= companies;
        this.context = context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        Company company= companies.get(position);
        if(company!=null){
            if (v == null) {
                LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.company_row,parent,false);
            }
            String category = company.getCategory();
            if(!tempCategory.equalsIgnoreCase(category)) {
                TextView categoryName = (TextView) v.findViewById(R.id.category_name);
                if (categoryName != null) {
                    categoryName.setText(company.getCategory());
                }
                tempCategory = category;
            }else{
                TextView categoryName = (TextView) v.findViewById(R.id.category_name);
                categoryName.setText("");
            }
            TextView companyName = (TextView) v.findViewById(R.id.company_name);
            ImageView companyIcon = (ImageView) v.findViewById(R.id.logo);
            if (companyName != null) {
                companyName.setText(company.getName());
            }
            if (companyIcon != null) {
                companyIcon.setImageBitmap(company.getImage());
            }
        }
        return v;
    }
}
