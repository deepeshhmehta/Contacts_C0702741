package com.deepeshhmehta.contacts_c0702741;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddContactActivity extends AppCompatActivity  implements View.OnClickListener{
    //initialse the necessary variable
    ContactDb db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //instanttiate the variable
        db = new ContactDb(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);


        //set onclick listeners for save and cancel buttons
        Button btn;
        btn = (Button) findViewById(R.id.buttonCancel);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.buttonSave);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            //if cancel is clicked do not save the contact
            case R.id.buttonCancel:{
                Log.d("button", "cancel");
                goBackToMain();
                break;
            }
            case R.id.buttonSave:{
                //save a contact if the save button is pressed
                Log.d("button", "save");
                //check if name and number exists
                String data[] = {((EditText) findViewById(R.id.editTextFirstName)).getText().toString(),
                        ((EditText) findViewById(R.id.editTextLastName)).getText().toString(),
                        ((EditText) findViewById(R.id.editTextNumber)).getText().toString(),
                        ((EditText) findViewById(R.id.editTextEmail)).getText().toString(),
                };
                if(2 >= data.length){
                    //if data is incomplete show message
                    Log.d("noOfItems","error");
                    Toast.makeText(this, "Incomplete Data", Toast.LENGTH_SHORT).show();
                    break;
                }

                //add a contact, if succecssfull go back to main
                if(db.addAContact(data[0],data[1],data[2],data[3])){
                    goBackToMain();
                }else{
                }
                break;
            }
        }

    }

    private void goBackToMain() {
//        Intent in = new Intent(AddContactActivity.this, MainActivity.class);
//        startActivity(in);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goBackToMain();
    }
}
