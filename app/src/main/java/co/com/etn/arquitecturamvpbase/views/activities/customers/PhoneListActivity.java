package co.com.etn.arquitecturamvpbase.views.activities.customers;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.helper.Constants;
import co.com.etn.arquitecturamvpbase.helper.LocationService;
import co.com.etn.arquitecturamvpbase.models.Phone;
import co.com.etn.arquitecturamvpbase.views.activities.mapas.AddressActivity;
import co.com.etn.arquitecturamvpbase.views.activities.mapas.MapsActivity;
import co.com.etn.arquitecturamvpbase.views.adapters.CustomerAdapter;
import co.com.etn.arquitecturamvpbase.views.adapters.PhoneAdapter;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class PhoneListActivity extends AppCompatActivity {

    private static final int ALL_PERMISSIONS_RESULT = 101;
    private static final String TAG = "PhoneListActivity";
    private ArrayList<Phone> list;
    private int position;
    private PhoneAdapter phoneAdapter;
    private ListView customersListView;
    private FloatingActionButton viewMaps;

    private FloatingActionButton getLocationButton;
    private LocationService locationService;

    private ArrayList<String> permissionsToRequest = new ArrayList<>();
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_list_activity);
        getData();
        callAdapter(list);
        init();

    }

    private void init() {
        viewMaps = (FloatingActionButton) findViewById(R.id.list_phone_viewMaps);
        viewMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMyLocation();
                Intent intent = new Intent(PhoneListActivity.this, MapsActivity.class);
                intent.putExtra(Constants.ITEM_CUSTOMER_PHONELIST,list);
                intent.putExtra("CurrentLAT",locationService.getLatitude());
                intent.putExtra("CurrentLON",locationService.getLongitude());
                startActivity(intent);
            }
        });

        getLocationButton = (FloatingActionButton) findViewById(R.id.list_phone_getLocation);
        getLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getMyLocation();
                Intent intent = new Intent(PhoneListActivity.this, AddressActivity.class);
                intent.putExtra("CurrentLAT",locationService.getLatitude());
                intent.putExtra("CurrentLON",locationService.getLongitude());
                startActivity(intent);
            }
        });

        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (permissionsToRequest.size()>0){
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]),ALL_PERMISSIONS_RESULT);
            }
        }

        permissionsToRequest = findUnAskedPermissions(permissions);

        locationService = new LocationService(PhoneListActivity.this);
    }

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> permissions) {
        ArrayList<String> results = new ArrayList<String>();
        for (String permission : permissions){
            if(!hasPermission(permission)){
                results.add(permission);
            }
        }

        return results;
    }

    private boolean hasPermission(String permission) {
        if (canMakeMatch()){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    //@TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case ALL_PERMISSIONS_RESULT:
                for (String permission: permissionsToRequest){
                    if(!hasPermission(permission)){
                        permissionsRejected.add(permission);
                    }
                }
                if (permissionsRejected.size() > 0){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))){
                            showMessageOKCancel("Este permiso es necesario para continuar usando esta aplicacion",
                                    new DialogInterface.OnClickListener(){
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(
                                                        new String[permissionsRejected.size()]),
                                                        ALL_PERMISSIONS_RESULT
                                                );
                                            }
                                        }
                                    });
                        }
                    }
                }

        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(PhoneListActivity.this)
                .setMessage(message)
                .setPositiveButton("OK",onClickListener)
                .setNegativeButton("CANCELAR",null)
                .create()
                .show();
    }

    private boolean canMakeMatch() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    private void getMyLocation() {
        if (locationService.isCanGetLocation()){
            double longitude = locationService.getLongitude();
            double latitude = locationService.getLatitude();
            //Toast.makeText(this, "Latitude "+latitude+" - Longitude "+longitude, Toast.LENGTH_LONG).show();
        } else {
            locationService.showSettingsAlert();
        }
    }

    private void getData() {
        list = (ArrayList<Phone>) getIntent().getSerializableExtra(Constants.ITEM_CUSTOMER_PHONELIST);
        position = (int) getIntent().getSerializableExtra(Constants.ITEM_CUSTOMER_POSITION);
    }

    private void callAdapter(final ArrayList<Phone> list) {
        if(list.size()>0) {
            customersListView = (ListView) findViewById(R.id.phone_listview);
            phoneAdapter = new PhoneAdapter(this, customersListView.getId(), list);
            if (phoneAdapter != null) {
                customersListView.setAdapter(phoneAdapter);
            }

            /*customersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(CustomersActivity.this, DetailActivity.class);
                    intent.putExtra(Constants.ITEM_PRODUCT,list.get(position));
                    startActivity(intent);
                }
            });
            */
            customersListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(PhoneListActivity.this, "Aun no hace nada, lo siento :(", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }
    }
}
