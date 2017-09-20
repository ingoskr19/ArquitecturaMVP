package co.com.etn.arquitecturamvpbase.presenters;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.models.Product;
import co.com.etn.arquitecturamvpbase.repositories.ProductRepository;
import co.com.etn.arquitecturamvpbase.views.activities.IProductView;
import co.com.etn.arquitecturamvpbase.views.activities.ProductActivity;
import retrofit.RetrofitError;

/**
 * Created by Erika on 16/09/2017.
 */

public class ProductPresenter extends BasePresenter<IProductView> {

    private ProductRepository productRepository;

    public ProductPresenter() {
        this.productRepository = new ProductRepository();
    }

    public void validateInternetProduct() {
        if (getVaidateInternet().isConnected()){
            createThreadProduct();
        } else {
            //getView().showMessage("No esta conectado");
        }
    }

    private void createThreadProduct() {
        getView().showProgress(R.string.loading_message);
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
