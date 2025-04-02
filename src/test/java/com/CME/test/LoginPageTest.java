package com.CME.test;

import com.CME.BaseClass;
import com.CME.Pages.LoginPage;
import com.CME.base.BaseTest;
import com.CME.utils.Common;
import org.testng.annotations.*;

import static io.reactivex.rxjava3.internal.util.NotificationLite.getValue;

public class LoginPageTest extends BaseTest {

    public static LoginPage loginPage;



    @BeforeClass
    public void beforeClass(String type) {
        BaseClass.setup();
        loginPage = new LoginPage();

    }

    @BeforeMethod
    public void beforeMethod() {

        loginPage.visitURL();
    }

    @Test(description = "Verify that User is able to login")
    public void verifyUserIsAbleToLogin() {
        loginPage.enterUsername(getValue("users.admin.username"))
                .enterPassword(getValue("users.admin.password"))
                .clickOnLoginButton();
       // Common.assertionTrue(dashboardPage.isDashboardVisible(),"Dashboard is displayed");
    }

    @AfterMethod
    public void afterTest(){
        BaseTest.tearDown();
    }
}


