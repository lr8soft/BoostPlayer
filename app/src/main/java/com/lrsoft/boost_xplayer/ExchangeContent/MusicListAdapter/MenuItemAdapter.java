package com.lrsoft.boost_xplayer.ExchangeContent.MusicListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lrsoft.boost_xplayer.R;

import java.util.List;

public class MenuItemAdapter extends ArrayAdapter<MenuListItem> {
    private int resid;
    public enum ListType{
        MenuItem, MusicItem
    };
    private ListType adapterType = ListType.MusicItem;
    public MenuItemAdapter(Context context, int rid, List<MenuListItem> list){
        super(context, rid, list);
        resid = rid;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(resid, parent, false);
        ImageView image = view.findViewById(R.id.musiclist_item_image);
        TextView title = view.findViewById(R.id.musiclist_item_name);
        MenuListItem item = getItem(position);
        if(item.getBitmap()==null){
            image.setImageResource(item.getTabIcon());
        }else{
            image.setImageBitmap(item.getBitmap());
        }
        title.setText(item.getTabName());
        return view;
    }
    public ListType getAdapterType(){
        return adapterType;
    }
    public void setAdapterType(ListType type){
        adapterType = type;
    }
}
