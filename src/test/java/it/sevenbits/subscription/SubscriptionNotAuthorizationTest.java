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
public class SubscriptionNotAuthorizationTest {
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
        driver.findElement(By.cssSelector("input.for-email")).clear();
        driver.findElement(By.cssSelector("input.for-email")).sendKeys("antonoff@mail.com");
        driver.findElement(By.cssSelector("input.submit-email")).click();
       // driver.findElement(By.cssSelector("input.for-emqil").name("Ваш e-mail добавлен."));
        //driver.findElement(By.cssSelector("input.for-emqil").name("Вы уже подписаны"));
       // driver.findElement(By.)

        if (isElementPresent(By.xpath("Вы уже подписаны."))) {
            Assert.assertTrue(rezult);
        }
        else {
            rezult = false;
            Assert.assertFalse(rezult);
        }
        TimeUnit.SECONDS.sleep(3);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
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
