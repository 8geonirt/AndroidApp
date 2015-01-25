package com.trinoeg8.trino.oneloginexampleapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.trinoeg8.trino.oneloginexampleapp.Clases.Adapter;
import com.trinoeg8.trino.oneloginexampleapp.Clases.CompaniesDataSource;
import com.trinoeg8.trino.oneloginexampleapp.Clases.Company;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Trino on 17/01/2015.
 */
public class CompaniesList extends ActionBarActivity implements Adapter.ViewHolder.ClickListener{
    private CompaniesDataSource datasource;
    ArrayList<Company> list = null;
    ListView listView = null;
    Adapter adapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companies_list);
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
        adapter = new Adapter(this,list,this,"");
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    public void onItemClicked(int position){
        Company c = adapter.getSelectedItem(position);
        if(c.getUrl().startsWith("http")){
            Toast.makeText(getApplicationContext(),"Abriendo página",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(c.getUrl()));
            startActivity(i);
        }else{
            Toast.makeText(getApplicationContext(),"Error al obtener url",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onItemLongClicked(int position) {
        return false;
    }
}
