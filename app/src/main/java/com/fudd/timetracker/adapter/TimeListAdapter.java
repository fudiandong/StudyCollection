package com.fudd.timetracker.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fudd.main.R;

/**
 * Created by fudd-office on 2017-3-16 15:32.
 * Email: 5036175@qq.com
 * QQ: 5036175
 * Description:
 */

public class TimeListAdapter extends ArrayAdapter<Long> {

    public TimeListAdapter(Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.row_time,null);
        }
        TextView task_name = (TextView) view.findViewById(R.id.task_name);
        TextView task_time = (TextView) view.findViewById(R.id.task_time);

        String name = getContext().getResources().getString(R.string.task_name);
        task_name.setText(String.format(name,position+1));

        long time = getItem(position);
        task_time.setText(DateUtils.formatElapsedTime(time));

        return view;
    }
}
