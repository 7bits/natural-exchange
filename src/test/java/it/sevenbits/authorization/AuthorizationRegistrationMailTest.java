package it.sevenbits.authorization;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;


/**
 * Created by booktina on 07.08.14.
 */
public class AuthorizationRegistrationMailTest {

    private String getDomen;
    private String eMail;
    private String passWord;
    private WebDriver driver;
    private String link;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();


    @Before
    public void setUp() throws Exception {

        driver = new FirefoxDriver();
        getDomen = "http://naturalexchange.ru/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        Md5PasswordEncoder md5encoder = new Md5PasswordEncoder();
        eMail = "andrey-antonoff@list.ru";
        passWord = "sevenbits";
        String userPassword = md5encoder.encodePassword(passWord, "");
        String code = md5encoder.encodePassword(userPassword, eMail);

        link = getDomen + "/user/magic.html?code=" + code + "&mail=" + eMail;
    }
    @Test
    public void testRegistrationpart2() throws Exception {
        driver.get(link);

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
  /*Узнать про генерацию ссылки для завершения регистрации через e-mail



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
       // driver.findElement(By.name("http://naturalexchange.ru/user/magic.html?code=c7c09fe78178e311862141b7bdf19bc8&mail=antonovandrey@ro.ru")).click();
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
*/
}
