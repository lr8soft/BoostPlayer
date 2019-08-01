package com.lrsoft.boost_xplayer.ExchangeContent;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.lrsoft.boost_xplayer.MainActivity;
import com.lrsoft.boost_xplayer.MusicContent.MusicItem;
import com.lrsoft.boost_xplayer.PlayerService;
import com.lrsoft.boost_xplayer.R;
public class NowPlayingContent extends Fragment {
    private MainActivity mainActivity;
    public NowPlayingContent(MainActivity callback){
        mainActivity = callback;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exchange_now_playing, container, false);
        final ImageButton btnPauseResume = view.findViewById(R.id.nowplaying_playing_pause_resume);
        ImageButton btnPreviousMusic = view.findViewById(R.id.nowplaying_playing_previous);
        ImageButton btnNextMusic = view.findViewById(R.id.nowplaying_playing_next);
        ImageView musicImage = view.findViewById(R.id.nowplaying_music_image);
        musicImage.setImageResource(R.drawable.now_playing_default_image);
        btnPauseResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainActivity, PlayerService.class);
                if(PlayerService.getIsPlaying()){
                    btnPauseResume.setBackgroundResource(R.drawable.nowplaying_resume);
                    intent.putExtra("PlayerAction","pause");
                }else{
                    btnPauseResume.setBackgroundResource(R.drawable.nowplaying_pause);
                    intent.putExtra("PlayerAction","resume");
                }
                mainActivity.startService(intent);
            }
        });
        btnPreviousMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainActivity, PlayerService.class);
                intent.putExtra("PlayerAction","previous");
                mainActivity.startService(intent);
            }
        });
        btnNextMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainActivity, PlayerService.class);
                intent.putExtra("PlayerAction","next");
                mainActivity.startService(intent);
            }
        });
        return  view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ImageButton btnPauseResume = mainActivity.findViewById(R.id.nowplaying_playing_pause_resume);
        if(PlayerService.getIsPlaying()){
            btnPauseResume.setBackgroundResource(R.drawable.nowplaying_pause);
        }else{
            btnPauseResume.setBackgroundResource(R.drawable.nowplaying_resume);
        }
    }

}
