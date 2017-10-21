package co.com.etn.arquitecturamvpbase.views.activities.mapas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.helper.Constants;
import co.com.etn.arquitecturamvpbase.models.Location;
import co.com.etn.arquitecturamvpbase.models.Phone;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "MapsActivity";
    private GoogleMap mMap;
    public ArrayList<LatLng> points = new ArrayList<>();
    ArrayList<Phone> phoneList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        if(checkPlayServices()) {
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
        loadData();
    }

    private void loadData() {
        phoneList = (ArrayList<Phone>) getIntent().getSerializableExtra(Constants.ITEM_CUSTOMER_PHONELIST);
        for(Phone phone : phoneList){
            LatLng point = new LatLng(phone.getLocation().getCoordinates()[0],
                    phone.getLocation().getCoordinates()[1]);
            points.add(point);
        }
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);

        if(result != ConnectionResult.SUCCESS){
            if(googleAPI.isUserResolvableError(result)){
                googleAPI.getErrorDialog(this,result,9000).show();
            }
            return false;
        }
        return true;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        boolean success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this,R.raw.style_json));

        // Add a marker in Sydney and move the camera
        createMarkers();
        changeStateControls();

    }

    private void changeStateControls() {
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setZoomGesturesEnabled(true);
    }

    private void createMarkers() {
        for (int i = 0; i<points.size(); i++){
            mMap.addMarker(new MarkerOptions().position(points.get(i)).title(phoneList.get(i).getDescripcion())
                    .icon(bitMapDescriptorFromVecctor(this,R.drawable.ic_location)));
        }
        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(points.get(0) ,13));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .tilt(80)
                .target(points.get(0))
                .bearing(20)
                .zoom(13)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        calculateRoute();
    }

    RoutingListener routingListener = new RoutingListener() {
        @Override
        public void onRoutingFailure(RouteException e) {
            Log.e(TAG,e.getMessage());
        }

        @Override
        public void onRoutingStart() {
            Log.e(TAG,"Iniciando Ruta");
        }

        @Override
        public void onRoutingSuccess(ArrayList<Route> routes, int shortestRoutesIndex) {
            ArrayList polyLines = new ArrayList<>();

            for (int i = 0; i<routes.size(); i++){
                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.color(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                polylineOptions.width(10);
                polylineOptions.addAll(routes.get(i).getPoints());

                Polyline polyline = mMap.addPolyline(polylineOptions);
                polyLines.add(polyline);
                int distance = routes.get(i).getDistanceValue();
                int time = routes.get(i).getDistanceValue();
                //Toast.makeText(MapsActivity.this, "Distance: "+distance+ " Time: "+time, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onRoutingCancelled() {
            Log.e(TAG,"Ruta Cancelada");
        }
    };

    private void calculateRoute() {
        Routing rounting = new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .waypoints(points)
                .key(getString(R.string.google_maps_key))
                .optimize(false)
                .withListener(routingListener)
                //.alternativeRoutes(true)
                .build();
        rounting.execute();
        //centerJustPoints(points);

    }

    private void centerJustPoints(ArrayList<LatLng> points) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (int i=0;i<points.size();i++){
            builder.include(points.get(i));
        }
        LatLngBounds bounds = builder.build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds,50);
        mMap.animateCamera(cameraUpdate);

    }

    private BitmapDescriptor bitMapDescriptorFromVecctor(Context context, int vectorId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context,vectorId);
        vectorDrawable.setBounds(0,0,vectorDrawable.getIntrinsicWidth(),vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
