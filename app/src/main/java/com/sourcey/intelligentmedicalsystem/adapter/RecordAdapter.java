package com.sourcey.intelligentmedicalsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sourcey.intelligentmedicalsystem.R;
import com.sourcey.intelligentmedicalsystem.bean.Record;

import java.util.List;

/**
 * Created by ly on 2017/6/20.
 */

public class RecordAdapter extends ArrayAdapter<Record> {
    int resourceId;

    public RecordAdapter(Context context, int itemId, List<Record> objects) {
        super(context, itemId, objects);
        resourceId=itemId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Record record=getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null){
            view=LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder =new ViewHolder();
//            viewHolder.textView_quest=(TextView)view.findViewById((R.id.text1));
            viewHolder.textView_quest= (TextView) view.findViewById(R.id.textview_quest);
            viewHolder.textView_solution=(TextView)view.findViewById(R.id.textview_solution);
            viewHolder.textView_medicine=(TextView)view.findViewById(R.id.textview_medicine);
            view.setTag(viewHolder);

        }else{
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
//        viewHolder.textView_quest.setText(record.getQuest());
        viewHolder.textView_quest.setText(record.getQuest());
        viewHolder.textView_solution.setText(record.getSolution());
        viewHolder.textView_medicine.setText(record.getMedicine());
        return view;
    }
}

class ViewHolder{
    TextView textView_quest;
    TextView textView_solution;
    TextView textView_medicine;
}
