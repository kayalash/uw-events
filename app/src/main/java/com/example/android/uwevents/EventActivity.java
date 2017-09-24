package com.example.android.uwevents;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.RecyclerView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.uwevents.DrawerItem;

import java.util.ArrayList;
import java.util.List;

public class EventActivity extends AppCompatActivity {

    private static final String LOG_TAG = EventActivity.class.getName();

    /**
     * URL for University of Waterloo events from Open Data API
     */
    private static final String UWE_REQUEST_URL =
            "https://api.uwaterloo.ca/v2/events.json?key=5376422d7e8e6a2b4ffc97a5a5c9d1f8";

    /**
     * Adapter for the list of events
     */
    private EventAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<DrawerItem> mDrawerItemList;
    private Toolbar myToolBar;

    private DrawerLayout mDrawerLayout;


    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list);

        // Find a reference to the {@link ListView} in the layout
        ListView eventListView = (ListView) findViewById(R.id.list);

        // Create a new adapter that takes an empty list of events as input
        mAdapter = new EventAdapter(this, new ArrayList<Event>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        eventListView.setAdapter(mAdapter);

        // Set and item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected event
        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Find the current event that was clicked on
                Event currentEvent = mAdapter.getItem(i);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri eventUri = Uri.parse(currentEvent.geteURL());

                // Create a new intent to view the event URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, eventUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        // Start the AsyncTask to fetch the event data
        EventAsyncTask task = new EventAsyncTask();
        task.execute(UWE_REQUEST_URL);


        myToolBar = (Toolbar)findViewById(R.id.appBar);
        setSupportActionBar(myToolBar);

        mDrawerItemList = new ArrayList<DrawerItem>();
        DrawerItem item = new DrawerItem();
        item.setIcon(R.drawable.ic_menu_slideshow);
        item.setTitle("Filters");
        mDrawerItemList.add(item);

        DrawerItem item2 = new DrawerItem();
        item2.setIcon(R.drawable.ic_menu_manage);
        item2.setTitle("Settings");
        mDrawerItemList.add(item2);

        mRecyclerView = (RecyclerView) findViewById(R.id.drawerRecyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);

        DrawerAdapter adapter = new DrawerAdapter(mDrawerItemList);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, myToolBar, R.string.open, R.string.close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //TODO Add some action here
                //Executed when drawer closes

                Toast.makeText(EventActivity.this, "Closed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //TODO Add some action here
                //executes when drawer open
                Toast.makeText(EventActivity.this, "Opened", Toast.LENGTH_SHORT).show();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        adapter.setOnItemClickLister(new DrawerAdapter.OnItemSelecteListener() {
            @Override
            public void onItemSelected(View v, int position) {
                Toast.makeText(EventActivity.this, "You clicked at position: "+ position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    private class EventAsyncTask extends AsyncTask<String, Void, List<Event>> {

        @Override
        protected List<Event> doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<Event> result = QueryUtils.fetchEventData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<Event> data) {
            // Clear the adapter of previous earthquake data
            mAdapter.clear();

            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }
        }
    }
}

