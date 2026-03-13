package amitchauhan.Tests;

import amitchauhan.TestComponents.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ErrorValidations extends BaseTest {

    @Test
    public void submitOrder(){
        landingPage.loginApplication("virat.kohli@gmail.com", "Virat");
        Assert.assertEquals(landingPage.getErrorMessage(), "Incorrect email or password.");
    }
}
