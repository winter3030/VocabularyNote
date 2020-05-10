package com.example.roomvocabularys1.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomvocabularys1.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class TranslationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_TITLE=1;
    private static final int TYPE_CONTENT=2;
    private Context context;
    private ArrayList<Translation> translationlist;
    private Map<Integer, String> map;
    private StringBuffer SelectedItems;

    public TranslationAdapter(Context context,ArrayList<Translation> translationlist) {
        this.context=context;
        this.translationlist = translationlist;
        this.map = new HashMap<>();
    }

    //建立不同ViewHolder
    class TitleViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        public TitleViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }
    }
    class ContentViewHolder extends RecyclerView.ViewHolder{
        private CheckedTextView checkedTextView;
        public ContentViewHolder(@NonNull View itemView) {
            super(itemView);
            checkedTextView=itemView.findViewById(R.id.checkedTextView);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //return null;
        View view;
        if(viewType==TYPE_TITLE){
            view = LayoutInflater.from(context).inflate(R.layout.recycleview_dialog_item_a, parent, false);
            return new TitleViewHolder(view);
        }
        else{
            view = LayoutInflater.from(context).inflate(R.layout.recycleview_dialog_item_b, parent, false);
            return new ContentViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Translation translation=translationlist.get(position);
        if(getItemViewType(position)==TYPE_TITLE){
            ((TitleViewHolder)holder).title.setText(translation.getChinese());
        }
        else{
            ((ContentViewHolder)holder).checkedTextView.setText(translation.getChinese());
            if(translation.getIscheck()){
                ((ContentViewHolder)holder).checkedTextView.setChecked(true);
            }
            else {
                ((ContentViewHolder)holder).checkedTextView.setChecked(false);
            }
            ((ContentViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!translation.getIscheck()){
                        ((ContentViewHolder)holder).checkedTextView.setChecked(true);
                        translation.setIscheck(true);
                        map.put(position,translation.getChinese());
                    }
                    else {
                        ((ContentViewHolder)holder).checkedTextView.setChecked(false);
                        translation.setIscheck(false);
                        map.remove(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(translationlist!=null){
            return translationlist.size();
        }
        else return 0;
    }

    @Override
    public int getItemViewType(int position) {
        //return super.getItemViewType(position);
        if(translationlist.get(position).getViewtype().equals("A")){
            return TYPE_TITLE;
        }
        else {
            return TYPE_CONTENT;
        }
    }

    public String getCheckedItems(){
        SelectedItems=new StringBuffer();
        Set keySet = map.keySet();
        Iterator it = keySet.iterator();
        while(it.hasNext()){
            String key = it.next().toString();
            //有了鍵就可以通過map集合的get方法獲取其對應的値
            String value = map.get(Integer.valueOf(key));
            if(it.hasNext()){
                SelectedItems.append(value).append("\n");
            }
            else{
                SelectedItems.append(value);
            }
        }
        return SelectedItems.toString();
    }
}
