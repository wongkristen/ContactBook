package com.kristenwong.contactlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by kristenwong on 10/16/17.
 */

public class ContactDetailsActivity extends Activity {
    private EditText mInputContactName, mInputContactNumber;
    private ListView mRelationshipList;
    private Button mAddContactButton;
    private ArrayList<ContactProfile> mContactList;
    private ContactListAdapter mAdapter;
    private String mStringName, mStringPhone;

    private static final String KEY = "intent key";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        mContactList = (ArrayList<ContactProfile>) getIntent().getSerializableExtra(KEY);

        mInputContactName = (EditText) findViewById(R.id.input_contact_name);
        mInputContactNumber = (EditText) findViewById(R.id.input_contact_number);

        mRelationshipList = (ListView) findViewById(R.id.listview_relationships);

        mAddContactButton = (Button) findViewById(R.id.button_confirm_add);

        mAdapter = new ContactListAdapter(mContactList, getApplicationContext(), this);
        mRelationshipList.setAdapter(mAdapter);

        mInputContactName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mStringName = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mInputContactNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mStringPhone = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mAddContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactProfile newContact = new ContactProfile();
                newContact.setName(mStringName);
                newContact.setPhoneNumber(mStringPhone);

                ArrayList<ContactProfile> relationships = new ArrayList<>();
                int i = 0;
                while (i < mContactList.size() && mContactList.get(i).isChecked()) {
                    relationships.add(mContactList.get(i));
                    mContactList.get(i).setChecked(false);
                    i ++;
                }
                newContact.setRelationships(relationships);

                mContactList.add(newContact);

                Intent resultIntent = new Intent();
                resultIntent.putExtra(KEY, mContactList);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
