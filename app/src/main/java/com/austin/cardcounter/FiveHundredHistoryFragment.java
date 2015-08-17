package com.austin.cardcounter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FiveHundredHistoryFragment extends HistoryFragment{
	public static String TABLE_NAME = "fiveHundredHistory";
	
	public FiveHundredHistoryFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return onCreateViewHelper(inflater, container, savedInstanceState, R.layout.fragment_500_history, R.id.five_hundred_history_grid, TABLE_NAME);
	}
}
