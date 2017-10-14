package co.com.etn.arquitecturamvpbase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.helper.IVaidateInternet;
import co.com.etn.arquitecturamvpbase.models.Customer;
import co.com.etn.arquitecturamvpbase.models.Location;
import co.com.etn.arquitecturamvpbase.models.Phone;
import co.com.etn.arquitecturamvpbase.presenters.customers.CreateCustomerPresenter;
import co.com.etn.arquitecturamvpbase.presenters.customers.CustomerPresenter;
import co.com.etn.arquitecturamvpbase.repositories.RepositoryError;
import co.com.etn.arquitecturamvpbase.repositories.customers.ICustomerRepository;
import co.com.etn.arquitecturamvpbase.views.customers.ICreateCustomerView;
import co.com.etn.arquitecturamvpbase.views.customers.ICustomerView;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Erika on 3/10/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomerPresenterTest {

    @Mock
    IVaidateInternet validateInternet;

    @Mock
    ICustomerRepository customerRepository;

    @Mock
    ICreateCustomerView createCustomerView;

    @Mock
    ICustomerView customerView;

    CreateCustomerPresenter createCustomerPresenter;
    CustomerPresenter customerPresenter;

    @InjectMocks
    Customer customer;

    @Before
    public void setUp() throws Exception {
        customerPresenter = Mockito.spy(new CustomerPresenter(customerRepository));
        customerPresenter.inject(customerView, validateInternet);

        createCustomerPresenter = Mockito.spy(new CreateCustomerPresenter(customerRepository));
        createCustomerPresenter.inject(createCustomerView, validateInternet);
    }

    public Customer fillCustomer(){
        Customer customer = new Customer();
        customer.setName("Customer1");
        customer.setSurname("Customer__11");
        ArrayList<Phone> phoneList = new ArrayList<>();
        Phone phone = new Phone();
        phone.setNumber("1234567890");
        Location location = new Location();
        location.setType("Point");
        location.setCoordinates(new double[]{-75.090991,-59.900002});
        phone.setLocation(location);
        phoneList.add(phone);
        customer.setPhonesList(phoneList);
        return customer;
    }

    @Test
    public void methodValidateInternetShouldCallMethodwithoutConneccion(){
        customerPresenter.getCustomersList();
        when(validateInternet.isConnected()).thenReturn(false);
        verify(customerView).showAlertDialog(R.string.no_conected_internet);
        verify(customerPresenter,never()).createThreadGetCustomersList();
    }

    @Test
    public void methodValidateInternetShouldCallMethodCreateThreadGetCustomersList(){
        when(validateInternet.isConnected()).thenReturn(true);
        customerPresenter.createThreadGetCustomersList();
        verify(customerPresenter).createThreadGetCustomersList();
        verify(customerView,never()).showAlertDialog(R.string.no_conected_internet);
    }

    @Test
    public void methodValidateInternetShouldCallMethodGetCustomerRepositoryExist() throws RepositoryError {
        Customer customer = fillCustomer();
        ArrayList<Customer> arrayCustomer = new ArrayList<>();
        arrayCustomer.add(customer);
        customerPresenter.getCustomersListService();
        when(customerRepository.getCustomersList()).thenReturn(arrayCustomer);
        //verify(customerView).showCustomerList(arrayCustomer);
    }

    @Test
    public void methodValidateInternetShouldCallMethodCreateWithoutConneccion(){

        Customer customer = fillCustomer();
        when(validateInternet.isConnected()).thenReturn(false);
        createCustomerPresenter.createCustomer(customer);
        verify(createCustomerView).showAlertDialog(R.string.no_conected_internet);
        verify(createCustomerPresenter,never()).createThreadCreateCustomer(customer);
    }

    @Test
    public void methodValidateInternetShouldCallMethodCreateThreadCreatetCustomersList(){
        Customer customer = fillCustomer();
        when(validateInternet.isConnected()).thenReturn(true);
        createCustomerPresenter.createThreadCreateCustomer(customer);
        verify(createCustomerPresenter).createThreadCreateCustomer(customer);
        verify(createCustomerView,never()).showAlertDialog(R.string.no_conected_internet);
    }

    @Test
    public void methodValidateInternetShouldCallMethodCreateCustomerRepository() throws RepositoryError {
        Customer customer = fillCustomer();
        ArrayList<Customer> arrayCustomer = new ArrayList<>();
        arrayCustomer.add(customer);
        when(customerRepository.createCustomer(customer)).thenReturn(customer);
        createCustomerPresenter.createCustomerService(customer);
        verify(createCustomerView).showMessage(R.string.customer_add_ok);
        verify(createCustomerView,never()).showAlertDialog(R.string.error);
    }


}
