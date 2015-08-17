package com.austin.cardcounter;

import java.util.Locale;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
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

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {

	
	// Navigation drawer-related items
	String[] mGameTypeTitles;
	DrawerLayout mDrawerLayout;
	ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    
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

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		// Set up the navigation drawer ---------------------
		mGameTypeTitles = getResources().getStringArray(R.array.game_types);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mGameTypeTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        
        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mGameTypeTitles[mGameType]); // TODO
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle("Choose game type");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        
        // end nav drawer code ----------------------------------

        setupPinochleTabs(savedInstanceState, actionBar);
        //setup500Tabs(actionBar);

	}
	
	private void setupPinochleTabs(Bundle savedInstanceState, final ActionBar actionBar) {
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
        mPinochlePagerAdapter = new PinochlePagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(null);
		mViewPager.setAdapter(mPinochlePagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});
		
		actionBar.removeAllTabs();
		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mPinochlePagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mPinochlePagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
		mViewPager.setCurrentItem(1);
		
		// tabs are set up, now restore status
		if (savedInstanceState != null) {
	        // Restore value of members from saved state
			// Restore state members from saved instance
		    mTeam1Scores = savedInstanceState.getIntArray(PINOCHLE_TEAM1_SCORES);
		    mTeam2Scores = savedInstanceState.getIntArray(PINOCHLE_TEAM2_SCORES);
	    }
	}
	
	private void setup500Tabs(final ActionBar actionBar) {
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
        m500PagerAdapter = new FiveHundredPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(null);
		mViewPager.setAdapter(m500PagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		actionBar.removeAllTabs();
		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < m500PagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(m500PagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
		mViewPager.setCurrentItem(1);
	}
	
	private void setupGenericTabs(final ActionBar actionBar) {
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
        mGenericPagerAdapter = new GenericPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(null);
		mViewPager.setAdapter(mGenericPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		actionBar.removeAllTabs();
		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mGenericPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mGenericPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
		mViewPager.setCurrentItem(1);
	}

	// Override to save scores already entered
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		LinearLayout scrollArea1 = (LinearLayout)findViewById(R.id.team1_list);
		int[] team1Scores = new int[scrollArea1.getChildCount()];
		for(int i = 0; i < scrollArea1.getChildCount(); i++) {
			team1Scores[i] = Integer.parseInt(((TextView)scrollArea1.getChildAt(i)).getText().toString());
		}
		savedInstanceState.putIntArray(PINOCHLE_TEAM1_SCORES, team1Scores);
		
		LinearLayout scrollArea2 = (LinearLayout)findViewById(R.id.team2_list);
		int[] team2Scores = new int[scrollArea2.getChildCount()];
		for(int i = 0; i < scrollArea2.getChildCount(); i++) {
			team2Scores[i] = Integer.parseInt(((TextView)scrollArea2.getChildAt(i)).getText().toString());
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

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}
	
	public ImgAdapter getSuitImagesAdapter(Boolean noTrump) {
		// trump suit spinner setup
		if(noTrump) {
			String[] strings = {"Spades", "Clubs", "Diamonds", "Hearts", "No Trump"};
			return new ImgAdapter(this, R.layout.row, strings);
		}
		else {
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
        public View getDropDownView(int position, View convertView,ViewGroup parent) {
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
		}@
		
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
			switch(position) {
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
			switch(position) {
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
			switch(position) {
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
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
       if (mDrawerToggle.onOptionsItemSelected(item)) {
           return true;
       }
       // Handle action buttons
       switch(item.getItemId()) {
       default:
           return super.onOptionsItemSelected(item);
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
    	if(old != mGameType) {
    		// only do stuff if something different was selected
    		//mViewPager.not
        	switch(mGameType) {
        	case 0:
        	default:
        		setupPinochleTabs(null, getActionBar());
        		break;
        	case 1:
        		setup500Tabs(getActionBar());
        		break;
	    	case 2:
	    		setupGenericTabs(getActionBar());
	    		break;
	    	}
    	}
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
    }
    
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
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
