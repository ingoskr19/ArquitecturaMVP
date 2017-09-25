package co.com.etn.arquitecturamvpbase.repositories.products;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.helper.ServicesFactory;
import co.com.etn.arquitecturamvpbase.models.DeleteProductResponse;
import co.com.etn.arquitecturamvpbase.models.Product;
import co.com.etn.arquitecturamvpbase.repositories.products.IProductRepository;
import co.com.etn.arquitecturamvpbase.services.IServices;
import retrofit.RetrofitError;

/**
 * Created by Erika on 16/09/2017.
 */

public class ProductRepository implements IProductRepository {

    private IServices services;

    public ProductRepository() {
        ServicesFactory servicesFactory = new ServicesFactory();
        services = (IServices) servicesFactory.getInstance(IServices.class);
    }

    @Override
    public ArrayList<Product> getProducList() throws RetrofitError {
            ArrayList<Product> products = services.getProductsList();
            return products;
    }

    @Override
    public Product addProduct(Product product) {
        return services.addProduct(product);
    }

    @Override
    public DeleteProductResponse deleteProduct(String id) {
        return services.deleteProduct(id);
    }

}
