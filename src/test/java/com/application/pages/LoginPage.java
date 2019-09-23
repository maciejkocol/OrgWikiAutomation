package com.application.pages;

import com.application.common.CommonObjects;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Maciej Kocol
 * <p>
 * Login page objects for OrgWiki application.
 */
public class LoginPage extends CommonObjects {

    //input fields
    @FindBy(xpath = "//input[@type='email']")
    private WebElement usernameField;
    @FindBy(xpath = "//input[@type='password']")
    private WebElement passwordField;

    //buttons
    @FindBy(xpath = "//button[text()='Sign in with Google']")
    private WebElement loginButton;
    @FindBy(xpath = "//span[text()='Next']")
    private WebElement nextButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Logs user into Google for the first time.
     */
    public void login() {
        String username = System.getenv("orgwiki_username");
        String password = System.getenv("orgwiki_password");
        clickElement(loginButton);
        fillInputField(usernameField, username);
        clickElement(nextButton);
        fillInputField(passwordField, password);
        clickElement(nextButton);
    }
}
