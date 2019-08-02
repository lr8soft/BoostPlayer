package com.lrsoft.boost_xplayer;

import android.content.Context;
import android.util.JsonReader;
import android.util.Log;

import com.lrsoft.boost_xplayer.ExchangeContent.MusicListAdapter.MenuListItem;
import com.lrsoft.boost_xplayer.MusicContent.MusicItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class StorageManager {
    private static JSONObject FavourInit(Context context){
        FileOutputStream os = null;
        BufferedWriter writer = null;
        JSONObject jsonObject = null;
        try{
            jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            jsonObject.put("favMusicList", jsonArray);
            os = context.openFileOutput("MusicConfig.cfg",Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(os));
            writer.write(jsonObject.toString());
            writer.close();
        }catch (IOException |JSONException exp){
            Log.e("FavourInit: ",exp.getLocalizedMessage());
        }
        return jsonObject;
    }
    private static JSONObject readFavourJsonObject(Context context) {
        FileInputStream in = null;
        BufferedReader reader = null;
        JSONObject jsonObject = null;
        try
        {
            in = context.openFileInput("MusicConfig.cfg");
            reader = new BufferedReader(new InputStreamReader(in));
            String temp = "";
            String buffer = "";
            while ((temp = reader.readLine()) != null) {
                buffer += temp;
            }
            Log.i("readFavourJsonObject: ", buffer);
            reader.close();
            jsonObject = new JSONObject(buffer);
        }catch (Exception expt){
            jsonObject = FavourInit(context);
        }
        return jsonObject;
    }
    private static boolean saveDataChange(Context context,JSONObject jsonObject){
        FileOutputStream os = null;
        BufferedWriter writer = null;
        try{
            os = context.openFileOutput("MusicConfig.cfg",Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(os));
            writer.write(jsonObject.toString());
            writer.close();
            return true;
        }catch (IOException exp){
            Log.e("saveDataChange: ",exp.getLocalizedMessage());
            return false;
        }
    }
    public static List<MenuListItem> getFavourList(Context context){
        List<MenuListItem> list = null;
        try{
            JSONObject jsonObject = readFavourJsonObject(context);
            JSONArray jsonArray = jsonObject.optJSONArray("favMusicList");
            list = new ArrayList<>();
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject tempObj = jsonArray.getJSONObject(i);
                String musicPath = tempObj.optString("musicPath");
                String musicName = tempObj.optString("musicName");
                String musicAuthor = tempObj.optString("musicAuthor");
                String musicAlbum = tempObj.optString("musicAlbum");
                MusicItem musicItem = new MusicItem();
                MenuListItem menuListItem = new MenuListItem();
                musicItem.setMusicName(musicName);
                musicItem.setAuthorName(musicAuthor);
                musicItem.setAlbumName(musicAlbum);
                musicItem.setMusicPath(musicPath);

                menuListItem.setMusicItem(musicItem);
                menuListItem.setTabName(musicName);
                menuListItem.setTabIcon(R.drawable.now_playing_default_image);
                list.add(menuListItem);
            }
        }catch (Exception expt){
            Log.e("getFavourList: ", expt.getMessage());
        }
        return list;
    }
    public static boolean  addToFavourList(Context context,MusicItem musicItem){
        if(!checkIsExisted(context, musicItem)){
            try{
                JSONObject jsonObject = readFavourJsonObject(context);
                JSONArray jsonArray = jsonObject.optJSONArray("favMusicList");
                JSONObject object = new JSONObject();
                object.put("musicAlbum",musicItem.getAlbumName());
                object.put("musicAuthor",musicItem.getAuthorName());
                object.put("musicName",musicItem.getMusicName());
                object.put("musicPath",musicItem.getMusicPath());
                jsonArray.put(object);
                if(saveDataChange(context, jsonObject)) {
                    return true;
                }
            }catch (Exception expt){
                Log.e("addToFavourList: ", expt.getLocalizedMessage());
            }
        }
        return false;
    }
    public static boolean deleteFromFavourite(Context context,MusicItem musicItem){
        try{
            JSONObject jsonObject = readFavourJsonObject(context);
            JSONArray jsonArray = jsonObject.optJSONArray("favMusicList");
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject tempObj = jsonArray.getJSONObject(i);
                if(tempObj.optString("musicPath").equals(musicItem.getMusicPath())){
                    jsonArray.remove(i);
                    if(saveDataChange(context,jsonObject)){
                        return true;
                    }
                    break;
                }
            }
        }catch (Exception expt){
            Log.i("checkIsExisted: ", expt.getLocalizedMessage());
        }
        return false;
    }
    public static boolean checkIsExisted(Context context,MusicItem musicItem){
        try{
            JSONObject jsonObject = readFavourJsonObject(context);
            JSONArray jsonArray = jsonObject.optJSONArray("favMusicList");
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject tempObj = jsonArray.getJSONObject(i);
                if(tempObj.optString("musicPath").equals(musicItem.getMusicPath())){
                    return true;
                }
            }
        }catch (Exception expt){
            Log.i("checkIsExisted: ", expt.getLocalizedMessage());
        }
        return false;
    }
}
