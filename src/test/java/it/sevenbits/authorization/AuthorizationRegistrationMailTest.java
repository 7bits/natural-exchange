package it.sevenbits.authorization;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
/**
 * Created by booktina on 07.08.14.
 */
public class AuthorizationRegistrationMailTest {

    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "https://mail.rambler.ru/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testPegistrationpart2() throws Exception {
        driver.get(baseUrl + "/#/folder/INBOX/");
        driver.findElement(By.id("login")).clear();
        driver.findElement(By.id("login")).sendKeys("antonovandrey");
        driver.findElement(By.cssSelector("b.uiDropdownText")).click();
        new Select(driver.findElement(By.id("domain"))).selectByVisibleText("@ro.ru");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("sevenbits7bits");
        driver.findElement(By.name("profile.send")).click();
        driver.findElement(By.name("регистрация на сайте")).click();
        driver.findElement(By.name("http://naturalexchange.ru/user/magic.html?code=c7c09fe78178e311862141b7bdf19bc8&mail=antonovandrey@ro.ru")).click();
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