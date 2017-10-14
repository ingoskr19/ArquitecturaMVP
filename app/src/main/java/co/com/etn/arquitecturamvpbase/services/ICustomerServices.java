package co.com.etn.arquitecturamvpbase.services;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.models.Customer;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by Erika on 3/10/2017.
 */

public interface ICustomerServices {
    @GET("/customers")
    ArrayList<Customer> getCustomersList();

    @POST("/customers")
    Customer createCustomer(@Body Customer customer);

}
