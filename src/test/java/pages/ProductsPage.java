package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import base.WebDriverBase;

public class ProductsPage extends WebDriverBase {

	WebElement shoppingCartLink, inventoryList, checkout;

	public WebElement getCheckout() {
		return driver.findElement(By.cssSelector("#checkout"));
	}

	public WebElement getShoppingCartLink() {
		return driver.findElement(By.cssSelector(".shopping_cart_link"));
	}

	public List<WebElement> getInventoryList() {
		return driver.findElements(By.cssSelector(".inventory_list .inventory_item"));
	}

}
