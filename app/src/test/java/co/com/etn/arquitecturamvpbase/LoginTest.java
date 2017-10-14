package co.com.etn.arquitecturamvpbase;

import android.util.Log;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import co.com.etn.arquitecturamvpbase.helper.IVaidateInternet;
import co.com.etn.arquitecturamvpbase.models.Login;
import co.com.etn.arquitecturamvpbase.presenters.customers.CreateCustomerPresenter;
import co.com.etn.arquitecturamvpbase.presenters.customers.CustomerPresenter;
import co.com.etn.arquitecturamvpbase.presenters.login.LoginPresenter;
import co.com.etn.arquitecturamvpbase.repositories.ILoginRepository;
import co.com.etn.arquitecturamvpbase.repositories.RepositoryError;
import co.com.etn.arquitecturamvpbase.views.activities.login.ILoginView;
import retrofit.RetrofitError;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Erika on 14/10/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class LoginTest {

    @Mock
    IVaidateInternet validateInternet;

    @Mock
    ILoginRepository loginRepository;

    @Mock
    ILoginView loginView;

    LoginPresenter loginPresenter;

    @InjectMocks
    Login login;

    public Login fillLoginObject(){
        Login login = new Login();
        login.setName("NAME");
        login.setUsername("USERNAME");
        login.setEmail("EMAIL");
        login.setPassword("PASSWORD");
        login.setFollowers(11);
        login.setFollowings(11);
        login.setLikes(11);
        login.setPhoto("PHOTO");
        login.setToken("TOKEN");
        return login;
    }

    @Before
    public void setUp() throws Exception {
        loginPresenter = Mockito.spy(new LoginPresenter(loginRepository));
        loginPresenter.inject(loginView, validateInternet);
    }

    @Test
    public void methodValidateInternetShouldCallMethodLoginWithoutConneccion(){
        String email = "EMAIl";
        String password = "PASSWORD";
        loginPresenter.login(email,password);
        when(validateInternet.isConnected()).thenReturn(false);
        verify(loginView).showMessage(R.string.no_conected_internet);
        verify(loginPresenter,never()).createThreadLogin(email,password);
    }

    @Test
    public void methodValidateInternetShouldCallMethodLoginWithConneccion(){
        String email = "EMAIl";
        String password = "PASSWORD";
        when(validateInternet.isConnected()).thenReturn(true);
        loginPresenter.login(email,password);
        verify(loginPresenter).createThreadLogin(email,password);
        verify(loginView,never()).showMessage(R.string.no_conected_internet);
    }

    @Test
    public void methodValidateInternetShouldCallMethodLoginRepositorySuccess() throws RetrofitError {
        String email = "EMAIl";
        String password = "PASSWORD";
        Login login = fillLoginObject();
        when(loginRepository.login(email,password)).thenReturn(login);
        loginPresenter.loginRepository(email,password);
        Assert.assertEquals("TOKEN",login.getToken());
        verify(loginView).showProfileview(login);
        verify(loginView,never()).showMessage(R.string.login_fail);
    }

    @Test
    public void methodValidateInternetShouldCallMethodLoginRepositoryFail() throws RetrofitError{
        String email = "EMAIl";
        String password = "PASSWORD";
        Login login = fillLoginObject();
        login.setToken("");
        when(loginRepository.login(email,password)).thenReturn(login);
        loginPresenter.loginRepository(email,password);
        verify(loginView).showProfileview(login);
    }
}
