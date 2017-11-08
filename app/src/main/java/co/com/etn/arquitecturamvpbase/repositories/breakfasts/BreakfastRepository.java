package co.com.etn.arquitecturamvpbase.repositories.breakfasts;

import co.com.etn.arquitecturamvpbase.helper.ServicesFactory;
import co.com.etn.arquitecturamvpbase.helper.TypeDecryption;
import co.com.etn.arquitecturamvpbase.models.Breakfast;
import co.com.etn.arquitecturamvpbase.services.IServices;

/**
 * Created by Erika on 7/11/2017.
 */

public class BreakfastRepository implements IBreakFastRepository {

    private IServices services;

    public BreakfastRepository() {
            ServicesFactory servicesFactory = new ServicesFactory(TypeDecryption.XML);
            services = (IServices) servicesFactory.getInstance(IServices.class);
    }

    @Override
    public Breakfast getBreakfastMenu() {
        return services.getBreakfastMenu();
    }
}
