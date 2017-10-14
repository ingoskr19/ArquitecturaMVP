package co.com.etn.arquitecturamvpbase.repositories;

import co.com.etn.arquitecturamvpbase.models.Login;
import retrofit.RetrofitError;

/**
 * Created by Erika on 14/10/2017.
 */

public interface ILoginRepository {
    public Login login(String email, String password) throws RetrofitError;
}
