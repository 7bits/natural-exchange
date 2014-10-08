package it.sevenbits.subscription;

import java.util.concurrent.TimeUnit;

import it.sevenbits.TestOptions;
import it.sevenbits.authorization.Authorization;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SubscriptionNewTest {

    private WebDriver driver;
    private String baseUrl;
    private String email;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private TestOptions testOptions;

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = testOptions.getDomen();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        Authorization authorization = new Authorization();
        email = authorization.getEmailTrue();
    }

    @Test
    public void testSubscriptionAuth() throws Exception {
        driver.get(baseUrl + "/");
        driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div[3]/form/input[1]")).sendKeys(email);
        driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div[3]/form/input[2]")).click();
        TimeUnit.SECONDS.sleep(5);
        if (driver.findElement(By.xpath("/html/body/div[9]/div/div[2]/div[1]/span")).getText().matches("Вы уже подписаны.")) {
            driver.quit();
            fail("You are  subscription!");
        }
        if (driver.findElement(By.xpath("/html/body/div[9]/div/div[2]/div[1]/span")).getText().matches("Вы подписались на наш проект!")) {
            driver.quit();

        }
    }

    @After
    public void tearDown() throws Exception {
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }

}
