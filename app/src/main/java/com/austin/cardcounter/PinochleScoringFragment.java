package com.austin.cardcounter;

import junit.framework.Assert;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.GridLayout.LayoutParams;


public class PinochleScoringFragment extends Fragment implements OnClickListener {

	public PinochleScoringFragment() {
	}
	
	static Boolean mSimpleScoring = true;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_pinochle_scoring,
				container, false);
		
		// click listener
        Button switchBtn = (Button) rootView.findViewById(R.id.switch_scoring_button);
        switchBtn.setOnClickListener(this);
		
		// layout for meld items
		GridLayout meldLayout = (GridLayout)rootView.findViewById(R.id.fragment_scoring_melds);

		String[] melds = getResources().getStringArray(R.array.melds);
		String[] meldVals = getResources().getStringArray(R.array.meld_values);
		
		Assert.assertEquals(melds.length, meldVals.length);
		
		for(int i = 0; i < melds.length; i++) {
			TextView meld = new TextView(getActivity());
			meld.setText(melds[i]);
			meldLayout.addView(meld, new GridLayout.LayoutParams());
			
			TextView meldVal = new TextView(getActivity());
			meldVal.setText(meldVals[i]);
			LayoutParams rightJust = new GridLayout.LayoutParams();
			rightJust.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.RIGHT);
			meldLayout.addView(meldVal, rightJust);
		}
		
		// layout for simple trick scoring
		GridLayout simpleTrickLayout = (GridLayout)rootView.findViewById(R.id.fragment_scoring_tricks_simple);

		String[] simpleTricks = getResources().getStringArray(R.array.tricks);
		String[] simpleTrickValues = getResources().getStringArray(R.array.trick_values);
		
		Assert.assertEquals(simpleTricks.length, simpleTrickValues.length);
		
		for(int i = 0; i < simpleTricks.length; i++) {
			TextView trick = new TextView(getActivity());
			trick.setText(simpleTricks[i]);
			simpleTrickLayout.addView(trick, new GridLayout.LayoutParams());
			
			TextView val = new TextView(getActivity());
			val.setText(simpleTrickValues[i]);
			LayoutParams rightJust = new GridLayout.LayoutParams();
			rightJust.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.RIGHT);
			simpleTrickLayout.addView(val, rightJust);
		}
		
		
		// layout for non-simple trick scoring
		GridLayout trickLayout = (GridLayout)rootView.findViewById(R.id.fragment_scoring_tricks);

		String[] tricks = getResources().getStringArray(R.array.tricks_alt);
		String[] trickValues = getResources().getStringArray(R.array.trick_alt_values);
		
		Assert.assertEquals(tricks.length, trickValues.length);

		for(int i = 0; i < tricks.length; i++) {
			TextView trick = new TextView(getActivity());
			trick.setText(tricks[i]);
			trickLayout.addView(trick, new GridLayout.LayoutParams());
			
			TextView val = new TextView(getActivity());
			val.setText(trickValues[i]);
			LayoutParams rightJust = new GridLayout.LayoutParams();
			rightJust.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.RIGHT);
			trickLayout.addView(val, rightJust);
		}
		
		trickLayout.setVisibility(View.GONE);
		
		return rootView;
	}
	
	// swap the two tricks layouts
	public void SwitchTricks(View view) {
		View root = getView();
		if(mSimpleScoring) {
			mSimpleScoring = false;
			
			GridLayout trickLayout = (GridLayout)root.findViewById(R.id.fragment_scoring_tricks);
			trickLayout.setVisibility(View.VISIBLE);
			
			GridLayout simpleTrickLayout = (GridLayout)root.findViewById(R.id.fragment_scoring_tricks_simple);
			simpleTrickLayout.setVisibility(View.GONE);
		}
		else {
			mSimpleScoring = true;
			
			GridLayout trickLayout = (GridLayout)root.findViewById(R.id.fragment_scoring_tricks);
			trickLayout.setVisibility(View.GONE);
			
			GridLayout simpleTrickLayout = (GridLayout)root.findViewById(R.id.fragment_scoring_tricks_simple);
			simpleTrickLayout.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(View view) {
		switch(view.getId()) {
		case R.id.switch_scoring_button:
			SwitchTricks(view);
			break;
		default:
			break;
		
		}
		
	}
}