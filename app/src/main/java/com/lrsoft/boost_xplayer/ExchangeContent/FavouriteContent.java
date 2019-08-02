package com.lrsoft.boost_xplayer.ExchangeContent;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.lrsoft.boost_xplayer.ExchangeContent.MusicListAdapter.MenuItemAdapter;
import com.lrsoft.boost_xplayer.ExchangeContent.MusicListAdapter.MenuListItem;
import com.lrsoft.boost_xplayer.MainActivity;
import com.lrsoft.boost_xplayer.MusicContent.MusicScanner;
import com.lrsoft.boost_xplayer.PlayerService;
import com.lrsoft.boost_xplayer.R;
import com.lrsoft.boost_xplayer.StorageManager;

import java.util.ArrayList;
import java.util.List;

public class FavouriteContent extends Fragment {
    private MainActivity mainActivity;
    private static List<MenuListItem> menuItemList = new ArrayList<>();
    private static List<MenuListItem> searchItemList = new ArrayList<>();
    public static MenuItemAdapter menuItemAdapter = null;
    public static MenuItemAdapter menumSearchAdapter = null;
    public FavouriteContent(MainActivity callback){
        mainActivity = callback;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exchange_music_list, container, false);
        final ListView menuList = view.findViewById(R.id.musiclist_showList);
        if(menuItemAdapter == null){
            menuItemAdapter = new MenuItemAdapter(mainActivity,R.layout.music_list,menuItemList);

        }
        if(menumSearchAdapter == null) {
            menumSearchAdapter = new MenuItemAdapter(mainActivity, R.layout.music_list, searchItemList);
        }
        menuList.setAdapter(menuItemAdapter);
        LinearLayout searchPlane =view.findViewById(R.id.musiclist_searchPlane);
        final TextView searchEdit = view.findViewById(R.id.musiclist_search_textbox);
        Button btnSearch = view.findViewById(R.id.musiclist_search_submitbtn);
        searchPlane.setVisibility(LinearLayout.VISIBLE);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<MenuListItem> item = new ArrayList<>();
                String keyword = searchEdit.getText().toString();
                if(!keyword.isEmpty()){
                    for(MenuListItem e:menuItemList){
                        if(e.getTabName().contains(keyword)){
                            item.add(e);
                        }
                    }
                    menuList.setAdapter(menumSearchAdapter);
                    menumSearchAdapter.clear();
                    menumSearchAdapter.addAll(item);
                    menumSearchAdapter.notifyDataSetChanged();

                }else{
                    menuList.setAdapter(menuItemAdapter);
                }
            }
        });
        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MenuListItem item = menuItemAdapter.getItem(i);
                MainActivity.btnNowplay.callOnClick();
                PlayerService.setAudioPlay(item.getMusicItem());
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(menuItemAdapter != null) {
            List<MenuListItem> item = StorageManager.getFavourList(mainActivity);
            if(item!=null){
                menuItemAdapter.clear();
                menuItemAdapter.addAll(item);
                menuItemAdapter.notifyDataSetChanged();
            }
        }
    }
}
