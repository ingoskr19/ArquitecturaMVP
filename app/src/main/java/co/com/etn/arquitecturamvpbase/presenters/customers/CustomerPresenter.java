package co.com.etn.arquitecturamvpbase.presenters.customers;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.models.Customer;
import co.com.etn.arquitecturamvpbase.presenters.BasePresenter;
import co.com.etn.arquitecturamvpbase.repositories.RepositoryError;
import co.com.etn.arquitecturamvpbase.repositories.customers.ICustomerRepository;
import co.com.etn.arquitecturamvpbase.views.customers.ICustomerView;

/**
 * Created by Erika on 3/10/2017.
 */

public class CustomerPresenter extends BasePresenter<ICustomerView> {

    ICustomerRepository customerRepository;

    public CustomerPresenter(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void getCustomersList() {
        if(getValidateInternet().isConnected()){
            createThreadGetCustomersList();
        }else{
            getView().showAlertDialog(R.string.no_conected_internet);
        }
    }

    public void createThreadGetCustomersList() {
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                getCustomersListService();
            }
        });
        hilo.start();
    }

    public void getCustomersListService() {
        try{
            ArrayList<Customer> list = customerRepository.getCustomersList();
            getView().showCustomerList(list);
        } catch (RepositoryError repositoryError){
            getView().showAlertDialog(R.string.error);
        }

    }
}
