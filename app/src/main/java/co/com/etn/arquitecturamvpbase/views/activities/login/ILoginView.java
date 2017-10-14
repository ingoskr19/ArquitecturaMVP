package co.com.etn.arquitecturamvpbase.views.activities.login;

import co.com.etn.arquitecturamvpbase.models.Login;
import co.com.etn.arquitecturamvpbase.views.IBaseView;

/**
 * Created by Erika on 14/10/2017.
 */

public interface ILoginView extends IBaseView {
    void showProfileview(Login login);
}
