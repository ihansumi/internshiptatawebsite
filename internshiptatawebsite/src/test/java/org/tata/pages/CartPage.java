package org.tata.pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {
	 WebDriver driver;
	 WebDriverWait wait;

	    public CartPage(WebDriver driver) {
	        this.driver = driver;
	        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	        PageFactory.initElements(driver, this);
	    }
	    

	    @FindBy(xpath = "//div[@class='CartItemForDesktop__removeLabelForCartPage' and text()='Remove']")
	    private WebElement removeButton;

	    @FindBy(xpath = "//div[contains(text(), 'Your bag is empty!')]")
	    private WebElement emptyCartMessage;


	    @FindBy(xpath = "//div[@class='Button__base' and .//span[text()='Continue Shopping']]")
	    private WebElement continueShoppingButton;

	    @FindBy(xpath = "//div[contains(text(),'Product removed successfully')]")
	    private WebElement removeSuccessToast;


	    public void removeItemFromCart() {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(removeButton)).click();
        System.out.println("Remove button clicked.");
	    }

	    public boolean isCartEmptyMessageDisplayed() {
	    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    	wait.until(ExpectedConditions.visibilityOf(emptyCartMessage));
	    	return emptyCartMessage.isDisplayed();
	    }

	    // Clicks the "Continue Shopping" button
	    public void clickContinueShopping() {
	    wait.until(ExpectedConditions.elementToBeClickable(continueShoppingButton)).click();
        System.out.println("Clicked on Continue Shopping button.");
       }

      public void waitForRemoveToastToDisappear() {
      try {
        wait.until(ExpectedConditions.invisibilityOf(removeSuccessToast));
        System.out.println("Toast message disappeared.");
    } catch (Exception e) {
        System.out.println("Toast did not disappear in time.");
    }
      }
}
