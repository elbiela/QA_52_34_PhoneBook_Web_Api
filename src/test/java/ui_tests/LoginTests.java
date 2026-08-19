package ui_tests;

import dto.User;
import manager.AppManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;

import static utils.PropertiesReader.getProperty;
import static utils.UserFactory.positiveLoginUser;

public class LoginTests extends AppManager {
    LoginPage loginPage;
    User user;

    @BeforeMethod
    public void goToRegistrationLoginPage() {
        new HomePage(getDriver()).clickBtnLogin();
        loginPage = new LoginPage(getDriver());
    }

    @Test
    public void loginPositiveTest() {
        user = positiveLoginUser();
        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnLogin();

        Assert.assertTrue(new ContactsPage(getDriver())
                .validateBtnContacts());
    }

    @Test
    public void loginNegativeWrongEmailTest() {
        user = User.builder()
                .username(getProperty("base.properties", "wrongEmail"))
                .password(getProperty("base.properties", "password"))
                .build();
        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnLogin();

        Assert.assertTrue(loginPage.closeAlert()
                .contains("Wrong email or password"));
    }

    @Test
    public void loginNegativeWrongPasswordTest() {
        user = User.builder()
                .username(getProperty("base.properties", "email"))
                .password(getProperty("base.properties", "wrongPassword"))
                .build();
        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnLogin();

        Assert.assertTrue(loginPage.closeAlert()
                .contains("Wrong email or password"));
    }

    @Test
    public void loginNegativeEmptyEmailFieldTest() {
        user = positiveLoginUser();
        user.setUsername("");
        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnLogin();

        Assert.assertTrue(loginPage.closeAlert()
                .contains("Wrong email or password"));
    }

    @Test
    public void loginNegativeEmptyPasswordFieldTest() {
        user = positiveLoginUser();
        user.setPassword("");
        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnLogin();

        Assert.assertTrue(loginPage.closeAlert()
                .contains("Wrong email or password"));
    }

    @Test
    public void loginNegativeEmptyAllFieldsTest() {
        user = positiveLoginUser();
        user.setUsername("");
        user.setPassword("");
        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnLogin();

        Assert.assertTrue(loginPage.closeAlert()
                .contains("Wrong email or password"));
    }
}
