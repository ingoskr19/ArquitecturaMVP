package co.com.etn.arquitecturamvpbase.repositories.products;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.helper.ServicesFactory;
import co.com.etn.arquitecturamvpbase.models.ProductResponse;
import co.com.etn.arquitecturamvpbase.models.Product;
import co.com.etn.arquitecturamvpbase.repositories.MapperError;
import co.com.etn.arquitecturamvpbase.repositories.RepositoryError;
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
    public ProductResponse deleteProduct(String id) throws RepositoryError {
        try {
            return services.deleteProduct(id);
        } catch (RetrofitError retrofitError){
            throw MapperError.convertRetrofitErrorToRepositoryError(retrofitError);
        }
    }

    @Override
    public ProductResponse updateProduct(String id, Product product) throws RepositoryError {
        try {
            return services.updateProduct(id,product);
        } catch (RetrofitError retrofitError){
            throw MapperError.convertRetrofitErrorToRepositoryError(retrofitError);
        }
    }

}
