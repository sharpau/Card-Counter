package com.austin.cardcounter;

import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridLayout.LayoutParams;
import android.widget.GridLayout.Spec;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GenericGameFragment extends Fragment implements OnClickListener {
	ArrayList<Integer> mTeamScores;
	ArrayList<LinearLayout> mTeamColumns;
	
	GridLayout mTeamsGrid;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_generic_game, container, false);
		mTeamsGrid = (GridLayout) rootView.findViewById(R.id.teams_grid);
		
		// click listener
        Button addBtn = (Button) rootView.findViewById(R.id.add_scores);
        addBtn.setOnClickListener(this);
        Button undoBtn = (Button) rootView.findViewById(R.id.undo);
        undoBtn.setOnClickListener(this);
        Button clearBtn = (Button) rootView.findViewById(R.id.clear);
        clearBtn.setOnClickListener(this);
        Button plusBtn = (Button) rootView.findViewById(R.id.plus);
        plusBtn.setOnClickListener(this);
		
		return rootView;
	}

	@Override
	public void onClick(View view) {
		switch(view.getId()) {
		case R.id.add_scores:
			addScores(view);
			break;
		case R.id.undo:
			undo(view);
			break;
		case R.id.clear:
			clear(view);
			break;
		case R.id.plus:
			plus(view);
			break;
		default:
			break;
		
		}
	}
	
	public void errorToast(String errorMsg) {
		Context context = getActivity().getApplicationContext();
		int duration = Toast.LENGTH_SHORT;
		
		Toast toast = Toast.makeText(context, errorMsg, duration);
		toast.show();
	}
	
	// add a new team column
	public void plus(View view) {
		mTeamsGrid.setColumnCount(mTeamsGrid.getColumnCount() + 1);
		View newCol = getActivity().getLayoutInflater().inflate(R.layout.column_team, null);
		

//        android:layout_width="wrap_content"
//        android:layout_height="wrap_content"
//   	    android:layout_row="0"
//   	    android:layout_column="2"
//   	    android:layout_rowSpan="5"

		Spec col = GridLayout.spec(mTeamsGrid.getColumnCount() - 1);
		Spec row = GridLayout.spec(0, 5);
		LayoutParams lp = new LayoutParams(row, col);
		mTeamsGrid.addView(newCol, lp);
		mTeamsGrid.invalidate();
	}
	
	// reset scores page entirely
	public void clear(View view) {
//		mTeam1Score = 0;
//		mTeam2Score = 0;
//		LinearLayout scrollArea1 = (LinearLayout)getActivity().findViewById(R.id.team1_list);
//		LinearLayout scrollArea2 = (LinearLayout)getActivity().findViewById(R.id.team2_list);
//		scrollArea1.removeAllViews();
//		scrollArea2.removeAllViews();
//		scrollArea1.invalidate();
//		scrollArea2.invalidate();
//
//
//		// update total scores
//		TextView score1 = (TextView)getActivity().findViewById(R.id.team1_score);
//		score1.setText(mTeam1Score.toString());
//		
//		TextView score2 = (TextView)getActivity().findViewById(R.id.team2_score);
//		score2.setText(mTeam2Score.toString());
//		
//		// zero out various entries
//		EditText meld1 = (EditText)getActivity().findViewById(R.id.team1_meld);
//		EditText meld2 = (EditText)getActivity().findViewById(R.id.team2_meld);
//		EditText trick1 = (EditText)getActivity().findViewById(R.id.team1_tricks);
//		EditText trick2 = (EditText)getActivity().findViewById(R.id.team2_tricks);
//		EditText bid = (EditText)getActivity().findViewById(R.id.bid);
//		meld1.setText("");
//		trick1.setText("");
//		meld2.setText("");
//		trick2.setText("");
//		bid.setText("");
	}
	
	// remove last set of scores entered
	public void undo(View view) {
//		// make sure we have something to undo
//		LinearLayout scrollArea1 = (LinearLayout)getActivity().findViewById(R.id.team1_list);
//		LinearLayout scrollArea2 = (LinearLayout)getActivity().findViewById(R.id.team2_list);
//		if(scrollArea1.getChildCount() < 2 || scrollArea2.getChildCount() < 2) {
//			return;
//		}
//		
//		// get IDs of the last 2 text boxes in each LinearLayout
//		TextView v1 = (TextView) scrollArea1.getChildAt(scrollArea1.getChildCount() - 1);
//		TextView v2 = (TextView) scrollArea1.getChildAt(scrollArea1.getChildCount() - 2);
//		Integer toSubtract = Integer.parseInt(v1.getText().toString()) + Integer.parseInt(v2.getText().toString());
//		mTeam1Score -= toSubtract;
//		
//		// remove text boxes
//		scrollArea1.removeViewAt(scrollArea1.getChildCount() - 1);
//		scrollArea1.removeViewAt(scrollArea1.getChildCount() - 1);
//
//		// figure out scores to subtract
//		TextView total1 = (TextView)getActivity().findViewById(R.id.team1_score);
//		Integer newTotal = Integer.parseInt(total1.getText().toString()) - toSubtract;
//		total1.setText(newTotal.toString());
//		scrollArea1.invalidate();
//
//		TextView v3 = (TextView) scrollArea2.getChildAt(scrollArea2.getChildCount() - 1);
//		TextView v4 = (TextView) scrollArea2.getChildAt(scrollArea2.getChildCount() - 2);
//		toSubtract = Integer.parseInt(v3.getText().toString()) + Integer.parseInt(v4.getText().toString());
//		mTeam2Score -= toSubtract;
//		
//		// remove text boxes
//		scrollArea2.removeViewAt(scrollArea2.getChildCount() - 1);
//		scrollArea2.removeViewAt(scrollArea2.getChildCount() - 1);
//
//		// figure out scores to subtract
//		TextView total2 = (TextView)getActivity().findViewById(R.id.team2_score);
//		newTotal = Integer.parseInt(total2.getText().toString()) - toSubtract;
//		total2.setText(newTotal.toString());
//		scrollArea2.invalidate();
	}
	
	// add to total scores based on values entered
	public void addScores(View view) {
		// see 500 version for basic outline...will change a lot though
	}
	
	private void addTeamScore(Integer team, Integer value) {
//		mTeam1Score += value;
//		LinearLayout scrollArea = (LinearLayout)getActivity().findViewById(R.id.team1_list);
//		// add textview
//		TextView newScore = new TextView(getActivity());
//		newScore.setText(value.toString());
//		
//		scrollArea.addView(newScore);
//		
//		// update total scores
//		TextView score1 = (TextView)getActivity().findViewById(R.id.team1_score);
//		score1.setText(mTeam1Score.toString());
	}
}
