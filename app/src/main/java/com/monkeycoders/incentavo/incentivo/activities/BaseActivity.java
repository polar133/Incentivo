package com.monkeycoders.incentavo.incentivo.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.TypedValue;
import android.view.MenuItem;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.johnpersano.supertoasts.SuperToast;
import com.monkeycoders.incentavo.incentivo.R;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences preferences;

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_bip) {
            new MaterialDialog.Builder(this)
                    .title("Pagar BIP")
                    .content("Cual es el monto que desea ingresar?")
                    .inputType(InputType.TYPE_CLASS_PHONE)
                    .input("", "", new MaterialDialog.InputCallback() {
                        @Override
                        public void onInput(MaterialDialog dialog, CharSequence input) {
                            SuperToast.create(getBaseContext(), "Operacion Exitosa!", SuperToast.Duration.LONG).show();
                            dialog.dismiss();
                        }
                    }).show();
        } else if (id == R.id.nav_movil) {
            new MaterialDialog.Builder(this)
                    .title("Pagar Movil")
                    .content("Cual es el monto que desea ingresar?")
                    .inputType(InputType.TYPE_CLASS_PHONE)
                    .input("", "", new MaterialDialog.InputCallback() {
                        @Override
                        public void onInput(MaterialDialog dialog, CharSequence input) {
                            SuperToast.create(getBaseContext(), "Operacion Exitosa!", SuperToast.Duration.LONG).show();
                            dialog.dismiss();
                        }
                    }).show();

        } else if (id == R.id.nav_logout) {
            preferences = getApplicationContext().getSharedPreferences("AppPref", Context.MODE_PRIVATE);

            SharedPreferences.Editor prefEditor = preferences.edit();
            prefEditor.clear();
            prefEditor.apply();

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("EXIT", true);
            getApplicationContext().startActivity(intent);
        } else if (id == R.id.nav_control) {
            Intent intent = new Intent(BaseActivity.this, QRActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            BaseActivity.this.startActivity(intent);
        } else if (id == R.id.nav_transferir) {
            Intent intent = new Intent(getApplicationContext(), TransferActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
