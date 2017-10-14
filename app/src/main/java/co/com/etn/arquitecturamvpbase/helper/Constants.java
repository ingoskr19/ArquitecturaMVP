package co.com.etn.arquitecturamvpbase.helper;

/**
 * Created by Erika on 16/09/2017.
 */

public class Constants {
    public final static String URL_BASE = "https://shoppingproducts.herokuapp.com";
    public final static int TIME_OUT = 5;
    public final static String ITEM_PRODUCT = "product";
    public final static String ITEM_CUSTOMER_PHONELIST = "phone_list";
    public static final String ITEM_CUSTOMER_POSITION = "item_position";
    public static final String REQUEST_TIMEOUT_ERROR_MESSAGE = "La solicitud está tardando demasiado. Por favor inténtalo nuevamente.";
    public static final int DEFAUL_ERROR_CODE = 0;
    public static final String DEFAUL_ERROR = "Ha ocurrido un error, intentalo nuevamente.";
    public static final int UNAUTHORIZED_ERROR_CODE = 401;
    public static final int NOT_FOUND_ERROR_CODE = 404;

    //DATABASE
    public  static final String DATABASE_NAME = "shopping_db";
    public  static final int DATABASE_VERSION = 2;

    public static final String TITLE_PRODUCT = "Productos";
    public static final String TITLE_CONTACT = "Contactos";
}
