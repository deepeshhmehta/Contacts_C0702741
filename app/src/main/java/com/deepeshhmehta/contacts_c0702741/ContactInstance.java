package com.deepeshhmehta.contacts_c0702741;

/**
 * Created by Deepesh on 04/07/2017.
 */

public class ContactInstance {
    public int id;
    public String fname;
    public String lname;
    public String email;
    public String contact_no;

    public ContactInstance(int id, String fname, String lname, String email, String contact_no) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.contact_no = contact_no;
    }
}
