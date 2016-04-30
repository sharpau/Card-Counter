package com.austin.cardcounter;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // Navigation drawer-related items
    String[] mGameTypeTitles;
    DrawerLayout mDrawerLayout;
    NavigationView mDrawer;

    ViewPager mViewPager;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the navigation drawer
        mGameTypeTitles = getResources().getStringArray(R.array.game_types);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawer = (NavigationView) findViewById(R.id.left_drawer);

        // Set up listener for drawer
        mDrawer.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });

        // Get the ViewPager and set its PagerAdapter so that it can display items
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(new PinochlePagerAdapter(getSupportFragmentManager()));
        mViewPager.setCurrentItem(1);

        // Give the TabLayout the ViewPager
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Switch out the pager adapter based on what was chosen
        switch(menuItem.getItemId()) {
            case R.id.pinochle:
                mViewPager.setAdapter(new PinochlePagerAdapter(getSupportFragmentManager()));
                break;
            case R.id.five_hundred:
                mViewPager.setAdapter(new FiveHundredPagerAdapter(getSupportFragmentManager()));
                break;
            case R.id.generic:
            default:
                mViewPager.setAdapter(new GenericPagerAdapter(getSupportFragmentManager()));
                break;
        }

        // Select the scoring tab
        mViewPager.setCurrentItem(1);

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawerLayout.closeDrawers();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
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
            return getCustomView(position, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, parent);
        }

        public View getCustomView(int position, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.row, parent, false);
            ImageView icon = (ImageView) row.findViewById(R.id.icon);
            icon.setImageResource(arr_images[position]);
            return row;
        }
    }

    public abstract class GameTypeAdapter extends FragmentPagerAdapter {
        public GameTypeAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase();
                case 1:
                    return getString(R.string.title_section2).toUpperCase();
                case 2:
                    return getString(R.string.title_section3).toUpperCase();
            }
            throw new IllegalArgumentException("Not a position");
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
