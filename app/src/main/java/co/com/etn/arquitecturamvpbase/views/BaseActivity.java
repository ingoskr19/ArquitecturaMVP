package co.com.etn.arquitecturamvpbase.views;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import co.com.etn.arquitecturamvpbase.helper.IVaidateInternet;
import co.com.etn.arquitecturamvpbase.helper.ShowAlertDialog;
import co.com.etn.arquitecturamvpbase.helper.ValidateInternet;
import co.com.etn.arquitecturamvpbase.presenters.BasePresenter;

/**
 * Created by Erika on 16/09/2017.
 */

public class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements IBaseView {

    private ProgressDialog progressDialog;
    private IVaidateInternet vaidateInternet;
    private T presenter;
    private ShowAlertDialog showAlertDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vaidateInternet = new ValidateInternet(getApplicationContext());
        this.showAlertDialog = new ShowAlertDialog(this);
    }

    public ShowAlertDialog getShowAlertDialog() {
        return showAlertDialog;
    }

    @Override
    public void showProgress(final int msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.setMessage(getResources().getString(msg));
                progressDialog.show();
            }
        });

    }

    public void createProgressDialog(){
        this.progressDialog = new ProgressDialog(this);
    }

    @Override
    public void hideProgress() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.hide();
            }
        });
    }

    @Override
    public void showMessage(final int msj) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, getString(msj), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void showMessage(final String msj) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, msj, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void closeActivity() {
        this.finish();
    }

    public IVaidateInternet getVaidateInternet() {
        return vaidateInternet;
    }

    public T getPresenter() {
        return presenter;
    }

    public void setPresenter(T presenter) {
        this.presenter = presenter;
    }
}
