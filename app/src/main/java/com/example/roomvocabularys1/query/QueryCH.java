package com.example.roomvocabularys1.query;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.roomvocabularys1.VocabularyViewModel;
import com.example.roomvocabularys1.audio.OnEventListener;
import com.example.roomvocabularys1.ui.Translation;
import com.example.roomvocabularys1.utils.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class QueryCH extends AsyncTask<Void, Void, Void> {
    private String word;
    //private StringBuffer wordch;
    private OnEventListener callback;
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private ArrayList<Translation> items;

    public QueryCH(String word, OnEventListener callback, Context context) {
        this.word=word;
        //items=new ArrayList<>();
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
            String url="https://tw.dictionary.search.yahoo.com/search?p="+word;
            Document doc= Jsoup.connect(url).get();
            //Elements element=doc.select("div[class=fz-16 fl-l dictionaryExplanation]");
            Elements webdoc=doc.select("div[id=web]");
            //選基本的翻譯
            Elements element=webdoc.select("div[class=compList mb-25 p-rel]").select("li");
            Elements type=webdoc.select("div[class=compTitle lh-25]");
            Elements ch=webdoc.select("div[class=compTextList ml-50]");
            //String test1=element.first().toString()+"++";
            //vocabularykk.setValue("123");
            //livekk.setValue(test1);
            /*if(element.text().equals("")){
                wordch="0";
            }
            else{
                //wordch=element.text().replace('；','\n');
                //String[] tokens = element.text().split("；");
                //items.addAll(Arrays.asList(tokens));
                wordch=element.text();
            }*/
            String c1=""+element.size();
            Log.d("QueryCH",c1);
            //wordch=new StringBuffer();
            items=new ArrayList<>();
            if(element.size()!=0){
                items.add(new Translation("選擇翻譯",false,"A"));
            }
            for(int i=0;i<element.size();i++){
                Elements divs = element.get(i).select("div");
                //String part_of_speech = divs.get(0).text();
                //String meaning = divs.get(1).text();
                //divs裡的元素
                if(divs.size()==2){
                    //詞性+翻譯
                    //wordch.append(divs.get(0).text()).append(divs.get(1).text()).append("\n");
                    items.add(new Translation(divs.get(0).text()+" "+divs.get(1).text(),false,"B"));
                }
                else{
                    //沒有詞性 EX:matter的名詞複數
                    //wordch.append(divs.get(0).text()).append("\n");
                    items.add(new Translation(divs.get(0).text(),false,"B"));
                }
            }
            if(type.size()!=0){
                items.add(new Translation("更多翻譯",false,"A"));
            }
            for(int i=0;i<type.size();i++){
                //Elements divs = type.get(i).select("span[class=pos_button fz-14]");
                //String n1=divs.text();
                //Log.d("QueryCH",n1);
                Elements divs1 = ch.get(i).select("li");
                for(int j=0;j<divs1.size();j++){
                    items.add(new Translation(type.get(i).select("span[class=pos_button fz-14]").text()+" "+divs1.get(j).select("span[class=d-i fz-14 lh-20]").text(),false,"B"));
                }
            }
            Elements moretitle=webdoc.select("div[class=compTitle mt-24 insidePage]");
            Elements morech=webdoc.select("div[class=compTextList mt-5 insidePage]");
            if(moretitle.size()!=0){
                items.add(new Translation("相關翻譯",false,"A"));
            }
            for(int i=0;i<moretitle.size();i++){
                items.add(new Translation(moretitle.get(i).select("a").text(),false,"A"));
                Elements divs1 = morech.get(i).select("li");
                for(int j=0;j<divs1.size();j++){
                    items.add(new Translation(divs1.get(j).select("div[class=pos_button fz-14]").text()+" "+divs1.get(j).select("span[class=fz-14 d-i ml-1 va-mid]").text(),false,"b"));
                }
            }
            //Log.d("QueryCH",wordch.toString());
        }catch(IOException e){
            Log.e("QueryCH",e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //vocabularych.setValue(wordch);
        if(callback!=null){
            if(items.size()!=0){
                //Log.d("QueryCH","onSuccess");
                callback.onSuccess(items);
            }
            else{
                callback.onFailure(items);
                //vocabularych.setValue(wordch);
            }
        }
    }
}
