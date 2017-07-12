package com.deepeshhmehta.contacts_c0702741;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayContact extends AppCompatActivity implements View.OnClickListener, TextWatcher{

    //declare the required variables
    TextView title ;
    EditText edtxtfname ,edtxtlname ,edtxtphone ,edtxtemail ;
    ImageButton edit ;
    Button cancel ,save ;
    String fname,lname,number,email;
    ContactDb db;
    int id;
    Boolean hasChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);

        //initialise the variables to be used across all functions
        title = (TextView) findViewById(R.id.textViewTitle);
        edtxtfname = (EditText) findViewById(R.id.editTextFirstName);
        edtxtlname = (EditText) findViewById(R.id.editTextLastName);
        edtxtphone = (EditText) findViewById(R.id.editTextNumber);
        edtxtemail = (EditText) findViewById(R.id.editTextEmail);
        edit = (ImageButton) findViewById(R.id.imageButtonEdit);
        cancel = (Button) findViewById(R.id.buttonCancel);
        save = (Button) findViewById(R.id.buttonSave);

        //disable the textfields so that the user cannot edit until he enters the edit mode
        edtxtfname.setEnabled(false);
        edtxtlname.setEnabled(false);
        edtxtphone.setEnabled(false);
        edtxtemail.setEnabled(false);

        //fetch id from the intent
        Bundle extras = getIntent().getExtras();
        id = extras.getInt("id");

        //fetch data for the particular id from the database instance and populate variables
        db= new ContactDb(this);
        ContactInstance display_contact = db.getData(id);
        fname = display_contact.fname ;
        lname = display_contact.lname;
        number = display_contact.contact_no;
        email = display_contact.email;


        //set values of various textboxes to display details
        title.setText(fname + " " + lname);

        edtxtfname.setText(fname);
        edtxtlname.setText(lname);
        edtxtphone.setText(number);
        edtxtemail.setText(email);

        //add a changed listener to set a flag while editing, (DO NOT SAVE UNTIL ACTUALLY EDITED)
        edtxtfname.addTextChangedListener(this);
        edtxtlname.addTextChangedListener(this);
        edtxtphone.addTextChangedListener(this);
        edtxtemail.addTextChangedListener(this);

        //set on click listener for the edit cancel and save buttons
        edit.setOnClickListener(this);
        cancel.setOnClickListener(this);
        save.setOnClickListener(this);
    }




    @Override
    public void onClick(View view) {
        switch(view.getId()){
            //check which button is clicked
            case R.id.imageButtonEdit:{
                //if edit button is pressed, show the save and cancel buttons, hide the edit buttons, and enable the textboxes
                save.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.VISIBLE);
                edit.setVisibility(View.INVISIBLE);

                edtxtfname.setEnabled(true);
                edtxtlname.setEnabled(true);
                edtxtphone.setEnabled(true);
                edtxtemail.setEnabled(true);

                break;
            }
            case R.id.buttonCancel:{
                //if cancelled, revook all that happened in edit button and also replace values of the text fields with the original values, also if the hasChanged flag is set, unset it
                save.setVisibility(View.INVISIBLE);
                cancel.setVisibility(View.INVISIBLE);
                edit.setVisibility(View.VISIBLE);

                hasChanged = false;

                edtxtfname.setText(fname);
                edtxtlname.setText(lname);
                edtxtphone.setText(number);
                edtxtemail.setText(email);
                edtxtfname.setEnabled(false);
                edtxtlname.setEnabled(false);
                edtxtphone.setEnabled(false);
                edtxtemail.setEnabled(false);
                break;
            }
            case R.id.buttonSave:{
                //when save is pressed, only if the text has changed, trigger the save operation on the contactsDb class
                if(hasChanged){
                    String new_fname = edtxtfname.getText().toString();
                    String new_lname = edtxtlname.getText().toString();
                    String new_phone = edtxtphone.getText().toString();
                    String new_email = edtxtemail.getText().toString();
                    if(db.update(new ContactInstance(id,new_fname,new_lname, new_phone, new_email)) >0){
                        Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                        goBackToMain();
                    }
                }
                break;
            }
        }
    }

    private void goBackToMain() {
        //finish this activity to go back to main
        finish();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        //whenever a user edits a textfield set the haschanged flag
        hasChanged = true;
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goBackToMain();
    }
}
