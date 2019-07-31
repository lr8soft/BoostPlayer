package com.lrsoft.boost_xplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        ImageButton btnList = findViewById(R.id.homepage_detail_playlist);
        ImageButton btnNowplay = findViewById(R.id.homepage_detail_nowplay);
        ImageButton btnSearch = findViewById(R.id.homepage_detail_search);
        ImageButton btnSetting = findViewById(R.id.homepage_detail_setting);
        FunctionButtonContent content = new FunctionButtonContent(this);
        btnList.setOnClickListener(content);
        btnNowplay.setOnClickListener(content);
        btnSearch.setOnClickListener(content);
        btnSetting.setOnClickListener(content);
    }
    public void setTitle(String title){
        TextView texTitle = findViewById(R.id.homepage_title_titleTextView);
        texTitle.setText(title);
    }
}
