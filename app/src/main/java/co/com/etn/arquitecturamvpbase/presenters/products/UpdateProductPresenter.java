package co.com.etn.arquitecturamvpbase.presenters.products;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.models.Product;
import co.com.etn.arquitecturamvpbase.models.ProductResponse;
import co.com.etn.arquitecturamvpbase.presenters.BasePresenter;
import co.com.etn.arquitecturamvpbase.repositories.products.IProductRepository;
import co.com.etn.arquitecturamvpbase.repositories.RepositoryError;
import co.com.etn.arquitecturamvpbase.services.IServices;
import co.com.etn.arquitecturamvpbase.views.products.IUpdateProductView;

/**
 * Created by Erika on 26/09/2017.
 */

public class UpdateProductPresenter extends BasePresenter<IUpdateProductView> {

    private IServices services;
    private IProductRepository productRepository;

    public UpdateProductPresenter(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void updateProductRepository(String id, Product product) {
        try {
            ProductResponse productResponse = productRepository.updateProduct(id,product);
            if(productResponse.isStatus()){
                getView().showToast(R.string.update_ok);
            }else {
                getView().showAlertDialog(R.string.no_update);
            }
        } catch (RepositoryError repositoryError){
            getView().showToast(repositoryError.getMessage());
        }
    }

    public void updateProduct(String id, Product product) {
        updateProductThread(id,product);

    }

    public void updateProductThread(final String id, final Product product){
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                updateProductRepository(id,product);
            }
        });
        hilo.start();
    }
}
