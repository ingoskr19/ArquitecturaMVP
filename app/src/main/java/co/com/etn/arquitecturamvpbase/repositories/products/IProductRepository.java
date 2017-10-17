package co.com.etn.arquitecturamvpbase.repositories.products;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.models.ProductResponse;
import co.com.etn.arquitecturamvpbase.models.Product;
import co.com.etn.arquitecturamvpbase.repositories.RepositoryError;

/**
 * Created by Erika on 23/09/2017.
 */

public interface IProductRepository {

    public ArrayList<Product> getProducList() throws RepositoryError;

    public Product addProduct(Product product);

    public ProductResponse deleteProduct(String id) throws RepositoryError;

    public ProductResponse updateProduct(String id, Product product) throws RepositoryError;
}
