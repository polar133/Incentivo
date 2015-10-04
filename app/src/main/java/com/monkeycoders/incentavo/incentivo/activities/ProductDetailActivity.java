package com.monkeycoders.incentavo.incentivo.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.github.johnpersano.supertoasts.SuperToast;
import com.monkeycoders.incentavo.incentivo.R;
import com.monkeycoders.incentavo.incentivo.network.RequestManager;
import com.monkeycoders.incentavo.incentivo.services.CargoService;
import com.monkeycoders.incentavo.incentivo.services.ProductDetailService;
import com.monkeycoders.incentavo.incentivo.services.SaldoService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import cn.pedant.SweetAlert.SweetAlertDialog;
import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;

public class ProductDetailActivity extends BaseActivity {

    private int id;
    private PieChartView chart;
    private PieChartData data;
    private TextView title, product_price, product_missing_days;
    private NetworkImageView image;
    private String rut;
    private double valor_producto;
    private int monto_actual;
    private FloatingActionButton fab;
    private String nombre_producto;
    private String idProducto;
    SharedPreferences preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyProduct();
            }
        });

        preference = getSharedPreferences("AppPref", Context.MODE_PRIVATE);
        rut = preference.getString("rut", "");
        idProducto = preference.getString("id_producto", "");
        monto_actual = preference.getInt("monto_disponible", 0);

        title = (TextView) findViewById(R.id.product_title);
        product_price = (TextView) findViewById(R.id.product_price);
        product_missing_days = (TextView) findViewById(R.id.product_missing_days);
        image = (NetworkImageView) findViewById(R.id.product_image);

        chart = (PieChartView) findViewById(R.id.chart);
        chart.setOnValueTouchListener(new ValueTouchListener());

        id = (int) getIntent().getExtras().get("product_id");


        ProductDetailTask mProductDetailTask = new ProductDetailTask(String.valueOf(id));
        mProductDetailTask.execute((Void) null);
    }


    private void generateData() {
        List<SliceValue> values = new ArrayList<SliceValue>();


        String msg;
        int porc = (int)preference.getFloat("pecentage_financing", 0);
        int max_credito = (int)preference.getFloat("maximun_financing", 0);

        if(valor_producto <= max_credito)
            valor_producto = valor_producto * (100 - porc) / 100;

        SliceValue sliceValue = new SliceValue((float) (monto_actual - valor_producto), ChartUtils.COLOR_GREEN);
        SliceValue sliceValue2 = new SliceValue((float) Math.max(0, valor_producto - monto_actual), ChartUtils.COLOR_RED);
        values.add(sliceValue);
        values.add(sliceValue2);

        data = new PieChartData(values);

        chart.setPieChartData(data);
    }

    private void buyProduct(){

        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this file!")
                .setConfirmText("Yes,delete it!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        BuyProductTask buyProductTask = new BuyProductTask(sDialog);
                        buyProductTask.execute();
                    }
                })
                .show();
    }

    private class ValueTouchListener implements PieChartOnValueSelectListener {

        @Override
        public void onValueSelected(int arcIndex, SliceValue value) {
            String msg;
            int porc = (int)preference.getFloat("pecentage_financing", 0);
            int max_credito = (int)preference.getFloat("maximun_financing", 0);

            if(valor_producto <= max_credito)
                valor_producto = valor_producto * (100 - porc) / 100;

            if(monto_actual < valor_producto)
                msg = "Te falta: " + (valor_producto - monto_actual) + " Pesos";
            else
                msg = "ya puedes comprar este producto!";

            Toast.makeText(ProductDetailActivity.this, msg, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class BuyProductTask extends AsyncTask<Void, Void, HashMap<String, Object>> {

        private final SweetAlertDialog dialog;

        BuyProductTask(SweetAlertDialog sDialog) {
            dialog = sDialog;
        }

        @Override
        protected HashMap<String, Object> doInBackground(Void... params) {
            return CargoService.getInstance().loadCargo(ProductDetailActivity.this, rut, monto_actual,
                    "Compra de " + nombre_producto, idProducto);
        }

        @Override
        protected void onPostExecute(final HashMap<String, Object> success) {
            if (success.containsKey("message")){
                if (success.get("message").equals("OK")){
                    dialog
                            .setTitleText("Deleted!")
                            .setContentText("Your imaginary file has been deleted!")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    ProductDetailActivity.this.finish();
                                }
                            })
                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                }else{

                }
            }

        }
    }

    public class ProductDetailTask extends AsyncTask<Void, Void, HashMap<String, Object>> {

        private final String mID;

        ProductDetailTask(String id) {
            mID = id;
        }

        @Override
        protected HashMap<String, Object> doInBackground(Void... params) {
            return ProductDetailService.getInstance().loadProductDetail(ProductDetailActivity.this, mID);
        }

        @Override
        protected void onPostExecute(final HashMap<String, Object> success) {
            System.out.println(success);
            if (success.containsKey("name")) {
                nombre_producto = (String)success.get("name");
                title.setText(nombre_producto);
                valor_producto = (double) success.get("price");
                product_price.setText(String.valueOf(valor_producto));
                product_missing_days.setText("Faltan 15 dias");
                image.setImageUrl((String) success.get("picture"), RequestManager.getInstance().doRequest().getImageLoader());

                if (valor_producto > monto_actual){
                    fab.setVisibility(View.GONE);
                }
            }
            generateData();
        }

    }
}
