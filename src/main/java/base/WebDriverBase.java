package base;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverBase {

	public static WebDriver driver;	
	FileReader reader;
	public Properties properties;
	@BeforeMethod
	public void setup() throws IOException
	{
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(120));
		//driver.manage().window().maximize();
		reader=new FileReader("config.properties");  	     
		properties=new Properties();  
		properties.load(reader);  
		driver.get(properties.getProperty("url"));
	}
	
	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}
}
