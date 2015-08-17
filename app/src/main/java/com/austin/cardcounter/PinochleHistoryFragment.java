package com.austin.cardcounter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PinochleHistoryFragment extends HistoryFragment {
	public static String TABLE_NAME = "pinochleHistory";

	public PinochleHistoryFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return onCreateViewHelper(inflater, container, savedInstanceState, R.layout.fragment_pinochle_history, R.id.pinochle_history_grid, TABLE_NAME);
	}
}