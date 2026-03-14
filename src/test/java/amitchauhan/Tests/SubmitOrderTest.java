package amitchauhan.Tests;

import amitchauhan.TestComponents.BaseTest;
import amitchauhan.pageobjects.*;

import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;

public class SubmitOrderTest extends BaseTest {
    String productName = "ZARA COAT 3";
    String countryName = "india";

    @Test
    public void submitOrder() throws IOException {
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
    }

    @Test(dependsOnMethods = {"submitOrder"})
    public void orderHistoryCheck(){
        ProductCatalogue productCatalogue = landingPage.loginApplication("virat.kohli@gmail.com", "Virat@123");
        OrderPage orderPage = productCatalogue.goToOrder();
        Assert.assertTrue(orderPage.verifyOrderDisplay(productName));
    }
}
