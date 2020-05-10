package com.example.roomvocabularys1.ui;



import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.roomvocabularys1.R;
import com.example.roomvocabularys1.VocabularyViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ChoiceDialog extends DialogFragment {
    //private ArrayList SelectedItems;
    private String[] tokens;
    private Context context;
    private ListAdapter adapter;
    //private AlertDialog.Builder builder;
    private StringBuffer SelectedItems;
    private MutableLiveData<String> vocabularych;
    private String wordch;
    private VocabularyViewModel vocabularyViewModel;

    /*public ChoiceDialog(String wordch, MutableLiveData<String> vocabularych) {
        this.context=context;
        tokens = wordch.split("；");
        this.vocabularych = vocabularych;
        this.vocabularych.setValue("");
    }*/

    public static ChoiceDialog newInstance(String wordch) {
        ChoiceDialog f = new ChoiceDialog();
        Bundle args = new Bundle();
        args.putString("main1",wordch);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vocabularyViewModel=new ViewModelProvider(requireActivity()).get(VocabularyViewModel.class);
        vocabularych=vocabularyViewModel.getVocabularych();
        context=getContext();
        if (savedInstanceState==null) {
            //not restart
            Bundle args = getArguments();
            if (args==null) {
                throw new IllegalArgumentException("Bundle args required");
            }
            wordch = args.getString("main1");
            tokens = wordch.split("\n");
        } else {
            //restart
            wordch = savedInstanceState.getString("main1");
            tokens = wordch.split("\n");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("main1",wordch);
    }

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = getActivity();
    }*/

    /*public static ChoiceDialog getInstance(String someArgument) {
        ChoiceDialog appCompatMaterialAlertDialog = new ChoiceDialog();
        Bundle bundle = new Bundle();
        bundle.putString("someArgument", someArgument);
        appCompatMaterialAlertDialog.setArguments(bundle);
        return appCompatMaterialAlertDialog;
    }*/

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //SelectedItems = new ArrayList();
        SelectedItems=new StringBuffer();
        Map<Integer, String> map = new HashMap<>();
        //adapter = new ArrayAdapter<String>(context , android.R.layout.simple_list_item_multiple_choice ,tokens);
        builder.setTitle(R.string.dialogtitle)
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                //.setAdapter(adapter, new DialogInterface.OnClickListener() {
                 //   @Override
                //    public void onClick(DialogInterface dialog, int which) {
                //        String aa2=""+which;
                //        Log.d("onClick",aa2);
                //        String aa1=adapter.getItem(which).toString();
                //        Log.d("adapter",aa1);
                //    }
                //})
                // Set the action buttons
                .setMultiChoiceItems(tokens, null, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                //SelectedItems.remove(Integer.valueOf(which));
                                if (isChecked) {
                                    map.put(which,tokens[which]);
                                } else map.remove(which);
                            }
                        })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Set keySet = map.keySet();
                        Iterator it = keySet.iterator();
                        while(it.hasNext()){
                            String key = it.next().toString();
                            //有了鍵就可以通過map集合的get方法獲取其對應的値
                            String value = map.get(Integer.valueOf(key));
                            //Log.d("Map","key: " + key + ", vaule: " + value);
                            if(it.hasNext()){
                                SelectedItems.append(value).append("\n");
                            }
                            else{
                                SelectedItems.append(value);
                            }
                        }
                        vocabularych.setValue(SelectedItems.toString());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        return builder.create();
    }

   /* @Override
    public void onDestroyView() {
        super.onDestroyView();
        Dialog dialog = getDialog();
        if (dialog != null && getRetainInstance()) {
            dialog.setDismissMessage(null);
        }
        assert dialog != null;
        dialog.dismiss();
    }*/
}
