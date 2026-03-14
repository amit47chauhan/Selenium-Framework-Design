package amitchauhan.Tests;

import amitchauhan.TestComponents.BaseTest;
import amitchauhan.pageobjects.*;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.IOException;

public class SubmitOrderTest extends BaseTest {
    String countryName = "india";

    @Test(dataProvider ="getData", groups = "Purchase")
    public void submitOrder(String email, String password, String productName) throws IOException {
        ProductCatalogue productCatalogue = landingPage.loginApplication(email, password);
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

    @Test(dependsOnMethods = {"submitOrder"}, dataProvider = "getData")
    public void orderHistoryCheck(String email, String password, String productName){
        ProductCatalogue productCatalogue = landingPage.loginApplication(email, password);
        OrderPage orderPage = productCatalogue.goToOrder();
        Assert.assertTrue(orderPage.verifyOrderDisplay(productName));
    }

    @DataProvider
    public Object[][] getData(){
        return new Object[][] {
                {"virat.kohli@gmail.com","Virat@123","ZARA COAT 3"},
                {"test4545@gmail.com", "Test@123", "ADIDAS ORIGINAL"}
        };
    }
}
