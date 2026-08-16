package ui_tests;

import dto.User;
import dto.UserLombok;
import manager.AppManager;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;
import utils.UserFactory;

import static utils.PropertiesReader.*;

import static utils.UserFactory.*;

import java.util.Random;

public class RegistrationTests extends AppManager {
    LoginPage loginPage;

    @BeforeMethod
    public void goToRegistrationLoginPage() {
        new HomePage(getDriver()).clickBtnLogin();
        loginPage = new LoginPage(getDriver());
    }

    @Test
    public void registrationPositiveTest() {
        int i = new Random().nextInt(1000);
        UserLombok user = UserLombok.builder()
                .username("cvbfgnj" + i + "@xvcb.bn")
                .password(getProperty("base.properties", "password"))
                .build();
        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(new ContactsPage(getDriver())
                .validateTextInMessageNoContacts("No Contacts here!"));
    }

    @Test
    public void registrationPositiveWithFakerTest() {
        UserLombok user = positiveRegistrationUser();
        System.out.println(user);
        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(new ContactsPage(getDriver())
                .validateTextInMessageNoContacts("No Contacts here!"));
    }


}
