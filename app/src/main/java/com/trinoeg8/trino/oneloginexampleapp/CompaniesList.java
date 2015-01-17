package com.trinoeg8.trino.oneloginexampleapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.trinoeg8.trino.oneloginexampleapp.Clases.CompaniesDataSource;
import com.trinoeg8.trino.oneloginexampleapp.Clases.Company;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Trino on 17/01/2015.
 */
public class CompaniesList extends ActionBarActivity{
    private CompaniesDataSource datasource;
    ArrayList<Company> list = null;
    ListView listView = null;
    CompaniesAdapter adapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companies_list);
        listView = (ListView)findViewById(R.id.companiesList);
        datasource = new CompaniesDataSource(this);
        try {
            datasource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(datasource!=null){
            list = datasource.getAllCompanies();
            for(Company c:list){
                c.setContext(this);
            }
        }
        Log.i("Total",Integer.toString(list.size()));
        adapter = new CompaniesAdapter(this,0,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Company c = list.get(position);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(c.getUrl()));
                startActivity(i);
            }
        });
    }
    @Override
    protected void onResume(){
        super.onResume();
    }
}
