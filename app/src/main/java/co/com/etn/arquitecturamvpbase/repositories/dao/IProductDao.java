package co.com.etn.arquitecturamvpbase.repositories.dao;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.models.Product;

/**
 * Created by Erika on 2/10/2017.
 */

public interface IProductDao {
    public ArrayList<Product> fetchAllProducts();
    public Boolean createProduct(Product product);
    public Boolean updateProduct(Product product);
    public ArrayList<Product> fetchNotSyncProducts();
}
