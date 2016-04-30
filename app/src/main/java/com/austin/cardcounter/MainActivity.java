package com.austin.cardcounter;

import java.util.Locale;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    // Navigation drawer-related items
    String[] mGameTypeTitles;
    DrawerLayout mDrawerLayout;
    ListView mDrawerList;

    ViewPager mViewPager;

    PinochlePagerAdapter mPinochlePagerAdapter;
    FiveHundredPagerAdapter m500PagerAdapter;
    GenericPagerAdapter mGenericPagerAdapter;

    int mGameType = 0;
    PinochleHistoryFragment mPinochleHFrag;
    PinochleGameFragment mPinochleGFrag;
    PinochleScoringFragment mPinochleSFrag;
    FiveHundredHistoryFragment m500HFrag;
    FiveHundredGameFragment m500GFrag;
    FiveHundredScoringFragment m500SFrag;
    GenericHistoryFragment mGHFrag;
    GenericGameFragment mGGFrag;
    GenericScoringFragment mGSFrag;

    static final String PINOCHLE_TEAM1_SCORES = "team1Scores";
    static final String PINOCHLE_TEAM2_SCORES = "team2Scores";
    public int[] mTeam1Scores;
    public int[] mTeam2Scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set a Toolbar to replace the ActionBar.
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        // Set up the navigation drawer ---------------------
        mGameTypeTitles = getResources().getStringArray(R.array.game_types);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mGameTypeTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        setupPinochleTabs(savedInstanceState);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        //setup500Tabs(actionBar);

    }

    private void setupPinochleTabs(Bundle savedInstanceState) {
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the app.
        mPinochlePagerAdapter = new PinochlePagerAdapter(
                getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(null);
        mViewPager.setAdapter(mPinochlePagerAdapter);

        mViewPager.setCurrentItem(1);

        // tabs are set up, now restore status
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            // Restore state members from saved instance
            mTeam1Scores = savedInstanceState.getIntArray(PINOCHLE_TEAM1_SCORES);
            mTeam2Scores = savedInstanceState.getIntArray(PINOCHLE_TEAM2_SCORES);
        }
    }

    private void setup500Tabs() {
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the app.
        m500PagerAdapter = new FiveHundredPagerAdapter(
                getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(null);
        mViewPager.setAdapter(m500PagerAdapter);

        mViewPager.setCurrentItem(1);
    }

    private void setupGenericTabs() {
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the app.
        mGenericPagerAdapter = new GenericPagerAdapter(
                getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(null);
        mViewPager.setAdapter(mGenericPagerAdapter);

        mViewPager.setCurrentItem(1);
    }

    // Override to save scores already entered
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        LinearLayout scrollArea1 = (LinearLayout) findViewById(R.id.team1_list);
        int[] team1Scores = new int[scrollArea1.getChildCount()];
        for (int i = 0; i < scrollArea1.getChildCount(); i++) {
            team1Scores[i] = Integer.parseInt(((TextView) scrollArea1.getChildAt(i)).getText().toString());
        }
        savedInstanceState.putIntArray(PINOCHLE_TEAM1_SCORES, team1Scores);

        LinearLayout scrollArea2 = (LinearLayout) findViewById(R.id.team2_list);
        int[] team2Scores = new int[scrollArea2.getChildCount()];
        for (int i = 0; i < scrollArea2.getChildCount(); i++) {
            team2Scores[i] = Integer.parseInt(((TextView) scrollArea2.getChildAt(i)).getText().toString());
        }
        savedInstanceState.putIntArray(PINOCHLE_TEAM2_SCORES, team2Scores);


        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public ImgAdapter getSuitImagesAdapter(Boolean noTrump) {
        // trump suit spinner setup
        if (noTrump) {
            String[] strings = {"Spades", "Clubs", "Diamonds", "Hearts", "No Trump"};
            return new ImgAdapter(this, R.layout.row, strings);
        } else {
            String[] strings = {"Spades", "Clubs", "Diamonds", "Hearts"};
            return new ImgAdapter(this, R.layout.row, strings);
        }
    }

    // for trump suit spinner
    public class ImgAdapter extends ArrayAdapter<String> {

        int arr_images[] = {R.drawable.spades, R.drawable.clubs, R.drawable.diamonds, R.drawable.hearts, R.drawable.notrump};

        public ImgAdapter(Context context, int textViewResourceId, String[] objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            Bundle arg = new Bundle();
            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.row, parent, false);
            ImageView icon = (ImageView) row.findViewById(R.id.icon);
            icon.setImageResource(arr_images[position]);
            return row;
        }
    }

    public abstract class GameTypeAdapter extends FragmentStatePagerAdapter {
        public GameTypeAdapter(FragmentManager fm) {
            super(fm);
        }

        @

                Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    public class PinochlePagerAdapter extends GameTypeAdapter {

        public PinochlePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            Fragment fragment;
            Bundle args;
            switch (position) {
                case 0:
                    mPinochleHFrag = new PinochleHistoryFragment();
                    args = new Bundle();
                    mPinochleHFrag.setArguments(args);
                    return mPinochleHFrag;
                case 1:
                    mPinochleGFrag = new PinochleGameFragment();
                    args = new Bundle();
                    mPinochleGFrag.setArguments(args);
                    return mPinochleGFrag;
                case 2:
                    mPinochleSFrag = new PinochleScoringFragment();
                    args = new Bundle();
                    mPinochleSFrag.setArguments(args);
                    return mPinochleSFrag;
                default:
                    // Return a DummySectionFragment (defined as a static inner class
                    // below) with the page number as its lone argument.
                    fragment = new DummySectionFragment();
                    args = new Bundle();
                    args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
                    fragment.setArguments(args);
                    return fragment;
            }
        }
    }

    public class FiveHundredPagerAdapter extends GameTypeAdapter {

        public FiveHundredPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            Fragment fragment;
            Bundle args;
            switch (position) {
                case 0:
                    m500HFrag = new FiveHundredHistoryFragment();
                    args = new Bundle();
                    m500HFrag.setArguments(args);
                    return m500HFrag;
                case 1:
                    m500GFrag = new FiveHundredGameFragment();
                    args = new Bundle();
                    m500GFrag.setArguments(args);
                    return m500GFrag;
                case 2:
                    m500SFrag = new FiveHundredScoringFragment();
                    args = new Bundle();
                    m500SFrag.setArguments(args);
                    return m500SFrag;
                default:
                    // Return a DummySectionFragment (defined as a static inner class
                    // below) with the page number as its lone argument.
                    fragment = new DummySectionFragment();
                    args = new Bundle();
                    args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
                    fragment.setArguments(args);
                    return fragment;
            }
        }
    }

    public class GenericPagerAdapter extends GameTypeAdapter {

        public GenericPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            Fragment fragment;
            Bundle args;
            switch (position) {
                case 0:
                    mGHFrag = new GenericHistoryFragment();
                    args = new Bundle();
                    mGHFrag.setArguments(args);
                    return mGHFrag;
                case 1:
                    mGGFrag = new GenericGameFragment();
                    args = new Bundle();
                    mGGFrag.setArguments(args);
                    return mGGFrag;
                case 2:
                    mGSFrag = new GenericScoringFragment();
                    args = new Bundle();
                    mGSFrag.setArguments(args);
                    return mGSFrag;
                default:
                    // Return a DummySectionFragment (defined as a static inner class
                    // below) with the page number as its lone argument.
                    fragment = new DummySectionFragment();
                    args = new Bundle();
                    args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
                    fragment.setArguments(args);
                    return fragment;
            }
        }
    }

    /* The click listener for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
//        Fragment fragment = new PlanetFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//
//        FragmentManager fragmentManager = getFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
//
        // update selected item and title, then close the drawer
        int old = mGameType;
        mGameType = position;
        if (old != mGameType) {
            // only do stuff if something different was selected
            //mViewPager.not
            switch (mGameType) {
                case 0:
                default:
                    setupPinochleTabs(null);
                    break;
                case 1:
                    setup500Tabs();
                    break;
                case 2:
                    setupGenericTabs();
                    break;
            }
        }
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    /**
     * A dummy fragment representing a section of the app, but that simply
     * displays dummy text.
     */
    public static class DummySectionFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        public static final String ARG_SECTION_NUMBER = "section_number";

        public DummySectionFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_dummy,
                    container, false);
            TextView dummyTextView = (TextView) rootView
                    .findViewById(R.id.section_label);
            dummyTextView.setText(Integer.toString(getArguments().getInt(
                    ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

}
