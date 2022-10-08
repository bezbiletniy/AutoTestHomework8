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



}
