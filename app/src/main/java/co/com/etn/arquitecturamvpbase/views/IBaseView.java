package co.com.etn.arquitecturamvpbase.views;

/**
 * Created by Erika on 16/09/2017.
 */

public interface IBaseView {

    public void showProgress(int msg);
    public void hideProgress();
    public void showMessage(String msj);

}
