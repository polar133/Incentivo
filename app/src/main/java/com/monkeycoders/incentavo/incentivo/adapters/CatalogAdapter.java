package com.monkeycoders.incentavo.incentivo.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.etsy.android.grid.util.DynamicHeightTextView;
import com.monkeycoders.incentavo.incentivo.Items.CatalogItem;
import com.monkeycoders.incentavo.incentivo.R;
import com.monkeycoders.incentavo.incentivo.activities.CatalogDetailActivity;
import com.monkeycoders.incentavo.incentivo.activities.ProductDetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CatalogAdapter extends ArrayAdapter<CatalogItem> {
    private static final String TAG = "SampleAdapter";

    static class ViewHolder {
        DynamicHeightTextView txtTitle, txtPrice;
        ImageView imgProduct;
    }

    private final LayoutInflater mLayoutInflater;
    private final Random mRandom;
    private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();

    public CatalogAdapter(final Context context, final int textViewResourceId) {
        super(context, textViewResourceId);
        mLayoutInflater = LayoutInflater.from(context);
        mRandom = new Random();
    }

    @Override
    public void add(CatalogItem object) {
        super.add(object);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        ViewHolder vh;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_catalog, parent, false);
            vh = new ViewHolder();
            vh.txtTitle = (DynamicHeightTextView) convertView.findViewById(R.id.product_item_title);
            vh.txtPrice = (DynamicHeightTextView) convertView.findViewById(R.id.product_item_price);
            vh.imgProduct = (ImageView) convertView.findViewById(R.id.product_item_image);
            convertView.setTag(vh);
        }
        else {
            vh = (ViewHolder) convertView.getTag();
        }

        double positionHeight = getPositionRatio(position);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CatalogDetailActivity.class);
                intent.putExtra("product_id", 1);
                getContext().startActivity(intent);
            }
        });

        vh.txtTitle.setHeightRatio(positionHeight);
        vh.txtTitle.setText("Texto");

        vh.txtPrice.setHeightRatio(positionHeight);
        vh.txtPrice.setText("Texto 2");

        return convertView;
    }

    private double getPositionRatio(final int position) {
        double ratio = sPositionHeightRatios.get(position, 0.0);
        // if not yet done generate and stash the columns height
        // in our real world scenario this will be determined by
        // some match based on the known height and width of the image
        // and maybe a helpful way to get the column height!
        if (ratio == 0) {
            ratio = getRandomHeightRatio();
            sPositionHeightRatios.append(position, ratio);
        }
        return ratio;
    }

    private double getRandomHeightRatio() {
        return (mRandom.nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5 the width
    }

}
