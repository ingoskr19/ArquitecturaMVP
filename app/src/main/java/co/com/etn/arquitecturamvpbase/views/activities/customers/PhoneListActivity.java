package co.com.etn.arquitecturamvpbase.views.activities.customers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.helper.Constants;
import co.com.etn.arquitecturamvpbase.models.Phone;
import co.com.etn.arquitecturamvpbase.views.adapters.CustomerAdapter;
import co.com.etn.arquitecturamvpbase.views.adapters.PhoneAdapter;

public class PhoneListActivity extends AppCompatActivity {

    private ArrayList<Phone> list;
    private int position;
    private PhoneAdapter phoneAdapter;
    private ListView customersListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_list_activity);
        getData();
        callAdapter(list);
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
