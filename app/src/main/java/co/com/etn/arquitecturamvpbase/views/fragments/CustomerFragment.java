package co.com.etn.arquitecturamvpbase.views.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.models.Customer;
import co.com.etn.arquitecturamvpbase.presenters.customers.CustomerPresenter;
import co.com.etn.arquitecturamvpbase.presenters.products.ProductPresenter;
import co.com.etn.arquitecturamvpbase.repositories.customers.CustomerRepository;
import co.com.etn.arquitecturamvpbase.views.BaseFragments;
import co.com.etn.arquitecturamvpbase.views.activities.customers.CreateCustomerActvity;
import co.com.etn.arquitecturamvpbase.views.activities.customers.CustomersActivity;
import co.com.etn.arquitecturamvpbase.views.adapters.CustomerAdapter;
import co.com.etn.arquitecturamvpbase.views.customers.ICustomerView;
import co.com.etn.arquitecturamvpbase.views.products.IProductView;

/**
 * Created by Erika on 14/10/2017.
 */

public class CustomerFragment extends BaseFragments<CustomerPresenter> implements ICustomerView {

    private CustomerAdapter customerAdapter;
    private ListView customersList;
    private FloatingActionButton addCustomer;
    private View view;
    private SwipeRefreshLayout swipeRefresh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);
        this.view = inflater.inflate(R.layout.activity_list_customers,container,false);

        setPresenter(new CustomerPresenter(new CustomerRepository()));
        getPresenter().inject(this, getValidateInternet());
        getBaseActivity().createProgressDialog();
        showProgress(R.string.loading_message);
        getPresenter().getCustomersList();


        addCustomer = (FloatingActionButton) view.findViewById(R.id.list_customer_add);
        addCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),CreateCustomerActvity.class);
                startActivity(intent);
            }
        });

        customersList = (ListView) view.findViewById(R.id.customer_listview);
        customersList.setNestedScrollingEnabled(true);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.customer_listview_swipe);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPresenter().getCustomersList();
            }
        });

        return this.view;
    }

    @Override
    public void showAlertDialog(int msg) {
        showMessage(msg);
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showCustomerList(final ArrayList<Customer> arrayCustomer) {
        hideProgress();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                callAdapter(arrayCustomer);
            }
        });
    }

    private void callAdapter(final ArrayList<Customer> list) {
        swipeRefresh.setRefreshing(false);
        customersList = (ListView) this.view.findViewById(R.id.customer_listview);
        customerAdapter = new CustomerAdapter(getActivity(),customersList.getId(),list);
        if(customerAdapter!=null) {
            customersList.setAdapter(customerAdapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
