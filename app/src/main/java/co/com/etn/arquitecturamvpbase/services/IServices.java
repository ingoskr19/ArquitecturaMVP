package co.com.etn.arquitecturamvpbase.services;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.models.DeleteProductResponse;
import co.com.etn.arquitecturamvpbase.models.Product;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

import static android.R.attr.id;

/**
 * Created by Erika on 16/09/2017.
 */

public interface IServices
{
    @GET("/products")
    ArrayList<Product> getProductsList();

    @POST("/products")
    Product addProduct(@Body Product product);

    @DELETE("/products/{id}")
    DeleteProductResponse deleteProduct(@Path("id") String _id);

}
