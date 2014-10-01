package it.sevenbits.subscription;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
/**
 * Created by booktina on 07.08.14.
 */
public class SubscriptionNotNewTest {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private boolean rezult = true;

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "http://naturalexchange.ru/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testSubscription() throws Exception {
        driver.get(baseUrl + "/");
        driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div[3]/form/input[1]")).sendKeys("andrey-antonoff@list.ru");
        driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div[3]/form/input[2]")).click();

        TimeUnit.SECONDS.sleep(5);
        if (driver.findElement(By.xpath("/html/body/div[9]/div/div[2]/div[1]/span")).getText().matches("Вы подписались на наш проект!")) {
            driver.quit();
            fail("You are new subscription!");
        }
        if (driver.findElement(By.xpath("/html/body/div[9]/div/div[2]/div[1]/span")).getText().matches("Вы уже подписаны.")) {
            driver.quit();

        }

    }

    @After
    public void tearDown() throws Exception {
       // driver.quit();
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
