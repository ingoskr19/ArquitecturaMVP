package co.com.etn.arquitecturamvpbase.views.activities.products;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.models.Product;
import co.com.etn.arquitecturamvpbase.presenters.products.AddProductPresenter;
import co.com.etn.arquitecturamvpbase.views.BaseActivity;

/**
 * Created by Erika on 19/09/2017.
 */

public class AddProductActivity extends BaseActivity<AddProductPresenter> implements IAddProductView, TextWatcher {

    private EditText addProductName;
    private EditText addProductPrice;
    private EditText addProductQuantity;
    private EditText addProductDescription;
    private Button addButton;
    private Product product;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);
        setPresenter(new AddProductPresenter());
        getPresenter().inject(this,getVaidateInternet());
        init();
    }

    public void fillFields(){
        product.setName(addProductName.getText().toString());
        product.setPrice(addProductPrice.getText().toString());
        product.setQuantity(addProductQuantity.getText().toString());
        product.setDescription(addProductDescription.getText().toString());
    }

    public void addProduct(){
        getPresenter().addProduct(product);
    }

    @Override
    public void afterTextChanged(Editable s) {
        if(!"".equals(String.valueOf(addProductName.getText().toString()))
                && !"".equals(String.valueOf(addProductPrice.getText().toString()))
                && !"".equals(String.valueOf(addProductQuantity.getText().toString()))
                && !"".equals(String.valueOf(addProductDescription.getText().toString()))
                ){
            addButton.setEnabled(true);
            fillFields();
            enableButton(addButton);
        }else{
            addButton.setEnabled(false);
            disableButton(addButton);
        }
    }

    public void init(){
        addButton = (Button) findViewById(R.id.addproduct_button_add);
        addButton.setEnabled(false);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });

        addButton.setBackgroundColor(ContextCompat.getColor(this,R.color.colorGray));
        addButton.setTextColor(ContextCompat.getColor(this,R.color.colorWhite));

        addProductName = (EditText) findViewById(R.id.addproduct_edittext_name);
        addProductPrice = (EditText) findViewById(R.id.addproduct_edittext_price);
        addProductDescription = (EditText) findViewById(R.id.addproduct_edittext_description);
        addProductQuantity = (EditText) findViewById(R.id.addproduct_edittext_quantity);

        addProductName.addTextChangedListener(this);
        addProductPrice.addTextChangedListener(this);
        addProductDescription.addTextChangedListener(this);
        addProductQuantity.addTextChangedListener(this);

        product = new Product();
        createProgressDialog();
    }

    @Override
    public void showResultAdd(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(AddProductActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
        closeActivity();
    }

    @Override
    public void closeActivity() {
        super.closeActivity();
        product = null;
    }

    public void enableButton(Button button){
        button.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary));
        button.setTextColor(ContextCompat.getColor(this,R.color.colorWhite));
    }

    public void disableButton(Button button){
        button.setBackgroundColor(ContextCompat.getColor(this,R.color.colorGray));
        button.setTextColor(ContextCompat.getColor(this,R.color.colorWhite));
    }



    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }
}
