package amitchauhan;

import amitchauhan.pageobjects.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.testng.Assert;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class submitOrderTest {
    public static void main(String[] args) {

        String productName = "ZARA COAT 3";
        String countryName = "india";

        //turning off popup password manager leak detection
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("credentials_enable_service", false);
        prefs.put("password_manager_enabled", false);
        Map<String, Object> profile = new HashMap<String, Object>();
        profile.put("password_manager_leak_detection", false);
        prefs.put("profile", profile);
        options.setExperimentalOption("prefs", prefs);

        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        LandingPage landingPage = new LandingPage(driver);
        landingPage.goTo();

        ProductCatalogue productCatalogue = landingPage.loginApplication("virat.kohli@gmail.com","Virat@123");
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
