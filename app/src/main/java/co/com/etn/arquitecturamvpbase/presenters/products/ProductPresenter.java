package co.com.etn.arquitecturamvpbase.presenters.products;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.models.Product;
import co.com.etn.arquitecturamvpbase.presenters.BasePresenter;
import co.com.etn.arquitecturamvpbase.repositories.products.ProductRepository;
import co.com.etn.arquitecturamvpbase.views.activities.products.IProductView;
import retrofit.RetrofitError;

/**
 * Created by Erika on 16/09/2017.
 */

public class ProductPresenter extends BasePresenter<IProductView> {

    private ProductRepository productRepository;

    public ProductPresenter() {
        this.productRepository = new ProductRepository();
        //TODO recibir el repositorio en el constructor
    }

    public void validateInternetProduct() {
        if (getVaidateInternet().isConnected()){
            createThreadProduct();
        } else {
            //getView().showMessage("No esta conectado");
        }
    }

    private void createThreadProduct() {
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                getProductList();
            }
        });
        hilo.start();
    }

    private void getProductList() {
        try {
            ArrayList<Product> list = productRepository.getProducList();
            getView().showProductList(list);
        } catch (RetrofitError retrofitError) {
           //TODO: capturar error
        } finally {
            getView().hideProgress();
        };
    }
}