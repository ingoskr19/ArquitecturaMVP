package co.com.etn.arquitecturamvpbase.views;


import android.content.pm.ProviderInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import co.com.etn.arquitecturamvpbase.helper.IVaidateInternet;
import co.com.etn.arquitecturamvpbase.presenters.BasePresenter;

/**
 * Created by Erika on 14/10/2017.
 */

public class BaseFragments<T extends BasePresenter> extends Fragment implements IBaseView {

    private BaseActivity baseActivity;
    private IVaidateInternet validateInternet;
    private T presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseActivity = (BaseActivity) getActivity();
        validateInternet = baseActivity.getVaidateInternet();
    }

    @Override
    public void showProgress(int msg) {
        //baseActivity.showProgress(msg);
    }

    @Override
    public void hideProgress() {
        //baseActivity.hideProgress();
    }

    @Override
    public void showMessage(int msj) {
        //baseActivity.showMessage(msj);
    }

    @Override
    public void showMessage(String msj) {
       // baseActivity.showMessage(msj);
    }

    @Override
    public void closeActivity() {
        //baseActivity.closeActivity();
    }

    public BaseActivity getBaseActivity() {
        return baseActivity;
    }

    public void setBaseActivity(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    public IVaidateInternet getValidateInternet() {
        return validateInternet;
    }

    public void setValidateInternet(IVaidateInternet validateInternet) {
        this.validateInternet = validateInternet;
    }

    public T getPresenter() {
        return presenter;
    }

    public void setPresenter(T presenter) {
        this.presenter = presenter;
    }
}
