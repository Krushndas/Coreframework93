package com.CME.Pages;

import com.CME.base.BaseTest;

import static com.CME.utils.TestDataUtil.getValue;
import static com.CME.utils.TestUtils.*;

public class LoginPage extends BaseTest {

    public void visitURL(){
        driver.get(getValue("app.url"));
    }

    public LoginPage enterUsername(String username){
        enterValue(getLocator("loginPage.usernameField"), username);
        return this;
    }

    public LoginPage enterPassword(String password){
        enterValue((getLocator("loginPage.passwordField")), password);
        return this;
    }

    public LoginPage clickOnLoginButton(){
        clickElement(getLocator("loginPage.loginButton"));
        return this;
    }
}

