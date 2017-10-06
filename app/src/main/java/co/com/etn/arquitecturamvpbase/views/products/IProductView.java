package co.com.etn.arquitecturamvpbase.views.products;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.models.Product;
import co.com.etn.arquitecturamvpbase.views.IBaseView;

/**
 * Created by Erika on 16/09/2017.
 */

public interface IProductView extends IBaseView {

    void showProductList(ArrayList<Product> list);

    void showAlertDialog(int error, int no_conected_internet);

    void showAlertError(int error, int error_);
}
