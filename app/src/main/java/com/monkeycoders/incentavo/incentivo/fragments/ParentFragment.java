package com.monkeycoders.incentavo.incentivo.fragments;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.github.johnpersano.supertoasts.SuperToast;
import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.monkeycoders.incentavo.incentivo.Items.Card;
import com.monkeycoders.incentavo.incentivo.R;
import com.monkeycoders.incentavo.incentivo.adapters.ChildAdapter;
import com.monkeycoders.incentavo.incentivo.services.ChildrenService;

import java.util.ArrayList;
import java.util.HashMap;


public class ParentFragment extends Fragment {

    private ListView listView;
    private ChildAdapter childAdapter;

    public ParentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_parent, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.list);

        childAdapter = new ChildAdapter(getActivity(), R.id.child_name_text);

    }

    
}
