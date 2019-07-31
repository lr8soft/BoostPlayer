package com.lrsoft.boost_xplayer.ExchangeContent;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.lrsoft.boost_xplayer.MainActivity;
import com.lrsoft.boost_xplayer.R;
public class NowPlayingContent extends Fragment {
    private MainActivity mainActivity;
    private static MediaPlayer mediaPlayer = null;
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
        final ImageButton btnPauseResume = mainActivity.findViewById(R.id.nowplaying_playing_pause_resume);
        ImageButton btnPreviousMusic = mainActivity.findViewById(R.id.nowplaying_playing_previous);
        ImageButton btnNextMusic = mainActivity.findViewById(R.id.nowplaying_playing_next);
        ImageView musicImage = mainActivity.findViewById(R.id.nowplaying_music_image);
        musicImage.setImageResource(R.drawable.now_playing_default_image);

        btnPauseResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer==null){
                    mediaPlayer = MediaPlayer.create(mainActivity,R.raw.eternal_moment);
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    btnPauseResume.setBackgroundResource(R.drawable.nowplaying_resume);
                }else{
                    mediaPlayer.start();
                    btnPauseResume.setBackgroundResource(R.drawable.nowplaying_pause);
                }
            }
        });
    }
}
