package co.com.etn.arquitecturamvpbase.presenters.customers;

import android.util.Log;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.models.Customer;
import co.com.etn.arquitecturamvpbase.presenters.BasePresenter;
import co.com.etn.arquitecturamvpbase.repositories.RepositoryError;
import co.com.etn.arquitecturamvpbase.repositories.customers.ICustomerRepository;
import co.com.etn.arquitecturamvpbase.views.customers.ICreateCustomerView;

/**
 * Created by Erika on 3/10/2017.
 */

public class CreateCustomerPresenter extends BasePresenter<ICreateCustomerView>{

    ICustomerRepository customerRepository;

    public CreateCustomerPresenter(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void createCustomer(Customer customer) {
        if(getValidateInternet().isConnected()){
            createThreadCreateCustomer(customer);
        } else {
            getView().showAlertDialog(R.string.no_conected_internet);
        }
    }

    public void createThreadCreateCustomer(final Customer customer) {
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                createCustomerService(customer);
            }
        });
        hilo.start();
    }

    public void createCustomerService(Customer customer) {
        try {
            Customer _customer = customerRepository.createCustomer(customer);
            getView().showMessage(R.string.customer_add_ok);
        } catch (RepositoryError repositoryError){
            Log.e("Catch Repository2",repositoryError.getMessage()+repositoryError.getCause());
            getView().showAlertDialog(repositoryError.getMessage());

        }
    }
}
