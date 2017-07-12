package com.deepeshhmehta.contacts_c0702741;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Adapter class to load data, Created by Deepesh on 07/07/2017.
 */

public class ContactsAdapter implements ListAdapter {
    //Declare the variables we shall need
    Context context;
    boolean isOdd = true;
    ArrayList<ContactInstance> ci_list;

    //constructor accepting context and database instance from MainActivity
    public ContactsAdapter(MainActivity con, ContactDb db){
        context =con;
        ci_list = db.getAllContacts();
    }

    //constructor accepting context, database instance and search string from MainActivity
    public ContactsAdapter(MainActivity con, ContactDb db, String search){
        context =con;
        ci_list = db.getAllContacts(search);
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

    //generate each element to be thrown inside the listview buy this adapter
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //initialise the inflator we use to generate a generic view for each element of the lsitadapter
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View v;
        if (view == null) {
            v = inflater.inflate(R.layout.contact_details_view, null);
        } else {
            v = view;
        }

        //initialise the elements inside the inflator
        TextView contactName = (TextView) v.findViewById(R.id.contact_name);
        ImageView delete_button = (ImageView) v.findViewById(R.id.delete_button);
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.background);

        int clr;
        // extract the data from the data set
        final ContactInstance current_contact = ((ContactInstance) ci_list.get(i));
        contactName.setTag(Integer.valueOf(current_contact.id));

        // define the header info
        final String studInfo = "" + current_contact.fname + " " + current_contact.lname;
        contactName.setText(studInfo);     //txtStudent.setText((String)this.getItem(position));

        //set listner to open the contacts details page for the contact name
        contactName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id_To_Search = (int) ((TextView) view).getTag();

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id_To_Search);

                Intent intent = new Intent(context.getApplicationContext(),DisplayContact.class);
                intent.putExtras(dataBundle);
                context.startActivity(intent);
            }
        });

        //set background colour for every alternate elements
        if(isOdd){
            isOdd = false;
            clr = (Color.parseColor("#cccccc"));
        }else{
            isOdd = true;
            clr = (Color.parseColor("#e6e6e6"));
        }
        layout.setBackgroundColor(clr);

        //append data and set the onclick listener to invoke delete dialog of a contact
        delete_button.setTag(Integer.valueOf(current_contact.id));
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)context).showDeleteDialog(studInfo,current_contact.id);
            }
        });


        return v;
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
