package co.com.etn.arquitecturamvpbase.presenters;

import co.com.etn.arquitecturamvpbase.helper.IVaidateInternet;
import co.com.etn.arquitecturamvpbase.views.IBaseView;

/**
 * Created by Erika on 16/09/2017.
 */

public class BasePresenter <T extends IBaseView> {

    private T view;
    private IVaidateInternet VaidateInternet;

    public void inject(T view, IVaidateInternet VaidateInternet) {
        this.VaidateInternet = VaidateInternet;
        this.view = view;
    }

    public T getView() {
        return view;
    }

    public IVaidateInternet getVaidateInternet() {
        return VaidateInternet;
    }

}
