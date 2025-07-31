package org.tata.pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
	
	WebDriver driver;
    WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

 // ==== ELEMENTS ====

    @FindBy(xpath = "//div[@class='DesktopHeader__logoHolder']")
    private WebElement logo;

    @FindBy(xpath = "//div[@class='DesktopHeader__categoryAndBrand' and contains(text(),'Categories')]")
    private WebElement category;

    @FindBy(xpath = "//div[@class='DesktopHeader__categoryDetailsValue' and contains(text(),'Gadgets')]")
    private WebElement gadgetsButton;

    @FindBy(xpath = "//div[@class='Plp__plpHeading' and contains(text(), 'Gadgets')]")
    WebElement gadgetsTitle;
    
 // CLiQ Care link
    @FindBy(xpath = "//div[@role='button' and text()='CLiQ Care']")
    private WebElement cliqCareLink;

    // Payments link (under Help/FAQ section)
    @FindBy(xpath = "//div[text()='Payments']")
    private WebElement paymentsLink;

    // Tata Pay Later FAQ question
    @FindBy(xpath = "//div[text()='What is Tata Pay Later?']")
    private WebElement tataPayLaterQuestion;

    // 'Was this helpful?' label
    @FindBy(xpath = "//div[@class='CustomerIssue__feedBackHeader' and text()='Was this helpful?']")
    private WebElement wasThisHelpfulLabel;

    // Yes feedback button
    @FindBy(xpath = "//span[text()='Yes']/ancestor::div[@role='button']")
    private WebElement yesFeedbackButton;

    // 'Thank You' header
    @FindBy(xpath = "//div[@class='CustomerIssue__feedBackHeader' and text()='Thank you']")
    private WebElement thankYouHeader;

    // Thank you message
    @FindBy(xpath = "//div[@class='CustomerIssue__feedBackContent' and contains(text(),'for your valuable feedback')]")
    private WebElement thankYouMessage;

    // ==== METHODS ====

    public boolean isLogoDisplayed() {
        return logo.isDisplayed();
    }

    public String getHomePageTitle() {
        return driver.getTitle();
    }

    public String verifyCategoryElement() {
        return category.getText();
    }

    public void clickCategory() {
        category.click();
    }

    public String verifyGadgetsButton() {
        return gadgetsButton.getText();
    }

    public void clickGadgetsButton() {
        gadgetsButton.click();
    }

    public boolean isGadgetsTitleDisplayed() {
        return gadgetsTitle.isDisplayed(); 
    }

    public boolean isHomePageTitleCorrect() {
        return driver.getTitle().contains("Tata CLiQ");
    }
    
 // Actions for Test Case 08
    public void clickCliqCare() {
        wait.until(ExpectedConditions.elementToBeClickable(cliqCareLink)).click();
    }

    public boolean isCliqCareUrlLoaded() {
    	return wait.until(ExpectedConditions.urlContains("cliq-care"));
    }

    // Actions for Test Case 09
    public void clickPaymentsLink() {
        wait.until(ExpectedConditions.elementToBeClickable(paymentsLink)).click();
    }

    public void clickTataPayLaterQuestion() {
        wait.until(ExpectedConditions.elementToBeClickable(tataPayLaterQuestion)).click();
    }

    public boolean isWasThisHelpfulVisible() {
        return wait.until(ExpectedConditions.visibilityOf(wasThisHelpfulLabel)).isDisplayed();
    }

    public void clickYesFeedback() {
        wait.until(ExpectedConditions.elementToBeClickable(yesFeedbackButton)).click();
    }

    public boolean isThankYouDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(thankYouHeader)).isDisplayed();
    }

    public boolean isThankYouMessageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(thankYouMessage)).isDisplayed();
    }


}
