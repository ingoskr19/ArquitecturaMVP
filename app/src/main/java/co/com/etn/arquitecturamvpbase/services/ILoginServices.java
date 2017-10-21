package co.com.etn.arquitecturamvpbase.services;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.models.Login;
import co.com.etn.arquitecturamvpbase.models.Product;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Erika on 14/10/2017.
 */

public interface ILoginServices {
    @GET("/user/auth")
    Login login(@Query("email") String email, @Query("password") String password);

    @GET("/user")
    Login login(@Header("Authorization") String token);
}
