package co.com.etn.arquitecturamvpbase.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Erika on 16/09/2017.
 */

public class ValidateInternet implements IVaidateInternet {

    private Context context;

    public ValidateInternet(Context context) {
        this.context = context;
    }

    @Override
    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting() && networkInfo.isAvailable() && networkInfo.isConnected();
    }
}
