package co.com.etn.arquitecturamvpbase.repositories;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.helper.ServicesFactory;
import co.com.etn.arquitecturamvpbase.models.Product;
import co.com.etn.arquitecturamvpbase.services.IServices;
import retrofit.RetrofitError;

/**
 * Created by Erika on 16/09/2017.
 */

public class ProductRepository {

    private IServices services;

    public ProductRepository() {
        ServicesFactory servicesFactory = new ServicesFactory();
        services = (IServices) servicesFactory.getInstance(IServices.class);
    }

    public ArrayList<Product> getProducList() throws RetrofitError {
            ArrayList<Product> products = services.getProductsList();
            return products;
    }

    public Product addProduct(Product product) {
        return services.addProduct(product);
    }
}
