package it.sevenbits.authorization;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

/**
 * Created by sevenbits on 02.10.14.
 */
public class Authorization {

    private String emailTrue = "bookatina@gmail.com";
    private String passwordTrue = "111";
    private String emailFalse = "bookatina@gmail.com";
    private String passwordFalse = "1111";
    private String numberVk = "+79069915343";
    private String passwordVk = "Sevenbits";
    private String emailVk = "antonovandrey@ro.ru";
    private String emailRegistration = "andrey-antonoff@list.ru";
    private String passwordRegistration = "sevenbits";
    private String firstName = "Andrey";
    private String lastName = "Antonov";

    private String numberVkRegistration = "+79136038232";
    private String passwordVkRegistration = "Sevenbits7bits";
    private String emailVkRegistration = "antonoff@mail.com";
    private String passwordLink = "dsfklosdaaevvsdfywewehwehsdu";


    public String getEmailTrue() { return emailTrue; }
    public String getPasswordTrue() { return passwordTrue; }

    public String getEmailFalse() { return emailFalse; }
    public String getPasswordFalse() { return passwordFalse; }

    public String getEmailVk() { return emailVk; }
    public String getPasswordVk() { return passwordVk; }
    public String getNumberVk() { return numberVk; }

    public String getEmailVkRegistration() { return emailVkRegistration; }
    public String getPasswordVkRegistration() { return passwordVkRegistration; }
    public String getNumberVkRegistration() { return numberVkRegistration; }
    public String getPasswordLink() { return passwordLink; }

    public String getEmailRegistration() { return emailRegistration; }
    public String getPasswordRegistration() { return passwordRegistration; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }


}
