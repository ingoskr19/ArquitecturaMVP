package co.com.etn.arquitecturamvpbase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import co.com.etn.arquitecturamvpbase.helper.Constants;
import co.com.etn.arquitecturamvpbase.helper.IVaidateInternet;
import co.com.etn.arquitecturamvpbase.models.ProductResponse;
import co.com.etn.arquitecturamvpbase.models.Product;
import co.com.etn.arquitecturamvpbase.presenters.products.CreateProductPresenter;
import co.com.etn.arquitecturamvpbase.presenters.products.DetailProductPresenter;
import co.com.etn.arquitecturamvpbase.presenters.products.UpdateProductPresenter;
import co.com.etn.arquitecturamvpbase.repositories.products.IProductRepository;
import co.com.etn.arquitecturamvpbase.repositories.RepositoryError;
import co.com.etn.arquitecturamvpbase.views.products.ICreateProductView;
import co.com.etn.arquitecturamvpbase.views.products.IDetailProductView;
import co.com.etn.arquitecturamvpbase.views.products.IUpdateProductView;

import static junit.framework.Assert.assertEquals;
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

    @Mock
    IUpdateProductView updateProductView;

    @Mock
    ICreateProductView createProductView;

    DetailProductPresenter detailProductPresenter;
    UpdateProductPresenter updateProductPresenter;
    CreateProductPresenter createProductPresenter;

    @InjectMocks
    Product product;

    @Before
    public void setUp() throws Exception{
        detailProductPresenter = Mockito.spy(new DetailProductPresenter(productRepository));
        detailProductPresenter.inject(detailProductView,validateInternet);

        updateProductPresenter = Mockito.spy(new UpdateProductPresenter(productRepository));
        updateProductPresenter.inject(updateProductView,validateInternet);

        createProductPresenter = Mockito.spy(new CreateProductPresenter(productRepository));
        createProductPresenter.inject(createProductView,validateInternet);

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
    public void methodDeleteProductShouldCallMehtodDeleteProductInRepositoryTrue() throws RepositoryError {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setStatus(true);
        String id = "13g1jhh2d32";
        when(productRepository.deleteProduct(id)).thenReturn(productResponse);
        detailProductPresenter.deleteProductRepository(id);
        Assert.assertTrue(productResponse.isStatus());
        verify(detailProductView).showToast(R.string.delete_ok);
        verify(detailProductView,never()).showAlertDialogError(R.string.no_deleted);
    }

    @Test
    public void methodDeleteProductShouldCallMehtodDeleteProductInRepositoryFalse() throws RepositoryError {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setStatus(false);
        String id = "13g1jhh2d32";
        when(productRepository.deleteProduct(id)).thenReturn(productResponse);
        detailProductPresenter.deleteProductRepository(id);
        Assert.assertFalse(productResponse.isStatus());
        verify(detailProductView).showAlertDialogError(R.string.no_deleted);
        verify(detailProductView,never()).showToast(R.string.delete_ok);
    }

    @Test
    public void methodDeleteProductShouldCallMethodDeleteProductInRepositoryOnError() throws RepositoryError {
        String id = "13g1jhh2d32";
        RepositoryError repositoryError  = new RepositoryError(Constants.DEFAUL_ERROR);
        when(productRepository.deleteProduct(id)).thenThrow(repositoryError);
        detailProductPresenter.deleteProductRepository(id);
        verify(detailProductView).showToast(repositoryError.getMessage());
        verify(detailProductView, never()).showToast(R.string.delete_ok);
    }

    @Test
    public void methodUpdateProductShouldCallMethodUpdateProductInRepositoryOnTrue() throws RepositoryError {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setStatus(true);
        String id = "13g1jhh2d32";
        Product product = new Product();
        when(productRepository.updateProduct(id,product)).thenReturn(productResponse);
        updateProductPresenter.updateProductRepository(id,product);
        Assert.assertTrue(productResponse.isStatus());
        verify(updateProductView).showToast(R.string.update_ok);
        verify(updateProductView, never()).showAlertDialog(R.string.update_fail);
    }

    @Test
    public void methodValidateInternetShouldCallMethodCreateThread(){
        product.setName("Name1");
        product.setDescription("Description1");
        product.setQuantity("Quantity1");
        product.setPrice("Price1");
        when(validateInternet.isConnected()).thenReturn(true);
        createProductPresenter.createProduct("Name1", "Price1", "Quantity1", "Description1");
        //verify(createProductPresenter).createThreadProduct(refEq(product));
        ArgumentCaptor<Product> argument = ArgumentCaptor.forClass(Product.class);
        verify(createProductPresenter).createThreadProduct(argument.capture(),true);
        assertEquals("Name1", argument.getValue().getName());
        assertEquals("Price1", argument.getValue().getPrice());
        assertEquals("Quantity1", argument.getValue().getQuantity());
        assertEquals("Description1", argument.getValue().getDescription());
    }


}
