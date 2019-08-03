package com.lrsoft.boost_xplayer.ExchangeContent;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.lrsoft.boost_xplayer.MainActivity;
import com.lrsoft.boost_xplayer.MusicContent.MusicItem;
import com.lrsoft.boost_xplayer.PlayerService;
import com.lrsoft.boost_xplayer.R;
import com.lrsoft.boost_xplayer.StorageManager;

import java.util.Timer;
import java.util.TimerTask;

public class NowPlayingContent extends Fragment {
    private MainActivity mainActivity;
    public static ImageButton btnPauseResume = null;
    public static ImageButton btnPreviousMusic = null;
    public static ImageButton btnNextMusic = null;
    public static ImageButton btnAddToFav = null;
    private static SeekBar seekBar;
    public NowPlayingContent(MainActivity callback){
        mainActivity = callback;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exchange_now_playing, container, false);
        btnPauseResume = view.findViewById(R.id.nowplaying_playing_pause_resume);
        btnPreviousMusic = view.findViewById(R.id.nowplaying_playing_previous);
        btnNextMusic = view.findViewById(R.id.nowplaying_playing_next);
        btnAddToFav = view.findViewById(R.id.nowplaying_add_to_fav);
        ImageView musicImage = view.findViewById(R.id.nowplaying_music_image);
        musicImage.setImageResource(R.drawable.now_playing_default_image);
        btnAddToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicItem item = PlayerService.getMediaNowPlaying();
                if(item!=null){
                    if(StorageManager.addToFavourList(mainActivity, item)){
                        Toast.makeText(mainActivity,"成功添加到我的收藏！",Toast.LENGTH_SHORT).show();
                    }else{
                        if(StorageManager.checkIsExisted(mainActivity,item)){
                            StorageManager.deleteFromFavourite(mainActivity,item);
                            Toast.makeText(mainActivity,"已经从我的收藏中删除！",Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        });
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
        ImageButton btnSingle = view.findViewById(R.id.nowplaying_recyle_single);
        ImageButton btnList = view.findViewById(R.id.nowplaying_recyle_order);
        ImageButton btnRandom = view.findViewById(R.id.nowplaying_recyle_random);
        btnSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayerService.setPlayingRecycleType(PlayerService.PlayingStyle.SingleRecycle);
                Toast.makeText(mainActivity, "开启单曲循环模式",Toast.LENGTH_SHORT).show();
            }
        });
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayerService.setPlayingRecycleType(PlayerService.PlayingStyle.ListRecycle);
                Toast.makeText(mainActivity, "开启列表循环模式",Toast.LENGTH_SHORT).show();
            }
        });
        btnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayerService.setPlayingRecycleType(PlayerService.PlayingStyle.RandomPlay);
                Toast.makeText(mainActivity, "开启随机播放模式",Toast.LENGTH_SHORT).show();
            }
        });
        seekBar = view.findViewById(R.id.nowplaying_music_progressbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(PlayerService.getMediaPlayer()!=null && seekBar.isEnabled()){
                    try{
                        PlayerService.getMediaPlayer().seekTo(seekBar.getProgress());
                    }catch (IllegalStateException expt){ }
                }
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) { }
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
        if(PlayerService.getMediaNowPlaying()!=null){
            TextView title = mainActivity.findViewById(R.id.nowplaying_music_name);
            TextView info = mainActivity.findViewById(R.id.nowplaying_detail_info);
            title.setText(PlayerService.getMediaNowPlaying().getMusicName());
            info.setText(PlayerService.getMediaNowPlaying().getAuthorName()+" "+PlayerService.getMediaNowPlaying().getAlbumName());
            SeekBar seekBar = mainActivity.findViewById(R.id.nowplaying_music_progressbar);
            seekBar.setMax(PlayerService.getMediaPlayer().getDuration());
        }
        Timer timer = new Timer();
        timer.schedule(timertask, 0, 1000);
    }
    private TimerTask timertask = new TimerTask(){
        public void run() {
            try{
                if(PlayerService.getIsPlaying()){
                    seekBar.setProgress(PlayerService.getMediaPlayer().getCurrentPosition());
                }
            }catch (Exception expt){ }
        }
    };
}
