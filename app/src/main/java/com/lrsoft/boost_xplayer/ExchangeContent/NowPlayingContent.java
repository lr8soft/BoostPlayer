package com.lrsoft.boost_xplayer.ExchangeContent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.lrsoft.boost_xplayer.MainActivity;
import com.lrsoft.boost_xplayer.R;
public class NowPlayingContent extends Fragment {
    private MainActivity mainActivity;
    public NowPlayingContent(MainActivity callback){
        mainActivity = callback;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exchange_now_playing, container, false);
        return  view;
    }

    @Override
    public void onStart() {
        super.onStart();
        TextView titleView =mainActivity.findViewById(R.id.homepage_title_titleTextView);
    }
}
