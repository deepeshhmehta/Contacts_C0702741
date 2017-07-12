package com.deepeshhmehta.contacts_c0702741;

        import android.content.Context;
        import android.content.Intent;
        import android.database.DataSetObserver;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.ListAdapter;
        import android.widget.TextView;

        import java.text.ParseException;
        import java.util.ArrayList;

/**
 * Created by Deepesh on 11/07/2017.
 */


public class DeletedContactsAdapter implements ListAdapter {

    Context context;
    boolean isOdd = true;
    ArrayList<ContactInstance> ci_list;

    public DeletedContactsAdapter(Context con, ContactDb db) throws ParseException {
        Log.d("DeletedCOntactsAdapter","called");
        context =con;
        ci_list = db.getAllDeletedContacts();
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View v;
        if (view == null) {
            v = inflater.inflate(R.layout.deleted_contact_details_view, null);
        } else {
            v = view;
        }
        TextView contactName = (TextView) v.findViewById(R.id.contact_name);
        TextView deleted_on = (TextView) v.findViewById(R.id.deleted_date);
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.background);

        int clr;

        final ContactInstance current_contact = ((ContactInstance) ci_list.get(i));
        contactName.setTag(Integer.valueOf(current_contact.id));

        final String studInfo = "" + current_contact.fname + " " + current_contact.lname;
        contactName.setText(studInfo);     //txtStudent.setText((String)this.getItem(position));
        Log.d("Deleted Contact: ", studInfo);


        if(isOdd){
            isOdd = false;
            clr = (Color.parseColor("#cccccc"));
        }else{
            isOdd = true;
            clr = (Color.parseColor("#e6e6e6"));
        }

        layout.setBackgroundColor(clr);

        deleted_on.setText(current_contact.deleted_on);



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
