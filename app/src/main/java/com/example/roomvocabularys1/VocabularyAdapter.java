package com.example.roomvocabularys1;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomvocabularys1.audio.OnEventListener;
import com.example.roomvocabularys1.database.Vocabulary;
import com.example.roomvocabularys1.audio.PlayAudio;
import com.example.roomvocabularys1.database.VocabularyDatabase;
import com.example.roomvocabularys1.query.QueryAudio;

import java.util.List;

public class VocabularyAdapter extends RecyclerView.Adapter<VocabularyAdapter.VocabularyViewHolder> {
    private List<Vocabulary> allvocabulary;
    private LayoutInflater layoutInflater;
    private Context context;
    private VocabularyViewModel vocabularyViewModel;
    //private float animationscale;
    private View viewback;
    //private View viewParent;
    private View CardBackLayout;
    private String word;
    private PlayAudio playAudio;

    public VocabularyAdapter(Context context,VocabularyViewModel vocabularyViewModel) {
        //Log.e("VocabularyAdapter","VocabularyAdapter");
        layoutInflater=LayoutInflater.from(context);
        this.context=context;
        this.vocabularyViewModel=vocabularyViewModel;
        //this.animationscale=animationscale;
        //LayoutInflater layoutInflater = LayoutInflater.from(context);
        //ViewGroup viewGroup=
        //viewback = layoutInflater.inflate(R.layout.back_item, viewParent,false);
        //this.viewback=viewback;
        playAudio=new PlayAudio(context);
    }

    //在MainActivity中設定
    public void setAllvocabulary(List<Vocabulary> allvocabulary) {
        this.allvocabulary = allvocabulary;
    }

    //ViewHolder管理item
    class VocabularyViewHolder extends RecyclerView.ViewHolder{
        private TextView textView_id;
        private TextView textView_en;
        private TextView textView_kk;
        private TextView textView_ch;
        private ImageButton imageButton;
        private ImageButton playaudio;

        //翻轉
        /*private View CardFrontLayout;
        // View CardBackLayout;
        private AnimatorSet SetRightOut;
        private AnimatorSet SetLeftIn;
        private AnimatorSet buttonIn, topOut, buttonOut, topIn;
        private AnimatorSet showFrontAnim;
        private AnimatorSet showBackAnim;*/
        public VocabularyViewHolder(@NonNull View itemView) {
            super(itemView);
            /*RelativeLayout mainLayout = (RelativeLayout) itemView.findViewById(R.id.main_root);
            CardBackLayout=viewback.findViewById(R.id.card_front_root_back);
            if(CardBackLayout!=null){
                Log.e("CardBackLayout","null");
                mainLayout.addView(CardBackLayout);
            }*/

            //Log.e("VocabularyViewHolder","VocabularyViewHolder");
            textView_id=itemView.findViewById(R.id.textView_id);
            textView_en=itemView.findViewById(R.id.textView_en);
            textView_kk=itemView.findViewById(R.id.textView_kk);
            textView_ch=itemView.findViewById(R.id.textView_ch);
            imageButton=itemView.findViewById(R.id.imageMenu);
            playaudio=itemView.findViewById(R.id.playaudio);
            playaudio.setImageResource(R.drawable.ic_volume_up_black_24dp);
            //翻轉
            //CardFrontLayout=itemView.findViewById(R.id.card_front_root);
            //CardBackLayout=new View(context);
            //CardBackLayout=CardFrontLayout;
            /*CardFrontLayout=itemView.findViewById(R.id.card_front_root);
            //CardBackLayout=itemView.findViewById(R.id.card_front_root_back);
            CardFrontLayout.setVisibility(View.VISIBLE);
            if(CardBackLayout==null){
                Log.e("CardBackLayout1","null");
            }
            CardBackLayout.setVisibility(View.GONE);*/
            /*VocabularyDatabase.databaseWriteExecutor.execute(() ->{

            });*/
            //CardBackLayout=itemView.findViewById(R.id.card_front_root_back);
            //CardBackLayout.setVisibility(View.GONE);
            /*SetRightOut= (AnimatorSet)AnimatorInflater.loadAnimator(context,R.animator.out_animation);
            SetLeftIn= (AnimatorSet)AnimatorInflater.loadAnimator(context,R.animator.in_animation);*/
            /*buttonIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.card_flip_button_in);
            topOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.card_flip_top_out);
            buttonOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.card_flip_button_out);
            topIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.card_flip_top_in);*/
            /*showFrontAnim=new AnimatorSet();
            //showBackAnim=new AnimatorSet();
            SetRightOut.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if(textView_ch.getVisibility()==View.GONE){
                        textView_kk.setVisibility(View.VISIBLE);
                        textView_ch.setVisibility(View.VISIBLE);
                    }
                    else{
                        textView_kk.setVisibility(View.GONE);
                        textView_ch.setVisibility(View.GONE);
                    }
                    CardFrontLayout.setElevation(6.0f);
                    CardBackLayout.setElevation(6.0f);
                    ////CardFrontLayout.setVisibility(View.GONE);

                }

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    //String a1=""+CardFrontLayout.getElevation();
                    //Log.d("onAnimationStart",a1);
                    CardFrontLayout.setElevation(0);
                    CardBackLayout.setElevation(0);
                    CardBackLayout.setVisibility(View.VISIBLE);
                }
            });*/
        }
    }

