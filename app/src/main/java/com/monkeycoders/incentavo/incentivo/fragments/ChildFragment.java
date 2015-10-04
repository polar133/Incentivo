package com.monkeycoders.incentavo.incentivo.fragments;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.github.johnpersano.supertoasts.SuperToast;
import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.monkeycoders.incentavo.incentivo.Items.Card;
import com.monkeycoders.incentavo.incentivo.R;
import com.monkeycoders.incentavo.incentivo.adapters.CardArrayAdapter;
import com.monkeycoders.incentavo.incentivo.services.ChildrenService;
import com.monkeycoders.incentavo.incentivo.services.SaldoService;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import java.util.ArrayList;
import java.util.HashMap;

public class ChildFragment extends BaseFragment implements ObservableScrollViewCallbacks  {

    private static final float MAX_TEXT_SCALE_DELTA = 0.3f;

    private View mImageView;
    private View mOverlayView;
    private View mListBackgroundView;
    private TextView mTitleView;
    private View mFab;
    private int mActionBarSize;
    private int mFlexibleSpaceShowFabOffset;
    private int mFlexibleSpaceImageHeight;
    private int mFabMargin;
    private boolean mFabIsShown;
    private ObservableListView listView;
    private int monto;
    SharedPreferences preference;
    CardArrayAdapter cardArrayAdapter;

    public ChildFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_child, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFlexibleSpaceImageHeight = getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
        mFlexibleSpaceShowFabOffset = getResources().getDimensionPixelSize(R.dimen.flexible_space_show_fab_offset);
        mActionBarSize = getToolbarSize();

        mImageView = view.findViewById(R.id.image);
        mOverlayView = view.findViewById(R.id.overlay);
        listView = (ObservableListView) view.findViewById(R.id.list);
        listView.setScrollViewCallbacks(this);

