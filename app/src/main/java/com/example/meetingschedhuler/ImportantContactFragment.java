package com.example.meetingschedhuler;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ImportantContactFragment extends Fragment {
    private  View rootView;
    RecyclerView recyclerView_important_contacts;
    FloatingActionButton add_new_contact_important;
    MyDatabaseHelper myDatabaseHelper;
    ArrayList<Contact> contacts;
    CustomAdapter customAdapter;
    ImageView no_record_found;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_important_contact, container, false);
        recyclerView_important_contacts = rootView.findViewById(R.id.recycler_view_important_contacts);
        add_new_contact_important = rootView.findViewById(R.id.add_new_contact_important);
        no_record_found = rootView.findViewById(R.id.no_record_found);
        add_new_contact_important.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(rootView.getContext(), AddContact.class);
                startActivity(intent);
            }
        });

        myDatabaseHelper = new MyDatabaseHelper(rootView.getContext());

        contacts = myDatabaseHelper.getAllContacts(ContactType.IMPORTANT);
        //Toast.makeText(rootView.getContext(), ""+ contacts.size(), Toast.LENGTH_SHORT).show();

        if (this.contacts.size() == 0){
            no_record_found.setVisibility(View.VISIBLE);
        }

        customAdapter = new CustomAdapter (rootView.getContext(), contacts);

        recyclerView_important_contacts.setAdapter(customAdapter);

        recyclerView_important_contacts.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        return  rootView;
    }
}
