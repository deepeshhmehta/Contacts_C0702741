package com.deepeshhmehta.contacts_c0702741;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Deepesh on 30/06/2017.
 */

public class ContactDb extends SQLiteOpenHelper {
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
    public static final String[] log_columns_type = {"INTEGER PRIMARY KEY AUTOINCREMENT,","INTEGER,","INTEGER,","Datetime"};

    public static final String delete_table_name = "deleted_contacts";
    public static final String[] deleted_contacts_columns={ "id",
                                                            "fname","lname",
                                                            "number","email","datetime"};
    public static final String[] deleted_contacts_columns_type={ "INTEGER PRIMARY KEY AUTOINCREMENT,",
                                                        "varchar(50) NOT NULL,","varchar(50),",
                                                        "number(20)NOT NULL,", "varchar(50),", "Datetime"};

    public ContactDb(Context context) {
        super(context.getApplicationContext(), db_name , null, 3);
    }

    public ContactDb(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context.getApplicationContext(), name, factory, version);
    }

    public ContactDb(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context.getApplicationContext(), name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

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

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + this.table_name + ";");
        sqLiteDatabase.execSQL("drop table if exists " + this.log_table_name + ";");
        sqLiteDatabase.execSQL("drop table if exists " + this.delete_table_name + ";");
        onCreate(sqLiteDatabase);
    }

    public ArrayList<ContactInstance> getAllContacts() {
        ArrayList<ContactInstance> ci_list = new ArrayList<ContactInstance>();


        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + this.table_name +
                                    " where active = 1 " +
                                    " Order By fname", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            ci_list.add(new ContactInstance(Integer.parseInt(res.getString(0)),res.getString(1),res.getString(2),res.getString(3),res.getString(4)));
            //name.add(res.getString(res.getColumnIndex(this.contacts_columns[1])) + " " + res.getString(res.getColumnIndex(this.contacts_columns[2])));
            res.moveToNext();
        }
        return ci_list;
    }

    public ArrayList<ContactInstance> getAllContacts(String name_like) {
        ArrayList<ContactInstance> ci_list = new ArrayList<ContactInstance>();


        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + this.table_name +
                                            " where fname like '%"+name_like+ "%'" +
                                            " OR lname like '%"+name_like+ "%'" +
                                            " OR number like '%"+name_like+ "%'" +
                                            " OR email like '%"+name_like+ "%'" +
                                            " AND active = 1 "+
                                            " Order By fname", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            ci_list.add(new ContactInstance(Integer.parseInt(res.getString(0)),res.getString(1),res.getString(2),res.getString(3),res.getString(4)));
            //name.add(res.getString(res.getColumnIndex(this.contacts_columns[1])) + " " + res.getString(res.getColumnIndex(this.contacts_columns[2])));
            res.moveToNext();
        }
        return ci_list;
    }

    public boolean addAContact(String fname,String lname,String number,String email){
        try{
            String sql = "insert into " + this.table_name + " values (null,'"+fname+"' ,'"+lname+"',"+number+",'"+email+"',1); select last_insert_rowid()";
            Cursor id = this.getReadableDatabase().rawQuery(sql,null);
            if (id.moveToFirst()){
                sql = "insert into " + this.log_table_name + " values (null,"+id+" ,1,datetime()');";
                this.getReadableDatabase().rawQuery(sql,null);
            }

            return true;
        }catch (Exception e){
            Log.d("Error in SQL ADD", e.toString());
            return false;
        }
    }

    public ContactInstance getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts where id="+id+"", null );
        res.moveToFirst();
        ContactInstance ci = new ContactInstance(id,res.getString(1),res.getString(2),res.getString(3),res.getString(4));
        return ci;
    }

    public int update(ContactInstance contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "insert into " + this.log_table_name + " values (null,"+contact.id+" ,2,datetime());";
        this.getReadableDatabase().rawQuery(sql,null);

        ContentValues values = new ContentValues();
        values.put("fname", contact.fname);
        values.put("lname", contact.lname);
        values.put("number", contact.contact_no);
        values.put("email", contact.email);

        // updating row


        return db.update(table_name, values, "id" + " = ?",
                new String[] { (String.valueOf(contact.id)) });
    }

    public int deleteAContact(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "insert into " + this.log_table_name + " values (null,"+id+" ,3,datetime());";
        this.getReadableDatabase().rawQuery(sql,null);
        sql = "insert into " + this.delete_table_name + "(fname,lname,number,email,datetime) select fname,lname,number,email,datetime() from "+this.table_name+" where id = "+id+";";
        this.getReadableDatabase().rawQuery(sql,null);

        ContentValues values = new ContentValues();
        values.put("active", 0);

        return db.update(table_name, values, "id" + " = ?",
                new String[] { (String.valueOf(id))});
    }

    public ArrayList<ContactInstance> getAllDeletedContacts() {
        ArrayList<ContactInstance> ci_list = new ArrayList<ContactInstance>();


        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + this.delete_table_name +
                " Order By fname", null );
        res.moveToFirst();

        Log.d("from contacts db", "query called");

        while(res.isAfterLast() == false){
            ci_list.add(new ContactInstance(Integer.parseInt(res.getString(0)),res.getString(1),res.getString(2),res.getString(3),res.getString(4)));
            Log.d("from contacts db", res.getString(1));
            //name.add(res.getString(res.getColumnIndex(this.contacts_columns[1])) + " " + res.getString(res.getColumnIndex(this.contacts_columns[2])));
            res.moveToNext();
        }
        return ci_list;
    }
}
