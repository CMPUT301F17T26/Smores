package cmput301f17t26.smores.all_activities;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import cmput301f17t26.smores.R;
import cmput301f17t26.smores.all_adapters.TabAdapter;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private TabAdapter mTabAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private FloatingActionButton mFloatingActionButton;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        mFloatingActionButton.hide();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mTabAdapter = new TabAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mTabAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        setupTabLayoutListener();

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

    private void setupTabLayoutListener() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()) {
                    case 0:
                        mFloatingActionButton.hide();
                        return;
                    case 1:
                        mFloatingActionButton.show();
                        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar.make(view, "Add habit to be implemented", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        });
                        return;
                    case 2:
                        mFloatingActionButton.show();
                        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar.make(view, "Add habit history to be implemented", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        });
                        return;
                    case 3:
                        mFloatingActionButton.hide();
                        return;
                    case 4:
                        mFloatingActionButton.show();
                        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar.make(view, "Add request to be implemented", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        });
                        return;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}
