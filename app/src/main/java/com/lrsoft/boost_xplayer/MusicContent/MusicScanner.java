package com.lrsoft.boost_xplayer.MusicContent;

import android.util.Log;

import com.lrsoft.boost_xplayer.ExchangeContent.NowPlayingContent;
import com.lrsoft.boost_xplayer.PlayerService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MusicScanner extends Thread{
    private String scannerPath = "/sdcard";
    private float leastMB = 0.2f;
    private static List<MusicItem> musicList = new ArrayList<>();

    public MusicScanner(){}
    public MusicScanner(String startScannerPath, float scannerLeastSize){
        scannerPath = startScannerPath;
        scannerLeastSize = leastMB;
    }
    public List<MusicItem> getMusicList(){
        return musicList;
    }
    @Override
    public void run() {
        EasyScanner();
    }

    private void EasyScanner(){
        final File[] file = new File(scannerPath).listFiles();//设定扫描路径
        startScanner(file);
    }
    private void startScanner(final File[] file){
        for(int i=0 ; file!= null && i<file.length ;i++) {
            //判读是否文件以及文件后缀名
            if(file[i].isFile() && file[i].canRead() && file[i].length()/1024.0/1024.0 >= leastMB &&
                    (file[i].getName().endsWith("mp3") ||
                            file[i].getName().endsWith("ogg")||
                            file[i].getName().endsWith("wav")||
                            file[i].getName().endsWith("flac")||
                            file[i].getName().endsWith("aac")
                    )){
                MusicItem item = new MusicItem();
                item.setMusicPath(file[i].getPath());
                item.setMusicName(file[i].getName());
                musicList.add(item);
                Log.e("startScanner",item.getMusicPath()+" size:"+file[i].length()/1024.0/1024.0);
            }
            //如果是文件夹，递归扫描
            else if(file[i].isDirectory()) {
                final File[] newFileList = new File(file[i].getAbsolutePath()).listFiles();
                //startScanner(newFileList);
                //通过多线程来加速
				new Thread(new Runnable() {
                    public void run() {
                        startScanner(newFileList);
                    }
                }).start();
            }
        }

    }
}
