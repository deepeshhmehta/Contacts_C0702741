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

        title = (TextView) findViewById(R.id.textViewTitle);
        edtxtfname = (EditText) findViewById(R.id.editTextFirstName);
        edtxtlname = (EditText) findViewById(R.id.editTextLastName);
        edtxtphone = (EditText) findViewById(R.id.editTextNumber);
        edtxtemail = (EditText) findViewById(R.id.editTextEmail);
        edit = (ImageButton) findViewById(R.id.imageButtonEdit);
        cancel = (Button) findViewById(R.id.buttonCancel);
        save = (Button) findViewById(R.id.buttonSave);

        edtxtfname.setEnabled(false);
        edtxtlname.setEnabled(false);
        edtxtphone.setEnabled(false);
        edtxtemail.setEnabled(false);

        Bundle extras = getIntent().getExtras();
        id = extras.getInt("id");

        db= new ContactDb(this);
        ContactInstance display_contact = db.getData(id);
        fname = display_contact.fname ;
        lname = display_contact.lname;
        number = display_contact.contact_no;
        email = display_contact.email;


        title.setText(fname + " " + lname);

        edtxtfname.setText(fname);
        edtxtlname.setText(lname);
        edtxtphone.setText(number);
        edtxtemail.setText(email);

        edtxtfname.addTextChangedListener(this);
        edtxtlname.addTextChangedListener(this);
        edtxtphone.addTextChangedListener(this);
        edtxtemail.addTextChangedListener(this);



        edit.setOnClickListener(this);
        cancel.setOnClickListener(this);
        save.setOnClickListener(this);



    }




    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.imageButtonEdit:{
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
//        Intent in = new Intent(DisplayContact.this, MainActivity.class);
//        startActivity(in);
        finish();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
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
