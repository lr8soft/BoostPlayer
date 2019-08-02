package com.lrsoft.boost_xplayer.ExchangeContent.MusicListAdapter;

import android.graphics.Bitmap;

import com.lrsoft.boost_xplayer.MusicContent.MusicItem;
import com.lrsoft.boost_xplayer.R;

public class MenuListItem {
    private String tabName = "";
    private int tabIcon = R.drawable.usr_list;//default setting
    private Bitmap bitmap = null;
    private MusicItem musicItem = null;
    public void setTabName(String name){
        tabName = name;
    }
    public void setTabIcon(int resourceID){
        tabIcon = resourceID;
    }
    public void  setBitmap(Bitmap t){
        bitmap = t;
    }
    public void setMusicItem(MusicItem item) {musicItem = item;}
    public String getTabName(){
        return tabName;
    }
    public int getTabIcon(){
        return tabIcon;
    }
    public Bitmap getBitmap(){
        return bitmap;
    }
    public MusicItem getMusicItem(){return musicItem;}
}
