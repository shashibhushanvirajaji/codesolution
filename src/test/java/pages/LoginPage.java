package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import base.WebDriverBase;

public class LoginPage extends WebDriverBase{
	
	WebElement username, password, loginbutton, error;

	public WebElement getError() {
		return driver.findElement(By.xpath("//h3[@data-test='error']"));
	}

	public WebElement getUsername() {
		return driver.findElement(By.cssSelector("#user-name"));
	}

	public WebElement getPassword() {
		return driver.findElement(By.cssSelector("#password"));
	}

	public WebElement getLoginbutton() {
		return driver.findElement(By.cssSelector("#login-button"));
	}
	
	

	
}
