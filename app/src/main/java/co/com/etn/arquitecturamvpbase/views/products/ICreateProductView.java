package co.com.etn.arquitecturamvpbase.views.products;

import co.com.etn.arquitecturamvpbase.models.Product;
import co.com.etn.arquitecturamvpbase.views.IBaseView;

/**
 * Created by Erika on 19/09/2017.
 */

public interface ICreateProductView extends IBaseView {

    void showCreateResponse(boolean resp);

    void closeActivity();

    void showMessageError(String s);

    void showAlertDialog(int error, int no_conected_internet);
}
