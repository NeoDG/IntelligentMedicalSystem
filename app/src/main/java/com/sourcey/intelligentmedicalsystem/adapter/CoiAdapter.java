package com.sourcey.intelligentmedicalsystem.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sourcey.intelligentmedicalsystem.R;

/**
 * Created by StFranky on 2017/6/25.
 */
/*
病例活动的适配器
 */

public class CoiAdapter extends RecyclerView.Adapter<CoiAdapter.MyViewHolder> {

    private Context context;
    public String[][] data;
    public CoiAdapter(Context context, String[][] data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.coiitems, null);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv1.setText(data[position][0]);
        holder.tv2.setText(data[position][1]);
        holder.tv3.setText(data[position][2]);
        holder.tv4.setText(data[position][3]);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv1, tv2, tv3, tv4;

        public MyViewHolder(View view) {
            super(view);
            tv1 = (TextView) view.findViewById(R.id.textView_time);
            tv2 = (TextView) view.findViewById(R.id.textView_quest);
            tv3 = (TextView) view.findViewById(R.id.textView_solvation);
            tv4 = (TextView) view.findViewById(R.id.textView_medicine);
        }
    }
}
