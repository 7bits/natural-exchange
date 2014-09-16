package it.sevenbits.authorization;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by booktina on 05.08.14.
 */
public class AuthorizationRegistrationTest {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "http://naturalexchange.ru/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testAuthorization() throws Exception {
        driver.get(baseUrl + "/");
        driver.findElement(By.linkText("Регистрация")).click();
        driver.findElement(By.id("reg-email")).clear();
        driver.findElement(By.id("reg-email")).sendKeys("antonovandrey@ro.ru");
        driver.findElement(By.id("reg-first-name")).clear();
        driver.findElement(By.id("reg-first-name")).sendKeys("Andrey");
        driver.findElement(By.id("reg-last-name")).clear();
        driver.findElement(By.id("reg-last-name")).sendKeys("Antonov");
        driver.findElement(By.id("reg-pass")).clear();
        driver.findElement(By.id("reg-pass")).sendKeys("sevenbits");
        driver.findElement(By.id("registr")).click();
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