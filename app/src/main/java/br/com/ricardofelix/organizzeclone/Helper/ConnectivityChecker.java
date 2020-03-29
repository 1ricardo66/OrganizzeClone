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

            assert cm != null;
            cm.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback(){
                @Override
                public void onLost(@NonNull Network network) {
                    super.onLost(network);
                    connectivity = false;
                    Toast.makeText(c.getApplicationContext(), "API 29", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onAvailable(@NonNull Network network) {
                    super.onAvailable(network);
                    connectivity = true;

                    Toast.makeText(c.getApplicationContext(), "API 29", Toast.LENGTH_SHORT).show();
                }
            });

        }else{
            assert cm != null;
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            connectivity = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
            Toast.makeText(c.getApplicationContext(), "API  < 29", Toast.LENGTH_SHORT).show();

        }

        return connectivity;
    }
}
