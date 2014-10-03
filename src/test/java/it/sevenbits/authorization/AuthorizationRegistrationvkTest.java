package it.sevenbits.authorization;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

/**
 * Created by sevenbits on 02.10.14.
 */
public class AuthorizationRegistrationvkTest {
    private WebDriver driver;
    private String baseUrl;
    private String email;
    private String number;
    private String password;
    private String link;
    private String passwordlink;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "http://naturalexchange.ru/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        Authorization authorization = new Authorization();
        number = authorization.getNumberVkRegistration();
        password = authorization.getPasswordVkRegistration();
        email = authorization.getEmailVkRegistration();
        passwordlink = authorization.getPasswordLink();

        Md5PasswordEncoder md5encoder = new Md5PasswordEncoder();
        String userPassword = md5encoder.encodePassword(passwordlink, email);

        link = baseUrl + "/user/magic.html?code=" + userPassword + "&mail=" + email;


    }



    @Test
    public void testAddNewAds() throws Exception {
        driver.get(baseUrl + "/");
        driver.findElement(By.linkText("Регистрация")).click();
        driver.findElement(By.cssSelector("html body div#fancybox-wrap div#fancybox-outer div#fancybox-content div div#registration-form form#enter.js-registration-form div.registration-form.nexchange-mainbg div.reg-and-logo a.vk-logo.logo-pos.js-vk-entry")).click();
        driver.findElement(By.name("email")).clear();
        driver.findElement(By.name("email")).sendKeys(number);
        driver.findElement(By.name("pass")).clear();
        driver.findElement(By.name("pass")).sendKeys(password);
        driver.findElement(By.id("install_allow")).click();
        driver.findElement(By.name("email")).clear();
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.xpath("//input[@value='Готово']")).click();
        driver.findElement(By.linkText("НА ГЛАВНУЮ")).click();

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
