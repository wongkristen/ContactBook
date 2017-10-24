package com.kristenwong.contactlist;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by kristenwong on 10/17/17.
 */

public class ContactProfileActivity extends Activity {
    private TextView mContactName, mContactPhone;
    private ListView mRelationships;
    private ContactListAdapter mAdapter;
    private ArrayList<ContactProfile> mRelationshipArray, mContactList;
    private int mContactIndex;
    private ContactProfile mCP;

    private static final String CONTACT_PROFILE_KEY = "contact profile key";
    private static final String CONTACT_LIST_KEY = "contact list key";
    private static final String CONTACT_PROFILE_TAG = "ContactList";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_profile);

        Bundle extras = getIntent().getExtras();

        Log.d(CONTACT_PROFILE_TAG, "index received from bundle: " + extras.getInt(CONTACT_PROFILE_KEY));
        mContactList = (ArrayList<ContactProfile>) extras.getSerializable(CONTACT_LIST_KEY);
        mContactIndex = (int) extras.getInt(CONTACT_PROFILE_KEY);
        mCP = mContactList.get(mContactIndex);

        mRelationshipArray = mCP.getRelationships();

        Log.d(CONTACT_PROFILE_TAG, "Contact profile activity created.");
        Log.d(CONTACT_PROFILE_TAG, "Profile retrieved, name: " + mCP.getName() + ", phone: " + mCP.getPhoneNumber());

        mContactName = (TextView) findViewById(R.id.text_contact_name);
        mContactName.setText(mCP.getName());
        mContactPhone = (TextView) findViewById(R.id.text_contact_phone);
        mContactPhone.setText(mCP.getPhoneNumber());

        mRelationships = (ListView) findViewById(R.id.listview_relationships_cp);

        mAdapter = new ContactListAdapter(mCP.getRelationships(), getApplicationContext(), this, false);
        mRelationships.setAdapter(mAdapter);

        mRelationships.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                boolean found = false;
                for (int j = 0; j < mContactList.size(); j ++) {
                    if (mRelationshipArray.get(i).getName().equals(mContactList.get(j).getName())) {
                        mContactIndex = j;
                        mCP = mContactList.get(j);
                        found = true;
                    }
                }

                if (!found){
                    Toast toast = Toast.makeText(getApplicationContext(), "Contact no longer exists.", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    mContactName.setText(mCP.getName());
                    mContactPhone.setText(mCP.getPhoneNumber());
                    mRelationshipArray = mCP.getRelationships();
                    mAdapter.updateList(mRelationshipArray);
                }
            }
        });
    }
}
