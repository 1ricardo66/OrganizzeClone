package br.com.ricardofelix.organizzeclone.Helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
public class ConnectivityChecker {

    private static Boolean connectivity=true;


    public static boolean getConnectivity( Context c){
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);

            assert cm != null;
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            connectivity = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();


        return connectivity;
    }
}
