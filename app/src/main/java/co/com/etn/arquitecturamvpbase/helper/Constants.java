package co.com.etn.arquitecturamvpbase.helper;

/**
 * Created by Erika on 16/09/2017.
 */

public class Constants {
    public final static String URL_BASE = "https://shoppingproducts.herokuapp.com";
    public static final String URL_XML_BASE = "https://www.w3schools.com/xml/";
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
    public static final CharSequence TITLE_PROFILE = "Perfil";

    public static final String SP_LOGIN = "login";
    public static final String PREFERENCE = "co.com.etn.arquitecturamvpbase";

    public static final String EMPTY = "";
    public static final String PACKAGE_NAME = "co.com.etn.arquitecturamvpbase";
    public static final String RECEIVER = PACKAGE_NAME + ".Receiver";
    public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LocationDataExtra";
    public static final int FAIL_RESULT = 1;
    public static final int SUCCESS_RESULT = 0;
    public static final String RESULT_DATA_KEY = PACKAGE_NAME + ".ResultDataKey";
    public static final int REQUEST_CODE_PERMISSION = 2;
    public static final int GALLERY_KITKAT_LOWER = 19;
    public static final int GALLERY = 20;
    public static final String PREFIX_FILE_IMAGE = "JPEG_";
    public static final String FORMAT_DATE_FILE = "yyyyMMdd_HHmmss";
    public static final String SUFFIX_FILE_NAME = ".jpeg";
    public static final int CAMERA_CAPTURE = 21;
    public static final String PREFIX_FILE_AUDIO = "audioRecord";
    public static final String SUFFIX_FILE_AUDIO = ".mp3";
    public static final int MAX_DURATION = 30000;
    public static final int INTERVAL = 500;
    public static final int REQUEST_VIDEO_CAPTURE = 22;

}
