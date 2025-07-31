package org.tata.testcases;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.tata.base.BaseClass;
import org.tata.pages.CartPage;
import org.tata.pages.HomePage;
import org.tata.pages.ProductDetailPage;
import org.tata.pages.SearchPage;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestCases extends BaseClass {
	
	HomePage homePageobj;
	SearchPage searchPageobj;
	ProductDetailPage productPage;
	CartPage cartPageobj;

	@BeforeClass
	public void objinit() 
	{
		homePageobj=new HomePage(driver);
		searchPageobj = new SearchPage(driver);
		productPage = new ProductDetailPage(driver);		
		cartPageobj=new CartPage(driver);
	}
	
	@AfterClass
	public void tearDown() 
	{  
	  driver.quit(); 
	}

	@Test(priority=1)
    public void verifyTataCliqHomePageElements_01() 
	{
        // 1. Verify logo
        Assert.assertTrue(homePageobj.isLogoDisplayed(), "Tata CLiQ logo is not displayed!");

        // 2. Verify title
        String title = homePageobj.getHomePageTitle();
        Assert.assertTrue(title.contains("Tata CLiQ"), "Homepage title does not contain 'Tata CLiQ'");
        String act_text=homePageobj.verifyCategoryElement();
		Assert.assertEquals(act_text, Constants.Expected_text);
		homePageobj.clickCategory();
    }

	@Test(priority=2)
	public void verifyGadgetmenu_02() 
	{
		String actual_gadtext=homePageobj.verifyGadgetsButton();
		Assert.assertEquals(actual_gadtext, Constants.Expected_gadtext);
		homePageobj.clickGadgetsButton();;
		Assert.assertTrue(homePageobj.isGadgetsTitleDisplayed(), "Gadgets title is not displayed");
	}
	
	
	@Test(priority=3)
	public void searchAppleProduct_03() 
	{
		searchPageobj.searchProduct("apple airpod pro 2nd");
	    Assert.assertTrue(searchPageobj.isAppleAirpodsProDisplayed(), 
	            "Apple AirPods Pro (2nd Generation) product is not displayed!");
	    System.out.println("Apple AirPods Pro (2nd Generation) is displayed.");
	    searchPageobj.appleAirpodsClick();
	    searchPageobj.switchToNewWindow();
	    String actualTitle = searchPageobj.getNewWindowTitle();
	    Assert.assertTrue(actualTitle.contains("Apple AirPods Pro"),
	        "Page title mismatch: " + actualTitle);

	    System.out.println("Verified page title: " + actualTitle);
	}
	
	
	@Test(priority = 4, dependsOnMethods = {"searchAppleProduct_03"})
	public void verifyProductPrice_04() 
	{	
		
		String actualPrice = productPage.verifyPrice();	    
	    Assert.assertEquals(actualPrice, Constants.expected_Price);
	    System.out.println("Product price verified successfully: " + actualPrice);
	}

	@Test(priority = 5, dependsOnMethods = {"searchAppleProduct_03"})
	public void verifyAddToBag_05() {
		productPage.clickAddToBag();
		productPage.clickGoToBag(); 
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait.until(ExpectedConditions.urlContains("/cart"));

	    String actualUrl = productPage.getCurrentUrl();
	    Assert.assertEquals(actualUrl, Constants.expected_Bag_Url, " Bag page URL mismatch!");

	    System.out.println("Product added to bag and navigated to cart successfully.");
	   
	}
		
	@Test(priority = 6, dependsOnMethods = {"verifyAddToBag_05"})
	public void verifyRemoveFromCart_06() {
		
		cartPageobj.removeItemFromCart();
			
	    Assert.assertTrue(cartPageobj.isCartEmptyMessageDisplayed(),
	        "Product was not removed properly or cart is not empty.");

	    System.out.println("Verified: Cart is empty after removal.");

	}
	@Test(priority = 7)
	public void verifyContinueShoppingRedirectsToHomePage_07() {
		cartPageobj.clickContinueShopping();
        Assert.assertTrue(homePageobj.isHomePageTitleCorrect(), "Home page title is incorrect!");
	}
	
	@Test(priority = 8)
	public void verifyCliqCareNavigation_08() {
		homePageobj.clickCliqCare();
	    Assert.assertTrue(homePageobj.isCliqCareUrlLoaded(), "CLiQ Care page URL does not contain 'cliq-care'");
	    System.out.println("CLiQ Care page is successfully loaded!");
	}
	
	@Test(priority = 9)
	public void verifyTataPayLaterFeedbackFlow_09() {
	    
		homePageobj.clickPaymentsLink();
		homePageobj.clickTataPayLaterQuestion();

	    Assert.assertTrue(homePageobj.isWasThisHelpfulVisible(), "'Was this helpful?' label not displayed");

	    homePageobj.clickYesFeedback();

	    Assert.assertTrue(homePageobj.isThankYouDisplayed(), "'Thank you' header not displayed");
	    Assert.assertTrue(homePageobj.isThankYouMessageDisplayed(), "'Thank you message' not displayed");

	    System.out.println("Tata Pay Later FAQ feedback flow verified successfully.");
	}


}


	

