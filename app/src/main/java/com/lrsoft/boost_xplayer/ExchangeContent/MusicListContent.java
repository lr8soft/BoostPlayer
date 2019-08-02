package com.lrsoft.boost_xplayer.ExchangeContent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.lrsoft.boost_xplayer.ExchangeContent.MusicListAdapter.MenuItemAdapter;
import com.lrsoft.boost_xplayer.ExchangeContent.MusicListAdapter.MenuListItem;
import com.lrsoft.boost_xplayer.MainActivity;
import com.lrsoft.boost_xplayer.R;

import java.util.ArrayList;
import java.util.List;

public class MusicListContent extends Fragment {
    private MainActivity mainActivity;
    public static MenuItemAdapter menuItemAdapter = null;
    private static List<MenuListItem> menuItemList = new ArrayList<>();
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
        return  view;
    }
}
