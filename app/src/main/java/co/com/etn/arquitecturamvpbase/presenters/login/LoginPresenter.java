package co.com.etn.arquitecturamvpbase.presenters.login;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.models.Login;
import co.com.etn.arquitecturamvpbase.presenters.BasePresenter;
import co.com.etn.arquitecturamvpbase.repositories.ILoginRepository;
import co.com.etn.arquitecturamvpbase.views.activities.login.ILoginView;
import retrofit.RetrofitError;
import retrofit.mime.TypedByteArray;

/**
 * Created by Erika on 14/10/2017.
 */

public class LoginPresenter extends BasePresenter<ILoginView> {
    ILoginRepository loginRepository;

    public LoginPresenter(ILoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public void login(String email, String password) {
        if(getValidateInternet().isConnected()){
            createThreadLogin(email,password);
        }else {
            Gson gson = new Gson();
            String data = loginRepository.getSharedPreferences().getString("login","");
            if("".equals(data)) {
                getView().showMessage(R.string.no_conected_internet);
            } else {
                Login login = gson.fromJson(data,Login.class);
                getView().showProfileview(login);
            }
        }
    }

    public void login(String token) {
        if(getValidateInternet().isConnected()){
            createThreadLoginWithToken(token);
        }else {
            getView().showMessage(R.string.no_conected_internet);
        }
    }


    public void createThreadLogin(final String email, final String password) {
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                loginRepository(email,password);
            }
        });
        hilo.start();
    }

    public void createThreadLoginWithToken(final String token) {
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                loginRepositoryWithToken(token);
            }
        });
        hilo.start();
    }

    public void loginRepository(String email, String password) {
        try {
            Login login = loginRepository.login(email,password);
            getView().showProfileview(login);
        } catch (RetrofitError retrofitError){
            String s =  new String(((TypedByteArray)retrofitError.getResponse().getBody()).getBytes());
            getView().showMessage(s);
        }
    }

    public void loginRepositoryWithToken(String token) {
        try {
            Login login = loginRepository.login(token);
            getView().showProfileview(login);
        } catch (RetrofitError retrofitError){
            String s =  new String(((TypedByteArray)retrofitError.getResponse().getBody()).getBytes());
            getView().showMessage(s);
        }
    }

}
