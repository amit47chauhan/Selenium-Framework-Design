package amitchauhan.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage {
    WebDriver driver;
    public LandingPage(WebDriver driver){
        this.driver = driver;
        // initElements triggers the initialization of all the element
        PageFactory.initElements(driver,this);
    }

    //WebElement userEmail = driver.findElement(By.id("userEmail"));

    //PageFactory - design pattern - reduce the syntax while creating your web elements
    @FindBy(id = "userEmail")
    WebElement userEmail;

    @FindBy(id = "userPassword")
    WebElement userPassword;

    @FindBy(id = "login")
    WebElement submit;

    //Action method
    public void loginApplication(String email, String password){
        userEmail.sendKeys(email);
        userPassword.sendKeys(password);
        submit.click();
    }

    public void goTo(){
        driver.get("https://rahulshettyacademy.com/client");
    }
}
