package com.deepeshhmehta.contacts_c0702741;

import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ListAdapter{
    boolean isOdd = true;
    ArrayList<ContactInstance> ci_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity.this,AddContactActivity.class);
                startActivity(in);
                finish();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        ContactDb db = new ContactDb(this);
        ci_list = db.getAllContacts();

        ListView obj = (ListView)findViewById(R.id.listView1);
        obj.setAdapter(this);
        //obj.setOnItemClickListener(new AdapterView.OnItemClickListener(){
        //    @Override
        //    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
        //        // TODO Auto-generated method stub
        //        int id_To_Search = arg2 + 1;
        //
        //        Bundle dataBundle = new Bundle();
        //        dataBundle.putInt("id", id_To_Search);
        //
        //        Intent intent = new Intent(getApplicationContext(),DisplayContact.class);
        //
        //        intent.putExtras(dataBundle);
        //        startActivity(intent);
        //    }
        //});
    }



    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int i) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return ci_list.size();
    }

    @Override
    public Object getItem(int i) {
        return ci_list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        int clr;
        TextView txtContact = new TextView(this);

        txtContact.setTypeface(Typeface.MONOSPACE);
        ContactInstance current_contact = ((ContactInstance) ci_list.get(i));
        txtContact.setTag(Integer.valueOf(current_contact.id));
        String studInfo = "" + current_contact.fname + " " + current_contact.lname;
        txtContact.setText(studInfo);     //txtStudent.setText((String)this.getItem(position));

        txtContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id_To_Search = (int) ((TextView) view).getTag();

                        Bundle dataBundle = new Bundle();
                        dataBundle.putInt("id", id_To_Search);

                        Intent intent = new Intent(getApplicationContext(),DisplayContact.class);

                        intent.putExtras(dataBundle);
                        startActivity(intent);
            }
        });

        if(isOdd){
            isOdd = false;
            clr = (Color.parseColor("#cccccc"));
        }else{
            isOdd = true;
            clr = (Color.parseColor("#e6e6e6"));
        }

        txtContact.setBackgroundColor(clr);
        return txtContact;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
