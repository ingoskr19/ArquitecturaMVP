package co.com.etn.arquitecturamvpbase.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Erika on 3/10/2017.
 */

public class Phone implements Serializable {

    @SerializedName("number")
    @Expose
    private String number;

    @SerializedName("location")
    @Expose
    private Location location;

    @SerializedName("_id")
    @Expose
    private String id;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
