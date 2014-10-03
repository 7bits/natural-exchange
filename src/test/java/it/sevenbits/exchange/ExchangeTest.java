package it.sevenbits.exchange;

import it.sevenbits.authorization.Authorization;
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
public class ExchangeTest {
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
        email = authorization.getEmailTrue();
        password = authorization.getPasswordTrue();
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

        driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/ul[1]/li[2]/a")).click();
        driver.findElement(By.cssSelector("html body div.container div.content div.container.advertisement-list div.row div.advertisement a.change-button.js-newExchange.js-who-owner")).click();
        driver.findElement(By.xpath("/html/body/div[6]/div/div[9]/div/div/form/div[2]/div[4]/table/tbody/tr[1]/td[1]/input")).click();
        driver.findElement(By.cssSelector("#fancybox-content > div > #new-exchange-popup > #exchange-form > div.exchange-form.nexchange-mainbg > div.end-form > a.exchange-button.js-exchange-complete")).click();
        if (driver.findElement(By.xpath("/html/body/div[8]/div/div[2]/div[1]/span")).getText().matches("Вы совершили обмен.")) {
            driver.findElement(By.linkText("Выход")).click();
        } else {
            driver.quit();
            fail("There is not exchange!");
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
