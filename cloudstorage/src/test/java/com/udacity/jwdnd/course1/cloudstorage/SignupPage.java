package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {
    @FindBy(id = "inputFirstName")
    private WebElement firstNameInput;

    @FindBy(id = "inputLastName")
    private WebElement lastNameInput;

    @FindBy(id = "inputUsername")
    private WebElement usernameInput;

    @FindBy(id = "inputPassword")
    private WebElement passwordInput;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void setFirstNameInput(String firstname) {
        firstNameInput.sendKeys(firstname);
    }

    public void setLastNameInput(String lastname) {
        lastNameInput.sendKeys(lastname);
    }

    public void setUsernameInput(String username) {
        usernameInput.sendKeys(username);
    }

    public void setPasswordInput(String password) {
        passwordInput.sendKeys(password);
    }

    public void submit() {
        usernameInput.submit();
    }
}