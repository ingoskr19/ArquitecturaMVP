package co.com.etn.arquitecturamvpbase.presenters.login;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.models.Login;
import co.com.etn.arquitecturamvpbase.presenters.BasePresenter;
import co.com.etn.arquitecturamvpbase.repositories.ILoginRepository;
import co.com.etn.arquitecturamvpbase.repositories.RepositoryError;
import co.com.etn.arquitecturamvpbase.repositories.customers.ICustomerRepository;
import co.com.etn.arquitecturamvpbase.views.activities.login.ILoginView;
import co.com.etn.arquitecturamvpbase.views.customers.ICustomerView;
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

    public void loginRepository(String email, String password) {
        try {
            Login login = loginRepository.login(email,password);
            getView().showProfileview(login);
        } catch (RetrofitError retrofitError){
            String s =  new String(((TypedByteArray)retrofitError.getResponse().getBody()).getBytes());
            getView().showMessage(s);
        }
    }
}
