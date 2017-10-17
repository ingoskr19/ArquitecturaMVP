package co.com.etn.arquitecturamvpbase.synchronizers;

import android.util.Log;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.helper.Database;
import co.com.etn.arquitecturamvpbase.models.Product;
import co.com.etn.arquitecturamvpbase.repositories.products.ProductRepository;
import retrofit.RetrofitError;

/**
 * Created by Erika on 16/10/2017.
 */

public class Synchronizer {
    private static final String TAG_LOCAL = "<<Synchronizer>>:";
    public static Synchronizer instance = null;

    public static Synchronizer getInstance() {

        if(instance == null){
            instance = new Synchronizer();
        }
        return instance;
    }

    public void executeSyncLocalToServer(boolean isConnected) {
        //TODO implementar
        Log.i(TAG_LOCAL,"Network Change: "+ isConnected);
        if(isConnected){
            ArrayList<Product> list = Database.productDao.fetchNotSyncProducts();
            ProductRepository repository = new ProductRepository();
            for (Product product: list ) {
                try {
                    repository.addProduct(product);
                    product.setSync("S");
                    Database.productDao.updateProduct(product);
                    Log.i(TAG_LOCAL,"Synchornized: "+ product.getName());
                }catch (RetrofitError retrofitError){
                    Log.i(TAG_LOCAL,"<NOT Synchornized>: "+ product.getName()+" Cause: "+retrofitError.getMessage());
                }
            }
        }else {
            //Log.i(TAG_LOCAL,"No hago nada: "+ isConnected);
        }
    }
}
