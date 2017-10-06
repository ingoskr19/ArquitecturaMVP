package co.com.etn.arquitecturamvpbase.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Erika on 3/10/2017.
 */

public class Customer implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("surname")
    @Expose
    private String surname;

    @SerializedName("phoneList")
    @Expose
    private ArrayList<Phone> phonesList;

    @SerializedName("_id")
    @Expose
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public ArrayList<Phone> getPhonesList() {
        return phonesList;
    }

    public void setPhonesList(ArrayList<Phone> phonesList) {
        this.phonesList = phonesList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
