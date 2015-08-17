package com.austin.cardcounter;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

public abstract class HistoryFragment extends Fragment implements OnClickListener {
	
	public String TABLE_NAME = "genericHistory"; // overwritten by subclasses
	public static final String COLUMN_NAME_WINNING_TEAM = "winningteam";
	public static final String COLUMN_NAME_WINNING_SCORE = "winningscore";
	public static final String COLUMN_NAME_LOSING_TEAM = "losingteam";
	public static final String COLUMN_NAME_LOSING_SCORE = "losingscore";

	private static final String TEXT_TYPE = " TEXT";
	private static final String INTEGER_TYPE = " INTEGER";
	private static final String COMMA_SEP = ",";
	private static final String _ID = "_id";
	
	public static final String SqlCreateEntries(String table) {
	    return "CREATE TABLE " + table + " (" +
		_ID + " INTEGER PRIMARY KEY," +
		COLUMN_NAME_WINNING_TEAM + TEXT_TYPE + COMMA_SEP +
	    COLUMN_NAME_WINNING_SCORE + INTEGER_TYPE + COMMA_SEP +
	    COLUMN_NAME_LOSING_TEAM + TEXT_TYPE + COMMA_SEP +
	    COLUMN_NAME_LOSING_SCORE + INTEGER_TYPE +
	    " )";
	}
	    
	int numRows = 1;
	View rootView;
	int mGridID;
	String mTableName;
	

	public HistoryFragment() {
		super();
	}

	public void addGameEntry(String winningTeam, String losingTeam, String winningScore,
			String losingScore) {
		GridLayout historyGrid = (GridLayout)rootView.findViewById(mGridID);
	    // set up the grid with the list of games in history
		historyGrid.setRowCount(numRows + 1);
		
		GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
		// add textviews
		TextView winningTeamText = new TextView(getActivity());
		winningTeamText.setText(winningTeam);
		lp = new GridLayout.LayoutParams();
		lp.columnSpec = GridLayout.spec(0);
		lp.rowSpec = GridLayout.spec(numRows);
		lp.height = LayoutParams.WRAP_CONTENT;
		lp.width = LayoutParams.WRAP_CONTENT;
		historyGrid.addView(winningTeamText, lp);
	
		TextView winningScoreText = new TextView(getActivity());
		winningScoreText.setText(winningScore);
		lp = new GridLayout.LayoutParams();
		lp.columnSpec = GridLayout.spec(1);
		lp.rowSpec = GridLayout.spec(numRows);
		lp.height = LayoutParams.WRAP_CONTENT;
		lp.width = LayoutParams.WRAP_CONTENT;
		historyGrid.addView(winningScoreText, lp);
	
		TextView losingTeamText = new TextView(getActivity());
		losingTeamText.setText(losingTeam);
		lp = new GridLayout.LayoutParams();
		lp.columnSpec = GridLayout.spec(2);
		lp.rowSpec = GridLayout.spec(numRows);
		lp.height = LayoutParams.WRAP_CONTENT;
		lp.width = LayoutParams.WRAP_CONTENT;
		historyGrid.addView(losingTeamText, lp);
	
		TextView losingScoreText = new TextView(getActivity());
		losingScoreText.setText(losingScore);
		lp = new GridLayout.LayoutParams();
		lp.columnSpec = GridLayout.spec(3);
		lp.rowSpec = GridLayout.spec(numRows);
		lp.height = LayoutParams.WRAP_CONTENT;
		lp.width = LayoutParams.WRAP_CONTENT;
		historyGrid.addView(losingScoreText, lp);
		
		numRows++;
		rootView.invalidate();
	}

	@Override
	public abstract View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
	
	public View onCreateViewHelper(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, int layout, int gridID, String table) {
		rootView = inflater.inflate(layout,
				container, false);
		mGridID = gridID;
		mTableName = table;
		
		// setup clear history button
	    Button clearBtn = (Button) rootView.findViewById(R.id.clear_history);
	    clearBtn.setOnClickListener(this);
		
		// populate with list of past games
		HistoryDbHelper mDbHelper = new HistoryDbHelper(getActivity());
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		
		String[] projection = {
			    _ID,
			    COLUMN_NAME_WINNING_TEAM,
			    COLUMN_NAME_WINNING_SCORE,
			    COLUMN_NAME_LOSING_TEAM,
			    COLUMN_NAME_LOSING_SCORE
			    };
		String sortOrder = _ID + " ASC";
	
	    Cursor cursor = db.query(
    		mTableName,  // The table to query
	        projection,                               // The columns to return
	        null,                                // The columns for the WHERE clause
	        null,                            // The values for the WHERE clause
	        null,                                     // don't group the rows
	        null,                                     // don't filter by row groups
	        sortOrder                                 // The sort order
	        );
	
	
		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			String winningTeam = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_WINNING_TEAM));
			String winningScore = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_WINNING_SCORE));
			String losingTeam = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_LOSING_TEAM));
			String losingScore = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_LOSING_SCORE));
	
			addGameEntry(winningTeam, losingTeam, winningScore, losingScore);
			
			cursor.moveToNext();
		}
		
		return rootView;
	}

	@Override
	public void onClick(View view) {
		switch(view.getId()) {
		case R.id.clear_history:
			HistoryDbHelper mDbHelper = new HistoryDbHelper(getActivity());
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			String[] empty = null;
			db.delete(mTableName, "", empty);
			
	
			GridLayout historyGrid = (GridLayout)rootView.findViewById(mGridID);
			historyGrid.removeAllViews();
			numRows = 1;
			addGameEntry("Winning Team", "Losing Team", "Score", "Score");
			historyGrid.invalidate();
			break;
		default:
			break;
		
		}
	}

}