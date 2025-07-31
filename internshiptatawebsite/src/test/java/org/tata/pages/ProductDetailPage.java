package org.tata.pages;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductDetailPage {

	WebDriver driver;
    WebDriverWait wait;

    public ProductDetailPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }
    // ==== ELEMENTS ====

    @FindBy(xpath = "//h3[contains(text(),'₹')]")
    private WebElement priceElement;

    @FindBy(xpath = "//div[@data-test='button-main-div' and .//span[text()='ADD TO BAG']]")
    private WebElement addToBagButton;

    @FindBy(xpath = "//div[@data-test='button-main-div' and .//span[text()='GO TO BAG']]")
    private WebElement goToBagButton;

    // ==== METHODS ====

    public String verifyPrice() {
        // Ensure correct window
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
            if (driver.getTitle().contains("AirPods")) break;
        }

        new Actions(driver).moveByOffset(0, 0).perform();

        wait.until(ExpectedConditions.visibilityOf(priceElement));
        return priceElement.getText(); // e.g., "₹20915"
    }

    public void clickAddToBag() {
        Actions actions = new Actions(driver);
        actions.moveByOffset(0, 0).perform();

        wait.until(ExpectedConditions.elementToBeClickable(addToBagButton));

        try {
            addToBagButton.click();
            System.out.println("'ADD TO BAG' button clicked successfully.");
        } catch (Exception e) {
            System.out.println("Native click failed, trying JavaScript click.");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToBagButton);
        }
    }

    public void clickGoToBag() {
        Actions actions = new Actions(driver);
        actions.moveByOffset(0, 0).perform();

        wait.until(ExpectedConditions.elementToBeClickable(goToBagButton));

        try {
            goToBagButton.click();
            System.out.println("'GO TO BAG' button clicked successfully.");
        } catch (Exception e) {
            System.out.println("Native click failed, using JavaScript click.");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", goToBagButton);
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

}
