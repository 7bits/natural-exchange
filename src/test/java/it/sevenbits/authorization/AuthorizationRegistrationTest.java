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
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "http://naturalexchange.ru/";
        //baseUrl = "http://n-exchange.local/n-exchange/";
        Authorization authorization = new Authorization();
        email = authorization.getEmailRegistration();
        password = authorization.getPasswordRegistration();
        firstName = authorization.getFirstName();
        lastName = authorization.getLastName();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testAuthorization() throws Exception {
        driver.get(baseUrl + "/");
        driver.findElement(By.linkText("Регистрация")).click();
        driver.findElement(By.id("reg-email")).clear();
        driver.findElement(By.id("reg-email")).sendKeys(email);
        driver.findElement(By.id("reg-first-name")).clear();
        driver.findElement(By.id("reg-first-name")).sendKeys(firstName);
        driver.findElement(By.id("reg-last-name")).clear();
        driver.findElement(By.id("reg-last-name")).sendKeys(lastName);
        driver.findElement(By.id("reg-pass")).clear();
        driver.findElement(By.id("reg-pass")).sendKeys(password);
        driver.findElement(By.id("registr")).click();
        TimeUnit.SECONDS.sleep(5);

        if (driver.findElement(By.xpath("/html/body/div[9]/div/div[2]/div[1]/span")).getText().matches("Пользователь с таким e-mail существует.")) {
            driver.quit();
            fail("Account is registered");
        }


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