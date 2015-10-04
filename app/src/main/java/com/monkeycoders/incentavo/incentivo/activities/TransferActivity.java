package com.monkeycoders.incentavo.incentivo.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.github.johnpersano.supertoasts.SuperToast;
import com.monkeycoders.incentavo.incentivo.Items.CatalogItem;
import com.monkeycoders.incentavo.incentivo.R;
import com.monkeycoders.incentavo.incentivo.models.ChildrenData;
import com.monkeycoders.incentavo.incentivo.models.Dao.ChildDao;
import com.monkeycoders.incentavo.incentivo.models.Dao.Impl.ChildrenDataDaoImpl;
import com.monkeycoders.incentavo.incentivo.services.CatalogService;
import com.monkeycoders.incentavo.incentivo.services.TEFService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import cn.pedant.SweetAlert.SweetAlertDialog;
import fr.ganfra.materialspinner.MaterialSpinner;

public class TransferActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private MaterialSpinner spinner;
    private EditText monto;
    private ChildDao childDao;
    private ArrayList<ChildrenData> childrenDatas;
    private String rut, id_producto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        childDao = new ChildrenDataDaoImpl();
        preferences = getSharedPreferences("AppPref", Context.MODE_PRIVATE);
        rut = preferences.getString("rut", "");
        id_producto = preferences.getString("id_producto", "");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(TransferActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Esta seguro que desea transferir ese monto?")
                        .setConfirmText("Si,transferir!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.setTitleText("Dinero transferido!")
                                        .setContentText("Le has depositado en su cuenta personal!")
                                        .setConfirmText("OK")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                TransferActivity.this.finish();
                                            }
                                        })
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            }
                        })
                        .show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String[] ITEMS = {"Slaven", "Valentin"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (MaterialSpinner) findViewById(R.id.spinner_child);
        spinner.setAdapter(adapter);
    }

    public class TransferTask extends AsyncTask<Void, Void, HashMap<String, Object>> {

        private String rut;
        private String idProductoOrigin;
        private int monto;
        private String mensaje;
        private HashMap<String, Object> datosDestino;

        TransferTask(String mRut, String mIdProductoOrigin, int mMonto, String mMensaje, HashMap<String, Object> mDatosDestino) {
            rut = mRut;
            idProductoOrigin = mIdProductoOrigin;
            monto = mMonto;
            mensaje = mMensaje;
            datosDestino = mDatosDestino;
        }

        @Override
        protected HashMap<String, Object> doInBackground(Void... params) {
            return TEFService.getInstance().loadTEF(TransferActivity.this,rut, idProductoOrigin, monto, mensaje, datosDestino);
        }

        @Override
        protected void onPostExecute(final HashMap<String, Object> success) {
            if (success.containsKey("items")) {

            }else{
                SuperToast.create(TransferActivity.this, "Error en la conexion. Por favor intente de nuevo", SuperToast.Duration.LONG).show();
            }
        }

    }


}