    //在初始化VocabularyViewHolder時要載入.xml
    @NonNull
    @Override
    public VocabularyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Log.e("onCreateViewHolder","onCreateViewHolder");
        //String viewT=""+viewType;
        //Log.d("viewType",viewT);
        View itemView=layoutInflater.inflate(R.layout.recyclerview_item_card,parent,false);
        /*viewback=layoutInflater.inflate(R.layout.back_item,parent,false);*/
        /*if(viewback==null){
            viewback=layoutInflater.inflate(R.layout.back_item,parent,false);
            RelativeLayout mainLayout = (RelativeLayout) itemView.findViewById(R.id.main_root);
            CardBackLayout=viewback.findViewById(R.id.card_front_root_back);
            if(CardBackLayout!=null){
                Log.e("CardBackLayout","null");
                mainLayout.addView(CardBackLayout);
            }
        }*/

        //viewParent = mainLayout.getParent();
        //String ss=viewParent+"";
        //Log.e("viewParent",ss);
        return new VocabularyViewHolder(itemView);
        //return null;
    }

    //關聯holder裡的TextView跟資料
    @Override
    public void onBindViewHolder(@NonNull VocabularyViewHolder holder, int position) {
        //Log.e("onBindViewHolder","onBindViewHolder");
        //String cc1=""+holder.getItemViewType();
        //Log.d("cc1","onBindViewHolder");
        if (allvocabulary!=null){
            String po=""+position;
            //Log.d("Adapter",po);
            Vocabulary vocabulary=allvocabulary.get(position);
            holder.textView_id.setText(String.valueOf(position+1));
            holder.textView_en.setText(vocabulary.getVocabulary());
            holder.textView_kk.setText(vocabulary.getVocabulary_kk());
            holder.textView_kk.setVisibility(View.GONE);
            holder.textView_ch.setText(vocabulary.getVocabulary_ch());
            holder.textView_ch.setVisibility(View.GONE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(holder.textView_ch.getVisibility()==View.GONE){
                        holder.textView_kk.setVisibility(View.VISIBLE);
                        holder.textView_ch.setVisibility(View.VISIBLE);
                    }
                    else{
                        holder.textView_kk.setVisibility(View.GONE);
                        holder.textView_ch.setVisibility(View.GONE);
                    }
                    /*int distance = 8000;
                    float scale = animationscale * distance;
                    holder.CardFrontLayout.setCameraDistance(scale);*/
                    /*holder.SetRightOut.setTarget(holder.CardFrontLayout);
                    holder.SetLeftIn.setTarget(CardBackLayout);
                    //holder.SetRightOut.start();  //buttonOut  buttonIn
                    //holder.SetLeftIn.start();
                    holder.showFrontAnim.playTogether(holder.SetLeftIn, holder.SetRightOut);
                    holder.showFrontAnim.start();*/

                    /*holder.topIn.setTarget(holder.CardFrontLayout);
                    holder.buttonOut.setTarget(holder.CardBackLayout);
                    holder.topOut.setTarget(holder.CardFrontLayout);
                    holder.buttonIn.setTarget(holder.CardBackLayout);
                    holder.showFrontAnim.playTogether(holder.topIn, holder.buttonOut);
                    holder.buttonOut.start();*/
                    //holder.buttonIn.start();
                    //線上辭典
                    /*Uri rui= Uri.parse("https://dictionary.cambridge.org/zht/%E8%A9%9E%E5%85%B8/%E8%8B%B1%E8%AA%9E-%E6%BC%A2%E8%AA%9E-%E7%B9%81%E9%AB%94/"+holder.textView_en.getText());
                    Intent intent=new Intent(Intent.ACTION_VIEW);
                    intent.setData(rui);
                    holder.itemView.getContext().startActivity(intent);*/
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
                public boolean onLongClick(View view) {
                    Uri rui= Uri.parse("https://dictionary.cambridge.org/zht/%E8%A9%9E%E5%85%B8/%E8%8B%B1%E8%AA%9E-%E6%BC%A2%E8%AA%9E-%E7%B9%81%E9%AB%94/"+holder.textView_en.getText());
                    Intent intent=new Intent(Intent.ACTION_VIEW);
                    intent.setData(rui);
                    holder.itemView.getContext().startActivity(intent);
                    return false;
                }
            });
            //TextView下拉式選單
            holder.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu=new PopupMenu(context,holder.imageButton);
                    popupMenu.inflate(R.menu.recyclerview_menu);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch(menuItem.getItemId()){
                                case R.id.menu1:
                                    vocabularyViewModel.delete(vocabulary);
                                    break;
                                case R.id.menu2:
                                    break;
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
            //playaudio
            holder.playaudio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    word=holder.textView_en.getText().toString();
                    QueryAudio queryAudio=new QueryAudio(word,new OnEventListener<String>(){

                        @Override
                        public void onSuccess(String url) {
                            playAudio.play(url);
                        }

                        @Override
                        public void onFailure(String e) {
                            Toast.makeText(context, "ERROR: "+e, Toast.LENGTH_SHORT).show();
                        }
                    },context);
                    queryAudio.execute();
                }
            });
        }
        else{
            holder.textView_en.setText(R.string.no_data);
        }
    }

    //返回List數量
    @Override
    public int getItemCount() {
        if(allvocabulary!=null){
            return allvocabulary.size();
        }
        else return 0;
    }

    /*@Override
    public int getItemViewType(int position){

    }*/

}
