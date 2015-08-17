package com.austin.cardcounter;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.GridLayout.LayoutParams;

public class FiveHundredScoringFragment extends Fragment {
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_500_scoring,
				container, false);
		
		// layout for trick-based scoring
		GridLayout trickLayout = (GridLayout)rootView.findViewById(R.id.five_hundred_scoring);
		
		TextView trickCol = new TextView(getActivity());
		trickCol.setText("Tricks Bid");
		LayoutParams rightJust = new GridLayout.LayoutParams();
		rightJust.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.RIGHT);
		trickLayout.addView(trickCol, rightJust);

		ImageView spades = new ImageView(getActivity());
		spades.setImageResource(R.drawable.spades);
		rightJust = new GridLayout.LayoutParams();
		rightJust.height = 20;
		rightJust.width = 20;
		rightJust.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.RIGHT);
		trickLayout.addView(spades, rightJust);
		
		ImageView clubs = new ImageView(getActivity());
		clubs.setImageResource(R.drawable.clubs);
		rightJust = new GridLayout.LayoutParams();
		rightJust.height = 20;
		rightJust.width = 20;
		rightJust.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.RIGHT);
		trickLayout.addView(clubs, rightJust);
		
		ImageView diamonds = new ImageView(getActivity());
		diamonds.setImageResource(R.drawable.diamonds);
		rightJust = new GridLayout.LayoutParams();
		rightJust.height = 20;
		rightJust.width = 20;
		rightJust.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.RIGHT);
		trickLayout.addView(diamonds, rightJust);
		
		ImageView hearts = new ImageView(getActivity());
		hearts.setImageResource(R.drawable.hearts);
		rightJust = new GridLayout.LayoutParams();
		rightJust.height = 20;
		rightJust.width = 20;
		rightJust.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.RIGHT);
		trickLayout.addView(hearts, rightJust);
		
		TextView notrump = new TextView(getActivity());
		notrump.setText("NT");
		rightJust = new GridLayout.LayoutParams();
		rightJust.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.RIGHT);
		rightJust.height = LayoutParams.WRAP_CONTENT;
		rightJust.width = LayoutParams.WRAP_CONTENT;
		trickLayout.addView(notrump, rightJust);
		
		View dummy = new View(getActivity());
		rightJust = new GridLayout.LayoutParams();
		rightJust.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.RIGHT);
		trickLayout.addView(dummy, rightJust);
		
		for(int i = 0; i < 5; i++) {
			// i + 6, i*100 + 40, i*100 + 60, etc
			TextView tricks = new TextView(getActivity());
			tricks.setText(Integer.toString(i + 6));
			rightJust = new GridLayout.LayoutParams();
			rightJust.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.LEFT);
			rightJust.height = LayoutParams.WRAP_CONTENT;
			rightJust.width = LayoutParams.WRAP_CONTENT;
			trickLayout.addView(tricks, rightJust);

			TextView spadeVal = new TextView(getActivity());
			spadeVal.setText(Integer.toString(i * 100 + 40));
			rightJust = new GridLayout.LayoutParams();
			rightJust.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.RIGHT);
			rightJust.height = LayoutParams.WRAP_CONTENT;
			rightJust.width = LayoutParams.WRAP_CONTENT;
			trickLayout.addView(spadeVal, rightJust);

			TextView clubVal = new TextView(getActivity());
			clubVal.setText(Integer.toString(i * 100 + 60));
			rightJust = new GridLayout.LayoutParams();
			rightJust.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.RIGHT);
			rightJust.height = LayoutParams.WRAP_CONTENT;
			rightJust.width = LayoutParams.WRAP_CONTENT;
			trickLayout.addView(clubVal, rightJust);

			TextView diamondVal = new TextView(getActivity());
			diamondVal.setText(Integer.toString(i * 100 + 80));
			rightJust = new GridLayout.LayoutParams();
			rightJust.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.RIGHT);
			rightJust.height = LayoutParams.WRAP_CONTENT;
			rightJust.width = LayoutParams.WRAP_CONTENT;
			trickLayout.addView(diamondVal, rightJust);

			TextView heartVal = new TextView(getActivity());
			heartVal.setText(Integer.toString(i * 100 + 100));
			rightJust = new GridLayout.LayoutParams();
			rightJust.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.RIGHT);
			rightJust.height = LayoutParams.WRAP_CONTENT;
			rightJust.width = LayoutParams.WRAP_CONTENT;
			trickLayout.addView(heartVal, rightJust);
			
			TextView ntVal = new TextView(getActivity());
			ntVal.setText(Integer.toString(i * 100 + 120));
			rightJust = new GridLayout.LayoutParams();
			rightJust.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.RIGHT);
			rightJust.height = LayoutParams.WRAP_CONTENT;
			rightJust.width = LayoutParams.WRAP_CONTENT;
			trickLayout.addView(ntVal, rightJust);
			
			dummy = new View(getActivity());
			rightJust = new GridLayout.LayoutParams();
			rightJust.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.RIGHT);
			trickLayout.addView(dummy, rightJust);
		}
		
		return rootView;
	}

}
