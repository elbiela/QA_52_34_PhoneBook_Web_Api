package ui_tests;

import data_providers.UserDataProvider;
import dto.User;
import lombok.extern.slf4j.Slf4j;
import manager.AppManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;

import static utils.PropertiesReader.*;

import static utils.UserFactory.*;

import java.util.Random;

@Slf4j
public class RegistrationTests extends AppManager {
    LoginPage loginPage;
    User user;
    SoftAssert softAssert;

    @BeforeMethod
    public void goToRegistrationLoginPage() {
        logger.info("Start registration test");
        new HomePage(getDriver()).clickBtnLogin();
        loginPage = new LoginPage(getDriver());
        softAssert = new SoftAssert();
    }

    @Test
    public void registrationPositiveTest() {
        int i = new Random().nextInt(1000);
        user = User.builder()
                .username("cvbfgnj" + i + "@xvcb.bn")
                .password(getProperty("base.properties", "password"))
                .build();
        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(new ContactsPage(getDriver())
                .isTextInMessageNoContacts("No Contacts here!"));
    }

    @Test
    public void registrationPositiveWithFakerTest() {
        user = positiveRegistrationUser();
        System.out.println(user);
        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(new ContactsPage(getDriver())
                .isTextInMessageNoContacts("No Contacts here!"));
    }


    @Test
    public void registrationNegativeEmptyAllFieldsTest() {
        loginPage.clickBtnRegistration();

        Assert.assertTrue(loginPage.closeAlert()
                        .contains("Wrong email or password format"));
    }

    @Test
    public void registrationNegativeEmptyEmailFieldTest() {
        user = positiveRegistrationUser();
        user.setUsername("");
        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnRegistration();

        Assert.assertTrue(loginPage.closeAlert()
                .contains("Wrong email or password format"));
    }

    @Test
    public void registrationNegativeEmptyPasswordFieldTest() {
        user = positiveRegistrationUser();
        user.setPassword("");
        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnRegistration();

        Assert.assertTrue(loginPage.closeAlert()
                .contains("Wrong email or password format"));
    }

    @Test(dataProvider = "dataProviderWrongEmailAndPasswordFormat",
            dataProviderClass = UserDataProvider.class)
    public void registrationNegativeWrongEmailAndPasswordFormatTest(User user) {
        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnRegistration();

        Assert.assertTrue(loginPage.closeAlert()
                .contains("Wrong email or password format"));
    }

    @Test
    public void registrationNegativeExistingUserTest() {
        user = positiveLoginUser();
        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnRegistration();

        Assert.assertTrue(loginPage.closeAlert()
                        .contains("User already exist"));
    }

}
