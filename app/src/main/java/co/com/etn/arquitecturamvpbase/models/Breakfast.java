package co.com.etn.arquitecturamvpbase.models;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

/**
 * Created by Erika on 7/11/2017.
 */
@Root(name = "breakfast_menu")
public class Breakfast {
    @ElementList(inline = true,name = "food")
    ArrayList<Food> foods;

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
    }
}
