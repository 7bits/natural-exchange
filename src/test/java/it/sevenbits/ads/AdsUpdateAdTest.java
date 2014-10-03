package it.sevenbits.ads;

import java.util.concurrent.TimeUnit;

import it.sevenbits.authorization.Authorization;
import org.junit.*;
import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by booktina on 07.08.14.
 */
public class AdsUpdateAdTest {

    private WebDriver driver;
    private String baseUrl;
    private String email;
    private String password;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "http://naturalexchange.ru/";
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Authorization authorization = new Authorization();
        email = authorization.getEmailTrue();
        password = authorization.getPasswordTrue();
        authorizationLogin();
        TimeUnit.SECONDS.sleep(5);
    }

    @Test
    public void testUpdateAd() throws Exception {

        driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/ul[1]/li[2]/a")).click();
        driver.findElement(By.linkText("new ads")).click();

        driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div[1]/a[2]")).click();

        driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/form/div[1]/input")).sendKeys(" edit");

        driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/form/div[3]/div[1]/input[1]")).sendKeys("#testediting");

        driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/form/div[3]/div[1]/a")).click();

        driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/form/div[4]/input[1]")).click();
        driver.findElement(By.linkText("Выход")).click();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    public void authorizationLogin() throws Exception {
        driver.get(baseUrl + "/");
        driver.findElement(By.linkText("Вход")).click();
        driver.findElement(By.id("entry-email")).clear();
        driver.findElement(By.id("entry-email")).sendKeys(email);
        driver.findElement(By.id("entry-pass")).clear();
        driver.findElement(By.id("entry-pass")).sendKeys(password);
        driver.findElement(By.id("entry")).click();
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
