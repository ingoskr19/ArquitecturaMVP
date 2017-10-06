package co.com.etn.arquitecturamvpbase.views.customers;

import co.com.etn.arquitecturamvpbase.views.IBaseView;

/**
 * Created by Erika on 3/10/2017.
 */

public interface ICreateCustomerView extends IBaseView{

    void showAlertDialog(String msg);
    void showAlertDialog(int no_conected_internet);

}
