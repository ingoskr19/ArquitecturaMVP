package co.com.etn.arquitecturamvpbase.services;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.models.Breakfast;
import co.com.etn.arquitecturamvpbase.models.Note;
import co.com.etn.arquitecturamvpbase.models.ProductResponse;
import co.com.etn.arquitecturamvpbase.models.Product;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

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
    ProductResponse deleteProduct(@Path("id") String _id);

    @PUT("/products/{id}")
    ProductResponse updateProduct(@Path("id") String _id, @Body Product product);

    @GET("/note.xml")
    Note getNote();

    @GET("/simple.xml")
    Breakfast getBreakfastMenu();

}
