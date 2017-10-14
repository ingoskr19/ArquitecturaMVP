package co.com.etn.arquitecturamvpbase.views.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import co.com.etn.arquitecturamvpbase.R;
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

public class LoginActivity extends BaseActivity<LoginPresenter> implements ILoginView{

    private EditText email;
    private EditText password;
    private Button loginButton;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        setPresenter(new LoginPresenter(new LoginRepository()));
        getPresenter().inject(this,getVaidateInternet());
        init();
    }

    private void login(String email, String password){
        getPresenter().login(email,password);
    }

    private void init() {
        email = (EditText) findViewById(R.id.login_email);
        password = (EditText) findViewById(R.id.login_password);
        loginButton = (Button) findViewById(R.id.login_button_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(email.getText().toString(),password.getText().toString());
            }
        });
    }

    @Override
    public void showProfileview(Login login) {
        Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
        startActivity(intent);
    }
}
