package co.com.etn.arquitecturamvpbase.repositories.customers;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.models.Customer;
import co.com.etn.arquitecturamvpbase.repositories.RepositoryError;

/**
 * Created by Erika on 3/10/2017.
 */

public interface ICustomerRepository {

    ArrayList<Customer> getCustomersList() throws RepositoryError;

    Customer createCustomer(Customer customer) throws RepositoryError;
}
