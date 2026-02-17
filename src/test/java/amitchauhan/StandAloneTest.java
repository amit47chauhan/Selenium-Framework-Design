package amitchauhan;

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

public class StandAloneTest {
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
        WebDriver driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://rahulshettyacademy.com/client");

        // Entering id and pass to log-in
        driver.findElement(By.id("userEmail")).sendKeys("virat.kohli@gmail.com");
        driver.findElement(By.id("userPassword")).sendKeys("Virat@123");
        driver.findElement(By.id("login")).click();

        // Explicitly waiting for 5 seconds to load all the products
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));

        // Getting list of all the products available
        List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));

        //Finding product with name containing ZARA COAT 3
        WebElement prod = products.stream().filter(
                product -> product.findElement(By.cssSelector("b"))
                        .getText()
                        .equals(productName)
        ).findFirst().orElse(null);

        assert prod != null;
        // Clicking on Add to cart button
        prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();

        // Waiting for to check if add to cart notification popped or not
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
        // checking for loading screen to disappear after adding product to cart
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));

        //clicking on cart
        driver.findElement(By.cssSelector("[routerlink*='cart']")).click();

        // Getting list of all the product added to cart and checking if its matches with the product name which
        // we had added earlier

        List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
        boolean match = cartProducts.stream().anyMatch(
                cartProduct -> cartProduct.getText().equalsIgnoreCase(productName)
        );
        Assert.assertTrue(match);

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
