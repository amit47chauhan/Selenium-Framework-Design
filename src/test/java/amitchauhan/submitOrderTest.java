package amitchauhan;

import amitchauhan.pageobjects.CartCatalogue;
import amitchauhan.pageobjects.LandingPage;
import amitchauhan.pageobjects.ProductCatalogue;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class submitOrderTest {
    public static void main(String[] args) {

        String productName = "ZARA COAT 3";

        //turning off popup password manager leak detection
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("credentials_enable_service", false);
        prefs.put("password_manager_enabled", false);
        Map<String, Object> profile = new HashMap<String, Object>();
        profile.put("password_manager_leak_detection", false);
        prefs.put("profile", profile);
        options.setExperimentalOption("prefs", prefs);
        // initializing Web-driver
        WebDriver driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        LandingPage landingPage = new LandingPage(driver);
        landingPage.goTo();
        driver.manage().window().maximize();
        landingPage.loginApplication("virat.kohli@gmail.com","Virat@123");

        ProductCatalogue productCatalogue = new ProductCatalogue(driver);
        CartCatalogue cartCatalogue = new CartCatalogue(driver);

        productCatalogue.addProductToCart(productName);
        productCatalogue.goToCart();
        Boolean match = cartCatalogue.verifyProductDisplay(productName);
        Assert.assertTrue(match);
        cartCatalogue.goToCheckout();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));


        // Clicking on the checkout button to proceed further
        driver.findElement(By.cssSelector(".totalRow button")).click();

        Actions a = new Actions(driver);
        a.sendKeys(driver.findElement(By.cssSelector("input[placeholder='Select Country']")), "india")
                .build().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));

        driver.findElement(By.xpath("//button[contains(@class,'ta-item')][2]")).click();

        WebElement Submit = driver.findElement(By.cssSelector(".action__submit"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", Submit);

        String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

        driver.close();
    }
}
