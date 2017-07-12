package com.deepeshhmehta.contacts_c0702741;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

//Main Activity, controller for the activity_main
public class MainActivity extends AppCompatActivity{
    //declare the variables we shall need
    ContactDb db;
    ListView lstview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set onclick listener for the floating button leading to add contact page
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity.this,AddContactActivity.class);
                startActivity(in);

            }
        });

        // instantiate the database
        db = new ContactDb(getApplicationContext());

        //instantiate the lsitview to be populated
        lstview = (ListView)findViewById(R.id.listView1);

        //instantiate the search bar
        EditText search = (EditText) findViewById(R.id.search_bar);

        //add a text change listener to the search bar
        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //set the adapter for the listview to a set of contacts matching the searchbar string
                lstview.setAdapter(new ContactsAdapter(MainActivity.this,db,charSequence.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //set the adapter of the listview to all the contacts
        lstview.setAdapter(new ContactsAdapter(MainActivity.this,db));
    }


    //show a confirm box before deleting (called in the 2 Adapter classes)
    public void showDeleteDialog(String name, final int id_no) throws Resources.NotFoundException {
        new AlertDialog.Builder(this)
            .setTitle("Delete Contact")
            .setMessage("Are you sure you want to delete " + name)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton(
                    getResources().getString(R.string.PostiveYesButton),
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            db.deleteAContact(id_no);
                            onResume();


                        }
                    })
            .setNegativeButton(
                    getResources().getString(R.string.NegativeNoButton),
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            //Do Something Here
                        }
                    }).show();
    }


    //Prevent closing of app when back is pressed on mainActivity
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    //Populate Menu Options
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    //Set Listener for various menu options
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.view_log:
//                //startActivity(new Intent(this, About.class));
//                return true;
            case R.id.view_deleted:
                //lstview.setAdapter(new DeletedContactsAdapter(MainActivity.this,db));
                startActivity(new Intent(this, DeletedView.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
