package com.example.roomvocabularys1.utils;

import android.content.Context;
import android.widget.Toast;

public class Utils {
    public static void show_toast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}
