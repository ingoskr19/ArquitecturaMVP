package co.com.etn.arquitecturamvpbase.helper;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Erika on 28/10/2017.
 */

public class FetchAddressIntentService extends IntentService {

    private static final String TAG = "FetchAddressIntentService";
    private ResultReceiver resultReceiver;

    public FetchAddressIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        resultReceiver = intent.getParcelableExtra(Constants.RECEIVER);

        if(resultReceiver == null){
            Log.e(TAG,"No se pudo obtener el receiver");
            return;
        }

        Location location = intent.getParcelableExtra(Constants.LOCATION_DATA_EXTRA);

        if(location == null){
            Log.e(TAG,"Localización No Proporcionada");
            deliverResultToReceiver(Constants.FAIL_RESULT, "Localización No Proporcionada");
            return;
        }

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;

        try{

            addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),5);
        } catch (IOException ex){
            Log.e(TAG, ex.getMessage());
            deliverResultToReceiver(Constants.FAIL_RESULT,"Servicio No Disponible");
        } catch (Exception ex){
            Log.e(TAG, "ERROR: "+ex.getMessage());
            deliverResultToReceiver(Constants.FAIL_RESULT,"Ha ocurrido un error inexperado");
        }

        if(addresses == null || addresses.size()==0){
            Log.e(TAG, "No se han encontrado resultados");
            deliverResultToReceiver(Constants.FAIL_RESULT,"No se han encontrado resultados");
        } else {
            String addressLine = "";
            for (Address address : addresses){
                for (int i = 0; i<=address.getMaxAddressLineIndex();i++ ){
                    addressLine+=","+address.getAddressLine(i);
                }
            }

            deliverResultToReceiver(Constants.SUCCESS_RESULT,addressLine);
        }
    }

    private void deliverResultToReceiver(int typeResult, String response) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.RESULT_DATA_KEY,response);
        resultReceiver.send(typeResult,bundle);
    }
}
