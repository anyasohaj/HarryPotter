package com.bagirapp.harrypotterquiz.eredmenyData;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bagirapp.harrypotterquiz.R;

import java.util.ArrayList;
import butterknife.ButterKnife;

public class ResultsFragment extends android.support.v4.app.Fragment {
    Context context;
    LinearLayoutManager mLayoutManager;
    ResultsAdapter mAdapter;
    ArrayList<Results> mDataset;
//    @BindView(R.id.results_recycler_view)
//    RecyclerView mRecyclerView;
    RecyclerView mRecyclerView;
    public View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*rootView = inflater.inflate(R.layout.activity_settings, container, false);
        ButterKnife.bind(this, rootView);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.resultFragment);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mDataset = getResultsFromDatabase();
        mAdapter = new ResultsAdapter(context, mDataset);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;*/
        return container;
    }

    public ArrayList<Results> getResultsFromDatabase(){
        ArrayList<Results> resultsArray = new ArrayList<>();
        EredmenyDbHelper mDbHelper = new EredmenyDbHelper(context);
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        String projection[] = {EredmenyContract.EredmenyEntry._ID,
                EredmenyContract.EredmenyEntry.COLUMN_PRODUCT_DATE,
                EredmenyContract.EredmenyEntry.COLUMN_LEVEL,
                EredmenyContract.EredmenyEntry.COLUMN_POINTS};

        Cursor cursor = database.query(EredmenyContract.EredmenyEntry.TABLE_NAME, projection, null, null, null, null, null);

        int dateIndex = cursor.getColumnIndex(EredmenyContract.EredmenyEntry.COLUMN_PRODUCT_DATE);
        int levelIndex = cursor.getColumnIndex(EredmenyContract.EredmenyEntry.COLUMN_LEVEL);
        int pointsIndex = cursor.getColumnIndex(EredmenyContract.EredmenyEntry.COLUMN_POINTS);

        while (cursor.moveToNext()) {
            String resultDate = cursor.getString(dateIndex);
            int resultLevel = cursor.getInt(levelIndex);
            int resultPoints = cursor.getInt(pointsIndex);
            Results currentResult = new Results(resultDate, resultLevel, resultPoints);
            resultsArray.add(currentResult);
        }
        cursor.close();
        return resultsArray;
    }



}
