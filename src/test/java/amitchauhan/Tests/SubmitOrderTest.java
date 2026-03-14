package amitchauhan.Tests;

import amitchauhan.TestComponents.BaseTest;
import amitchauhan.pageobjects.*;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class SubmitOrderTest extends BaseTest {
    String countryName = "india";

    @Test(dataProvider ="getData", groups = "Purchase")
    public void submitOrder(HashMap<String, String> input) throws IOException {
        ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
        productCatalogue.addProductToCart(input.get("product"));

        CartCatalogue cartCatalogue = productCatalogue.goToCart();
        Boolean match = cartCatalogue.verifyProductDisplay(input.get("product"));
        Assert.assertTrue(match);

        CheckoutPage checkoutPage = cartCatalogue.goToCheckout();
        checkoutPage.selectCountry(countryName);

        ConfirmPage confirmPage = checkoutPage.submit();
        String confirmMessage = confirmPage.confirmMessage();

        Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
    }

    @Test(dependsOnMethods = {"submitOrder"}, dataProvider = "getData")
    public void orderHistoryCheck(HashMap<String, String> input){
        ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
        OrderPage orderPage = productCatalogue.goToOrder();
        Assert.assertTrue(orderPage.verifyOrderDisplay(input.get("product")));
    }

    @DataProvider
    public Object[][] getData() throws IOException {
//        HashMap<String, String> map1 = new HashMap<String, String>();
//        map1.put("email", "virat.kohli@gmail.com");
//        map1.put("password", "Virat@123");
//        map1.put("product", "ZARA COAT 3");
//
//        HashMap<String, String> map2 = new HashMap<String, String>();
//        map2.put("email", "test4545@gmail.com");
//        map2.put("password", "Test@123");
//        map2.put("product", "ADIDAS ORIGINAL");

        List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")
                + "\\src\\main\\java\\amitchauhan\\data\\PurchaseOrder.json");

        return new Object[][] {
                {data.get(0)}, {data.get(1)}
        };
    }
}
