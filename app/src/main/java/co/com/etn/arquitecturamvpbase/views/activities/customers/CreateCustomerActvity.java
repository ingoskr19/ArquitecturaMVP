package co.com.etn.arquitecturamvpbase.views.activities.customers;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.helper.Constants;
import co.com.etn.arquitecturamvpbase.models.Customer;
import co.com.etn.arquitecturamvpbase.models.Location;
import co.com.etn.arquitecturamvpbase.models.Phone;
import co.com.etn.arquitecturamvpbase.presenters.customers.CreateCustomerPresenter;
import co.com.etn.arquitecturamvpbase.repositories.customers.CustomerRepository;
import co.com.etn.arquitecturamvpbase.views.BaseActivity;
import co.com.etn.arquitecturamvpbase.views.adapters.PhoneAdapter;
import co.com.etn.arquitecturamvpbase.views.customers.ICreateCustomerView;

public class CreateCustomerActvity extends BaseActivity<CreateCustomerPresenter> implements ICreateCustomerView, TextWatcher {

    private EditText name;
    private EditText surname;
    private EditText phoneNumber;
    private EditText phoneLocationLat;
    private EditText phoneLocationLong;
    private Button addCustomerButton;
    private Button addPhoneButton;
    private Button viewPhoneListButton;
    private ListView phoneListView;
    private PhoneAdapter phoneAdapter;

    private ArrayAdapter<Phone> phoneList;
    private Customer customer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_customer_actvity);
        init();
        setPresenter(new CreateCustomerPresenter(new CustomerRepository()));
        getPresenter().inject(this,getVaidateInternet());
    }

    @Override
    public void showMessage(int msj) {
        super.showMessage(msj);
        closeActivity();
    }

    @Override
    public void showMessage(String msj) {
        super.showMessage(msj);
        closeActivity();
    }

    @Override
    public void showAlertDialog(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getShowAlertDialog().showAlertDialog(R.string.error, msg, true, R.string.reintentar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addCustomer();
                    }
                }, R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        initCustomer();
                    }
                });
            }
        });
    }

    @Override
    public void showAlertDialog(final int msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getShowAlertDialog().showAlertDialog(R.string.error, msg, true, R.string.reintentar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addCustomer();
                    }
                }, R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        initCustomer();
                    }
                });
            }
        });
    }

    @Override
    public void afterTextChanged(Editable s) {
        if(!"".equals(String.valueOf(name.getText().toString()).trim())
                && !"".equals(String.valueOf(surname.getText().toString()).trim())
                && customer.getPhonesList().size()>0
                ){
            enableButton(addCustomerButton,R.color.colorGreen);
        }else{
            disableButton(addCustomerButton);
        }

        if(customer.getPhonesList().size()>0){
            enableButton(viewPhoneListButton,R.color.colorOrange);
        }else{
            disableButton(viewPhoneListButton);
        }
        if(!"".equals(String.valueOf(phoneNumber.getText().toString()).trim())
                && !"".equals(String.valueOf(phoneLocationLat.getText().toString()).trim())
                && !"".equals(String.valueOf(phoneLocationLong.getText().toString()).trim())
                ){
            enableButton(addPhoneButton,R.color.colorPrimary);
        }else{
            disableButton(addPhoneButton);
        }
    }

    public void addPhone(){
        Phone phone = new Phone();
        phone.setNumber(phoneNumber.getText().toString().trim());
        double[] coordinates = new double[]{
                Double.parseDouble(phoneLocationLat.getText().toString().trim()),
                Double.parseDouble(phoneLocationLong.getText().toString().trim())
        };
        Location location = new Location();
        location.setType("Point");
        location.setCoordinates(coordinates);
        phone.setLocation(location);
        phone.setDescripcion("");
        customer.getPhonesList().add(phone);
        String text = ""+getString(R.string.customer_button_showPhoneList)+"("+customer.getPhonesList().size()+")";
        viewPhoneListButton.setText(text);
        phoneNumber.setText("");
        phoneLocationLat.setText("");
        phoneLocationLong.setText("");
        enableButton(viewPhoneListButton,R.color.colorOrange);
        enableButton(addCustomerButton,R.color.colorGreen);
    }

    public void addCustomer(){
        if(customer.getPhonesList().size()>0) {
            customer.setName(name.getText().toString().trim());
            customer.setSurname(surname.getText().toString().trim());
            getPresenter().createCustomer(customer);
        }
    }

    private void showPhoneList(){
        callAdapter(customer.getPhonesList());
    }

    private void callAdapter(final ArrayList<Phone> list) {
        setContentView(R.layout.activity_phone_list_activity);
        phoneListView = (ListView) findViewById(R.id.phone_listview);
        phoneAdapter = new PhoneAdapter(this, phoneListView.getId(), list);
        if (phoneAdapter != null) {
            phoneListView.setAdapter(phoneAdapter);
        } else {
            showMessage("Era null adapater");
        }
        Toast.makeText(CreateCustomerActvity.this, "Listando telefonos", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(CreateCustomerActvity.this, PhoneListActivity.class);
        intent.putExtra(Constants.ITEM_CUSTOMER_PHONELIST,customer.getPhonesList());
        intent.putExtra(Constants.ITEM_CUSTOMER_POSITION,0);
        setContentView(R.layout.activity_create_customer_actvity);
        startActivity(intent);
    }

    private void init(){
        name = (EditText) findViewById(R.id.customer_name);
        surname = (EditText) findViewById(R.id.customer_surname);
        phoneNumber = (EditText) findViewById(R.id.customer_phone_numer);
        phoneLocationLat = (EditText) findViewById(R.id.customer_phone_lat);
        phoneLocationLong = (EditText) findViewById(R.id.customer_phone_long);
        addCustomerButton = (Button) findViewById(R.id.customer_button_save);
        addPhoneButton = (Button) findViewById(R.id.customer_button_savePhone);
        viewPhoneListButton = (Button) findViewById(R.id.customer_button_showPhoneList);
        name.addTextChangedListener(this);
        surname.addTextChangedListener(this);
        phoneNumber.addTextChangedListener(this);
        phoneLocationLat.addTextChangedListener(this);
        phoneLocationLong.addTextChangedListener(this);
        disableButton(addCustomerButton);
        disableButton(addPhoneButton);
        disableButton(viewPhoneListButton);

        initCustomer();

        addCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCustomer();
            }
        });

        addPhoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPhone();
            }
        });

        viewPhoneListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhoneList();
            }
        });
    }

    private void initCustomer(){
        customer = new Customer();
        customer.setPhonesList(new ArrayList<Phone>());
    }

    public void enableButton(Button button, int color){
        button.setBackgroundColor(ContextCompat.getColor(this,color));
        button.setTextColor(ContextCompat.getColor(this,R.color.colorWhite));
        button.setEnabled(true);
    }

    public void disableButton(Button button){
        button.setBackgroundColor(ContextCompat.getColor(this,R.color.colorGray));
        button.setTextColor(ContextCompat.getColor(this,R.color.colorWhite));
        button.setEnabled(false);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }
}
