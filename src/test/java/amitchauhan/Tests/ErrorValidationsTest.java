package amitchauhan.Tests;

import amitchauhan.TestComponents.BaseTest;
import amitchauhan.pageobjects.CartCatalogue;
import amitchauhan.pageobjects.ProductCatalogue;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ErrorValidationsTest extends BaseTest {

    @Test(groups = {"ErrorHandling"})
    public void loginErrorValidation(){
        landingPage.loginApplication("virat.kohli@gmail.com", "Virat");
        Assert.assertEquals(landingPage.getErrorMessage(), "Incorrect email or password.");
    }

    @Test
    public void productErrorValidation(){
        String productName = "ZARA COAT 3";
        ProductCatalogue productCatalogue = landingPage.loginApplication("test4545@gmail.com", "Test@123");
        productCatalogue.addProductToCart(productName);
        CartCatalogue cartCatalogue = productCatalogue.goToCart();
        Boolean match = cartCatalogue.verifyProductDisplay("ZARA COAT 33");
        Assert.assertFalse(match);
    }
}
