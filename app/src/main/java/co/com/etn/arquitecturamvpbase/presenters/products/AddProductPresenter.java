package co.com.etn.arquitecturamvpbase.presenters.products;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.models.Product;
import co.com.etn.arquitecturamvpbase.presenters.BasePresenter;
import co.com.etn.arquitecturamvpbase.repositories.products.ProductRepository;
import co.com.etn.arquitecturamvpbase.views.activities.products.IAddProductView;
import retrofit.RetrofitError;

/**
 * Created by Erika on 19/09/2017.
 */

public class AddProductPresenter extends BasePresenter<IAddProductView> {


    private ProductRepository productRepository;

    public AddProductPresenter() {
        this.productRepository = new ProductRepository();
    }


    public void addProduct(final Product product) {
        if(getValidateInternet().isConnected()) {
            Thread hilo = new Thread(new Runnable() {
                @Override
                public void run() {
                    createProduct(product);
                }
            });
            hilo.start();
        }else{
            getView().showAlertDialog(R.string.error, R.string.no_conected_internet);
        }
    }

    public void createProduct(Product product){
        try {
            Product result = productRepository.addProduct(product);
            if(null != result && !"".equals(result.getId())){
                getView().showMessage("Se ha añadido correctamente el producto \""+product.getName()+"\"");
            } else {
                getView().showMessageError("No se ha podido crear el producto \""+product.getName()+"\"");
            }

        }catch (RetrofitError retrofitError){

        }

    }
}
