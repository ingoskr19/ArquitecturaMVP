package co.com.etn.arquitecturamvpbase.repositories;

import co.com.etn.arquitecturamvpbase.helper.ServicesFactory;
import co.com.etn.arquitecturamvpbase.models.Login;
import co.com.etn.arquitecturamvpbase.services.ICustomerServices;
import co.com.etn.arquitecturamvpbase.services.ILoginServices;
import retrofit.RetrofitError;

/**
 * Created by Erika on 14/10/2017.
 */

public class LoginRepository implements ILoginRepository {

    ILoginServices service;

    public LoginRepository() {
        ServicesFactory servicesFactory = new ServicesFactory();
        service = (ILoginServices) servicesFactory.getInstance(ILoginServices.class);
    }

    @Override
    public Login login(String email, String password) throws RetrofitError {
            return service.login(email,password);
    }
}
