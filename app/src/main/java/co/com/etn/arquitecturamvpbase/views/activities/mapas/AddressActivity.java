package co.com.etn.arquitecturamvpbase.views.activities.mapas;

import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.helper.Constants;
import co.com.etn.arquitecturamvpbase.helper.FetchAddressIntentService;

public class AddressActivity extends AppCompatActivity {


    private Location location;
    private TextView addressText;
    private EditText latitud;
    private EditText longitud;
    private Button find;
    private String address;
    private AddressResultReceiver resultReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        init();
        loadData();
    }

    private void loadData() {
        Double CurrentLAT = getIntent().getDoubleExtra("CurrentLAT",0);
        Double CurrentLON = getIntent().getDoubleExtra("CurrentLON",0);
        longitud.setText(String.valueOf(CurrentLON));
        latitud.setText(String.valueOf(CurrentLAT));
    }

    private void init() {
        find = (Button) findViewById(R.id.address_findAddress);
        longitud = (EditText) findViewById(R.id.address_longitude);
        latitud = (EditText) findViewById(R.id.address_latitude);
        addressText = (TextView) findViewById(R.id.address_addressText);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findLocation();
            }
        });
        resultReceiver = new AddressResultReceiver(new Handler());
    }

    private void findLocation() {
        address="";
        displayAddress(address);
        location = new Location("Point");
        location.setLatitude(Double.parseDouble(latitud.getText().toString()));
        location.setLongitude(Double.parseDouble(longitud.getText().toString()));
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER,resultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA,location);
        startService(intent);
    }

    private class AddressResultReceiver extends ResultReceiver {

        /**
         * Create a new ResultReceive to receive results.  Your
         * {@link #onReceiveResult} method will be called from the thread running
         * <var>handler</var> if given, or from an arbitrary thread if null.
         *
         * @param handler
         */
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            address = resultData.getString(Constants.RESULT_DATA_KEY);
            if(resultCode == Constants.FAIL_RESULT){
                Toast.makeText(AddressActivity.this, "Direccion no disponible", Toast.LENGTH_SHORT).show();
            }else{
                displayAddress(address);
            }
        }
    }

    private void displayAddress(String address){
        addressText.setText(address);
    }
}
