package tests;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageObjects.LandingPage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import resources.Base;

public class LoginTest extends Base {
	Logger log;
	public WebDriver driver;
	@BeforeMethod
	public void openApplication() throws IOException {
		log=LogManager.getLogger(LoginTest.class.getName());
		driver = initializeDriver();
		log.debug("Browser got launched");
		driver.get(prop.getProperty("url"));
		log.debug("Navigated to application Url");
	}

	@Test(dataProvider = "getLogindata")
	public void login(String email,String password,String expected) throws IOException {

		

		LandingPage lp = new LandingPage(driver);
		lp.myAccount().click();
		log.debug("Clicked on MyAccountDropDown");
		lp.loginOpt().click();
		log.debug("Clicked on login Option");
		LoginPage logPage = new LoginPage(driver);
		logPage.emailfield().sendKeys(email);
		log.debug("Entered email address");
		logPage.passwordfield().sendKeys(password);
		log.debug("Entered password");
		logPage.loginBtn().click();
		log.debug("Clicked on login button");
		MyAccountPage mp = new MyAccountPage(driver);
		
		String actual=null;
		
		try {
			if(mp.editAccInfo().isDisplayed()) {
				log.debug("User got logged in");
				actual="Success";
			}
		}
			catch (Exception e) {
				log.debug("User didn't login");
				actual="failure";
			}
		Assert.assertEquals(actual, expected);
		log.info("Login test got passed");
	}

	@AfterMethod
	public void tearDown() {
		driver.close();
		log.debug("Browser got closed");
	}
	@DataProvider
	public Object[][] getLogindata() {
		Object[][] data= {{"phaniatw@gmail.com","atw@123","Success"},
				          {"sample@test.com","12345","failure"}};
		
		return data;
	}

}
