package com.lrsoft.boost_xplayer.ExchangeContent;

import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class ScannerContent extends Fragment {
    private MainActivity mainActivity;
    private MusicScanner musicScanner;
    private static List<MenuListItem> menuItemList = new ArrayList<>();
    public static MenuItemAdapter menuItemAdapter = null;
    public ScannerContent(MainActivity callback){
        mainActivity = callback;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exchange_music_list, container, false);
        ListView menuList = view.findViewById(R.id.musiclist_showList);
        if(menuItemAdapter == null){
            menuItemAdapter = new MenuItemAdapter(mainActivity,R.layout.music_list,menuItemList);
        }
        menuList.setAdapter(menuItemAdapter);

        LinearLayout searchPlane =view.findViewById(R.id.musiclist_searchPlane);
        LinearLayout scannPlane = view.findViewById(R.id.musiclist_scanPlane);
        searchPlane.setVisibility(LinearLayout.VISIBLE);
        scannPlane.setVisibility(LinearLayout.VISIBLE);
        Button btnSearch = view.findViewById(R.id.musiclist_search_submitbtn);
        Button btnScann = view.findViewById(R.id.musiclist_scann_submitbtn);
        final TextView searchEdit = view.findViewById(R.id.musiclist_search_textbox);
        final TextView scannSize = view.findViewById(R.id.musiclist_scann_sizebox);
        final TextView scannRoot = view.findViewById(R.id.musiclist_scann_rootbox);
        btnScann.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicScanner = new MusicScanner(scannRoot.getText().toString(),Float.parseFloat(scannSize.getText().toString()));
                musicScanner.setAdapter(menuItemAdapter);
                musicScanner.start();
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<MenuListItem> item = new ArrayList<>();
                for(MenuListItem e:menuItemList){
                    if(e.getTabName().contains(searchEdit.getText().toString())){
                        item.add(e);
                    }
                }
                menuItemAdapter.clear();
                menuItemAdapter.addAll(item);
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
}
