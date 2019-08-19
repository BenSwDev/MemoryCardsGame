package com.example.memorycardsgame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends BaseAdapter {

    private Context context;

    public ListViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return LeaderBoardActivity.Champions.size();
    }

    @Override
    public Object getItem(int position) {
        return LeaderBoardActivity.Champions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.champions_cell, parent,false);
        }

        User user = LeaderBoardActivity.Champions.get(position);

        TextView nameTv = convertView.findViewById(R.id.champions_name_tv);
        TextView scoreTv = convertView.findViewById(R.id.champions_score_tv);

        nameTv.setText(user.getmName());
        String score = Integer.toString(user.getmScore());
        scoreTv.setText(score);
        return convertView;
    }
}
