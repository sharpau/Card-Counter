package com.austin.cardcounter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GenericHistoryFragment extends HistoryFragment {

	public static String TABLE_NAME = "genericHistory";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//return onCreateViewHelper(inflater, container, savedInstanceState, R.layout.fragment_history_generic, R.id.generic_history_grid, TABLE_NAME);
		return inflater.inflate(R.layout.fragment_generic_history, container, false);
	}

}
