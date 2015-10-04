package com.monkeycoders.incentavo.incentivo.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.github.johnpersano.supertoasts.SuperToast;
import com.monkeycoders.incentavo.incentivo.Items.Card;
import com.monkeycoders.incentavo.incentivo.Items.CatalogItem;
import com.monkeycoders.incentavo.incentivo.R;
import com.monkeycoders.incentavo.incentivo.fragments.CatalogPageFragment;
import com.monkeycoders.incentavo.incentivo.services.CatalogService;
import com.monkeycoders.incentavo.incentivo.services.ChildrenService;

import java.util.ArrayList;
import java.util.HashMap;

public class CatalogActivity extends BaseActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private ArrayList<CatalogItem> tecnoList;
    private ArrayList<CatalogItem> ropaList;
    private ArrayList<CatalogItem> otrosList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        CatalogTask catalogTask = new CatalogTask();
        catalogTask.execute();
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return CatalogPageFragment.newInstance(new ArrayList<CatalogItem>());

                case 1:
                    return CatalogPageFragment.newInstance(new ArrayList<CatalogItem>());

                case 2:
                    return CatalogPageFragment.newInstance(new ArrayList<CatalogItem>());

                default:
                    return CatalogPageFragment.newInstance(new ArrayList<CatalogItem>());
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Tecnologia";
                case 1:
                    return "Ropa";
                case 2:
                    return "Tiempo Libre";
            }
            return null;
        }
    }

    public class CatalogTask extends AsyncTask<Void, Void, HashMap<String, Object>> {

        CatalogTask() {
        }

        @Override
        protected HashMap<String, Object> doInBackground(Void... params) {
            return CatalogService.getInstance().loadCatalog(CatalogActivity.this);
        }

        @Override
        protected void onPostExecute(final HashMap<String, Object> success) {
            if (success.containsKey("items")) {
                ArrayList<HashMap<String, Object>> list_object = (ArrayList<HashMap<String, Object>>) success.get("items");
                tecnoList = new ArrayList<>();
                ropaList = new ArrayList<>();
                otrosList = new ArrayList<>();
                for (HashMap<String, Object> object : list_object){
                    CatalogItem catalogItem = new CatalogItem();
                    catalogItem.setName((String) object.get("name"));
                    catalogItem.setPrice((double) object.get("price"));
                    catalogItem.setUrl_image((String) object.get("picture"));
                    catalogItem.setUrl((String) object.get("url"));
                    catalogItem.setDiscount((double) object.get("discount"));
                    catalogItem.setTotal_price((double) object.get("total_price"));
                    switch ((int) object.get("code")){
                        case 1:
                            tecnoList.add(catalogItem);
                            break;
                        case 2:
                            ropaList.add(catalogItem);
                            break;
                        case 3:
                            otrosList.add(catalogItem);
                            break;
                    }
                }
            }else{
                SuperToast.create(CatalogActivity.this, "Error en la conexion. Por favor intente de nuevo", SuperToast.Duration.LONG).show();
            }
        }

    }
}
