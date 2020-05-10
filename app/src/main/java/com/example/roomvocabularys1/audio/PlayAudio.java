package com.example.roomvocabularys1.audio;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.roomvocabularys1.query.QueryAudio;

public class PlayAudio{
    private MediaPlayer mediaPlayer;
    private Context context;
    private String geturl;
    public PlayAudio(Context context) {
        mediaPlayer = new MediaPlayer();
        this.context=context;
    }

    public void play(String word){
        try{
            word="https://dictionary.cambridge.org"+word;
            Log.d("word",word);
            //Uri uri = Uri.parse(word);
            //mediaPlayer.setDataSource(context,uri);
            if(mediaPlayer!=null){
                Log.d("reset","reset");
                mediaPlayer.reset();
            }
            mediaPlayer.setDataSource(word);

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });
            mediaPlayer.prepareAsync();
            //mediaPlayer.start();
        }
        catch (IllegalStateException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            Log.e("PlayAudio",e.toString());
            Toast.makeText(context, "ERROR: 撥放器錯誤", Toast.LENGTH_SHORT).show();
        }
    }

    public void stop(){
        mediaPlayer.release();
    }

}
