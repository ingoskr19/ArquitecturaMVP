package co.com.etn.arquitecturamvpbase.views.activities.products;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.helper.Constants;
import co.com.etn.arquitecturamvpbase.models.Product;
import co.com.etn.arquitecturamvpbase.presenters.products.ProductPresenter;
import co.com.etn.arquitecturamvpbase.repositories.products.ProductRepository;
import co.com.etn.arquitecturamvpbase.views.BaseActivity;
import co.com.etn.arquitecturamvpbase.views.adapters.ProductAdapter;
import co.com.etn.arquitecturamvpbase.views.products.IProductView;

/**
 * Created by Erika on 16/09/2017.
 */

public class ProductActivity extends BaseActivity<ProductPresenter> implements IProductView {

    private ListView productList;
    private FloatingActionButton addButton;
    private ProductAdapter productAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        createProgressDialog();
        showProgress(R.string.loading_message);
        setPresenter(new ProductPresenter(new ProductRepository()));
        getPresenter().inject(this,getVaidateInternet());
        productList = (ListView) findViewById(R.id.product_listview);
        getPresenter().listProduct();
        addButton = (FloatingActionButton) findViewById(R.id.list_product_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductActivity.this, CreateProductActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void showProductList(final ArrayList<Product> list) {
        hideProgress();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                callAdapter(list);
            }
        });
    }

    @Override
    public void showAlertDialog(int error, int message) {
        showAlert(error,message);
    }

    @Override
    public void showAlertError(int error, int message) {
        showAlert(error,message);
    }

    public void showAlert(final int title, final int message){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getShowAlertDialog().showAlertDialog(title, message, false, R.string.reintentar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getPresenter().listProduct();
                    }
                }, R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
            }
        });
    }

    private void callAdapter(final ArrayList<Product> list) {
        productAdapter = new ProductAdapter(this,R.id.product_listview,list);
        productList.setAdapter(productAdapter);
        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ProductActivity.this, DetailActivity.class);
                intent.putExtra(Constants.ITEM_PRODUCT,list.get(position));
                startActivity(intent);
            }
        });

        productList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ProductActivity.this, UpdateProductActivity.class);
                intent.putExtra(Constants.ITEM_PRODUCT,list.get(position));
                startActivity(intent);
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().listProduct();
    }

    @Override
    public void closeActivity() {
        super.closeActivity();
        productList = null;
    }

}
