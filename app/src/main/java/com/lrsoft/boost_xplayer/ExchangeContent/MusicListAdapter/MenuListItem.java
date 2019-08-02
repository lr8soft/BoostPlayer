package com.lrsoft.boost_xplayer.ExchangeContent.MusicListAdapter;

import android.graphics.Bitmap;

import com.lrsoft.boost_xplayer.R;

public class MenuListItem {
    private String tabName = "";
    private int tabIcon = R.drawable.usr_list;//default setting
    private Bitmap bitmap = null;
    public enum ItemType {
        MenuItem, MusicItem
    };
    private ItemType itemType;
    public void setTabName(String name){
        tabName = name;
    }
    public void setTabIcon(int resourceID){
        tabIcon = resourceID;
    }
    public void  setBitmap(Bitmap t){
        bitmap = t;
    }
    public void setItemType(ItemType type){
        itemType = type;
    }
    public String getTabName(){
        return tabName;
    }
    public int getTabIcon(){
        return tabIcon;
    }
    public Bitmap getBitmap(){
        return bitmap;
    }
    public ItemType getItemType(){
        return itemType;
    }
}
