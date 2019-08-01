package com.lrsoft.boost_xplayer.MusicContent;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.lrsoft.boost_xplayer.MainActivity;

import java.io.File;
import java.io.IOException;

public class MusicHelper {
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
            File file = new File(Environment.getExternalStorageDirectory(),extern_path);
            try{
                if(file.exists()){
                    mediaPlayer.setDataSource(file.getPath());
                    mediaPlayer.prepare();
                }else{
                    throw new NullPointerException("Can't open music file " + extern_path +"!");
                }
            }catch (IOException |NullPointerException ioexpt){
                Log.e("loadMusicFromPath: ",ioexpt.getMessage());
            }
        }
        return mediaPlayer;
    }
}

