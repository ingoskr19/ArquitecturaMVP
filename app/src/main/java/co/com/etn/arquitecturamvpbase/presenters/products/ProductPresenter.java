package co.com.etn.arquitecturamvpbase.presenters.products;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.helper.Database;
import co.com.etn.arquitecturamvpbase.models.Product;
import co.com.etn.arquitecturamvpbase.presenters.BasePresenter;
import co.com.etn.arquitecturamvpbase.repositories.RepositoryError;
import co.com.etn.arquitecturamvpbase.repositories.products.IProductRepository;
import co.com.etn.arquitecturamvpbase.views.products.IProductView;
import retrofit.RetrofitError;

/**
 * Created by Erika on 16/09/2017.
 */

public class ProductPresenter extends BasePresenter<IProductView> {

    private IProductRepository productRepository;

    public ProductPresenter(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void listProduct() {
        createThreadProduct(getValidateInternet().isConnected());
    }

    private void createThreadProduct(final boolean conected) {
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                if(conected){
                    getProductList();
                } else {
                    getProductListDB();
                }
            }
        });
        hilo.start();
    }

    private void getProductList() {
        try {
            ArrayList<Product> list = productRepository.getProducList();
            getView().showProductList(list);
        } catch (RepositoryError repositoryError) {
            getView().showMessage(repositoryError.getMessage());
        } finally {
            getView().hideProgress();
        };
    }

    private void getProductListDB() {
        try {
            ArrayList<Product> list = Database.productDao.fetchAllProducts();
            getView().showProductList(list);
        } catch (Exception ex) {
            //getView().showAlertError(R.string.error, ex.getMessage());
            getView().showMessage(ex.getMessage());
        }
    }
}
