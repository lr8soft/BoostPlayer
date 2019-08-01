package com.lrsoft.boost_xplayer.ExchangeContent;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.lrsoft.boost_xplayer.MainActivity;
import com.lrsoft.boost_xplayer.MusicContent.MusicHelper;
import com.lrsoft.boost_xplayer.PlayingStaticInfo;
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
                if(PlayingStaticInfo.mainPlayer==null){
                    MusicHelper musicHelper = new MusicHelper(mainActivity);
                    PlayingStaticInfo.mainPlayer = musicHelper.loadMusicFromPath("eternal_moment.mp3");
                }
                if(PlayingStaticInfo.mainPlayer.isPlaying()){
                    PlayingStaticInfo.mainPlayer.pause();
                    btnPauseResume.setBackgroundResource(R.drawable.nowplaying_resume);
                }else{
                    PlayingStaticInfo.mainPlayer.start();
                    btnPauseResume.setBackgroundResource(R.drawable.nowplaying_pause);
                }
            }
        });
        return  view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(PlayingStaticInfo.mainPlayer!=null){
            ImageButton btnPauseResume = mainActivity.findViewById(R.id.nowplaying_playing_pause_resume);
            if(PlayingStaticInfo.mainPlayer.isPlaying()){
                btnPauseResume.setBackgroundResource(R.drawable.nowplaying_pause);
            }else{
                btnPauseResume.setBackgroundResource(R.drawable.nowplaying_resume);
            }
        }
    }

}
