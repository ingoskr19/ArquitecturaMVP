package co.com.etn.arquitecturamvpbase.repositories;

/**
 * Created by Erika on 26/09/2017.
 */

public class RepositoryError extends Exception {
    private int idError;

    public RepositoryError(String message) {
        super(message);
    }

    public int getIdError() {
        return idError;
    }

    public void setIdError(int idError) {
        this.idError = idError;
    }

}
