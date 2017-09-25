package co.com.etn.arquitecturamvpbase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import co.com.etn.arquitecturamvpbase.helper.IVaidateInternet;
import co.com.etn.arquitecturamvpbase.models.DeleteProductResponse;
import co.com.etn.arquitecturamvpbase.presenters.products.DetailProductPresenter;
import co.com.etn.arquitecturamvpbase.repositories.products.IProductRepository;
import co.com.etn.arquitecturamvpbase.views.activities.products.IDetailProductView;

import static android.R.attr.id;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Erika on 23/09/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class DetailPresenterTest {

    @Mock
    IVaidateInternet validateInternet;

    @Mock
    IProductRepository productRepository;

    @Mock
    IDetailProductView detailProductView;

    DetailProductPresenter detailProductPresenter;

    @Before
    public void setUp() throws Exception{
        detailProductPresenter = Mockito.spy(new DetailProductPresenter(productRepository));
        detailProductPresenter.inject(detailProductView,validateInternet);
    }

    @Test
    public void methodDeleteProductWithConnectionShouldCallMethodCreateThreadDeleteProduct(){
        String _id = "13g1jhh2d32";
        when(validateInternet.isConnected()).thenReturn(true);
        detailProductPresenter.deleteProduct(_id);
        verify(detailProductPresenter).createThreadDeleteProduct(_id);
    }

    @Test
    public void methodDeleteProductWithoutConnectionShouldShowAlertDialog(){
        String id = "13g1jhh2d32";
        when(validateInternet.isConnected()).thenReturn(false);
        detailProductPresenter.deleteProduct(id);
        verify(detailProductView).showAlertDialog(R.string.no_conected_internet);
        verify(detailProductPresenter,never()).createThreadDeleteProduct(id);
    }

    @Test
    public void methodDeleteProductShouldCallMehtodDeleteProductInRepositoryTrue(){
        DeleteProductResponse deleteProductResponse = new DeleteProductResponse();
        deleteProductResponse.setStatus(true);
        String id = "13g1jhh2d32";
        when(productRepository.deleteProduct(id)).thenReturn(deleteProductResponse);
        detailProductPresenter.deleteProductRepository(id);
        Assert.assertTrue(deleteProductResponse.isStatus());
        verify(detailProductView).showToast(R.string.delete_ok);
        verify(detailProductView,never()).showAlertDialogError(R.string.no_deleted);
    }

    @Test
    public void methodDeleteProductShouldCallMehtodDeleteProductInRepositoryFalse(){
        DeleteProductResponse deleteProductResponse = new DeleteProductResponse();
        deleteProductResponse.setStatus(false);
        String id = "13g1jhh2d32";
        when(productRepository.deleteProduct(id)).thenReturn(deleteProductResponse);
        detailProductPresenter.deleteProductRepository(id);
        Assert.assertFalse(deleteProductResponse.isStatus());
        verify(detailProductView).showAlertDialogError(R.string.no_deleted);
        verify(detailProductView,never()).showToast(R.string.delete_ok);
    }
}
