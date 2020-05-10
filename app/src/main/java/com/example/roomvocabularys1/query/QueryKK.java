package com.example.roomvocabularys1.query;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QueryKK extends AsyncTask<Void, Void, Void> {
    //改用Livedata
    private MutableLiveData<String> vocabularykk;
    private String word;
    private String word_kk;
    public QueryKK(MutableLiveData<String> vocabularykk) {
        /*if (vocabularykk == null) {
            vocabularykk = new MutableLiveData<String>();
            Log.d("QueryKK","new MutableLiveData" );
        }*/
        this.vocabularykk=vocabularykk;
        this.vocabularykk.setValue("");
    }

    public MutableLiveData<String> getVocabularykk() {
        return vocabularykk;
    }

    public void setWord(String word) {
        this.word = word;
    }
    //static final ExecutorService QueryKKExecutor = Executors.newFixedThreadPool(2);
    //String  vocabularykk="";


    //public String get_vocabularykk(String vocabulary){
        /*if (vocabularykk == null) {
            vocabularykk = new MutableLiveData<String>();
        }*/
        //QueryKKExecutor.execute(()->{

       // });
        //vocabularykk.setValue(test1);
       // Log.d("123","123");
        //livekk.setValue(test1);
        //livekk.setValue("456");
        //return test1;
    //}

    @Override
    protected Void doInBackground(Void... voids) {
        try{
            String url="https://tw.dictionary.search.yahoo.com/search?p="+word;
            Document doc= Jsoup.connect(url).get();
            Elements element=doc.select("li[class=d-ib mr-10 va-top] > span");
            //String test1=element.first().toString()+"++";
            //vocabularykk.setValue("123");
            //livekk.setValue(test1);
            if(element.text().equals("")){
                word_kk="0";
            }
            else{
                word_kk=element.text();
            }
            //Log.d("QueryKK",word_kk);
        }catch(IOException e){
            Log.e("QueryKK",e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        vocabularykk.setValue(word_kk);
    }
}
