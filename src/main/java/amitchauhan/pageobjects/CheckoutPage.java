package amitchauhan.pageobjects;

import amitchauhan.AbstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage extends AbstractComponent {
    WebDriver driver;
    public CheckoutPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(css = "input[placeholder='Select Country']")
    WebElement country;

    @FindBy(xpath = "//button[contains(@class,'ta-item')][2]")
    WebElement selectCountry;

    @FindBy(css = ".action__submit")
    WebElement submit;

    By result = By.cssSelector(".ta-results");

    public void selectCountry(String countryName){
        Actions a = new Actions(driver);
        a.sendKeys(country,countryName).build().perform();
        waitForElementToAppear(result);
        selectCountry.click();
    }

    public ConfirmPage submit(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", submit);

        return new ConfirmPage(driver);
    }


    //        Actions a = new Actions(driver);
    //        a.sendKeys(driver.findElement(By.cssSelector("input[placeholder='Select Country']")), "india")
    //                .build().perform();
    //        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
    //
    //        driver.findElement(By.xpath("//button[contains(@class,'ta-item')][2]")).click();
    //
    //        WebElement Submit = driver.findElement(By.cssSelector(".action__submit"));
    //        JavascriptExecutor js = (JavascriptExecutor) driver;
    //        js.executeScript("arguments[0].click();", Submit);
    //
    //        String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
    //        Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

}
