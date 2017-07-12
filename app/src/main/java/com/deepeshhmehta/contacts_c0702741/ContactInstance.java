package com.deepeshhmehta.contacts_c0702741;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Deepesh on 04/07/2017.
 */

public class ContactInstance {
    public int id;
    public String fname;
    public String lname;
    public String email;
    public String contact_no;
    public String deleted_on;

    public ContactInstance(int id, String fname, String lname,  String contact_no, String email) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.contact_no = contact_no;
    }

    public ContactInstance(int id, String fname, String lname, String contact_no, String email, String deleted_on) throws ParseException {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.contact_no = contact_no;
//        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY", new Locale("us"));
//        this.deleted_on = sdf.format(sdf.parse(deleted_on));
        this.deleted_on = deleted_on;

//        Date dt = sdf.parse(deleted_on);
//        this.deleted_on = "" + String.valueOf(dt.getMonth()) + "/" + String.valueOf(dt.getDay()) +"/"+ String.valueOf(dt.getYear());


    }
}
