package co.com.etn.arquitecturamvpbase.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;

import co.com.etn.arquitecturamvpbase.helper.Constants;
/**
 * Created by Erika on 16/09/2017.
 */

public class ServicesFactory {

    private static final String API_BASE_PATH = Constants.URL_BASE;
    private RestAdapter restAdapter;

    public ServicesFactory() {
        createServicesFactoryInstance(getGsonConverter(), API_BASE_PATH);
    }

    private void createServicesFactoryInstance(Converter converter, String baseUrl) {
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(Constants.TIME_OUT, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(Constants.TIME_OUT, TimeUnit.SECONDS);
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(baseUrl)
                .setConverter(converter)
                .setClient(new OkClient(okHttpClient))
                .build();
    }

    private Converter getGsonConverter() {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        return new GsonConverter(gson);
    }

    public Object getInstance(Class service) {
        return restAdapter.create(service);
    }

}
