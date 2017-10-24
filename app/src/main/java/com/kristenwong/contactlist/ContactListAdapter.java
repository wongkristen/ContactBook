package com.kristenwong.contactlist;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Created by kristenwong on 10/16/17.
 */

public class ContactListAdapter extends BaseAdapter {
    private ArrayList<ContactProfile> contactList;
    private LayoutInflater mInflater;
    private Context mActivityContext;
    private boolean mIsContactDetails;

    private static final String CONTACT_LIST_ADAPTER_DEBUG_TAG = "ContactList";

    public ContactListAdapter(ArrayList<ContactProfile> contacts, Context appContext, Context activityContext, boolean isContactDetails) {
        contactList = contacts;
        mActivityContext = activityContext;
        mInflater = (LayoutInflater) appContext.getSystemService(appContext.LAYOUT_INFLATER_SERVICE);
        mIsContactDetails = isContactDetails;
    }

    @Override
    public int getCount() {
        return contactList.size();
    }

    @Override
    public ContactProfile getItem(int i) {
        return contactList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ContactProfile contactProfile = getItem(i);
        final int index = i;
        View v = mInflater.inflate(R.layout.contact_list_item, viewGroup, false);

        TextView contactName = (TextView) v.findViewById(R.id.list_item_contact_name);
        contactName.setText(contactProfile.getName());

        final CheckBox cb = (CheckBox) v.findViewById(R.id.list_item_checkbox);
        cb.setClickable(true);
        cb.setChecked(contactProfile.isChecked());
        cb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                 Log.d(CONTACT_LIST_ADAPTER_DEBUG_TAG, "Checkbox was clicked");
                contactProfile.setChecked(cb.isChecked());

                Log.d(CONTACT_LIST_ADAPTER_DEBUG_TAG, "Context is instance of ContactDetailsActivity: " + (mActivityContext instanceof ContactDetailsActivity));

                if (mIsContactDetails) {
                    Log.d(CONTACT_LIST_ADAPTER_DEBUG_TAG, "Item moved to correct location on list");
                    int i = 0;
                    while (contactList.get(i).isChecked() && i < contactList.size() - 1 && i < index)
                        i++;
                    Collections.swap(contactList, i, index);
                }
                notifyDataSetChanged();
            }
        });

        return v;
    }

    void updateList(ArrayList<ContactProfile> contacts) {
        contactList = contacts;
        notifyDataSetChanged();
    }
    ArrayList<ContactProfile> getContactList() {return contactList;}
}
