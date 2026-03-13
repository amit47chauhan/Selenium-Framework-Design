package amitchauhan.TestComponents;

import amitchauhan.pageobjects.LandingPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class BaseTest {
    public WebDriver driver;
    public LandingPage landingPage;
    public WebDriver initializeDriver() throws IOException {
        //Property class - Using this class we can parse .properties files and extract all global parameter value.
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream( System.getProperty("user.dir") + "\\src\\main\\java\\amitchauhan\\resources\\GlobalData.properties");
        // load file
        prop.load(fis);
        String browserName = prop.getProperty("browser");

        //turning off popup password manager leak detection
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("credentials_enable_service", false);
        prefs.put("password_manager_enabled", false);
        Map<String, Object> profile = new HashMap<String, Object>();
        profile.put("password_manager_leak_detection", false);
        prefs.put("profile", profile);
        options.setExperimentalOption("prefs", prefs);

        if (browserName.equalsIgnoreCase("chrome")){
        driver = new ChromeDriver(options);
        } else if (browserName.equalsIgnoreCase("firefox")) {
            //firefox driver
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        return driver;
    }

    @BeforeMethod
    public LandingPage launchApplication() throws IOException {
        driver = initializeDriver();
        landingPage = new LandingPage(driver);
        landingPage.goTo();
        return landingPage;
    }

    @AfterMethod
    public  void tearDown(){
        driver.close();
    }
}
