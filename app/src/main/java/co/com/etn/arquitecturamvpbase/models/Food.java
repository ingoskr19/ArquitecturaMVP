package co.com.etn.arquitecturamvpbase.models;

import org.simpleframework.xml.Element;

/**
 * Created by Erika on 7/11/2017.
 */
@Element(name = "food")
public class Food {
    @Element(name = "name")
    private String name;

    @Element(name = "price")
    private String price;

    @Element(name = "description")
    private String description;

    @Element(name = "calories")
    private String calories;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }
}
