package co.com.etn.arquitecturamvpbase.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.helper.Constants;
import co.com.etn.arquitecturamvpbase.models.Product;

/**
 * Created by Erika on 16/09/2017.
 */

public class DetailActivity extends AppCompatActivity{

    private TextView product_name;
    private TextView product_price;
    private TextView product_cantidad;
    private TextView product_descripcion;
    private Product product;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
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

    }
}
