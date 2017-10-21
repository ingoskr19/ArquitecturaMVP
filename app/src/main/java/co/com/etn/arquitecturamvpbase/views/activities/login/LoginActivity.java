package co.com.etn.arquitecturamvpbase.views.activities.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.helper.Constants;
import co.com.etn.arquitecturamvpbase.helper.CustomSharedPreferences;
import co.com.etn.arquitecturamvpbase.models.Login;
import co.com.etn.arquitecturamvpbase.models.Product;
import co.com.etn.arquitecturamvpbase.presenters.login.LoginPresenter;
import co.com.etn.arquitecturamvpbase.presenters.products.CreateProductPresenter;
import co.com.etn.arquitecturamvpbase.repositories.ILoginRepository;
import co.com.etn.arquitecturamvpbase.repositories.LoginRepository;
import co.com.etn.arquitecturamvpbase.repositories.products.ProductRepository;
import co.com.etn.arquitecturamvpbase.views.BaseActivity;
import co.com.etn.arquitecturamvpbase.views.activities.DashBoardActivity;

/**
 * Created by Erika on 14/10/2017.
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements ILoginView, TextWatcher{

    private EditText email;
    private EditText password;
    private Button loginButton;
    private CustomSharedPreferences customSharedPreferences;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        createProgressDialog();
        showProgress(R.string.loading_message);
        setPresenter(new LoginPresenter(new LoginRepository(getSharedPreferences(Constants.SP_LOGIN, Context.MODE_PRIVATE))));
        customSharedPreferences = new CustomSharedPreferences(this);
        getPresenter().inject(this,getVaidateInternet());
        init();
        validateAutoLogin();
    }

    private void validateAutoLogin() {
        Login login = customSharedPreferences.getObjectUser(Constants.SP_LOGIN);
        if(login != null && !"".equals(login.getToken())){
            getPresenter().login(login.getToken());
        } else {
            hideProgress();
        }

    }

    private void login(String email, String password){
        getPresenter().login(email,password);
    }

    private void init() {
        email = (EditText) findViewById(R.id.login_email);
        password = (EditText) findViewById(R.id.login_password);
        email.addTextChangedListener(this);
        password.addTextChangedListener(this);
        loginButton = (Button) findViewById(R.id.login_button_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(email.getText().toString(),password.getText().toString());
            }
        });
        disableButton(loginButton);
    }

    @Override
    public void showProfileview(Login login) {
        customSharedPreferences.saveObjectUser(Constants.SP_LOGIN,login);
        Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
        startActivity(intent);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(!"".equals(String.valueOf(email.getText().toString()))
                && !"".equals(String.valueOf(password.getText().toString()))
                ){
            loginButton.setEnabled(true);
            enableButton(loginButton);
        }else{
            loginButton.setEnabled(false);
            disableButton(loginButton);
        }
    }

    public void enableButton(Button button){
        button.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary));
        button.setTextColor(ContextCompat.getColor(this,R.color.colorWhite));
    }

    public void disableButton(Button button){
        button.setBackgroundColor(ContextCompat.getColor(this,R.color.colorGray));
        button.setTextColor(ContextCompat.getColor(this,R.color.colorWhite));
    }
}
