package com.trinoeg8.trino.oneloginexampleapp.Clases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * Created by Trino on 17/01/2015.
 */
public class CompaniesDataSource {
    // Database fields
    private SQLiteDatabase database;
    private SQliteHelper dbHelper;
    private String[] allColumns = { SQliteHelper.COLUMN_ID,
            SQliteHelper.COLUMN_NAME,SQliteHelper.COLUMN_CATEGORY,SQliteHelper.COLUMN_ICON};

    public CompaniesDataSource(Context context) {
        dbHelper = new SQliteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void insertCompany(Company company) {
        ContentValues values = new ContentValues();
        values.put("name", company.getName());
        values.put("category", company.getCategory());
        values.put("icon", company.getIcon_url());
        long i = database.insert(SQliteHelper.TABLE_NAME, null, values);
    }

    public ArrayList<Company> getAllCompanies() {
        ArrayList<Company> companies= new ArrayList<Company>();
        Cursor cursor = database.query(SQliteHelper.TABLE_NAME,
                allColumns, null, null, null, null, "category asc");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Company c = cursorToCompany(cursor);
            companies.add(c);
            cursor.moveToNext();
        }
        cursor.close();
        return companies;
    }

    private Company cursorToCompany(Cursor cursor) {
        Company company= new Company();
        company.setId(cursor.getInt(0));
        company.setName(cursor.getString(1));
        company.setCategory(cursor.getString(2));
        company.setIcon_url(cursor.getString(3));
        return company;
    }
    public void deleteAllCompanies(){
        database.delete(SQliteHelper.TABLE_NAME,null,null);
    }
}
