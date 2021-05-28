package com.itaakash.android.demoapp.adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.itaakash.android.demoapp.R;
import com.itaakash.android.demoapp.model.TodotaskModel;

import java.util.List;


public class TodotaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    public List<TodotaskModel> todotaskModelsList;

    public TodotaskAdapter(Activity activity, List<TodotaskModel> todotaskModelsList) {
        this.activity = activity;
        this.todotaskModelsList = todotaskModelsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todotask, parent, false);
        return new VContentInfoHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        ((VContentInfoHolder) holder).tv_task_name.setText(todotaskModelsList.get(position).getTaskname());
        ((VContentInfoHolder) holder).tv_task_date.setText(todotaskModelsList.get(position).getTasktime());


    }

    @Override
    public int getItemCount() {
        return todotaskModelsList.size();
    }


    private class VContentInfoHolder extends RecyclerView.ViewHolder {

        private TextView tv_task_date;
        private TextView tv_task_name;




        public VContentInfoHolder(View itemView) {
            super(itemView);

            tv_task_date = (TextView) itemView.findViewById(R.id.tv_task_date);
            tv_task_name = (TextView) itemView.findViewById(R.id.tv_task_name);




         }
    }


}
