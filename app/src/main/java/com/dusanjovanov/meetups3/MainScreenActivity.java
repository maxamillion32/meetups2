package com.dusanjovanov.meetups3;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dusanjovanov.meetups3.fragments.ContactsFragment;
import com.dusanjovanov.meetups3.fragments.GroupsFragment;
import com.dusanjovanov.meetups3.fragments.HomeFragment;
import com.dusanjovanov.meetups3.fragments.ProfileFragment;
import com.dusanjovanov.meetups3.models.User;
import com.google.firebase.auth.FirebaseAuth;

public class MainScreenActivity extends AppCompatActivity {

    public static final String TAG = "TagMainScreen";
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private SearchView searchView;
    private TabLayout tabs;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private User currentUser;
    private NetworkReceiver networkReceiver;
    private CoordinatorLayout clMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        handleIntent();

        clMain = (CoordinatorLayout) findViewById(R.id.cl_main);
        setupNetworkReceiver();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.requestFocus();
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayShowTitleEnabled(false);
        }

        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(pager);
        setTabIcons();

        searchView = (SearchView) findViewById(R.id.search);
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    startActivity(new Intent(MainScreenActivity.this,SearchActivity.class));
                }
                else{

                }
            }
        });

    }

    private void setupNetworkReceiver(){
        networkReceiver = new NetworkReceiver(new NetworkReceiver.ConnectionChangeListener() {
            @Override
            public void onDisconnected() {
                Snackbar.make(clMain,"Nema internet konekcije",Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onConnected() {

            }
        });
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkReceiver,filter);
    }

    private void handleIntent(){
        Intent intent = getIntent();
        String action = null;
        if(intent!=null){
            action = intent.getStringExtra("action");
        }
        if(action!=null){
            if(action.equals(MainActivity.TAG)){
                currentUser = (User) intent.getSerializableExtra("user");
            }
        }
    }

    private void setTabIcons(){
        tabs.getTabAt(0).setIcon(R.drawable.tab_home);
        tabs.getTabAt(1).setIcon(R.drawable.tab_groups);
        tabs.getTabAt(2).setIcon(R.drawable.tab_contacts);
        tabs.getTabAt(3).setIcon(R.drawable.tab_profile);
    }

    private class PagerAdapter extends FragmentPagerAdapter{

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    HomeFragment homeFragment = new HomeFragment();
                    Bundle args4 = new Bundle();
                    args4.putSerializable("user",currentUser);
                    homeFragment.setArguments(args4);
                    return homeFragment;
                case 1:
                    GroupsFragment groupsFragment = new GroupsFragment();
                    Bundle args = new Bundle();
                    args.putSerializable("user",currentUser);
                    groupsFragment.setArguments(args);
                    return groupsFragment;
                case 2:
                    ContactsFragment contactsFragment = new ContactsFragment();
                    Bundle args2 = new Bundle();
                    args2.putSerializable("user",currentUser);
                    contactsFragment.setArguments(args2);
                    return contactsFragment;
                case 3:
                    ProfileFragment profileFragment = new ProfileFragment();
                    Bundle args3 = new Bundle();
                    args3.putSerializable("user",currentUser);
                    profileFragment.setArguments(args3);
                    return profileFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(searchView!=null){
            searchView.clearFocus();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_screen,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_sign_out:
                firebaseAuth.signOut();
                startActivity(new Intent(this,SignInActivity.class));
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        if(networkReceiver!=null){
            unregisterReceiver(networkReceiver);
        }
        super.onDestroy();
    }
}
