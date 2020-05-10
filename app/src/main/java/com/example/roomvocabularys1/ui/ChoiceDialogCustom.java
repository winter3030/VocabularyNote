package com.example.roomvocabularys1.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomvocabularys1.R;
import com.example.roomvocabularys1.VocabularyViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ChoiceDialogCustom extends DialogFragment {
    private Context context;
    private VocabularyViewModel vocabularyViewModel;
    private RecyclerView recyclerView;
    private MutableLiveData<ArrayList<Translation>> translationlist;
    private TranslationAdapter translationAdapter;
    private Button button_ok;
    private Button button_cancel;

    public static ChoiceDialogCustom newInstance() {
        ChoiceDialogCustom f = new ChoiceDialogCustom();
        return f;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.recycleview_dialog, container, false);
        recyclerView=view.findViewById(R.id.recycleview_dialog);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        translationAdapter=new TranslationAdapter(context,translationlist.getValue());
        recyclerView.setAdapter(translationAdapter);
        button_ok=view.findViewById(R.id.button_ok);
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vocabularyViewModel.getVocabularych().setValue(translationAdapter.getCheckedItems());
                getDialog().dismiss();
            }
        });
        button_cancel=view.findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vocabularyViewModel=new ViewModelProvider(requireActivity()).get(VocabularyViewModel.class);
        translationlist=vocabularyViewModel.getTranslationlist();
        context=getContext();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /*@NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.dialogtitle)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        return builder.create();
    }*/

    /*@NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        //dialog.setTitle(R.string.dialogtitle);
        //int width = getResources().getDimensionPixelSize(R.dimen.popup_width);
        //dialog.getWindow().setLayout(300,700);
        int dialogWidth = 1000;
        //int dialogHeight = 1200;
        int dialogHeight = ConstraintLayout.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setLayout(dialogWidth, dialogHeight);
        return dialog;
    }*/

    @Override
    public void onStart() {
        super.onStart();
        // safety check
        if (getDialog() == null) {
            return;
        }

        int dialogWidth = 1000;
        //int dialogHeight = 1200;
        int dialogHeight = ConstraintLayout.LayoutParams.WRAP_CONTENT;

        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
        // ... other stuff you want to do in your onStart() method
    }
}
