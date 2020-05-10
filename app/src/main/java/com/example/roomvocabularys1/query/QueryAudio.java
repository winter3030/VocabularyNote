package com.example.roomvocabularys1.query;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.roomvocabularys1.audio.OnEventListener;
import com.example.roomvocabularys1.utils.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class QueryAudio extends AsyncTask<Void, Void, Void> {
    private String word;
    private String geturl;
    private OnEventListener<String> callback;
    private Context context;

    public QueryAudio(String word, OnEventListener callback, Context context) {
        this.word = word;
        this.callback=callback;
        this.context=context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Utils.show_toast(context,"Loading...");
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try{
            //String url="https://cdict.net/?q="+word;
            String url="https://dictionary.cambridge.org/zht/%E8%A9%9E%E5%85%B8/%E8%8B%B1%E8%AA%9E-%E6%BC%A2%E8%AA%9E-%E7%B9%81%E9%AB%94/"+word;
            Document doc= Jsoup.connect(url).get();
            Elements element=doc.select("source");
            //element.select("src");
            /*if(element!=null){
                geturl=element.get(2).attr("src");
            }

            Log.d("PlayAudio",geturl);*/
            for (Element element1 : element) {
                if(element1.attr("src").contains("mp3")){
                    geturl=element1.attr("src");
                    Log.d("PlayAudio",geturl);
                }
                //element1.text();
                //String test1=element1.attr("src").toString()+"++";
            }
            //String test1=element.first().toString()+"++";
            //Log.d("PlayAudio",test1);
        }catch(IOException e){
            Log.e("QueryKK",e.toString());
        }
        return null;
    }

    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(callback!=null){
            if(geturl!=null){
                callback.onSuccess(geturl);
            }
            else{
                callback.onFailure("找不到這個單字的發音");
            }
        }
    }
}
