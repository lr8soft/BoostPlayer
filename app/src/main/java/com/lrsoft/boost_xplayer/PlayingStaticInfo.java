package com.lrsoft.boost_xplayer;

import android.media.MediaPlayer;
import android.provider.MediaStore;

public class PlayingStaticInfo {
    public static MediaPlayer mainPlayer = null;
    public static void InfoRelease(){
        mainPlayer = null;
    }
}
