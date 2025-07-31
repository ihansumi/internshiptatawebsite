package org.tata.pages;

import java.time.Duration;

import org.openqa.selenium.TimeoutException;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchPage {
	WebDriver driver;
    WebDriverWait wait;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }
    // ==== ELEMENTS ====
    @FindBy(id = "search-text-input")
    private WebElement searchInput;

    @FindBy(id = "moe-dontallow_button")
    private WebElement noThanksButton;

    @FindBy(xpath = "//h2[contains(text(),'Apple AirPods Pro (2nd Generation)')]")
    private WebElement airpodsProduct;

    // ==== METHODS ====

    public void dismissPopupIfPresent() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(noThanksButton)).click();
            System.out.println("Popup dismissed: Clicked 'No, Thanks'");
        } catch (TimeoutException e) {
            System.out.println("No popup appeared.");
        }
    }

    public void searchProduct(String productName) {
        dismissPopupIfPresent();

        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(searchInput));

        try {
            input.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", input);
        }

        input.clear();
        input.sendKeys(productName);
        input.sendKeys(Keys.ENTER);
    }

    public boolean isAppleAirpodsProDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            return wait.until(ExpectedConditions.visibilityOf(airpodsProduct)).isDisplayed();
        } catch (StaleElementReferenceException e) {
            System.out.println("Stale element. Retrying...");
            return isAppleAirpodsProDisplayed();
        } catch (TimeoutException e) {
            System.out.println("Apple AirPods Pro title not found within timeout.");
            return false;
        }
    }

    public void appleAirpodsClick() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.visibilityOf(airpodsProduct));

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", airpodsProduct);
            Thread.sleep(500);

            wait.until(ExpectedConditions.elementToBeClickable(airpodsProduct));

            try {
                airpodsProduct.click();
                System.out.println("Native click on product successful.");
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", airpodsProduct);
                System.out.println("Fallback to JS click.");
            }
        } catch (Exception e) {
            System.out.println("Error in clicking Apple AirPods product: " + e.getMessage());
        }
    }

    public void switchToNewWindow() {
        String originalWindow = driver.getWindowHandle();
        wait.until(driver -> driver.getWindowHandles().size() > 1);

        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                wait.until(d -> !d.getTitle().isEmpty());
                break;
            }
        }
    }

    public String getNewWindowTitle() {
        return driver.getTitle();
    }



}
