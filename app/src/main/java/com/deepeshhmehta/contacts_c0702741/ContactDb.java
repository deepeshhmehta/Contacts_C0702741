package com.deepeshhmehta.contacts_c0702741;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Database Helper class Created by Deepesh on 30/06/2017.
 */

public class ContactDb extends SQLiteOpenHelper {
    //Definitions and table names
    /** I have set the column names and the data types as arrays of strings for dynamic generation of tables*/
    public static final String db_name="contacts_db";
    public static final String table_name="contacts";
    public static final String[] contacts_columns={ "id",
                                                    "fname","lname",
                                                    "number","email","active"};
    public static final String[] contacts_columns_type ={ "INTEGER PRIMARY KEY AUTOINCREMENT,",
                                                "varchar(50) NOT NULL,","varchar(50),",
                                                "number(20)NOT NULL,", "varchar(50),","INTEGER"};


    public static final String log_table_name = "log";
    public static final String[] log_columns = {"id","contact_id","action","time"};
    public static final String[] log_columns_type = {"INTEGER PRIMARY KEY AUTOINCREMENT,","INTEGER,","INTEGER,","datetime default current_timestamp"};

    public static final String delete_table_name = "deleted_contacts";
    public static final String[] deleted_contacts_columns={ "id",
                                                            "fname","lname",
                                                            "number","email","datetime"};
    public static final String[] deleted_contacts_columns_type={ "INTEGER PRIMARY KEY AUTOINCREMENT,",
                                                        "varchar(50) NOT NULL,","varchar(50),",
                                                        "number(20)NOT NULL,", "varchar(50),", "datetime default current_timestamp"};

    public ContactDb(Context context) {
        super(context.getApplicationContext(), db_name , null, 3);
    }

    //Method called to create the database object in the local storage
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //the loop logic generates the query required using the array of column names and data types for each table

        String query = "create table if not exists " + this.table_name + "(";
        for(int i = 0; i <contacts_columns.length; i ++){
            query += this.contacts_columns[i] + " " +  this.contacts_columns_type[i];
        }
        query +=");";
        sqLiteDatabase.execSQL(query);

        query = "create table if not exists " + this.log_table_name + "(";
        for(int i = 0; i <log_columns.length; i ++){
            query += this.log_columns[i] + " " +  this.log_columns_type[i];
        }
        query +=");";
        sqLiteDatabase.execSQL(query);

