package co.com.etn.arquitecturamvpbase.views.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.helper.Constants;
import co.com.etn.arquitecturamvpbase.models.Customer;
import co.com.etn.arquitecturamvpbase.views.activities.customers.PhoneListActivity;

/**
 * Created by Erika on 5/10/2017.
 */

public class CustomerAdapter extends ArrayAdapter<Customer> {

    private ArrayList<Customer> customerArrayList;
    private Activity context;
    private int resource;
    private Customer customer;
    private TextView name;
    private TextView surname;
    private TextView phone_number;
    private TextView phone_lat;
    private TextView phone_long;
    private LinearLayout showall;


    public  CustomerAdapter(Activity context, int resource, ArrayList<Customer> customerArrayList){
        super(context,resource, customerArrayList);
        this.context = context;
        this.resource = resource;
        this.customerArrayList = customerArrayList;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_item, parent, false);
        this.customer = this.customerArrayList.get(position);
        loadView(convertView);
        name.setText(customer.getName());
        surname.setText(customer.getSurname());
        if(customer.getPhonesList().size()>0) {
            phone_number.setText(customer.getPhonesList().get(0).getNumber());
            if(customer.getPhonesList().get(0).getLocation().getCoordinates().length>1){
                phone_lat.setText(String.valueOf(customer.getPhonesList().get(0).getLocation().getCoordinates()[0]));
                phone_long.setText(String.valueOf(customer.getPhonesList().get(0).getLocation().getCoordinates()[1]));
            }
        }

        showall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), PhoneListActivity.class);
                intent.putExtra(Constants.ITEM_CUSTOMER_PHONELIST,customer.getPhonesList());
                intent.putExtra(Constants.ITEM_CUSTOMER_POSITION,position);
                parent.getContext().startActivity(intent);
            }
        });
        return convertView;
    }

    private void loadView(View convertView) {
        name = (TextView) convertView.findViewById(R.id.customer_item_name);
        surname = (TextView) convertView.findViewById(R.id.customer_item_surname);
        phone_number = (TextView) convertView.findViewById(R.id.customer_item_phone_number);
        showall = (LinearLayout) convertView.findViewById(R.id.customer_item_phone_showall);
        phone_lat = (TextView) convertView.findViewById(R.id.customer_item_phone_lat);
        phone_long = (TextView) convertView.findViewById(R.id.customer_item_phone_long);
    }


}
