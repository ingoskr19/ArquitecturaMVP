package co.com.etn.arquitecturamvpbase.views.activities.products;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.helper.Constants;
import co.com.etn.arquitecturamvpbase.models.Product;
import co.com.etn.arquitecturamvpbase.presenters.products.UpdateProductPresenter;
import co.com.etn.arquitecturamvpbase.repositories.products.ProductRepository;
import co.com.etn.arquitecturamvpbase.views.BaseActivity;

public class UpdateProductActivity extends BaseActivity<UpdateProductPresenter> implements IUpdateProductView{

    private EditText productName;
    private EditText productPrice;
    private EditText productQuantity;
    private EditText productDescription;
    private Button editButton;
    private Product product;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);
        setPresenter(new UpdateProductPresenter(new ProductRepository()));
        getPresenter().inject(this,getVaidateInternet());
        init();
    }

    public void setData(){
        product.setName(productName.getText().toString());
        product.setPrice(productPrice.getText().toString());
        product.setQuantity(productQuantity.getText().toString());
        product.setDescription(productDescription.getText().toString());
    }

    public void loadData(){
        product = (Product) getIntent().getSerializableExtra(Constants.ITEM_PRODUCT);
        productName.setHint(product.getName());
        productPrice.setHint(product.getPrice());
        productQuantity.setHint(product.getQuantity());
        productDescription.setHint(product.getDescription());
        productName.setText(product.getName());
        productPrice.setText(product.getPrice());
        productQuantity.setText(product.getQuantity());
        productDescription.setText(product.getDescription());
    }

    public void editProduct(Product product){
        getPresenter().updateProduct(product.getId(),product);
    }

    @Override
    public void showToast(int update_ok) {
        showMessage(getString(update_ok));
        closeActivity();
    }

    @Override
    public void showAlertDialog(final int update_fail) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getShowAlertDialog().showAlertDialog(R.string.error, update_fail, false, R.string.reintentar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }, R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
            }
        });
    }

    @Override
    public void showToast(String message) {
        showMessage(message);
        closeActivity();
    }

    public void init(){
        product = new Product();
        productName = (EditText) findViewById(R.id.updateproduct_edittext_name);
        productPrice = (EditText) findViewById(R.id.updateproduct_edittext_price);
        productDescription = (EditText) findViewById(R.id.updateproduct_edittext_description);
        productQuantity = (EditText) findViewById(R.id.updateproduct_edittext_quantity);
        editButton = (Button) findViewById(R.id.updateproduct_button_edit);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData();
                editProduct(product);
            }
        });

        loadData();
    }
}