        query = "create table if not exists " + this.delete_table_name + "(";
        for(int i = 0; i <deleted_contacts_columns.length; i ++){
            query += this.deleted_contacts_columns[i] + " " +  this.deleted_contacts_columns_type[i];
        }
        query +=");";
        sqLiteDatabase.execSQL(query);
    }

    //it is called when we upgrade the database version, it basically drops previous tables and re creates them with the new definitions
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + this.table_name + ";");
        sqLiteDatabase.execSQL("drop table if exists " + this.log_table_name + ";");
        sqLiteDatabase.execSQL("drop table if exists " + this.delete_table_name + ";");
        onCreate(sqLiteDatabase);
    }

    //return an arrayList of ContactInstances consisting of all contacts
    public ArrayList<ContactInstance> getAllContacts() {

        ArrayList<ContactInstance> ci_list = new ArrayList<ContactInstance>();
        SQLiteDatabase db = this.getReadableDatabase();
        //populate the cursor
        Cursor res =  db.rawQuery( "select * from " + this.table_name +
                                    " where active = 1 " +
                                    " Order By fname", null );
        res.moveToFirst();

        //populate the array list from the cursor
        while(res.isAfterLast() == false){
            ci_list.add(new ContactInstance(Integer.parseInt(res.getString(0)),res.getString(1),res.getString(2),res.getString(3),res.getString(4)));
            res.moveToNext();
        }
        return ci_list;
    }

    //return an arrayList of Contact Instance consisting of all contacts like the typed passphrase
    public ArrayList<ContactInstance> getAllContacts(String name_like) {

        ArrayList<ContactInstance> ci_list = new ArrayList<ContactInstance>();
        SQLiteDatabase db = this.getReadableDatabase();
        //populate the cursor
        Cursor res =  db.rawQuery( "select * from " + this.table_name +
                                            " where fname like '%"+name_like+ "%'" +
                                            " OR lname like '%"+name_like+ "%'" +
                                            " OR number like '%"+name_like+ "%'" +
                                            " OR email like '%"+name_like+ "%'" +
                                            " AND active = 1 "+
                                            " Order By fname", null );
        res.moveToFirst();
        //populate the arrayList from the cursor
        while(res.isAfterLast() == false){
            ci_list.add(new ContactInstance(Integer.parseInt(res.getString(0)),res.getString(1),res.getString(2),res.getString(3),res.getString(4)));
            //name.add(res.getString(res.getColumnIndex(this.contacts_columns[1])) + " " + res.getString(res.getColumnIndex(this.contacts_columns[2])));
            res.moveToNext();
        }
        return ci_list;
    }

    //insert into the contacts table and also into the log table
    public boolean addAContact(String fname,String lname,String number,String email){
        try{
            String sql = "insert into " + this.table_name + " values (null,'"+fname+"' ,'"+lname+"',"+number+",'"+email+"',1); select last_insert_rowid()";
            //insert and return the inserted element
            Cursor id = this.getWritableDatabase().rawQuery(sql,null);
            if (id.moveToFirst()){
                //only if inserted successfully in the contact table, insert into log table
                sql = "insert into " + this.log_table_name + " values (null,"+id+" ,1,null');";
                this.getWritableDatabase().rawQuery(sql,null);
            }

            return true;
        }catch (Exception e){
            Log.d("Error in SQL ADD", e.toString());
            return false;
        }
    }

    //get data of a single contact instance to display
    public ContactInstance getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts where id="+id+"", null );
        res.moveToFirst();
        //populate contactInstance from the cursor
        ContactInstance ci = new ContactInstance(id,res.getString(1),res.getString(2),res.getString(3),res.getString(4));
        return ci;
    }

    //update data of a contact instance
    public int update(ContactInstance contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        //add an entry in the log table
        String sql = "insert into " + this.log_table_name + " values (null,"+contact.id+" ,2,null);";
        db.rawQuery(sql,null);

        ContentValues values = new ContentValues();
        values.put("fname", contact.fname);
        values.put("lname", contact.lname);
        values.put("number", contact.contact_no);
        values.put("email", contact.email);

        // updating row


        return db.update(table_name, values, "id" + " = ?",
                new String[] { (String.valueOf(contact.id)) });
    }

    //soft delete a contact
    public int deleteAContact(int id){
        ContactInstance ci = getData(id);
        //write in log
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "insert into " + this.log_table_name + " values (null,"+id+" ,3,null);";
        db.rawQuery(sql,null);

        //insert in deleted_contacts table
        ContentValues conv = new ContentValues();
        conv.put("fname", ci.fname);
        conv.put("lname",ci.lname);
        conv.put("number",ci.contact_no);
        conv.put("email",ci.email);
        //insert and ping in log if success full
        Log.d("delete Inserted",String.valueOf(db.insert(delete_table_name,null,conv)));

        ContentValues values = new ContentValues();
        values.put("active", 0);

        //set the active flag to 0 (i.e soft delete the contact)
        return db.update(table_name, values, "id" + " = ?",
                new String[] { (String.valueOf(id))});
    }

    //fetch all deleted contacts from the database
    public ArrayList<ContactInstance> getAllDeletedContacts() throws ParseException {
        ArrayList<ContactInstance> ci_list = new ArrayList<ContactInstance>();

        SQLiteDatabase db = this.getReadableDatabase();
        //populate cursor
        Cursor res =  db.rawQuery( "select * from " + this.delete_table_name +" Order By fname", null );
        res.moveToFirst();

        //populate the arrayList of COntactInstances from the cursor
        while(res.isAfterLast() == false){
            ci_list.add(new ContactInstance(Integer.parseInt(res.getString(0)),res.getString(1),res.getString(2),res.getString(3),res.getString(4),res.getString(5)));
            Log.d("from contacts db", res.getString(1));
            res.moveToNext();
        }
        return ci_list;
    }
}
