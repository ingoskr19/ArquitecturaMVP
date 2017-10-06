package co.com.etn.arquitecturamvpbase.views.customers;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.models.Customer;
import co.com.etn.arquitecturamvpbase.views.IBaseView;

/**
 * Created by Erika on 3/10/2017.
 */

public interface ICustomerView extends IBaseView {

    void showAlertDialog(int no_conected_internet);
    void showCustomerList(ArrayList<Customer> arrayCustomer);
}
