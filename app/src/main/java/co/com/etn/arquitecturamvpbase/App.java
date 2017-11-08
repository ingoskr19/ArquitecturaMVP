package co.com.etn.arquitecturamvpbase;

import android.app.Application;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.onesignal.OSNotification;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

import co.com.etn.arquitecturamvpbase.helper.Database;
import co.com.etn.arquitecturamvpbase.receivers.NetworkStateReceiver;
import co.com.etn.arquitecturamvpbase.synchronizers.Synchronizer;
import co.com.etn.arquitecturamvpbase.views.fragments.ProfileFragment;

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
        settingOneSignal();
    }

    private void settingOneSignal(){
        OneSignal.startInit(this)
        .autoPromptLocation(false)
        .setNotificationReceivedHandler(new NotificationReceivedHandler())
        .setNotificationOpenedHandler(new NotificationOpenedHandler())
        .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
        .unsubscribeWhenNotificationsAreDisabled(true)
        .init();
    }

    private class NotificationReceivedHandler implements OneSignal.NotificationReceivedHandler{

        @Override
        public void notificationReceived(OSNotification notification) {

            JSONObject data = notification.payload.additionalData;
            String notificationID = notification.payload.notificationID;
            String title = notification.payload.title;

            Log.i("ONSESIGNAL","NOTIFIATIONID:"+notificationID);
            if(data!=null){
                String customerKey = data.optString("customerkey",null);
                if(customerKey!= null){
                    Log.i("ONESIGNAL","TOKEN:"+customerKey);
                }
            }

        }
    }

    private class NotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {

        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            OSNotificationAction.ActionType actionType = result.action.type;
            if(actionType == OSNotificationAction.ActionType.ActionTaken){
                Intent intent = new Intent(getApplicationContext(),ProfileFragment.class);
                startActivity(intent);
            }
        }
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
