package co.com.etn.arquitecturamvpbase.presenters.products;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.models.Product;
import co.com.etn.arquitecturamvpbase.presenters.BasePresenter;
import co.com.etn.arquitecturamvpbase.repositories.ProductRepository;
import co.com.etn.arquitecturamvpbase.views.activities.products.AddProductActivity;
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
        getView().showProgress(R.string.loading_message);
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
        String msg = "Ha ocurrido un error agregando el producto";
        try {
            Product result = productRepository.addProduct(product);
            if(null != result && !"".equals(result.getId())){
                msg = "Se ha añadido correctamente el producto \""+product.getName()+"\"";
                closeActivity();
            } else {
                msg = "No se ha podido agregar ";
            }
        }catch (RetrofitError retrofitError){
            getView().showResultAdd("ERROR:"+retrofitError.getMessage());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            getView().showResultAdd("ERROR:"+throwable.getMessage());
        } finally {
            getView().hideProgress();
        }

        getView().showResultAdd(msg);
    }
}
