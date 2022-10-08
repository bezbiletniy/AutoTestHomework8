package ru.netology.test;

import org.junit.jupiter.api.AfterAll;
import ru.netology.data.DataHelper;
import ru.netology.data.SqlHelper;
import ru.netology.page.LoginPage;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.SqlHelper.cleanDatabase;

public class Test {

    @AfterAll
    static void tearDown () {
        cleanDatabase();
    }

    @org.junit.jupiter.api.Test
    public void shouldLogin() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.loginValid(authInfo);
        var verificationCode = SqlHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }

    @org.junit.jupiter.api.Test
    public void shouldNotValidUser() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.user();
        loginPage.loginValid(authInfo);
        loginPage.getError();
    }

    @org.junit.jupiter.api.Test
    public void shouldNotValidLogin() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = new DataHelper.AuthInfo(DataHelper.user().getLogin(), DataHelper.getAuthInfoWithTestData().getPassword());
        loginPage.loginValid(authInfo);
        loginPage.getError();
    }

    @org.junit.jupiter.api.Test
    public void shouldNotValidPassword() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = new DataHelper.AuthInfo(DataHelper.getAuthInfoWithTestData().getLogin(), DataHelper.user().getPassword());
        loginPage.loginValid(authInfo);
        loginPage.getError();
    }

    @org.junit.jupiter.api.Test
    public void shouldNotValidVerificationCode() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.loginValid(authInfo);
        verificationPage.VerificationPage();
        var verificationCode = DataHelper.getVerificationCode().getCode();
        verificationPage.verifyPage(verificationCode);
        verificationPage.getError();
    }

    @org.junit.jupiter.api.Test
    public void shouldNotValidPasswordThreeTimes() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfoFirst = new DataHelper.AuthInfo(DataHelper.getAuthInfoWithTestData().getLogin(), DataHelper.user().getPassword());
        loginPage.loginValid(authInfoFirst);
        loginPage.getError();
        loginPage.cleanStrings();
        var authInfoSecond = new DataHelper.AuthInfo(DataHelper.getAuthInfoWithTestData().getLogin(), DataHelper.user().getPassword());
        loginPage.loginValid(authInfoSecond);
        loginPage.getError();
        loginPage.cleanStrings();
        var authInfoThird = new DataHelper.AuthInfo(DataHelper.getAuthInfoWithTestData().getLogin(), DataHelper.user().getPassword());
        loginPage.loginValid(authInfoThird);
        loginPage.getBlockError();
    }



}
