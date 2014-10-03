package it.sevenbits.authorization;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

/**
 * Created by sevenbits on 03.10.14.
 */
public class AuthorizationLoginLogoutFalseTest {
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
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        Authorization authorization = new Authorization();
        email = authorization.getEmailFalse();
        password = authorization.getPasswordFalse();

    }

    @Test
    public void testAuthorization() throws Exception {
        driver.get(baseUrl + "/");
        driver.findElement(By.linkText("Вход")).click();
        driver.findElement(By.id("entry-email")).clear();
        driver.findElement(By.id("entry-email")).sendKeys(email);
        driver.findElement(By.id("entry-pass")).clear();
        driver.findElement(By.id("entry-pass")).sendKeys(password);
        driver.findElement(By.id("entry")).click();
        TimeUnit.SECONDS.sleep(5);

       if (driver.findElement(By.cssSelector("html body div#fancybox-wrap div#fancybox-outer div#fancybox-content div div#entry-form form#enter.js-entry-form div.entry-form.nexchange-mainbg div.error-msg.js-pass-error")).getText().matches("Вы ввели неверный пароль.")) {
            driver.quit();
            //fail("Password incorrect!");
        }
        /*  if (driver.findElement(By.xpath("/html/body/div[9]/div/div[2]/div[1]/span")).getText().matches("Вы ввели неверный пароль."))
        {
            driver.quit();
            fail("Password incorrect!");
        }
*/
      /*  if (driver.findElement(By.className("gritter-title")).getText().matches("Вы еще не зарегистрированы.")) {
            driver.quit();
            fail("You are not registration!");
        }*/

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
