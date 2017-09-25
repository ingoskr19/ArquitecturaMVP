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
        //TODO recibir el repositorio en el constructor
    }


    public void addProduct(final Product product) {
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                createProduct(product);
            }
        });
        hilo.start();
    }

    public void closeActivity(){
        getView().closeActivity();
    }

    public void createProduct(Product product){
        try {
            Product result = productRepository.addProduct(product);
            if(null != result && !"".equals(result.getId())){
                getView().showResultAdd("Se ha añadido correctamente el producto \""+product.getName()+"\"");
                closeActivity();
            } else {
                getView().showResultAdd("No se ha podido agregar ");
            }

        }catch (RetrofitError retrofitError){
            //TODO capturar error
        } catch (Throwable throwable) {
        } finally {
            getView().hideProgress();
        }

    }
}
