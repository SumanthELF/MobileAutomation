package generic_Library;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class BaseClass {

	public AppiumDriver<MobileElement> driver;
	public WebDriverWait explicitWait; 

	//Expected Data
	public String expectedWelcomePageTitle = "ShoppersStack";
	public String expectedLoginPageTitle = "ShoppersStack | Login";
	public String expectedHomePageTitle = "ShoppersStack | Home";
	public String expectedProfilePageTitle = "ShoppersStack | Profile";
	public String expectedAddressPageTitle = "ShoppersStack | My Address";
	public String expectedCartPageTitle = "ShoppersStack | Cart";
	public String expectedWishListPageTitle = "ShoppersStack | Wishlist";
	public String expectedMyLikesPageTitle = "ShoppersStack | My Likes";
	
	public String expectedCartPageUrl ="https://www.shoppersstack.com/cart";
	public String expectedAddressPagePageUrl = "https://www.shoppersstack.com/selectaddress";
	public String expectedPaymentOptionsPagePageUrl = "https://www.shoppersstack.com/payment-options";
	public String expectedOrderConfirmationPageUrl =  "https://www.shoppersstack.com/place-order";
	
	@BeforeTest
	public void setup() throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();		
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 180);
		capabilities.setCapability("adbExecTimeout",60000);
		capabilities.setCapability("autoGrantPermissions",true);
		capabilities.setCapability("fullReset",false);
		capabilities.setCapability("noReset",true);
		
		//Device details
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Redmi Note 10");
		capabilities.setCapability(MobileCapabilityType.UDID, "9ad562a8");
		
		//Platform details
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "ANDROID");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "12");
	
		//App Details (Browser details)
		capabilities.setCapability("appPackage", "com.android.chrome");
		capabilities.setCapability("appActivity", "com.google.android.apps.chrome.Main");
		
		URL url = new URL("http://127.0.0.1:4723/wd/hub");
		driver = new AndroidDriver<MobileElement>(url, capabilities);
	}
	
	@BeforeClass
	public void navigateToApp() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(25,TimeUnit.SECONDS);
		explicitWait = new WebDriverWait(driver,20);
		driver.get("https://www.shoppersstack.com/user-signin");
	}
	
	@BeforeMethod
	public void LoginToApplication() {
		//Enter valid Email id, valid password and click on 'Login' button
		driver.findElement(By.id("Email")).sendKeys("neerajapasala21@gmail.com");
		driver.findElement(By.id("Password")).sendKeys("nrSBTYV8g@S@Eau");
		driver.findElement(By.xpath("//span[text()='Login']")).click();
	}

	@AfterMethod
	public void LogoutOfApplication() {
		//Logout of the application
		for(;;) {
			try {
				driver.findElement(By.xpath("//button[@aria-label='Account settings']")).click();
				break;
			}catch(ElementClickInterceptedException e) {
				
			}
		}		
		driver.findElement(By.xpath("//li[text()='Logout']")).click();
		explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.id("loginBtn")));
		String actualWelcomePageTitle = driver.getTitle();
		Assert.assertEquals(actualWelcomePageTitle, expectedWelcomePageTitle, "Welcome Page is not displayed");
		Reporter.log("User Logout was Sucessfull and Welcome Page is displayed", true);
	}

	@AfterClass
	public void browserTearDown() {
		//Closing the browser
		driver.quit();
		Reporter.log("Browser window Closed Sucessfully", true);
	}

}
