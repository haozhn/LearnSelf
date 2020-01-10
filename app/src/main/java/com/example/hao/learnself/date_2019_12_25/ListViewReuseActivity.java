package com.example.hao.learnself.date_2019_12_25;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.hao.learnself.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.hao.learnself.Util.TAG;

public class ListViewReuseActivity extends Activity {
    private ListView listView;
    private ListViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_reuse);

        listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter = new ListViewAdapter(this));
        adapter.setData(mockData());

        listView.setRecyclerListener(new AbsListView.RecyclerListener() {
            @Override
            public void onMovedToScrapHeap(View view) {
                ListViewAdapter.ViewHolder holder = (ListViewAdapter.ViewHolder) view.getTag();
                Log.e(TAG, "setRecyclerListener = " + holder.id);
            }
        });
    }

    private List<String> mockData() {
        List<String> data = new ArrayList<>(50);
        for (int i = 0; i < 50; i++) {
            data.add("item" + (i + 1));
        }
        return data;
    }
}
