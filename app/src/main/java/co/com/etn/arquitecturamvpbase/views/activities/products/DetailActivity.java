package co.com.etn.arquitecturamvpbase.views.activities.products;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.helper.Constants;
import co.com.etn.arquitecturamvpbase.models.Product;
import co.com.etn.arquitecturamvpbase.presenters.products.DetailProductPresenter;
import co.com.etn.arquitecturamvpbase.repositories.products.IProductRepository;
import co.com.etn.arquitecturamvpbase.repositories.products.ProductRepository;
import co.com.etn.arquitecturamvpbase.views.BaseActivity;

/**
 * Created by Erika on 16/09/2017.
 */

public class DetailActivity extends BaseActivity<DetailProductPresenter> implements IDetailProductView {

    private TextView product_name;
    private TextView product_price;
    private TextView product_cantidad;
    private TextView product_descripcion;
    private FloatingActionButton deleteButton;
    private Product product;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        setPresenter(new DetailProductPresenter(new ProductRepository()));
        getPresenter().inject(this,getVaidateInternet());
        loadView();
        setDataItem();
    }

    private void setDataItem() {
        product_name.setText(product.getName());
        product_price.setText(product.getPrice());
        product_descripcion.setText(product.getDescription());
        product_cantidad.setText(product.getQuantity());
    }

    private void loadView() {
        product_name = (TextView) findViewById(R.id.detail_product_name);
        product_price = (TextView) findViewById(R.id.detail_product_price);
        product_cantidad = (TextView) findViewById(R.id.detail_product_cantidad);
        product_descripcion = (TextView) findViewById(R.id.detail_product_descripcion);

        product = (Product) getIntent().getSerializableExtra(Constants.ITEM_PRODUCT);
        deleteButton = (FloatingActionButton) findViewById(R.id.detail_product_delete_product);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().deleteProduct(product.getId());
            }
        });
    }

    @Override
    public void showAlertDialog(int msg) {
        showMessage(getString(msg));
        //TODO mostar alertDialog
    }

    @Override
    public void showToast(int msg) {
        showMessage(getString(msg));
        closeActivity();
    }

    @Override
    public void showAlertDialogError(int msg) {
        showMessage(getString(msg));
        //TODO mostarAlertDialogError
    }


    @Override
    public void closeActivity() {
        super.closeActivity();
        product = null;
    }
}
