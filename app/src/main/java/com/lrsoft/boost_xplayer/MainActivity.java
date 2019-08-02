package com.lrsoft.boost_xplayer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.Image;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lrsoft.boost_xplayer.MusicContent.MusicScanner;

public class MainActivity extends AppCompatActivity {
    public static MainActivity tempThis;
    public AlertDialog.Builder dialog;

    public static ImageButton btnList = null;
    public static ImageButton btnNowplay = null;
    public static ImageButton btnSearch = null;
    public static ImageButton btnSetting = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog = new AlertDialog.Builder(this);
        tempThis = this;
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        FunctionButtonContent content = new FunctionButtonContent(this);
        btnList = findViewById(R.id.homepage_detail_playlist);
        btnNowplay = findViewById(R.id.homepage_detail_nowplay);
        btnSearch = findViewById(R.id.homepage_detail_search);
        btnSetting = findViewById(R.id.homepage_detail_setting);
        btnList.setOnClickListener(content);
        btnNowplay.setOnClickListener(content);
        btnSearch.setOnClickListener(content);
        btnSetting.setOnClickListener(content);
        btnList.callOnClick();
    }
    public void setTitle(String title){
        TextView texTitle = findViewById(R.id.homepage_title_titleTextView);
        texTitle.setText(title);
    }
}
