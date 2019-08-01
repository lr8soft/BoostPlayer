package com.lrsoft.boost_xplayer.MusicContent;

import android.graphics.Bitmap;

public class MusicItem {
    private String MusicName = "";
    private String MusicPath = "";
    private String AuthorName = "";
    private String AlbumName = "";
    private Bitmap AlbumImage = null;
    public void setMusicName(String name){
        MusicName = name;
    }
    public void setMusicPath(String path){
        MusicPath = path;
    }
    public void setAuthorName(String author){
        AuthorName = author;
    }
    public void setAlbumName(String name){
        AlbumName = name;
    }
    public void setAlbumImage(Bitmap bitmap){
        AlbumImage = bitmap;
    }
    public String getMusicName(){
        return MusicName;
    }
    public String getMusicPath(){
        return  MusicPath;
    }
    public String getAuthorName(){
        return AuthorName;
    }
    public String getAlbumName(){
        return AlbumName;
    }
    public Bitmap getAlbumImage(){
        return  AlbumImage;
    }
}
