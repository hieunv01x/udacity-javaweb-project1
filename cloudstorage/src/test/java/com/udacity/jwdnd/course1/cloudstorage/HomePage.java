package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class HomePage {
    @FindBy(id = "logoutButton")
    private WebElement logoutButton;

    @FindBy(id = "nav-notes-tab")
    private WebElement noteTab;

    @FindBy(id = "addNoteButton")
    private WebElement addNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(className = "edit-note")
    private WebElement editNoteButton;

    @FindBy(className = "delete-note")
    private WebElement deleteNoteButton;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialTab;

    @FindBy(id = "addCredentialButton")
    private WebElement addCredentialButton;

    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credential-decryptPassword")
    private WebElement credentialPassword;

    @FindBy(className = "edit-credential")
    private WebElement editCredentialButton;
    @FindBy(className = "delete-credential")
    private WebElement deleteCredentialButton;

    private WebDriver driver;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void logout() {
        logoutButton.submit();
    }

    public void openNoteTab() throws InterruptedException {
        Thread.sleep(1000);
        noteTab.click();
    }

    public List<Note> getNoteList() throws InterruptedException {
        Thread.sleep(1000);

        List<WebElement> listId = driver.findElements(By.className("noteId"));
        List<WebElement> listTitle = driver.findElements(By.className("noteTitle"));
        List<WebElement> listDescription = driver.findElements(By.className("noteDescription"));
        List<Note> noteList = new ArrayList<>();
        for (int i = 0; i < listTitle.size(); ++i) {
            try {
                int id = Integer.parseInt(listId.get(i).getAttribute("value"));
                String title = listTitle.get(i).getText();
                String description = listDescription.get(i).getText();
                Note note = new Note();
                note.setNoteId(id);
                note.setNoteTitle(title);
                note.setNoteDescription(description);
                noteList.add(note);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return noteList;
    }

    public void createNote(String title, String description) throws InterruptedException {
        Thread.sleep(1000);
        addNoteButton.click();
        Thread.sleep(1000);
        noteTitle.sendKeys(title);
        noteDescription.sendKeys(description);
        noteTitle.submit();
    }

    public void updateNote(String title, String description) throws InterruptedException {
        editNoteButton.click();
        Thread.sleep(1000);
        noteTitle.clear();
        noteTitle.sendKeys(title);
        noteDescription.clear();
        noteDescription.sendKeys(description);
        noteTitle.submit();
    }

    public void deleteNote() {
        deleteNoteButton.click();
    }

    public void openCredentialTab() throws InterruptedException {
        Thread.sleep(1000);
        credentialTab.click();
    }

    public void createCredential(String url, String username, String password) throws InterruptedException {
        Thread.sleep(1000);
        addCredentialButton.click();
        Thread.sleep(1000);
        credentialUrl.sendKeys(url);
        credentialUsername.sendKeys(username);
        credentialPassword.sendKeys(password);
        credentialUrl.submit();
    }

    public void updateCredential(String url, String username, String password) throws InterruptedException {
        editCredentialButton.click();
        Thread.sleep(1000);
        credentialUrl.clear();
        credentialUrl.sendKeys(url);
        credentialUsername.clear();
        credentialUsername.sendKeys(username);
        credentialPassword.clear();
        credentialPassword.sendKeys(password);
        credentialUrl.submit();
    }

    public void deleteCredential() {
        deleteCredentialButton.click();
    }
}