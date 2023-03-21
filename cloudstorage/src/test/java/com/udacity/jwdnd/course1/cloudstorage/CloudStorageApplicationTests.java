package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.common.Message;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;
	private SignupPage signupPage;
	private LoginPage loginPage;
	private HomePage homePage;
	private static String firstName;
	private static String lastName;
	private static String username;
	private static String password;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		firstName = "Nguyen";
		lastName = "Hieu";
		username = "hieunv";
		password = "123456";
	}

	@BeforeEach
	public void beforeEach() throws InterruptedException {
		this.driver = new ChromeDriver();
		driver.get("http://localhost:" + this.port + "/signup");
		signupPage = new SignupPage(driver);
		signupPage.setFirstNameInput(firstName);
		signupPage.setLastNameInput(lastName);
		signupPage.setUsernameInput(username);
		signupPage.setPasswordInput(password);
		signupPage.submit();
		Thread.sleep(1000);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password) {
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful.
		// You may have to modify the element "success-msg" and the sign-up
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains(Message.CREATE_USER_SUCCESS.toString()));
	}


	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password) {
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 * <p>
	 * If this test is failing, please ensure that you are handling redirecting users
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric:
	 * https://review.udacity.com/#!/rubrics/2724/view
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection", "Test", "RT", "123");

		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 * <p>
	 * If this test is failing, please ensure that you are handling bad URLs
	 * gracefully, for example with a custom error page.
	 * <p>
	 * Read more about custom error pages at:
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL", "Test", "UT", "123");
		doLogIn("UT", "123");

		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 * <p>
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code.
	 * <p>
	 * Read more about file size limits here:
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File", "Test", "LFT", "123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertTrue(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	@Test
	public void homePageInaccessibleTest() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void createNoteTest() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.setUsernameInput(username);
		loginPage.setPasswordInput(password);
		loginPage.submit();
		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		homePage.openNoteTab();
		Note note = new Note();
		note.setNoteTitle("note");
		note.setNoteDescription("note description");
		homePage.createNote(note.getNoteTitle(), note.getNoteDescription());
		homePage.openNoteTab();
		Thread.sleep(1000);
		List<Note> notes = homePage.getNoteList();
		System.out.println(notes);
		Assertions.assertTrue(driver.findElement(By.id("success-message")).getText().contains(Message.CREATE_NOTE_SUCCESS.toString()));
	}

	@Test
	public void updateNoteTest() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.setUsernameInput(username);
		loginPage.setPasswordInput(password);
		loginPage.submit();
		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		homePage.openNoteTab();
		Thread.sleep(1000);
		Note oldNote = new Note();
		oldNote.setNoteTitle("old note");
		oldNote.setNoteDescription("old note description");
		Note newNote = new Note();
		newNote.setNoteTitle("new note");
		newNote.setNoteDescription("new note description");
		homePage.createNote(oldNote.getNoteTitle(), oldNote.getNoteDescription());
		homePage.logout();
		Thread.sleep(1000);
		driver.get("http://localhost:" + this.port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.setUsernameInput(username);
		loginPage.setPasswordInput(password);
		loginPage.submit();
		Thread.sleep(1000);
		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		Thread.sleep(1000);
		homePage.openNoteTab();

		Thread.sleep(1000);
		homePage.updateNote(newNote.getNoteTitle(), newNote.getNoteDescription());
		homePage.openNoteTab();
		Thread.sleep(1000);

		Assertions.assertTrue(driver.findElement(By.id("success-message")).getText().contains(Message.UPDATE_NOTE_SUCCESS.toString()));
	}

	@Test
	public void deleteNoteTest() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.setUsernameInput(username);
		loginPage.setPasswordInput(password);
		loginPage.submit();
		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		homePage.openNoteTab();
		Thread.sleep(1000);
		Note note = new Note();
		note.setNoteTitle("new note");
		note.setNoteDescription("new note description");
		homePage.createNote(note.getNoteTitle(), note.getNoteDescription());
		homePage.logout();
		Thread.sleep(1000);

		driver.get("http://localhost:" + this.port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.setUsernameInput(username);
		loginPage.setPasswordInput(password);
		loginPage.submit();
		Thread.sleep(1000);
		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		Thread.sleep(1000);
		homePage.openNoteTab();
		Thread.sleep(1000);
		homePage.deleteNote();
		Thread.sleep(1000);
		Assertions.assertTrue(driver.findElement(By.id("success-message")).getText().contains(Message.DELETE_NOTE_SUCCESS.toString()));
	}

	@Test
	public void createCredentialTest() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.setUsernameInput(username);
		loginPage.setPasswordInput(password);
		loginPage.submit();
		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		homePage.openCredentialTab();
		Credential credential = new Credential();
		credential.setUrl("https://www.google.com/");
		credential.setUsername("hieu");
		credential.setPassword("123xyz");
		homePage.createCredential(credential.getUrl(), credential.getUsername(), credential.getPassword());
		homePage.openCredentialTab();
		Thread.sleep(1000);

		Assertions.assertTrue(driver.findElement(By.id("success-message")).getText().contains(Message.CREATE_CREDENTIAL_SUCCESS.toString()));
	}

	@Test
	public void updateCredentialTest() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.setUsernameInput(username);
		loginPage.setPasswordInput(password);
		loginPage.submit();
		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		homePage.openCredentialTab();
		Thread.sleep(1000);
		Credential oldCredential = new Credential();
		oldCredential.setUrl("https://www.google.com/");
		oldCredential.setUsername("hieunv");
		oldCredential.setPassword("123xyz");
		Credential newCredential = new Credential();
		newCredential.setUrl("https://www.oracle.com/");
		newCredential.setUsername("hieunv01x");
		newCredential.setPassword("123456");
		homePage.createCredential(oldCredential.getUrl(), oldCredential.getUsername(), oldCredential.getPassword());
		homePage.logout();
		Thread.sleep(1000);
		driver.get("http://localhost:" + this.port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.setUsernameInput(username);
		loginPage.setPasswordInput(password);
		loginPage.submit();
		Thread.sleep(1000);
		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		Thread.sleep(1000);
		homePage.openCredentialTab();
		Thread.sleep(1000);
		homePage.updateCredential(newCredential.getUrl(), newCredential.getUsername(), newCredential.getPassword());
		homePage.openCredentialTab();
		Thread.sleep(1000);
		Assertions.assertTrue(driver.findElement(By.id("success-message")).getText().contains(Message.UPDATE_CREDENTIAL_SUCCESS.toString()));
	}

	@Test
	public void deleteCredentialTest() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.setUsernameInput(username);
		loginPage.setPasswordInput(password);
		loginPage.submit();
		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		homePage.openCredentialTab();
		Thread.sleep(1000);
		Credential oldCredential = new Credential();
		oldCredential.setUrl("https://www.google.com/");
		oldCredential.setUsername("hieu");
		oldCredential.setPassword("123");
		homePage.createCredential(oldCredential.getUrl(), oldCredential.getUsername(), oldCredential.getPassword());
		homePage.logout();
		Thread.sleep(1000);
		driver.get("http://localhost:" + this.port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.setUsernameInput(username);
		loginPage.setPasswordInput(password);
		loginPage.submit();
		Thread.sleep(1000);
		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		Thread.sleep(1000);
		homePage.openCredentialTab();
		Thread.sleep(1000);
		homePage.deleteCredential();
		Thread.sleep(1000);
		Assertions.assertTrue(driver.findElement(By.id("success-message")).getText().contains(Message.DELETE_CREDENTIAL_SUCCESS.toString()));
	}

	@Test
	public void testFile() throws InterruptedException {
		doMockSignUp("uploadFile", "Test", "test", "123");
		doLogIn("test", "123");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "image.jpg";
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());
		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		Thread.sleep(1000);
		Assertions.assertTrue(driver.findElement(By.id("success-message")).getText().contains(Message.UPLOAD_FILE_SUCCESS.toString()));
		Thread.sleep(1000);
		WebElement deleteButton = driver.findElement(By.className("delete-file"));
		deleteButton.click();
		Thread.sleep(1000);
		Assertions.assertTrue(driver.findElement(By.id("success-message")).getText().contains(Message.DELETE_FILE_SUCCESS.toString()));
	}
}