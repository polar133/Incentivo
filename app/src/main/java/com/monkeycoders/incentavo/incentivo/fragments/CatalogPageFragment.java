package com.monkeycoders.incentavo.incentivo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etsy.android.grid.StaggeredGridView;
import com.monkeycoders.incentavo.incentivo.Items.CatalogItem;
import com.monkeycoders.incentavo.incentivo.R;
import com.monkeycoders.incentavo.incentivo.adapters.CatalogAdapter;
import com.monkeycoders.incentavo.incentivo.models.SampleData;

import java.util.ArrayList;
import java.util.List;


public class CatalogPageFragment extends Fragment {

    static List<CatalogItem> catalogItems;

    public static CatalogPageFragment newInstance(List<CatalogItem> mCatalogItems) {
        CatalogPageFragment fragment = new CatalogPageFragment();
        catalogItems = mCatalogItems;
        return fragment;
    }

    public CatalogPageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_catalog, container, false);
        StaggeredGridView gridView = (StaggeredGridView) rootView.findViewById(R.id.catalog_grid_view);

        CatalogAdapter mAdapter = new CatalogAdapter(getContext(), R.id.product_item_title);

        for (CatalogItem data : catalogItems) {
            mAdapter.add(data);
        }
        gridView.setAdapter(mAdapter);
        return rootView;

    }
}
