package com.example.meetingschedhuler;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AllContactFragment extends Fragment {

    private  View rootView;
    RecyclerView recyclerView_all_contacts;
    FloatingActionButton add_new_contact;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_all_contacts, container, false);

        add_new_contact = rootView.findViewById(R.id.add_new_contact);
        add_new_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(rootView.getContext(), AddContact.class);
                startActivity(intent);
            }
        });

        return  rootView;

    }


}
