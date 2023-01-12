package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import base.WebDriverBase;

public class CheckoutPage extends WebDriverBase {
	
	WebElement firstname, lastname, pincode, continuebutton;

	public WebElement getFirstname() {
		return driver.findElement(By.cssSelector("#first-name"));
	}

	public WebElement getLastname() {
		return driver.findElement(By.cssSelector("#last-name"));
	}

	public WebElement getPincode() {
		return driver.findElement(By.cssSelector("#postal-code"));
	}

	public WebElement getContinuebutton() {
		return driver.findElement(By.cssSelector("#continue"));
	}

}
