package co.com.etn.arquitecturamvpbase.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import co.com.etn.arquitecturamvpbase.models.Login;

/**
 * Created by Erika on 17/10/2017.
 */

public class CustomSharedPreferences {

    private SharedPreferences sharedPreferences;

    public CustomSharedPreferences(Context context) {
        this.sharedPreferences = context.getSharedPreferences(Constants.PREFERENCE,Context.MODE_PRIVATE);
    }

    public void saveObjectUser(String key, Login user){
        Gson gson = new Gson();
        String json = gson.toJson(user);
        addValue(key,json);
    }

    public void addValue(String key, String json) {
        sharedPreferences.edit().putString(key,json).commit();
    }

    public Login getObjectUser(String key){
        Gson gson = new Gson();
        String json = sharedPreferences.getString(key,"");
        Login user = gson.fromJson(json,Login.class);
        return user;
    }

    public void deleteValue(String key){
        sharedPreferences.edit().remove(key).commit();
    }
}
