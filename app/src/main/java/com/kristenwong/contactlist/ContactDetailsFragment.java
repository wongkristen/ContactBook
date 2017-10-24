package com.kristenwong.contactlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class ContactDetailsFragment extends Fragment {
    private EditText mInputContactName, mInputContactNumber;
    private ListView mRelationshipList;
    private Button mAddContactButton;
    private ArrayList<ContactProfile> mContactList;
    private ContactListAdapter mAdapter;
    private String mStringName, mStringPhone;

    private static final String CONTACT_DETAILS_DEBUG_TAG = "ContactList";
    private static final String f_KEY = "f intent key";

    public ContactDetailsFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(CONTACT_DETAILS_DEBUG_TAG, "Contact details fragment oncreate called");

        Bundle bundle = this.getArguments();
        mContactList = (ArrayList<ContactProfile>) bundle.getSerializable(f_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(CONTACT_DETAILS_DEBUG_TAG, "Contact details fragment oncreateview called");
        View v = inflater.inflate(R.layout.fragment_contact_details, container, false);

        mInputContactName = (EditText) v.findViewById(R.id.input_contact_name);
        mInputContactNumber = (EditText) v.findViewById(R.id.input_contact_number);

        mRelationshipList = (ListView) v.findViewById(R.id.listview_relationships);

        mAddContactButton = (Button) v.findViewById(R.id.button_confirm_add);

        mAdapter = new ContactListAdapter(mContactList, getContext(), getActivity(), true);
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
                    i ++;
                }
                newContact.setRelationships(relationships);

                i = 0;
                while (i < mContactList.size() && mContactList.get(i).isChecked()) {
                    mContactList.get(i).getRelationships().add(newContact);
                    mContactList.get(i).setChecked(false);
                    i ++;
                }

                mContactList.add(newContact);
                mInputContactName.setText("");
                mInputContactNumber.setText("");
                relationships.add(newContact);
                mAdapter.updateList(relationships);

                ((MainActivity)getActivity()).setContactList(mContactList);
            }
        });

        return v;
    }

}
