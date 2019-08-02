package com.lrsoft.boost_xplayer.ExchangeContent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.lrsoft.boost_xplayer.ExchangeContent.MusicListAdapter.MenuItemAdapter;
import com.lrsoft.boost_xplayer.ExchangeContent.MusicListAdapter.MenuListItem;
import com.lrsoft.boost_xplayer.MainActivity;
import com.lrsoft.boost_xplayer.PlayerService;
import com.lrsoft.boost_xplayer.R;

import java.util.ArrayList;
import java.util.List;

public class MusicListContent extends Fragment {
    private MainActivity mainActivity;
    public static MenuItemAdapter menuItemAdapter = null;
    private static List<MenuListItem> menuItemList = new ArrayList<>();
    private static ScannerContent scannerContent = null;
    public MusicListContent(MainActivity callback){
        mainActivity = callback;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exchange_music_list, container, false);
        ListView menuList = view.findViewById(R.id.musiclist_showList);
        if(menuItemAdapter == null){
            menuItemAdapter = new MenuItemAdapter(mainActivity,R.layout.music_list,menuItemList);
        }
        menuList.setAdapter(menuItemAdapter);
        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MenuListItem item = menuItemAdapter.getItem(i);
                if(menuItemAdapter.getAdapterType()==MenuItemAdapter.ListType.MusicItem){
                    MainActivity.btnNowplay.callOnClick();//exchange first
                    PlayerService.setAudioPlay(item.getMusicItem());
                }else{
                    if(item.getTabName().equals("扫描音乐")){
                        FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        if(scannerContent==null){
                            scannerContent = new ScannerContent(mainActivity);
                        }
                        transaction.replace(R.id.homepage_detail_content,scannerContent);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }else if(item.getTabName().equals("我的收藏")){

                    }
                }
            }
        });
        setDefaultMenuList();
        return  view;
    }
    private void setDefaultMenuList(){
        menuItemAdapter.setAdapterType(MenuItemAdapter.ListType.MenuItem);
        menuItemAdapter.clear();
        MenuListItem itemScann = new MenuListItem();
        itemScann.setTabIcon(R.drawable.usr_scan);
        itemScann.setTabName("扫描音乐");
        menuItemAdapter.add(itemScann);
        MenuListItem itemFav = new MenuListItem();
        itemFav.setTabIcon(R.drawable.usr_favo);
        itemFav.setTabName("我的收藏");
        menuItemAdapter.add(itemFav);
        menuItemAdapter.notifyDataSetChanged();
        /**
         * 加载用户创建的list
         */
    }
}
