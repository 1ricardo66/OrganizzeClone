package br.com.ricardofelix.organizzeclone.Helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class ConnectivityChecker {

    private static Boolean connectivity=true;


        public static boolean getConnectivity(final Context c){
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){

            connectivity = isConnected(c);

        }else{
            assert cm != null;
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            connectivity = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
            Toast.makeText(c.getApplicationContext(), "API  < 29", Toast.LENGTH_SHORT).show();

        }

        return connectivity;
    }

    public static boolean isConnected(Context c){
        ConnectivityManager conMgr = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null) {
            connectivity = false;
            Toast.makeText(c.getApplicationContext(), "Please turn on Internet ", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(c.getApplicationContext(), "You are connected", Toast.LENGTH_SHORT).show();
            connectivity = true;
        }
        return  connectivity;
    }
}
