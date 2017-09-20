package co.com.etn.arquitecturamvpbase.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.helper.Constants;
import co.com.etn.arquitecturamvpbase.models.Product;
import co.com.etn.arquitecturamvpbase.presenters.ProductPresenter;
import co.com.etn.arquitecturamvpbase.repositories.ProductRepository;
import co.com.etn.arquitecturamvpbase.views.BaseActivity;
import co.com.etn.arquitecturamvpbase.views.activities.products.AddProductActivity;
import co.com.etn.arquitecturamvpbase.views.adapters.ProductAdapter;

/**
 * Created by Erika on 16/09/2017.
 */

public class ProductActivity extends BaseActivity<ProductPresenter> implements IProductView{

    private ListView productList;
    private FloatingActionButton addButton;
    private ProductAdapter productAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        createProgressDialog();
        setPresenter(new ProductPresenter());
        getPresenter().inject(this,getVaidateInternet());
        productList = (ListView) findViewById(R.id.product_listview);
        getPresenter().validateInternetProduct();
        addButton = (FloatingActionButton) findViewById(R.id.list_product_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductActivity.this, AddProductActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void showProductList(final ArrayList<Product> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                callAdapter(list);
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().validateInternetProduct();
    }
}
