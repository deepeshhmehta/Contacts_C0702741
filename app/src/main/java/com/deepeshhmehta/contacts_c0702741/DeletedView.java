package com.deepeshhmehta.contacts_c0702741;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class DeletedView extends AppCompatActivity {

    ListView lstview;
    ContactDb db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleted_view);
        lstview = (ListView) findViewById(R.id.listView_deleted);
        db = new ContactDb(this.getApplicationContext());

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Deleted On Resume", "started");
        lstview.setAdapter(new DeletedContactsAdapter(DeletedView.this,db));
    }
}
