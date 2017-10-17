package co.com.etn.arquitecturamvpbase;

import android.app.Application;
import android.content.IntentFilter;

import co.com.etn.arquitecturamvpbase.helper.Database;
import co.com.etn.arquitecturamvpbase.receivers.NetworkStateReceiver;
import co.com.etn.arquitecturamvpbase.synchronizers.Synchronizer;

/**
 * Created by Erika on 16/10/2017.
 */

public class App extends Application {
    public static Database mdb;
    private final NetworkStateReceiver NETWORKSTATERECEIVER = new NetworkStateReceiver();

    @Override
    public void onCreate() {
        super.onCreate();
        initDB();
        registerNetworkReceiver();
    }

    private void registerNetworkReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(NETWORKSTATERECEIVER,filter);
    }

    private void initDB() {
        mdb = new Database(this);
        mdb.open();
    }

    @Override
    public void onTerminate() {
        mdb.close();
        super.onTerminate();
    }

    public void onNetworkStateChanged(boolean isConnected) {
        Synchronizer.getInstance().executeSyncLocalToServer(isConnected);
    }
}
