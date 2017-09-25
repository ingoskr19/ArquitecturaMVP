package co.com.etn.arquitecturamvpbase.repositories.products;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.models.DeleteProductResponse;
import co.com.etn.arquitecturamvpbase.models.Product;

/**
 * Created by Erika on 23/09/2017.
 */

public interface IProductRepository {

    public ArrayList<Product> getProducList();

    public Product addProduct(Product product);

    public DeleteProductResponse deleteProduct(String id);
}
