package tests;

import java.time.Duration;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.WebDriverBase;
import pages.CheckoutPage;
import pages.LoginPage;
import pages.ProductsPage;

public class SauceE2ETests extends WebDriverBase {

	LoginPage loginPage;
	ProductsPage productsPage;
	@Test
	public void verifyProductsAddedToCartTest() {
		loginPage = new LoginPage();
		
		loginPage.getUsername().sendKeys(properties.getProperty("username"));
		loginPage.getPassword().sendKeys(properties.getProperty("password"));
		loginPage.getLoginbutton().click();
		WebDriverWait wbWait = new WebDriverWait(driver, Duration.ofSeconds(120));
		
		productsPage = new ProductsPage();
		wbWait.until(ExpectedConditions.visibilityOf(productsPage.getShoppingCartLink()));

		int productsSize = productsPage .getInventoryList().size();

		Random random = new Random();
		int productsRequired = 2;
		Set set = new HashSet<Integer>(productsRequired);
		while (set.size() < productsRequired) {
			
			while (set.add(random.nextInt(productsSize)) != true);
		}

		Integer[] products = (Integer[]) set.toArray(new Integer[set.size()]);

		String firstItemPrice = driver
				.findElement(By.xpath("(//div[@class='inventory_item_price'])[" + products[0] + "]")).getText();
		
		double firstproductprice = Double.parseDouble(firstItemPrice.substring(1));
		
		driver.findElement(By.xpath("(//button[text()='Add to cart'])[" + products[0] + "]")).click();

		String secondItemPrice = driver
				.findElement(By.xpath("(//div[@class='inventory_item_price'])[" + products[1] + "]")).getText();
		double secondproductprice = Double.parseDouble(secondItemPrice.substring(1));
		
		double sumOfTwoProductsPrice = firstproductprice+secondproductprice;
		driver.findElement(By.xpath("(//button[text()='Add to cart'])[" + products[1] + "]")).click();

		productsPage.getShoppingCartLink().click();
		productsPage.getCheckout().click();
		CheckoutPage checkoutPage = new  CheckoutPage();
		checkoutPage.getFirstname().sendKeys("standard");
		checkoutPage.getLastname().sendKeys("user");
		checkoutPage.getPincode().sendKeys("500070");
		checkoutPage.getContinuebutton().click();		
		
		String sub_total = driver.findElement(By.cssSelector(".summary_subtotal_label")).getText();
		String item_total = sub_total.substring(13);
		double item_total_double = Double.parseDouble(item_total);
		Assert.assertEquals(item_total_double, sumOfTwoProductsPrice);
	}
	
	@Test
	public void verifyUserIsLockedOutTest()
	{
		loginPage = new LoginPage();
		
		loginPage.getUsername().sendKeys(properties.getProperty("lockedusername"));
		loginPage.getPassword().sendKeys(properties.getProperty("password"));
		loginPage.getLoginbutton().click();
		
		String ErrorMessage = loginPage.getError().getText();
		Assert.assertEquals(properties.getProperty("expectederrormessage"),ErrorMessage);
	}
}
