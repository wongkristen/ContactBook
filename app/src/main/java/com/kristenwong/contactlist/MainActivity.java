package com.kristenwong.contactlist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private ListView mContactList;
    private Button mAddContactButton, mDeleteContactsButton;
    private ContactListAdapter mAdapter;
    private ArrayList<ContactProfile> mContacts;

    private static final String KEY = "intent key";
    private static final int CONTACT_LIST_REQUEST = 1;
    private static final String MAIN_DEBUG_TAG = "ContactList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContacts = new ArrayList<>();

        // DEBUG DEBUG DEBUG DEBUG DEBUG DEBUG DEBUG
        ContactProfile cp = new ContactProfile();
        cp.setPhoneNumber("222222222");
        cp.setName("new test contact!!!!!");
        mContacts.add(cp);

        mContactList = (ListView) findViewById(R.id.listview_main_contact_list);

        mAddContactButton = (Button) findViewById(R.id.button_add_contact);
        mDeleteContactsButton = (Button) findViewById(R.id.button_delete_contacts);

        mAdapter = new ContactListAdapter(mContacts, getApplicationContext(), this);
        mContactList.setAdapter(mAdapter);


        mAddContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < mContacts.size(); i++) mContacts.get(i).setChecked(false);
                Intent intent = new Intent(getApplicationContext(), ContactDetailsActivity.class);
                intent.putExtra(KEY, mContacts);
                startActivityForResult(intent, CONTACT_LIST_REQUEST);
            }
        });

        mDeleteContactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < mContacts.size(); i ++) {
                    mContacts = mAdapter.getContactList();
                    if (mContacts.get(i).isChecked()) mContacts.remove(i);
                    mAdapter.updateList(mContacts);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CONTACT_LIST_REQUEST) {
            mContacts = (ArrayList<ContactProfile>) data.getSerializableExtra(KEY);
            mAdapter.updateList(mContacts);
        }
    }
}
