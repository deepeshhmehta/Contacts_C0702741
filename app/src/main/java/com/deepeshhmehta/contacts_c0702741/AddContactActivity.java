package com.deepeshhmehta.contacts_c0702741;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddContactActivity extends AppCompatActivity  implements View.OnClickListener{
    ContactDb db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = new ContactDb(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);



        Button btn;
        btn = (Button) findViewById(R.id.buttonCancel);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.buttonSave);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.buttonCancel:{
                Log.d("button", "cancel");
                goBackToMain();
                break;
            }
            case R.id.buttonSave:{
                Log.d("button", "save");
                String data[] = {((EditText) findViewById(R.id.editTextFirstName)).getText().toString(),
                        ((EditText) findViewById(R.id.editTextLastName)).getText().toString(),
                        ((EditText) findViewById(R.id.editTextNumber)).getText().toString(),
                        ((EditText) findViewById(R.id.editTextEmail)).getText().toString(),
                };
                if(2 >= data.length){
                    Log.d("noOfItems","error");
                    Snackbar.make(view, "Incomplete Data", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    break;
                }


                if(db.addAContact(data[0],data[1],data[2],data[3])){
                    goBackToMain();
                }else{
//                    Snackbar.make(view, "Something Wrong in SQL", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
                }
                break;
            }
        }

    }

    private void goBackToMain() {
        Intent in = new Intent(AddContactActivity.this, MainActivity.class);
        startActivity(in);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goBackToMain();
    }
}
