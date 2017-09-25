package co.com.etn.arquitecturamvpbase.presenters.products;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.models.DeleteProductResponse;
import co.com.etn.arquitecturamvpbase.presenters.BasePresenter;
import co.com.etn.arquitecturamvpbase.repositories.products.IProductRepository;
import co.com.etn.arquitecturamvpbase.repositories.products.ProductRepository;
import co.com.etn.arquitecturamvpbase.views.activities.products.IDetailProductView;
import retrofit.RetrofitError;

/**
 * Created by Erika on 23/09/2017.
 */

public class DetailProductPresenter extends BasePresenter<IDetailProductView> {

    private IProductRepository productRepository;

    public DetailProductPresenter(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void deleteProduct(String id) {
        if(getVaidateInternet().isConnected()){
            createThreadDeleteProduct(id);
        } else {
            getView().showAlertDialog(R.string.no_conected_internet);
        }
    }

    public void createThreadDeleteProduct(final String id) {
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                deleteProductRepository(id);
            }
        });
        hilo.start();
    }

    public void deleteProductRepository(String id){
        try {
            DeleteProductResponse deleteProductResponse = productRepository.deleteProduct(id);
            if(deleteProductResponse.isStatus()){
                getView().showToast(R.string.delete_ok);
            }else {
                getView().showAlertDialogError(R.string.no_deleted);
            }
        } catch (RetrofitError retrofitError){
            //TODO capturar exception
        }
    }
}
