package amitchauhan.pageobjects;

import amitchauhan.AbstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartCatalogue extends AbstractComponent {
    WebDriver driver;

    public CartCatalogue(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //        Assert.assertTrue(match);

    @FindBy(css = ".cartSection h3")
    List<WebElement> cartProducts;

    @FindBy(css = ".totalRow button")
    WebElement checkOut;


    public Boolean verifyProductDisplay(String productName) {
        Boolean match = cartProducts.stream().anyMatch(
                cartProduct -> cartProduct.getText().equalsIgnoreCase(productName)
                        );
        return match;
    }

    public void goToCheckout(){
        checkOut.click();
    }

}
