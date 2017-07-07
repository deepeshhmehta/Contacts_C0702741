package com.deepeshhmehta.contacts_c0702741;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ListAdapter{
    boolean isOdd = true;
    ContactDb db;
    ArrayList<ContactInstance> ci_list;
    ListView lstview;

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

            }
        });

        db = new ContactDb(this);
        lstview = (ListView)findViewById(R.id.listView1);

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

        EditText search = (EditText) findViewById(R.id.search_bar);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ci_list = db.getAllContacts(charSequence.toString());
                lstview.setAdapter(MainActivity.this);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ci_list = db.getAllContacts();
        lstview.setAdapter(this);
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
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(this.LAYOUT_INFLATER_SERVICE);
        View v;
        if (view == null) {
            v = inflater.inflate(R.layout.contact_details_view, null);
        } else {
            v = view;
        }
        TextView contactName = (TextView) v.findViewById(R.id.contact_name);
        ImageView delete_button = (ImageView) v.findViewById(R.id.delete_button);
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.background);

        int clr;

        final ContactInstance current_contact = ((ContactInstance) ci_list.get(i));
        contactName.setTag(Integer.valueOf(current_contact.id));

        final String studInfo = "" + current_contact.fname + " " + current_contact.lname;
        contactName.setText(studInfo);     //txtStudent.setText((String)this.getItem(position));

        contactName.setOnClickListener(new View.OnClickListener() {
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

        layout.setBackgroundColor(clr);

        delete_button.setTag(Integer.valueOf(current_contact.id));
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteDialog(studInfo,current_contact.id);
            }
        });


        return v;
    }

    private void showDeleteDialog(String name, final int id_no) throws Resources.NotFoundException {
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

    private void goBackToMain() {
        Intent in = new Intent(MainActivity.this, MainActivity.class);
        startActivity(in);
        finish();
    }
}
