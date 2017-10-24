package com.kristenwong.contactlist;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactProfileFragment extends Fragment {
    private TextView mContactName, mContactPhone;
    private ListView mRelationships;
    private ContactListAdapter mAdapter;
    private ArrayList<ContactProfile> mRelationshipArray, mContactList;
    private int mContactIndex;
    private ContactProfile mCP;

    private static final String f_CONTACT_PROFILE_KEY = "f contact profile key";
    private static final String f_CONTACT_LIST_KEY = "f contact list key";

    public ContactProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = this.getArguments();
        mContactList = (ArrayList<ContactProfile>) args.getSerializable(f_CONTACT_LIST_KEY);
        mContactIndex = args.getInt(f_CONTACT_PROFILE_KEY);
        mCP = mContactList.get(mContactIndex);
        mRelationshipArray = mCP.getRelationships();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contact_profile, container, false);

        mContactName = (TextView) v.findViewById(R.id.text_contact_name);
        mContactName.setText(mCP.getName());
        mContactPhone = (TextView) v.findViewById(R.id.text_contact_phone);
        mContactPhone.setText(mCP.getPhoneNumber());

        mRelationships = (ListView) v.findViewById(R.id.listview_relationships_cp);

        mAdapter = new ContactListAdapter(mCP.getRelationships(), getActivity().getApplicationContext(), getActivity(), false);
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
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Contact no longer exists.", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    mContactName.setText(mCP.getName());
                    mContactPhone.setText(mCP.getPhoneNumber());
                    mRelationshipArray = mCP.getRelationships();
                    mAdapter.updateList(mRelationshipArray);
                }
            }
        });
        return v;
    }

}
