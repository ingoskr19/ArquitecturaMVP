package co.com.etn.arquitecturamvpbase.views.products;

import co.com.etn.arquitecturamvpbase.views.IBaseView;

/**
 * Created by Erika on 26/09/2017.
 */

public interface IUpdateProductView extends IBaseView{

    public void showToast(int update_ok);

    public void showAlertDialog(int update_fail);

    public void showToast(String message);
}
