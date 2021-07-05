package com.example.vehiclebath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vehiclebath.Prevalent1.Prevalent;
import com.example.vehiclebath.View_Holder1.Advertisement_View_Holder1;
import com.example.vehiclebath.model1.Advertisements1;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;

public class HomeUserActivity extends AppCompatActivity {

    private Intent intent = getIntent();
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private RecyclerView userViewRecycler;
    private DatabaseReference databaseReference;
    private RecyclerView postList;
    private Toolbar mToolbar;
    private TextView navProfileName;
    private Button bookNowBTN;
    RecyclerView.LayoutManager layoutManager;
    private String logged1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user);

        //getting User Name
        final String logged = getIntent().getStringExtra("logged");
        logged1 = getIntent().getStringExtra("logged");


        Paper.init(this);
        bookNowBTN = findViewById(R.id.bookNowBTN);

//        View headerView = navigationView.getHeaderView(0);
//        TextView userNameTextView = headerView.findViewById(R.id.nav_user_full_name);
//        userNameTextView.setText(Prevalent.currentOnlineUser.getName());

        mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Home");

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Advertisement");

        drawerLayout = (DrawerLayout) findViewById(R.id.drawable_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(HomeUserActivity.this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        userViewRecycler = findViewById(R.id.all_users_post_list);
        userViewRecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        userViewRecycler.setLayoutManager(layoutManager);


        View navView = navigationView.inflateHeaderView(R.layout.navigation_header);
        TextView navProfileName = (TextView) navView.findViewById(R.id.nav_user_full_name);
        navProfileName.setText(Prevalent.currentOnlineUser.getName());



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                UserMenuSelector(menuItem);


                return false;
            }
        });

        bookNowBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeUserActivity.this, SelectCarWash.class);
                intent.putExtra("logged", logged);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Advertisements1> options = new FirebaseRecyclerOptions.Builder<Advertisements1>()
                .setQuery(databaseReference,Advertisements1.class).build();

        FirebaseRecyclerAdapter<Advertisements1, Advertisement_View_Holder1> adapter = new FirebaseRecyclerAdapter   <Advertisements1, Advertisement_View_Holder1>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Advertisement_View_Holder1 advertisementViewHolder, int i, @NonNull Advertisements1 advertisements1) {
                advertisementViewHolder.useradDesc.setText(advertisements1.getDescription());
                Picasso.get().load(advertisements1.getImageUrl()).into(advertisementViewHolder.userimageViewAd);
            }

            @NonNull
            @Override
            public Advertisement_View_Holder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_advertisement,parent,false);
                Advertisement_View_Holder1 holder = new Advertisement_View_Holder1(view);
                return holder;
            }
        };
        userViewRecycler.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void UserMenuSelector(MenuItem menuItem) {

        switch (menuItem.getItemId())
        {
            case R.id.nav_profileSettings:
                sendUserToSettingsActivity();
                Toast.makeText(this,"View Your Profile Settings", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_view:
                viewActivity();
                Toast.makeText(this,"View Your Bookings", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_home:
                homeActivity();
                Toast.makeText(this,"Home", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_loyalty:
                loyaltyActivity();
                Toast.makeText(this,"View Your Loyalty Points", Toast.LENGTH_SHORT).show();
                break;


            case R.id.nav_logout:
                logoutActivity();
                Toast.makeText(this,"Logout Successful", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void sendUserToSettingsActivity()
    {
        Paper.book().destroy();
        Intent settingsIntent = new Intent(HomeUserActivity.this, SettingsActivity1.class);
//        settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(settingsIntent);
//        finish();
    }

    private void logoutActivity()
    {
        Paper.book().destroy();
        Intent settingsIntent = new Intent(HomeUserActivity.this, LoginNew.class);
        startActivity(settingsIntent);
    }

    private void loyaltyActivity()
    {
        Paper.book().destroy();
        Intent settingsIntent = new Intent(HomeUserActivity.this, ViewUserLoyalty.class);
        settingsIntent.putExtra("cno",logged1);
        startActivity(settingsIntent);
    }
    private void homeActivity()
    {
        Paper.book().destroy();
        Intent settingsIntent = new Intent(HomeUserActivity.this, HomeUserActivity.class);
        startActivity(settingsIntent);
    }
    private void viewActivity()
    {
        Paper.book().destroy();
        Intent settingsIntent = new Intent(HomeUserActivity.this, CusShowAllAppointments.class);
        startActivity(settingsIntent);
    }

}

