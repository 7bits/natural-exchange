package it.sevenbits.ads;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by booktina on 01.08.14.
 */


public class AdsAddNewAdTest {
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
    public void testAddNewAd() throws Exception {
        driver.get(baseUrl + "/");
        driver.findElement(By.linkText("Вход")).click();
        driver.findElement(By.id("entry-email")).clear();
        driver.findElement(By.id("entry-email")).sendKeys("antonovandrey@ro.ru");
        driver.findElement(By.id("entry-pass")).clear();
        driver.findElement(By.id("entry-pass")).sendKeys("sevenbits");
        driver.findElement(By.id("entry")).click();
        driver.findElement(By.linkText("ДОБАВИТЬ ПРЕДЛОЖЕНИЕ")).click();
        driver.findElement(By.name("title")).clear();
        driver.findElement(By.name("title")).sendKeys("new ads");
        new Select(driver.findElement(By.name("category"))).selectByVisibleText("Игры");
        driver.findElement(By.name("text")).clear();
        driver.findElement(By.name("text")).sendKeys("newads");
        driver.findElement(By.xpath("(//input[@type='text'])[2]")).clear();
        driver.findElement(By.xpath("(//input[@type='text'])[2]")).sendKeys("#ads");
        driver.findElement(By.id("add-tag")).click();
        driver.findElement(By.xpath("//input[@value='ДОБАВИТЬ']")).click();
        driver.findElement(By.linkText("НА ГЛАВНУЮ")).click();
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
