package co.com.etn.arquitecturamvpbase.repositories.customers;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.helper.ServicesFactory;
import co.com.etn.arquitecturamvpbase.models.Customer;
import co.com.etn.arquitecturamvpbase.repositories.MapperError;
import co.com.etn.arquitecturamvpbase.repositories.RepositoryError;
import co.com.etn.arquitecturamvpbase.services.ICustomerServices;
import retrofit.RetrofitError;

/**
 * Created by Erika on 3/10/2017.
 */

public class CustomerRepository implements ICustomerRepository {

    ICustomerServices service;

    public CustomerRepository() {
        ServicesFactory servicesFactory = new ServicesFactory();
        service = (ICustomerServices) servicesFactory.getInstance(ICustomerServices.class);
    }

    @Override
    public ArrayList<Customer> getCustomersList() throws RepositoryError {
        try {
            return service.getCustomersList();
        } catch (RetrofitError retrofitError){
            throw MapperError.convertRetrofitErrorToRepositoryError(retrofitError);
        }
    }

    @Override
    public Customer createCustomer(Customer customer) throws RepositoryError {
        try {
            return service.createCustomer(customer);
        } catch (RetrofitError retrofitError){
            throw MapperError.convertRetrofitErrorToRepositoryError(retrofitError);
        }
    }
}
