package com.deepeshhmehta.contacts_c0702741;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.text.ParseException;

public class DeletedView extends AppCompatActivity {
    //initialise the required variables
    ListView lstview;
    ContactDb db;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleted_view);

        //initialise the lstview and the database instance
        lstview = (ListView) findViewById(R.id.listView_deleted);
        db = new ContactDb(getApplicationContext());

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Deleted On Resume", "started");
        try {
            //set adapter to load deleted contacts in the listview
            lstview.setAdapter(new DeletedContactsAdapter(DeletedView.this,db));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
