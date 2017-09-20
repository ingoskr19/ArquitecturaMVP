package co.com.etn.arquitecturamvpbase.services;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.models.Product;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by Erika on 16/09/2017.
 */

public interface IServices
{
    @GET("/products")
    ArrayList<Product> getProductsList();

    @POST("/products")
    Product addProduct(@Body Product product);
}
