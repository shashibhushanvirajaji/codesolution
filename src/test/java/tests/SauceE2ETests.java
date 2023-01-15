package tests;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.JavascriptExecutor;
import base.WebDriverBase;
import pages.CheckoutPage;
import pages.LoginPage;
import pages.ProductsPage;

public class SauceE2ETests extends WebDriverBase {

	LoginPage loginPage;
	ProductsPage productsPage;

	@Test
	public void verifyProductsAddedToCartTest() throws IOException {
		loginPage = new LoginPage();
		TakesScreenshot scrShot = ((TakesScreenshot) driver);
		String filePath = System.getProperty("user.dir") + "\\Screenshots";

		loginPage.getUsername().sendKeys(properties.getProperty("username"));
		loginPage.getPassword().sendKeys(properties.getProperty("password"));
		loginPage.getLoginbutton().click();
		WebDriverWait wbWait = new WebDriverWait(driver, Duration.ofSeconds(120));

		productsPage = new ProductsPage();
		wbWait.until(ExpectedConditions.visibilityOf(productsPage.getShoppingCartLink()));

		int productsSize = productsPage.getInventoryList().size();

		Set set = new HashSet<Integer>();
		Random r = new Random();
		int low = 1;
		int high = 6;
		int result;

		for (int index = 1; index <= 6; index++) {
			result = r.nextInt(high - low) + low;
			if (set.add(result) == true && set.size() == 2) {
				break;
			}

		}
		Integer[] products = (Integer[]) set.toArray(new Integer[set.size()]);
		
		String firstItemPrice = driver
				.findElement(By.xpath("(//div[@class='inventory_item_price'])[" + products[0] + "]")).getText();

		double firstproductprice = Double.parseDouble(firstItemPrice.substring(1));
		System.out.println(firstproductprice);

		driver.findElement(By.xpath("(//button[text()='Add to cart'])[" + products[0] + "]")).click();
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
				driver.findElement(By.xpath("(//button[text()='Add to cart'])[" + products[0] + "]")));

		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
		File DestFile = new File(filePath + "\\firstproduct.png");
		FileUtils.copyFile(SrcFile, DestFile);

		String secondItemPrice = driver
				.findElement(By.xpath("(//div[@class='inventory_item_price'])[" + products[1] + "]")).getText();
		double secondproductprice = Double.parseDouble(secondItemPrice.substring(1));
		
		double sumOfTwoProductsPrice = firstproductprice + secondproductprice;
		
		if(products[1]>1)
		{
			int newIndex = products[1]-1;
		driver.findElement(By.xpath("(//button[text()='Add to cart'])[" + newIndex + "]")).click();
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
				driver.findElement(By.xpath("(//button[text()='Add to cart'])[" + newIndex + "]")));

		File SrcFile2 = scrShot.getScreenshotAs(OutputType.FILE);
		File DestFile2 = new File(filePath + "\\secondproduct.png");
		FileUtils.copyFile(SrcFile2, DestFile2);
		}
		productsPage.getShoppingCartLink().click();
		productsPage.getCheckout().click();
		CheckoutPage checkoutPage = new CheckoutPage();
		checkoutPage.getFirstname().sendKeys("standard");
		checkoutPage.getLastname().sendKeys("user");
		checkoutPage.getPincode().sendKeys("500070");
		checkoutPage.getContinuebutton().click();

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
				driver.findElement(By.cssSelector(".summary_subtotal_label")));
		File SrcFile3 = scrShot.getScreenshotAs(OutputType.FILE);
		File DestFile3 = new File(filePath + "\\checkout.png");
		FileUtils.copyFile(SrcFile3, DestFile3);
		String sub_total = driver.findElement(By.cssSelector(".summary_subtotal_label")).getText();
		String item_total = sub_total.substring(13);
		double item_total_double = Double.parseDouble(item_total);
		Assert.assertEquals(item_total_double, sumOfTwoProductsPrice);
	}

	@Test
	public void verifyUserIsLockedOutTest() {
		loginPage = new LoginPage();

		loginPage.getUsername().sendKeys(properties.getProperty("lockedusername"));
		loginPage.getPassword().sendKeys(properties.getProperty("password"));
		loginPage.getLoginbutton().click();

		String ErrorMessage = loginPage.getError().getText();
		Assert.assertEquals(properties.getProperty("expectederrormessage"), ErrorMessage);
	}
}
