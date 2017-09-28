package co.com.etn.arquitecturamvpbase.presenters.products;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.models.ProductResponse;
import co.com.etn.arquitecturamvpbase.presenters.BasePresenter;
import co.com.etn.arquitecturamvpbase.repositories.products.IProductRepository;
import co.com.etn.arquitecturamvpbase.repositories.products.RepositoryError;
import co.com.etn.arquitecturamvpbase.views.activities.products.IDetailProductView;

/**
 * Created by Erika on 23/09/2017.
 */

public class DetailProductPresenter extends BasePresenter<IDetailProductView> {

    private IProductRepository productRepository;

    public DetailProductPresenter(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void deleteProduct(String id) {
        createThreadDeleteProduct(id);
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
            ProductResponse productResponse = productRepository.deleteProduct(id);
            if(productResponse.isStatus()){
                getView().showToast(R.string.delete_ok);
            }else {
                getView().showAlertDialogError(R.string.no_deleted);
            }
        } catch (RepositoryError repositoryError){
            getView().showToast(repositoryError.getMessage());
        }
    }
}
