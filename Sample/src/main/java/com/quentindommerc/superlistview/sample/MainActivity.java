package com.quentindommerc.superlistview.sample;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.quentindommerc.superlistview.OnMoreListener;
import com.quentindommerc.superlistview.SuperListview;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener, OnMoreListener {

    private SuperListview mList;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Empty list view demo, just pull to add more items
        ArrayList<String> lst = new ArrayList<String>();
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, lst);


        // This is what you're looking for
        mList = (SuperListview)findViewById(R.id.list);
        mList.setAdapter(mAdapter);

        // Setting the refresh listener will enable the refresh progressbar
        mList.setRefreshListener(this);

        // I want to get loadMore triggered if I see the last item (1)
        mList.setupMoreListener(this, 1);
    }

    @Override
    public void onRefresh() {
        Toast.makeText(this, "Refresh", Toast.LENGTH_LONG).show();

        // demo purpose, adding to the top so you can see it
        mAdapter.insert("New stuff", 0);
    }

    @Override
    public void loadMore() {
        Toast.makeText(this, "More", Toast.LENGTH_LONG).show();

        //demo purpose, adding to the bottom
        mAdapter.add("More asked, more served");
    }
}
