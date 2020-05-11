package com.example.roomvocabularys1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomvocabularys1.database.NoteBook;
import com.example.roomvocabularys1.database.Vocabulary;

import java.util.List;

public class NoteBookAdapter extends RecyclerView.Adapter<NoteBookAdapter.NoteViewHolder> {
    private Context context;
    private VocabularyViewModel vocabularyViewModel;
    private List<NoteBook> allnotebook;

    public NoteBookAdapter(Context context,VocabularyViewModel vocabularyViewModel) {
        this.context=context;
        this.vocabularyViewModel=vocabularyViewModel;
    }

    public void setAllnotebook(List<NoteBook> allnotebook) {
        this.allnotebook = allnotebook;
    }

    //建立ViewHolder
    class NoteViewHolder extends RecyclerView.ViewHolder{
        private TextView notebook_name;
        private ImageButton imageButton;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            notebook_name = itemView.findViewById(R.id.notebook_name);
            imageButton=itemView.findViewById(R.id.imageMenu);
        }
    }
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.notebook_item, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        if(allnotebook!=null){
            NoteBook noteBook=allnotebook.get(position);
            //沒指定<T>種類要強制轉型
            holder.notebook_name.setText(noteBook.getNote_name());
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
                                    vocabularyViewModel.delete_notebook(noteBook);
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
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vocabularyViewModel.getCurrent_notebook().setValue(noteBook.getNote_name());
                    ((MainActivity)context).show_recycleviewfragment();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(allnotebook!=null){
            return allnotebook.size();
        }
        else return 0;
    }
}
