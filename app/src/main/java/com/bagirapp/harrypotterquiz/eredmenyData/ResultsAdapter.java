package com.bagirapp.harrypotterquiz.eredmenyData;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bagirapp.harrypotterquiz.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ResultViewHolder> {

    Context context;
    private static final String TAG = "ResultsAdapter";
    private ArrayList<Results> listOfResults;
    private int numberOfItems;

    public ResultsAdapter(Context context, ArrayList<Results> listOfResults) {
        this.context = context;
        this.listOfResults = listOfResults;
        numberOfItems = listOfResults.size();


    }

    public class ResultViewHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.resultDate)
    TextView resultDate;
    @BindView(R.id.resultLevel)
    TextView resultLevel;
    @BindView(R.id.resultPoints)
    TextView resultPoints;

        public ResultViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    @Override
    public ResultsAdapter.ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.result_fragment_list_item, parent, false);

        return new ResultViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder( ResultViewHolder holder, int position) {
     Results currentResult =   listOfResults.get(position);
    holder.resultDate.setText(currentResult.getDate());
    holder.resultLevel.setText(currentResult.getLevel());
    holder.resultPoints.setText(currentResult.getPoints());
    }

    @Override
    public int getItemCount() {
        return numberOfItems;
    }
}
