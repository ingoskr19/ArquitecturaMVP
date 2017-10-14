package co.com.etn.arquitecturamvpbase.views.activities.customers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.models.Customer;
import co.com.etn.arquitecturamvpbase.presenters.customers.CustomerPresenter;
import co.com.etn.arquitecturamvpbase.repositories.customers.CustomerRepository;
import co.com.etn.arquitecturamvpbase.views.BaseActivity;
import co.com.etn.arquitecturamvpbase.views.adapters.CustomerAdapter;
import co.com.etn.arquitecturamvpbase.views.customers.ICustomerView;

public class CustomersActivity extends BaseActivity<CustomerPresenter> implements ICustomerView{

    private CustomerAdapter customerAdapter;
    private ListView customersList;
    private FloatingActionButton addCustomer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_customers);
        setPresenter(new CustomerPresenter(new CustomerRepository()));
        getPresenter().inject(this, getVaidateInternet());
        createProgressDialog();
        showProgress(R.string.loading_message);
        getPresenter().getCustomersList();

        addCustomer = (FloatingActionButton) findViewById(R.id.list_customer_add);
        addCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomersActivity.this,CreateCustomerActvity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void showAlertDialog(int msg) {
        showMessage(msg);
    }

    @Override
    public void showCustomerList(final ArrayList<Customer> arrayCustomer) {
        hideProgress();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                callAdapter(arrayCustomer);
            }
        });
    }

    private void callAdapter(final ArrayList<Customer> list) {
        customersList = (ListView) findViewById(R.id.customer_listview);
        customerAdapter = new CustomerAdapter(this,customersList.getId(),list);
        if(customerAdapter!=null) {
            customersList.setAdapter(customerAdapter);
        }else{
            showMessage("Era null adapater");
        }

        /*customersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CustomersActivity.this, DetailActivity.class);
                intent.putExtra(Constants.ITEM_PRODUCT,list.get(position));
                startActivity(intent);
            }
        });

        customersList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CustomersActivity.this, UpdateProductActivity.class);
                intent.putExtra(Constants.ITEM_PRODUCT,list.get(position));
                startActivity(intent);
                return true;
            }
        });*/
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
