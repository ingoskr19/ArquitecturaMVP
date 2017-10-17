package co.com.etn.arquitecturamvpbase.presenters.products;

import java.util.UUID;

import co.com.etn.arquitecturamvpbase.helper.Database;
import co.com.etn.arquitecturamvpbase.models.Product;
import co.com.etn.arquitecturamvpbase.presenters.BasePresenter;
import co.com.etn.arquitecturamvpbase.repositories.products.IProductRepository;
import co.com.etn.arquitecturamvpbase.views.products.ICreateProductView;
import retrofit.RetrofitError;

/**
 * Created by Erika on 19/09/2017.
 */

public class CreateProductPresenter extends BasePresenter<ICreateProductView> {


    private IProductRepository productRepository;

    public CreateProductPresenter(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createProduct(String name, String price, String quantity, String description) {
        Product product = new Product();
        product.setId(UUID.randomUUID().toString()); //WARNING
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setDescription(description);
        createThreadProduct(product,getValidateInternet().isConnected() );
    }

    public void createProduct(Product product) {
        //if (getValidateInternet().isConnected()){
        product.setId(UUID.randomUUID().toString()); //WARNING
        createThreadProduct(product,getValidateInternet().isConnected());
        //}else{
        //createThreadCreateProduct(product,true);
        //getView().showAlertInternet(R.string.error, R.string.validate_internet);
        //}
    }

    public void createThreadProduct(final Product product, final boolean isConnected) {
        //if(getValidateInternet().isConnected()) {
            Thread hilo = new Thread(new Runnable() {
                @Override
                public void run() {
                    //createProductService(product);
                    if(isConnected) {
                        createProductService(product);
                    }else {
                        createProductLocal(product);
                    }
                }
            });
            hilo.start();
       // }else{
       //     getView().showAlertDialog(R.string.error, R.string.no_conected_internet);
       // }
    }

    public void createProductService(Product product){
        try {
            Product result = productRepository.addProduct(product);
            getView().showMessage("Se ha añadido correctamente el producto \""+product.getName()+"\"");
        }catch (RetrofitError retrofitError){
            getView().showMessageError(retrofitError.getMessage());
        }
    }

    public void createProductLocal(Product product){
        try {
            boolean isCreated = Database.productDao.createProduct(product);
            getView().showCreateResponse(isCreated);
        }catch (Exception ex){
            getView().showMessageError(ex.getMessage());
        }
    }
}
