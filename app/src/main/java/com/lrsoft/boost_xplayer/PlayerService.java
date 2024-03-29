package com.lrsoft.boost_xplayer;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.lrsoft.boost_xplayer.ExchangeContent.MusicListAdapter.MenuListItem;
import com.lrsoft.boost_xplayer.MusicContent.MusicItem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayerService extends Service {
    public enum PlayingStyle{
        SingleRecycle, ListRecycle, RandomPlay
    };
    private static PlayingStyle playingRecycleType = PlayingStyle.ListRecycle;
    private static List<MusicItem> musicItemList = new ArrayList<>();
    private static MediaPlayer mediaPlayer = null;
    private static MusicItem mediaNowPlaying = null;
    private static boolean isPlaying = false;
    private static Thread updateThread = null;
    public static void setPlayingRecycleType(PlayingStyle style){ playingRecycleType = style; }
    public static boolean addAudio(MusicItem item){
        return musicItemList.add(item);
    }
    public static boolean addAllAudio(List<MusicItem> item){
        return musicItemList.addAll(item);
    }
    public static boolean setAudioPlay(MusicItem item){
        Release();
        final MusicHelper musicHelper = new MusicHelper(MainActivity.tempThis);
        mediaPlayer = null;
        mediaPlayer = musicHelper.loadMusicFromPath(item.getMusicPath());
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Log.i( "onCompletion: ", "finish "+mediaPlayer.getDuration());
                switch (playingRecycleType){
                    case RandomPlay:
                        Random random = new Random();
                        int nexti = random.nextInt(musicItemList.size());
                        setAudioPlay(musicItemList.get(nexti));
                        Resume();
                        setMusicInfo(musicItemList.get(nexti));
                        break;
                    case ListRecycle:
                        int nowIndex = musicItemList.indexOf(mediaNowPlaying);
                        int nextIndex;
                        if(nowIndex + 1 < musicItemList.size()){
                            nextIndex = nowIndex + 1;
                        }else{
                            nextIndex = 0;
                        }
                        setAudioPlay(musicItemList.get(nextIndex));
                        Resume();
                        setMusicInfo(musicItemList.get(nextIndex));
                        break;
                    case SingleRecycle:
                        mediaPlayer.start();
                        setMusicInfo(getMediaNowPlaying());
                        break;
                }
            }
        });
        if(mediaPlayer==null){
            return false;
        }else{
            if(musicItemList.indexOf(item)==-1){//unexisted
                musicItemList.add(item);
            }
            mediaNowPlaying = item;
            return true;
        }
    }
    public static void setMusicListByMenuListItem(List<MenuListItem> list){
        if(!list.isEmpty()){
            musicItemList.clear();
            for(MenuListItem item : list){
                musicItemList.add(item.getMusicItem());
            }
        }
    }
    public static void setMusicListByMusicItem(List<MusicItem> list){
        if(!list.isEmpty()){
            musicItemList.clear();
            musicItemList.addAll(list);
        }
    }
    public static MediaPlayer getMediaPlayer(){
        return mediaPlayer;
    }
    public static MusicItem getMediaNowPlaying(){
        return mediaNowPlaying;
    }
    public static boolean getIsPlaying(){
        return isPlaying;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String actionReceive = intent.getStringExtra("PlayerAction");
        if(actionReceive.equals("resume")){
            Resume();
            isPlaying = true;
        }else if(actionReceive.equals("pause")){
            Pause();
            isPlaying = false;
        }else if(actionReceive.equals("stop")){
            Stop();
            isPlaying = false;
            mediaNowPlaying = null;
        }else if(actionReceive.equals("release")){
            Release();
            isPlaying = false;
            mediaNowPlaying = null;
        }else if(actionReceive.equals("next")){
            if(!next()){
                MainActivity.tempThis.dialog.setTitle("切换失败");
                MainActivity.tempThis.dialog.setMessage("没有下一首了！");
                MainActivity.tempThis.dialog.show();
            }
        }else if(actionReceive.equals("previous")){
            if(!previous()){
                MainActivity.tempThis.dialog.setTitle("切换失败");
                MainActivity.tempThis.dialog.setMessage("没有上一首了！");
                MainActivity.tempThis.dialog.show();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public IBinder onBind(Intent intent) {
        return  null;
    }
    private static boolean next(){
        initIfFirstPlay();
        int nowIndex = musicItemList.indexOf(mediaNowPlaying);
        if(nowIndex!=-1){
            if(nowIndex+1<musicItemList.size()){
                setAudioPlay(musicItemList.get(nowIndex+1));
                setMusicInfo(musicItemList.get(nowIndex+1));
                Resume();
                return true;
            }
        }
        return false;
    }
    private static boolean previous(){
        initIfFirstPlay();
        int nowIndex = musicItemList.indexOf(mediaNowPlaying);
        if(nowIndex!=-1){
            if(nowIndex-1>=0){
                setAudioPlay(musicItemList.get(nowIndex-1));
                setMusicInfo(musicItemList.get(nowIndex-1));
                Resume();
                return true;
            }
        }
        return false;
    }
    private static void initIfFirstPlay(){
        if(mediaNowPlaying==null && !musicItemList.isEmpty()){
            mediaNowPlaying =musicItemList.get(0);
        }
    }
    private static void Resume(){
        if(mediaPlayer!=null){
            initIfFirstPlay();
            if(!mediaPlayer.isPlaying()){
                mediaPlayer.start();
            }
        }
    }
    private static void Pause(){
        if(mediaPlayer!=null){
            initIfFirstPlay();
            if(mediaPlayer.isPlaying()){
                mediaPlayer.pause();
            }
        }
    }
    private static void Stop(){
        if(mediaPlayer!=null){
            if(!mediaPlayer.isPlaying()){
                mediaPlayer.stop();
            }
        }
    }
    private static void Release(){
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    private static void setMusicInfo(MusicItem info){
        TextView title = MainActivity.tempThis.findViewById(R.id.nowplaying_music_name);
        TextView author = MainActivity.tempThis.findViewById(R.id.nowplaying_detail_info);
        ImageView image = MainActivity.tempThis.findViewById(R.id.nowplaying_music_image);
        SeekBar seekBar = MainActivity.tempThis.findViewById(R.id.nowplaying_music_progressbar);
        try{
            title.setText(info.getMusicName());
            author.setText(info.getAlbumName()+" "+info.getAuthorName());
            image.setImageResource(R.drawable.now_playing_default_image);
            seekBar.setProgress(0);
            seekBar.setMax(PlayerService.getMediaPlayer().getDuration());
        }catch (Exception expt){
            Log.i("setMusicInfo: ",expt.getMessage());
        }
    }
}
class MusicHelper {
    private MainActivity callBackObj;
    public MusicHelper(MainActivity obj){
        callBackObj = obj;
    }
    private boolean PremissionAdapter(){
        if(ContextCompat.checkSelfPermission(callBackObj, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(callBackObj,new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }else{
            return true;
        }
        return false;
    }
    public MediaPlayer loadMusicFromPath(String extern_path){
        MediaPlayer mediaPlayer = null;
        if(PremissionAdapter()){
            mediaPlayer = new MediaPlayer();
            File file = new File(extern_path);
            try{
                if(file.exists()){
                    mediaPlayer.setDataSource(file.getPath());
                    mediaPlayer.prepare();
                }else{
                    throw new NullPointerException("Can't open music file " + extern_path +"!");
                }
            }catch (IOException |NullPointerException expt){
                Log.e("loadMusicFromPath: ",expt.getMessage());
            }
        }
        return mediaPlayer;
    }
}
