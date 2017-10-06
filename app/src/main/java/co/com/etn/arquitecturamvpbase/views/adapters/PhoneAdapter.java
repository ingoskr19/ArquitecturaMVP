package co.com.etn.arquitecturamvpbase.views.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.models.Phone;
import co.com.etn.arquitecturamvpbase.models.Product;

/**
 * Created by Erika on 5/10/2017.
 */

public class PhoneAdapter extends ArrayAdapter<Phone>{

    private ArrayList<Phone> phoneArrayList;
    private Activity context;
    private int resource;
    private Phone phone;
    private TextView number;
    private TextView lat;
    private TextView _long;

    public  PhoneAdapter(Activity context, int resource, ArrayList<Phone> phoneArrayList){
        super(context,resource,phoneArrayList);
        this.context = context;
        this.resource = resource;
        this.phoneArrayList = phoneArrayList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_phone_item, parent, false);
        this.phone = this.phoneArrayList.get(position);
        loadView(convertView);
        number.setText(phone.getNumber());
        lat.setText(getCoordinate(0));
        _long.setText(getCoordinate(1));
        return convertView;
    }

    public String getCoordinate(int position){
        double[] coordenate = phone.getLocation().getCoordinates();
        if(null!=coordenate){
            if(coordenate.length>1){
                return String.valueOf(coordenate[position]);
            }
        }
        return "";
    }

    private void loadView(View convertView) {
        number = (TextView) convertView.findViewById(R.id.phone_customer_item_phone_number);
        lat = (TextView) convertView.findViewById(R.id.phone_customer_item_phone_lat);
        _long = (TextView) convertView.findViewById(R.id.phone_customer_item_phone_long);
    }
}
