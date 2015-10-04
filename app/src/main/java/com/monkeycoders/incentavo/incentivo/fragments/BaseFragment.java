package com.monkeycoders.incentavo.incentivo.fragments;
import android.app.Activity;
import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.monkeycoders.incentavo.incentivo.Items.Card;
import com.monkeycoders.incentavo.incentivo.R;
import com.monkeycoders.incentavo.incentivo.adapters.CardArrayAdapter;

import java.util.ArrayList;


public class BaseFragment extends Fragment {

    protected int getToolbarSize() {
        Activity activity = getActivity();
        if (activity == null) {
            return 0;
        }
        TypedValue typedValue = new TypedValue();
        int[] textSizeAttr = new int[]{R.attr.actionBarSize};
        int indexOfAttrTextSize = 0;
        TypedArray a = activity.obtainStyledAttributes(typedValue.data, textSizeAttr);
        int toolbarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
        a.recycle();
        return toolbarSize;
    }

}
