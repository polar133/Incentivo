package com.monkeycoders.incentavo.incentivo.fragments;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.github.johnpersano.supertoasts.SuperToast;
import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.monkeycoders.incentavo.incentivo.Items.Card;
import com.monkeycoders.incentavo.incentivo.Items.ChildCard;
import com.monkeycoders.incentavo.incentivo.R;
import com.monkeycoders.incentavo.incentivo.adapters.CardArrayAdapter;
import com.monkeycoders.incentavo.incentivo.adapters.ChildAdapter;
import com.monkeycoders.incentavo.incentivo.models.ChildrenData;
import com.monkeycoders.incentavo.incentivo.models.Dao.ChildDao;
import com.monkeycoders.incentavo.incentivo.models.Dao.Impl.ChildrenDataDaoImpl;
import com.monkeycoders.incentavo.incentivo.services.ChildrenService;
import com.monkeycoders.incentavo.incentivo.services.SaldoService;

import java.util.ArrayList;
import java.util.HashMap;


public class ParentFragment extends Fragment {

    private ListView listView;
    SharedPreferences preferences;

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


        listView = (ListView) view.findViewById(R.id.child_list);

        String[] values = new String[] { "Eslevan - Fecha 15/10/2015 - Gasto 10.000",
                "Eslevan - Fecha 10/10/2015 - Gasto 15.000",
                "Liam - Fecha 30/09/2015 - Gasto 12.000",
                "Eslevan - Fecha 10/09/2015 - Gasto 11.000",
                "Carlos - Fecha 1/09/2015 - Gasto 1.000",
                "Eslevan - Fecha 27/08/2015 - Gasto 300",
                "Eslevan - Fecha 20/08/2015 - Gasto 5.000",
                "Eslevan - Fecha 19/08/2015 - Gasto 8.000"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String itemValue = (String) listView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getActivity(),
                        "Para mas informacion puedes ingresar en control web", Toast.LENGTH_LONG)
                        .show();

            }

        });
    }


}
