package com.example.meetingschedhuler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    MyDatabaseHelper myDatabaseHelper;
    private ArrayList<Contact> allcontacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDatabaseHelper = new MyDatabaseHelper(MainActivity.this);
        allcontacts = myDatabaseHelper.getAllContacts(ContactType.NORMAL);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AllContactFragment (allcontacts)).commit();
            navigationView.setCheckedItem(R.id.all_contacts);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchmenu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type contact name to search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AllContactFragment (FilterContact(allcontacts, newText))).commit();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.all_contacts:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AllContactFragment(allcontacts)).commit();
                break;
            case R.id.important_contacts:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ImportantContactFragment()).commit();
                break;
            case R.id.favourite_contacts:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FavouriteContactFragment()).commit();
                break;
                /*
            case R.id.nav_share:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_send:
                Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();
                break;
                */
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public ArrayList<Contact> FilterContact(ArrayList<Contact> list, String searchString){
        ArrayList<Contact> output = new ArrayList<Contact>();
        for(Integer i =0; i<list.size(); i++){
            if((list.get(i).get_firstName() + " "+ list.get(i).get_lastName()).contains(searchString)){
                output.add(list.get(i));
            }
        }
        return  output;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}