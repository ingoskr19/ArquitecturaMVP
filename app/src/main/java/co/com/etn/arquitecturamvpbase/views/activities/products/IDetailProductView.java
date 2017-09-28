package co.com.etn.arquitecturamvpbase.views.activities.products;

import co.com.etn.arquitecturamvpbase.views.IBaseView;

/**
 * Created by Erika on 23/09/2017.
 */

public interface IDetailProductView extends IBaseView {

    void showAlertDialog(int no_conected_internet);
    void showToast(int message);
    void showToast(String message);
    void showAlertDialogError(int msg);
}
