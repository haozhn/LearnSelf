package com.example.hao.learnself.date_2019_12_25;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hao.learnself.R;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.hao.learnself.Util.TAG;

public class ListViewAdapter extends BaseAdapter {
    private Context context;
    private List<String> data;

    public ListViewAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            Log.e(TAG, "reuse view holder " + holder.id);
        }
        final String title = (String) getItem(position);
        holder.title.setText(title);
        return convertView;
    }

    public static class ViewHolder {
        static AtomicInteger count = new AtomicInteger(0);
        TextView title;
        int id;

        ViewHolder(View view) {
            id = count.addAndGet(1);
            Log.e(TAG, "create view holder " + id);
            title = view.findViewById(R.id.item_text);
        }
    }
}
