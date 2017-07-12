package com.deepeshhmehta.contacts_c0702741;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Deepesh on 04/07/2017.
 */

public class ContactInstance {
    //Defining all elements as public so they can be directly accessed instead of using getR and setR
    public int id;
    public String fname;
    public String lname;
    public String email;
    public String contact_no;
    public String deleted_on;

    //use this constructor for displaying contact details
    public ContactInstance(int id, String fname, String lname,  String contact_no, String email) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.contact_no = contact_no;
    }


    //use this constructor for displaying deleted_contact
    public ContactInstance(int id, String fname, String lname, String contact_no, String email, String deleted_on) throws ParseException {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.contact_no = contact_no;
        this.deleted_on = deleted_on;
    }
}
