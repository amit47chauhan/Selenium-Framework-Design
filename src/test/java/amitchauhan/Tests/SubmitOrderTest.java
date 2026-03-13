package amitchauhan.Tests;

import amitchauhan.TestComponents.BaseTest;
import amitchauhan.pageobjects.*;

import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;

public class SubmitOrderTest extends BaseTest {
    @Test
    public void submitOrder() throws IOException {
        String productName = "ZARA COAT 3";
        String countryName = "india";
        LandingPage landingPage = launchApplication();
        ProductCatalogue productCatalogue = landingPage.loginApplication("virat.kohli@gmail.com", "Virat@123");
        productCatalogue.addProductToCart(productName);

        CartCatalogue cartCatalogue = productCatalogue.goToCart();
        Boolean match = cartCatalogue.verifyProductDisplay(productName);
        Assert.assertTrue(match);

        CheckoutPage checkoutPage = cartCatalogue.goToCheckout();
        checkoutPage.selectCountry(countryName);

        ConfirmPage confirmPage = checkoutPage.submit();
        String confirmMessage = confirmPage.confirmMessage();

        Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

        driver.close();
    }
}
