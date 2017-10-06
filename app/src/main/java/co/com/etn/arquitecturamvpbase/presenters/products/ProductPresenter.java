package co.com.etn.arquitecturamvpbase.presenters.products;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.helper.Database;
import co.com.etn.arquitecturamvpbase.models.Product;
import co.com.etn.arquitecturamvpbase.presenters.BasePresenter;
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
        if (getValidateInternet().isConnected()){
            createThreadProduct(true);
        } else {
            createThreadProduct(false);
            getView().showAlertDialog(R.string.error, R.string.no_conected_internet);
        }
    }

    private void createThreadProduct(final boolean conected) {
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                getProductList(conected);
            }
        });
        hilo.start();
    }

    private void getProductList(boolean connected) {
        try {
            if(connected) {
                ArrayList<Product> list = productRepository.getProducList();
                getView().showProductList(list);
            }else {
                ArrayList<Product> list = Database.productDao.fetchAllProducts();
                getView().showProductList(list);
            }
        } catch (RetrofitError retrofitError) {
            getView().showAlertError(R.string.error, R.string.error_desconocido);
        } finally {
            getView().hideProgress();
        };
    }
}
