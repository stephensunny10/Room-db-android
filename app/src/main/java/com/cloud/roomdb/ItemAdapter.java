package com.cloud.roomdb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;


public class ItemAdapter extends ArrayAdapter {
    ArrayList<HashMap>list;
    Context context;

    public ItemAdapter(@NonNull Context context, ArrayList list) {
        super(context,R.layout.item_list,list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup root;
        View rowView = inflater.inflate(R.layout.item_list, null, true);
        TextView tv1,tv2,tv3;
        tv1 = (TextView)rowView.findViewById(R.id.tv1);
        tv2 = (TextView)rowView.findViewById(R.id.tv2);
        tv3 = (TextView)rowView.findViewById(R.id.tv3);
        HashMap<String, String>map = list.get(position);
        tv1.setText(map.get("name"));
        tv2.setText(map.get("contact"));
        tv3.setText(map.get("address"));
        return rowView;
    }
}
