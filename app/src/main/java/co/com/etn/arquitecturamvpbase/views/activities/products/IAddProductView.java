package co.com.etn.arquitecturamvpbase.views.activities.products;

import co.com.etn.arquitecturamvpbase.models.Product;
import co.com.etn.arquitecturamvpbase.views.IBaseView;

/**
 * Created by Erika on 19/09/2017.
 */

public interface IAddProductView extends IBaseView {

    void showResultAdd(String msg);

    void closeActivity();
}
