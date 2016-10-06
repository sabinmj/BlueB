package com.example.sabinmj.blueb;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sabinmj.blueb.model.BlogPost;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mBlogRecycler;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseReference= FirebaseDatabase.getInstance().getReference();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startActivity(new Intent(getApplication(),blogreader.class));

            }
        });


        mBlogRecycler=(RecyclerView)findViewById(R.id.re);
        mBlogRecycler.setHasFixedSize(true);
        mBlogRecycler.setLayoutManager(new LinearLayoutManager(this));


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.greeting_card) {
            // Handle the camera action
        } else if (id == R.id.cake) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }














    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference dbRef = databaseReference.child("datas");

        FirebaseRecyclerAdapter<BlogPost,BlogViewHolder> adapter=new FirebaseRecyclerAdapter<BlogPost, BlogViewHolder>(
                BlogPost.class,
                R.layout.model_layot,
                BlogViewHolder.class,
                dbRef
        ) {
            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, BlogPost model, int position) {

                viewHolder.setTitle(model.getBlogtitle());
                viewHolder.setDesc(model.getDescription());
                viewHolder.setImage(model.getUrl());


            }
        };

        mBlogRecycler.setAdapter(adapter);

    }

    public static  class BlogViewHolder extends RecyclerView.ViewHolder{

        private  View mview;

        public BlogViewHolder(View itemView) {
            super(itemView);
            mview=itemView;
        }
        public void setTitle(String title){
            TextView textView=(TextView)mview.findViewById(R.id.tvtitle);
            textView.setText(title);
        }
        public void setDesc(String desc){
            TextView textView=(TextView)mview.findViewById(R.id.tvdesc);
            textView.setText(desc);
        }
        public void setImage(String urlv){
            Log.i("vinothmaadhu", "setImage: " +urlv );
            ImageView imageView=(ImageView)mview.findViewById(R.id.ivmodel);
            Picasso.with(mview.getContext())
                    .load(urlv)
                    .into(imageView);
        }
    }

  }














