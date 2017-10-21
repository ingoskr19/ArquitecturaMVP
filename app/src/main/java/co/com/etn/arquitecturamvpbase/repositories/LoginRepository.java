package co.com.etn.arquitecturamvpbase.repositories;

import android.content.SharedPreferences;

import co.com.etn.arquitecturamvpbase.helper.ServicesFactory;
import co.com.etn.arquitecturamvpbase.models.Login;
import co.com.etn.arquitecturamvpbase.services.ILoginServices;
import retrofit.RetrofitError;

/**
 * Created by Erika on 14/10/2017.
 */

public class LoginRepository implements ILoginRepository {

    ILoginServices service;
    SharedPreferences sharedPreferences;

    public LoginRepository(SharedPreferences sharedPreference) {
        ServicesFactory servicesFactory = new ServicesFactory();
        service = (ILoginServices) servicesFactory.getInstance(ILoginServices.class);
        this.sharedPreferences = sharedPreference;
    }

    @Override
    public Login login(String email, String password) throws RetrofitError {
            return service.login(email,password);
    }

    @Override
    public Login login(String token) throws RetrofitError {
            return service.login("bearer:"+token);
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
