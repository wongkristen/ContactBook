package com.kristenwong.contactlist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends FragmentActivity {
    private ListView mContactList;
    private Button mAddContactButton, mDeleteContactsButton;
    private ContactListAdapter mAdapter;
    private ArrayList<ContactProfile> mContacts;

    private static final String KEY = "intent key";
    private static final String f_KEY = "f intent key";
    private static final String CONTACT_PROFILE_KEY = "contact profile key";
    private static final String CONTACT_LIST_KEY = "contact list key";
    private static final String MAIN_DEBUG_TAG = "ContactList";
    private static final String CONTACT_LIST_PREFS = "contact list prefs";
    private static final String CONTACT_LIST_PREFS_KEY = "contact list prefs key";
    private static final String f_CONTACT_PROFILE_KEY = "f contact profile key";
    private static final String f_CONTACT_LIST_KEY = "f contact list key";
    private static final int CONTACT_LIST_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mContacts = new ArrayList<>();

        // DEBUG DEBUG DEBUG DEBUG DEBUG DEBUG DEBUG
        ContactProfile cp = new ContactProfile();

        saveList();



        mContactList = (ListView) findViewById(R.id.listview_main_contact_list);

        mAddContactButton = (Button) findViewById(R.id.button_add_contact);
        mDeleteContactsButton = (Button) findViewById(R.id.button_delete_contacts);

        mAdapter = new ContactListAdapter(mContacts, getApplicationContext(), this, false);
        mContactList.setAdapter(mAdapter);


        mAddContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i < mContacts.size(); i++) mContacts.get(i).setChecked(false);

                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                    Log.d(MAIN_DEBUG_TAG, "fragment transaction begin");
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(f_KEY, mContacts);
                    ContactDetailsFragment fragment = new ContactDetailsFragment();
                    fragment.setArguments(bundle);
                    android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.add(R.id.fragment_holder,fragment);
                    transaction.commit();

                } else{
                    Intent intent = new Intent(getApplicationContext(), ContactDetailsActivity.class);
                    intent.putExtra(KEY, mContacts);
                    startActivityForResult(intent, CONTACT_LIST_REQUEST);
                }
            }
        });

        mDeleteContactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < mContacts.size(); i ++) {
//                    mContacts = mAdapter.getContactList();
//                    if (mContacts.get(i).isChecked()) {
//                        ArrayList<ContactProfile> relationships = mContacts.get(i).getRelationships();
//                        for (int j = 0; j < relationships.size(); j ++) {
//                            for (int k = 0; k < mContacts.size(); k++) {
//                                if(mContacts.get(k).getName().equals(relationships.get(j).getName()) && mContacts.get(k).getPhoneNumber().equals(relationships.get(j).getPhoneNumber())){
//                                    for (int l = 0; l < mContacts.get(k).getRelationships().size(); l ++) {
//                                        if(mContacts.get(k).getRelationships().get(l).getName().equals(mContacts.get(i).getName()))
//                                            mContacts.get(k).getRelationships().remove(l);
//                                    }
//                                }
//                            }
//                        }
                        mContacts.remove(i);
                    }
                    mAdapter.updateList(mContacts);
                }
        });

        mContactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(MAIN_DEBUG_TAG, "List item clicked at index: " + i + ", Contact name: " + mContacts.get(i).getName());
                Bundle extras = new Bundle();


                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                    ContactProfileFragment fragment = new ContactProfileFragment();
                    extras.putSerializable(f_CONTACT_LIST_KEY, mContacts);
                    extras.putInt(f_CONTACT_PROFILE_KEY, i);
                    fragment.setArguments(extras);
                    android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.add(R.id.fragment_holder, fragment);
                    transaction.commit();
                } else {
                    Intent intent = new Intent(getApplicationContext(), ContactProfileActivity.class);

                    extras.putSerializable(CONTACT_LIST_KEY, mContacts);
                    extras.putInt(CONTACT_PROFILE_KEY, i);
                    intent.putExtras(extras);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CONTACT_LIST_REQUEST) {
            mContacts = (ArrayList<ContactProfile>) data.getSerializableExtra(KEY);
            mAdapter.updateList(mContacts);
            saveList();
        }
    }

    void saveList() {
        SharedPreferences.Editor editor = getSharedPreferences(CONTACT_LIST_PREFS, Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String gsonContacts = gson.toJson(mContacts);
        editor.putString(CONTACT_LIST_PREFS_KEY, gsonContacts);
        editor.commit();

        Log.d(MAIN_DEBUG_TAG, "Saved json object: " + gsonContacts);
    }

    void updateList() {
        SharedPreferences sharedPreferences = getSharedPreferences(CONTACT_LIST_PREFS, Context.MODE_PRIVATE);
        String jsonContacts = sharedPreferences.getString(CONTACT_LIST_PREFS_KEY, "");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<ContactProfile>>(){}.getType();
        mContacts = gson.fromJson(jsonContacts, type);

        Log.d(MAIN_DEBUG_TAG, "Retrieved json object: " + jsonContacts);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        saveList();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        updateList();
        mAdapter.updateList(mContacts);
    }

    public void setContactList(ArrayList<ContactProfile> cl) {
        mContacts = cl;
//        saveList();
        mAdapter.updateList(mContacts);
    }

//    ------ ACTIVITY STATE METHODS ------
    @Override
    public void onStart() {
        super.onStart();
        Log.d(MAIN_DEBUG_TAG, "On start called.");
        updateList();
        mAdapter.updateList(mContacts);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(MAIN_DEBUG_TAG, "On resume called.");
        updateList();
        mAdapter.updateList(mContacts);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(MAIN_DEBUG_TAG, "On pause called.");
        saveList();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(MAIN_DEBUG_TAG, "On stop called.");
        saveList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(MAIN_DEBUG_TAG, "On destroy called.");
        saveList();
    }
}
