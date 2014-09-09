package it.sevenbits.ads;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

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
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void testAddAd() throws Exception {
        driver.get(baseUrl + "/");
        driver.findElement(By.linkText("вход")).click();
        driver.findElement(By.name("j_username")).clear();
        driver.findElement(By.name("j_username")).sendKeys("antonovandrey@ro.ru");
        driver.findElement(By.name("j_password")).clear();
        driver.findElement(By.name("j_password")).sendKeys("sevenbits");
        driver.findElement(By.cssSelector("input.sendOk")).click();
        driver.findElement(By.linkText("Добавить предложение")).click();
        driver.findElement(By.id("title")).clear();
        driver.findElement(By.id("title")).sendKeys("qwer test");
        driver.findElement(By.id("text")).clear();
        driver.findElement(By.id("text")).sendKeys("test");
        driver.findElement(By.id("tags")).clear();
        driver.findElement(By.id("tags")).sendKeys("#test");
        driver.findElement(By.cssSelector("input.sendAdv")).click();
        driver.findElement(By.cssSelector("img[alt=\"ex4ange)\"]")).click();
        driver.findElement(By.linkText("Добавить")).click();
        driver.findElement(By.id("title")).clear();
        driver.findElement(By.id("title")).sendKeys("qwer test");
        driver.findElement(By.id("text")).clear();
        driver.findElement(By.id("text")).sendKeys("test");
        driver.findElement(By.id("tags")).clear();
        driver.findElement(By.id("tags")).sendKeys("#test");
        driver.findElement(By.cssSelector("input.sendAdv")).click();
        driver.findElement(By.cssSelector("img[alt=\"ex4ange)\"]")).click();
        driver.findElement(By.linkText("выход")).click();
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