        // Set padding view for ListView. This is the flexible space.
        View paddingView = new View(getActivity());
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                mFlexibleSpaceImageHeight);
        paddingView.setLayoutParams(lp);

        // This is required to disable header's list selector effect
        paddingView.setClickable(true);

        listView.addHeaderView(paddingView);

        mTitleView = (TextView) view.findViewById(R.id.title);
        mTitleView.setText(getActivity().getTitle());
        getActivity().setTitle(null);
        mFab = view.findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "FAB is clicked", Toast.LENGTH_SHORT).show();
            }
        });
        mFabMargin = getResources().getDimensionPixelSize(R.dimen.margin_standard);
        ViewHelper.setScaleX(mFab, 0);
        ViewHelper.setScaleY(mFab, 0);

        // mListBackgroundView makes ListView's background except header view.
        mListBackgroundView = view.findViewById(R.id.list_background);

        preference = getActivity().getSharedPreferences("AppPref", Context.MODE_PRIVATE);
        String rut = preference.getString("rut", "");
        int id = preference.getInt("id", 0);
        SaldoTask mSaldoTask = new SaldoTask(rut);
        mSaldoTask.execute((Void) null);

        ChildrenTask mChildrenTask = new ChildrenTask(id);
        mChildrenTask.execute((Void) null);

    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        // Translate overlay and image
        float flexibleRange = mFlexibleSpaceImageHeight - mActionBarSize;
        int minOverlayTransitionY = mActionBarSize - mOverlayView.getHeight();
        ViewHelper.setTranslationY(mOverlayView, ScrollUtils.getFloat(-scrollY, minOverlayTransitionY, 0));
        ViewHelper.setTranslationY(mImageView, ScrollUtils.getFloat(-scrollY / 2, minOverlayTransitionY, 0));

        // Translate list background
        ViewHelper.setTranslationY(mListBackgroundView, Math.max(0, -scrollY + mFlexibleSpaceImageHeight));

        // Change alpha of overlay
        ViewHelper.setAlpha(mOverlayView, ScrollUtils.getFloat((float) scrollY / flexibleRange, 0, 1));

        // Scale title text
        float scale = 1 + ScrollUtils.getFloat((flexibleRange - scrollY) / flexibleRange, 0, MAX_TEXT_SCALE_DELTA);
        setPivotXToTitle();
        ViewHelper.setPivotY(mTitleView, 0);
        ViewHelper.setScaleX(mTitleView, scale);
        ViewHelper.setScaleY(mTitleView, scale);

        // Translate title text
        int maxTitleTranslationY = (int) (mFlexibleSpaceImageHeight - mTitleView.getHeight() * scale);
        int titleTranslationY = maxTitleTranslationY - scrollY;
        ViewHelper.setTranslationY(mTitleView, titleTranslationY);

        // Translate FAB
        int maxFabTranslationY = mFlexibleSpaceImageHeight - mFab.getHeight() / 2;
        float fabTranslationY = ScrollUtils.getFloat(
                -scrollY + mFlexibleSpaceImageHeight - mFab.getHeight() / 2,
                mActionBarSize - mFab.getHeight() / 2,
                maxFabTranslationY);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            // On pre-honeycomb, ViewHelper.setTranslationX/Y does not set margin,
            // which causes FAB's OnClickListener not working.
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mFab.getLayoutParams();
            lp.leftMargin = mOverlayView.getWidth() - mFabMargin - mFab.getWidth();
            lp.topMargin = (int) fabTranslationY;
            mFab.requestLayout();
        } else {
            ViewHelper.setTranslationX(mFab, mOverlayView.getWidth() - mFabMargin - mFab.getWidth());
            ViewHelper.setTranslationY(mFab, fabTranslationY);
        }

        // Show/hide FAB
        if (fabTranslationY < mFlexibleSpaceShowFabOffset) {
            hideFab();
        } else {
            showFab();
        }
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setPivotXToTitle() {
        Configuration config = getResources().getConfiguration();
        if (Build.VERSION_CODES.JELLY_BEAN_MR1 <= Build.VERSION.SDK_INT
                && config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            ViewHelper.setPivotX(mTitleView, getActivity().findViewById(android.R.id.content).getWidth());
        } else {
            ViewHelper.setPivotX(mTitleView, 0);
        }
    }

    private void showFab() {
        if (!mFabIsShown) {
            ViewPropertyAnimator.animate(mFab).cancel();
            ViewPropertyAnimator.animate(mFab).scaleX(1).scaleY(1).setDuration(200).start();
            mFabIsShown = true;
        }
    }

    private void hideFab() {
        if (mFabIsShown) {
            ViewPropertyAnimator.animate(mFab).cancel();
            ViewPropertyAnimator.animate(mFab).scaleX(0).scaleY(0).setDuration(200).start();
            mFabIsShown = false;
        }
    }

    public class SaldoTask extends AsyncTask<Void, Void, HashMap<String, Object>> {

        private final String mRut;

        SaldoTask(String rut) {
            mRut = rut;
        }

        @Override
        protected HashMap<String, Object> doInBackground(Void... params) {
            return SaldoService.getInstance().loadSaldo(getContext(), mRut);
        }

        @Override
        protected void onPostExecute(final HashMap<String, Object> success) {
            if (success.containsKey("body")) {
                ArrayList<Object> list_object = (ArrayList<Object>) success.get("body");
                HashMap<String, Object> objeto_valor = (HashMap<String, Object>) list_object.get(0);
                monto = (int) objeto_valor.get("montoDisponible");
                String id_producto = (String) objeto_valor.get("id");
                mTitleView.setText("Dinero ahorrado: " + String.valueOf(monto));
                cardArrayAdapter = new CardArrayAdapter(getActivity(), R.layout.card_wishlist);

                SharedPreferences preferences = getActivity().getSharedPreferences("AppPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = preferences.edit();
                prefEditor.putString("id_producto", id_producto);
                prefEditor.putInt("monto_disponible", monto);
                prefEditor.apply();
            }else{
                SuperToast.create(getContext(), "Error de conexion. Por favor intente de nuevo", SuperToast.Duration.LONG).show();
            }
        }

    }

    public class ChildrenTask extends AsyncTask<Void, Void, HashMap<String, Object>> {

        private final int mID;

        ChildrenTask(int id) {
            mID = id;
        }

        @Override
        protected HashMap<String, Object> doInBackground(Void... params) {
            return ChildrenService.getInstance().loadChildren(getContext(), mID);
        }

        @Override
        protected void onPostExecute(final HashMap<String, Object> success) {

            if (success.containsKey("wishes")) {
                ArrayList<HashMap<String, Object>> list_object = (ArrayList<HashMap<String, Object>>) success.get("wishes");
                for (HashMap<String, Object> wish: list_object){
                    Card card = new Card();
                    card.setId((int) wish.get("item_id"));
                    card.setName((String) wish.get("name"));
                    card.setPrice((Double) wish.get("price"));
                    card.setUrl_image((String) wish.get("picture"));
                    card.setIs_avaible(false);
                    cardArrayAdapter.add(card);
                }
                listView.setAdapter(cardArrayAdapter);
            }else{
                SuperToast.create(getActivity(), "Error en la conexion. Por favor intente de nuevo", SuperToast.Duration.LONG).show();
            }
        }

    }

}
