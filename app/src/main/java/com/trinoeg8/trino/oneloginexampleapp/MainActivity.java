package com.trinoeg8.trino.oneloginexampleapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.trinoeg8.trino.oneloginexampleapp.Clases.CompaniesDataSource;
import com.trinoeg8.trino.oneloginexampleapp.Clases.Company;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
public class MainActivity extends ActionBarActivity {
public static String SERVER_URL = "https://app.onelogin.com/mobile/v2/logins?version=4.0&session_token=ed92039a09f3cfcb3402a2bb5d74f9455135b3e8";
    Button btnGetData;
    private CompaniesDataSource datasource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGetData = (Button)findViewById(R.id.btnGetData);
        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetData data= new GetData();
                data.execute();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public class GetData  extends AsyncTask<String, String, String> {
        private ProgressDialog pDialog = null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Recuperando información...");
            pDialog.setIndeterminate(false);
            pDialog.setIcon(R.drawable.ic_launcher);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            int success;
            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(SERVER_URL);
                HttpResponse response = client.execute(get);
                StatusLine statusLine = response.getStatusLine();
                if(statusLine.getStatusCode() == 200) {
                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();
                    XmlPullParserFactory pullParserFactory;
                    try {
                        pullParserFactory = XmlPullParserFactory.newInstance();
                        XmlPullParser parser = pullParserFactory.newPullParser();
                        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                        parser.setInput(content, null);
                        parseXML(parser);
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("SERVER_ERROR", "Status Code: " + statusLine.getStatusCode());
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
        }
    }
    private void parseXML(XmlPullParser parser) throws XmlPullParserException,IOException
    {
        ArrayList<Company> companies = null;
        int eventType = parser.getEventType();
        Company currentCompany = null;
        while (eventType != XmlPullParser.END_DOCUMENT){
            String name = null;
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    companies = new ArrayList();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if(name.equalsIgnoreCase("site")) {
                        currentCompany = new Company();
                        parser.next();
                    }
                    if (currentCompany != null){
                        if (name.equalsIgnoreCase("name")){
                            currentCompany.setName(parser.nextText());
                        } else if (name.equalsIgnoreCase("icon_url")){
                            currentCompany.setIcon_url(parser.nextText());
                        } else if (name.equalsIgnoreCase("category")){
                            currentCompany.setCategory(parser.nextText());
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase("site") && currentCompany!= null){
                        Log.i("Guarda Company","Si");
                        companies.add(currentCompany);
                    }
            }
            eventType = parser.next();
        }
        saveCompanies(companies);
    }
    private void saveCompanies(ArrayList<Company> companies) {
        Log.i("Total compañías",Integer.toString(companies.size()));
        Iterator<Company> it = companies.iterator();
        datasource = new CompaniesDataSource(this);
        try {
            datasource.open();
            datasource.deleteAllCompanies();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        while (it.hasNext()) {
            Company c = it.next();
            if(datasource!=null){
                try {
                    datasource.insertCompany(c);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        Intent i = new Intent(this,CompaniesList.class);
        startActivity(i);
        finish();
    }

}
