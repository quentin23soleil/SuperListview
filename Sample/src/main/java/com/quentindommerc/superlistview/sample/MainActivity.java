package com.quentindommerc.superlistview.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.quentindommerc.superlistview.OnMoreListener;
import com.quentindommerc.superlistview.SuperGridview;
import com.quentindommerc.superlistview.SuperListview;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private SuperListview mList;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> lst = new ArrayList<String>();
        lst.add("List example");
        lst.add("Grid example");
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, lst);
        mList = (SuperListview)findViewById(R.id.list);
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent();
                switch (position) {
                    case 0:
                        i.setClass(MainActivity.this, ListSample.class);
                        break;
                    case 1:
                        i.setClass(MainActivity.this, GridSample.class);
                }
                startActivity(i);
            }
        });
    }
}
