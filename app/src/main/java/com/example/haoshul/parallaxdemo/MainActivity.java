package com.example.haoshul.parallaxdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.listView)
    ParallaxListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        View headerView = View.inflate(this,R.layout.layout_header,null);
        ImageView imageView = (ImageView) headerView.findViewById(R.id.parallaxImage);
        listView.addHeaderView(headerView);
        listView.setOverScrollMode(AbsListView.OVER_SCROLL_NEVER);

        listView.setImageView(imageView);
        listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Constant.name));
    }
}
