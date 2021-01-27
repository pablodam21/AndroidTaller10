package com.pablo.u6t10sensorball;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;
import android.util.SparseIntArray;

public class SoundManager {
    private SoundPool mySoundPool;
    private final int MAX_SOUNDPOOL_STREAMS = 4;


    private SparseIntArray mySoundPoolList;
    private AudioManager audioManager;
    private Context context;
    private int soundsLoaded = 0;

    public void initSoundManager(Context theContext){
        context = theContext;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            mySoundPool = new SoundPool(MAX_SOUNDPOOL_STREAMS, AudioManager.STREAM_MUSIC,0);

        }else {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build();

            mySoundPool = new SoundPool.Builder().setAudioAttributes(audioAttributes)
                    .setMaxStreams(MAX_SOUNDPOOL_STREAMS).build();
        }

       mySoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
           @Override
           public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
               if (status == 0)soundsLoaded++;
           }
       });
        mySoundPoolList = new SparseIntArray();
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    public void addSound(int index, int SoundID){

        mySoundPoolList.put(index, mySoundPool.load(context, SoundID, 1));
    }

    public void playSound(int index){
        // index have a number ramdom and execute a ramdom sound
        index = ramdom();
        if(soundsLoaded == mySoundPoolList.size()){
            mySoundPool.play(mySoundPoolList.get(index),0.5f,0.5f,1,0,1f);
            Log.i("SOUND","playing "+index);
        }
    }
    //6.4 Generate a ramdom number
    private int ramdom(){
         int ramdomNumber = (int) ( Math.random() * 4 ) + 1;
         return ramdomNumber;
    }



    public void liberar(){

        mySoundPoolList.clear();
        mySoundPool.release();
        mySoundPool = null;

    }
}
