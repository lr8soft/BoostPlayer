package com.lrsoft.boost_xplayer;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.lrsoft.boost_xplayer.ExchangeContent.MusicListContent;
import com.lrsoft.boost_xplayer.ExchangeContent.NowPlayingContent;
import com.lrsoft.boost_xplayer.ExchangeContent.SearchMusicContent;
import com.lrsoft.boost_xplayer.ExchangeContent.SettingContent;

public class FunctionButtonContent implements View.OnClickListener{
    private MainActivity callbackClass;
    private static MusicListContent musicListContent;
    private static NowPlayingContent nowPlayingContent;
    private static SearchMusicContent searchMusicContent;
    private static SettingContent settingContent;
    public FunctionButtonContent(MainActivity callbackAct){
        callbackClass = callbackAct;
    }
    private void onMusicListButtonClick(){
        FragmentManager fragmentManager = callbackClass.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(musicListContent==null){
            musicListContent = new MusicListContent(callbackClass);
        }
        transaction.replace(R.id.homepage_detail_content,musicListContent);
        transaction.commit();
    }
    private void onNowPlayBtnClick(){
        FragmentManager fragmentManager = callbackClass.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(nowPlayingContent==null){
            nowPlayingContent = new NowPlayingContent(callbackClass);
        }
        transaction.replace(R.id.homepage_detail_content,nowPlayingContent);
        transaction.commit();
    }
    private void onSearchButtonClick(){
        FragmentManager fragmentManager = callbackClass.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(searchMusicContent==null){
            searchMusicContent = new SearchMusicContent(callbackClass);
        }
        transaction.replace(R.id.homepage_detail_content,searchMusicContent);
        transaction.commit();
    }
    private void onSettingButtonClick(){
        FragmentManager fragmentManager = callbackClass.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(settingContent==null){
            settingContent = new SettingContent(callbackClass);
        }
        transaction.replace(R.id.homepage_detail_content,settingContent);
        transaction.commit();
    }
    @Override
    public void onClick(View view) {
        int btnId = view.getId();
        ImageButton btnList = callbackClass.findViewById(R.id.homepage_detail_playlist);
        ImageButton btnNowplay = callbackClass.findViewById(R.id.homepage_detail_nowplay);
        ImageButton btnSearch = callbackClass.findViewById(R.id.homepage_detail_search);
        ImageButton btnSetting = callbackClass.findViewById(R.id.homepage_detail_setting);
        try{
            switch (btnId){
                case R.id.homepage_detail_playlist:
                    btnList.setBackgroundResource(R.drawable.fav_btn_selected);
                    btnNowplay.setBackgroundResource(R.drawable.now_playing_btn);
                    btnSearch.setBackgroundResource(R.drawable.search_btn);
                    btnSetting.setBackgroundResource(R.drawable.setting_btn);
                    onMusicListButtonClick();
                    callbackClass.setTitle("播放列表");
                    break;
                case R.id.homepage_detail_nowplay:
                    btnList.setBackgroundResource(R.drawable.fav_btn);
                    btnNowplay.setBackgroundResource(R.drawable.now_playing_btn_select);
                    btnSearch.setBackgroundResource(R.drawable.search_btn);
                    btnSetting.setBackgroundResource(R.drawable.setting_btn);
                    onNowPlayBtnClick();
                    callbackClass.setTitle("正在播放");
                    break;
                case R.id.homepage_detail_search:
                    btnList.setBackgroundResource(R.drawable.fav_btn);
                    btnNowplay.setBackgroundResource(R.drawable.now_playing_btn);
                    btnSearch.setBackgroundResource(R.drawable.search_btn_selected);
                    btnSetting.setBackgroundResource(R.drawable.setting_btn);
                    onSearchButtonClick();
                    callbackClass.setTitle("搜索音乐");
                    break;
                case R.id.homepage_detail_setting:
                    btnList.setBackgroundResource(R.drawable.fav_btn);
                    btnNowplay.setBackgroundResource(R.drawable.now_playing_btn);
                    btnSearch.setBackgroundResource(R.drawable.search_btn);
                    btnSetting.setBackgroundResource(R.drawable.setting_btn_selected);
                    onSettingButtonClick();
                    callbackClass.setTitle("应用设置");
                    break;
            }
        }catch (Exception exp){
            Log.e("changeButtonView: ",exp.getLocalizedMessage());
        }
    }
}
