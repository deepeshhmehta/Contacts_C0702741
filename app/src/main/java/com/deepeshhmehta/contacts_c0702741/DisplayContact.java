package com.deepeshhmehta.contacts_c0702741;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class DisplayContact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);
        Bundle extras = getIntent().getExtras();
        int id = extras.getInt("id");
        Log.d("Display Contact called",Integer.toString(id) );

        ContactDb db = new ContactDb(this);
        ContactInstance display_contact = db.getData(id);
        Log.d("Name: ", display_contact.fname + " " + display_contact.lname);
        Log.d("Phone: ", display_contact.contact_no);
        Log.d("Email: ", display_contact.email);
    }
}
