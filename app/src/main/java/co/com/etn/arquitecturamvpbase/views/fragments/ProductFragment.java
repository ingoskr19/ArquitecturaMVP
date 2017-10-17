package co.com.etn.arquitecturamvpbase.views.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.helper.Constants;
import co.com.etn.arquitecturamvpbase.models.Product;
import co.com.etn.arquitecturamvpbase.presenters.products.ProductPresenter;
import co.com.etn.arquitecturamvpbase.repositories.products.ProductRepository;
import co.com.etn.arquitecturamvpbase.views.BaseFragments;
import co.com.etn.arquitecturamvpbase.views.activities.products.CreateProductActivity;
import co.com.etn.arquitecturamvpbase.views.activities.products.DetailActivity;
import co.com.etn.arquitecturamvpbase.views.activities.products.ProductActivity;
import co.com.etn.arquitecturamvpbase.views.activities.products.UpdateProductActivity;
import co.com.etn.arquitecturamvpbase.views.adapters.ProductAdapter;
import co.com.etn.arquitecturamvpbase.views.products.IProductView;

/**
 * Created by Erika on 14/10/2017.
 */

public class ProductFragment extends BaseFragments<ProductPresenter> implements IProductView {

    private ListView productList;
    private ProductAdapter productAdapter;
    private FloatingActionButton addButton;
    private SwipeRefreshLayout swipeRefresh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_products,container,false);
        //return super.onCreateView(inflater, container, savedInstanceState);

        getBaseActivity().createProgressDialog();
        showProgress(R.string.loading_message);
        setPresenter(new ProductPresenter(new ProductRepository()));
        getPresenter().inject(this,getValidateInternet());
        productList = (ListView) view.findViewById(R.id.product_listview);

        getPresenter().listProduct();
        addButton = (FloatingActionButton) view.findViewById(R.id.list_product_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateProductActivity.class);
                startActivity(intent);
            }
        });

        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.product_activity_swipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPresenter().listProduct();
            }
        });

        return view;
    }

    @Override
    public void showProductList(final ArrayList<Product> list) {
        hideProgress();
        //swipeRefresh.setRefreshing(false);
        getActivity().runOnUiThread(new Runnable() {
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
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getBaseActivity().getShowAlertDialog().showAlertDialog(title, message, false, R.string.reintentar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getPresenter().listProduct();
                    }
                }, R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                });
            }
        });
        swipeRefresh.setRefreshing(false);
    }

    private void callAdapter(final ArrayList<Product> list) {
        swipeRefresh.setRefreshing(false);
        productAdapter = new ProductAdapter(getActivity(),R.id.product_listview,list);
        productList.setAdapter(productAdapter);
        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(Constants.ITEM_PRODUCT,list.get(position));
                startActivity(intent);
            }
        });

        productList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), UpdateProductActivity.class);
                intent.putExtra(Constants.ITEM_PRODUCT,list.get(position));
                startActivity(intent);
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void closeActivity() {
        super.closeActivity();
        productList = null;
    }
}
