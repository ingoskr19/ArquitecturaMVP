package co.com.etn.arquitecturamvpbase.repositories.notes;

import co.com.etn.arquitecturamvpbase.helper.ServicesFactory;
import co.com.etn.arquitecturamvpbase.helper.TypeDecryption;
import co.com.etn.arquitecturamvpbase.models.Note;
import co.com.etn.arquitecturamvpbase.services.IServices;

/**
 * Created by Erika on 7/11/2017.
 */

public class NotesRepository implements INotesRepository {

    private IServices services;

    public NotesRepository() {
        ServicesFactory servicesFactory = new ServicesFactory(TypeDecryption.XML);
        services = (IServices) servicesFactory.getInstance(IServices.class);
    }

    @Override
    public Note
    getNote() {
        return services.getNote();
    }
}

