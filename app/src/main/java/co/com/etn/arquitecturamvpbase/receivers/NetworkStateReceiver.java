package co.com.etn.arquitecturamvpbase.receivers;

/**
 * Created by Erika on 16/10/2017.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import co.com.etn.arquitecturamvpbase.App;

/**
 * Created by Erika on 12/10/2017.
 */

public class NetworkStateReceiver extends BroadcastReceiver {

    public static boolean connected = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isConnected;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo == null || networkInfo.getState() != NetworkInfo.State.CONNECTED){
            isConnected = false;
        }else{
            isConnected = networkInfo.isConnectedOrConnecting();
        }
        waitAndCheckConnection(isConnected,context);
        connected = isConnected;
    }

    private void waitAndCheckConnection(final boolean isConnected, final Context context) {
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                App application = (App) context.getApplicationContext();
                if(connected == isConnected && application != null){
                    application.onNetworkStateChanged(isConnected);
                }
            }
        });
        hilo.start();
    }
}
