package com.app.golfapp.ui.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.widget.Toast;

import com.app.golfapp.R;


/**
 * Created by nnarayan on 4/11/2018.
 */

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";
    public ProgressDialog progressDialog;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    public void initObjects() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
    }

    public void isShowProgress(boolean b, String message) {
        if (b) {
            progressDialog.setMessage(message);
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }

    public void isShowProgress(boolean b) {
        if (b) {
            progressDialog.setMessage(getResources().getString(R.string.loading));
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public void showAlerDialog(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(
                BaseActivity.this).create();
        alertDialog.setTitle(getResources().getString(R.string.app_name));
        alertDialog.setMessage(message);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }

    public void showAlerDialogF(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(
                BaseActivity.this).create();
        alertDialog.setTitle(getResources().getString(R.string.app_name));
        alertDialog.setMessage(message);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }

    public static void showAlertToast(String s,Context context){
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, s, duration);
        toast.show();
    }
}
