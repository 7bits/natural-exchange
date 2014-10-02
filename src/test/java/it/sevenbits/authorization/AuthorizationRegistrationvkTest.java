package it.sevenbits.authorization;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

/**
 * Created by sevenbits on 02.10.14.
 */
public class AuthorizationRegistrationvkTest {
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
    public void testAddNewAds() throws Exception {
        driver.get(baseUrl + "/");
        driver.findElement(By.linkText("Регистрация")).click();
        driver.findElement(By.cssSelector("html body div#fancybox-wrap div#fancybox-outer div#fancybox-content div div#registration-form form#enter.js-registration-form div.registration-form.nexchange-mainbg div.reg-and-logo a.vk-logo.logo-pos.js-vk-entry")).click();
        driver.findElement(By.name("email")).clear();
        driver.findElement(By.name("email")).sendKeys("+79069915343");
        driver.findElement(By.name("pass")).clear();
        driver.findElement(By.name("pass")).sendKeys("Sevenbits");
        driver.findElement(By.id("install_allow")).click();
        driver.findElement(By.name("email")).clear();
        driver.findElement(By.name("email")).sendKeys("antonovandrey@ro.ru");
        driver.findElement(By.xpath("//input[@value='Готово']")).click();
        driver.findElement(By.linkText("НА ГЛАВНУЮ")).click();
        String link =  "http://naturalexchange.ru/user/magic.html?code=3d6e8fe0d67fded046fc67b1f51ca6fa&mail=antonovandrey@ro.ru";
        driver.get(link);
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



    private WebElement findElementIfPresent(WebDriver driver, By by){
        try {
            return driver.findElement(by);
        } catch (NoSuchElementException e) {
            return null;
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
