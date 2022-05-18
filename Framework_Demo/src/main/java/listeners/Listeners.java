package listeners;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import resources.Base;
import utilities.ExtentReporter;

public class Listeners extends Base implements ITestListener{

	WebDriver driver = null;
	ExtentReports extentReport = ExtentReporter.getExtentReport();
	ExtentTest extentTest;
	
	ThreadLocal<ExtentTest> etestThread= new ThreadLocal<ExtentTest>();
	@Override
	public void onTestStart(ITestResult result) {
		extentTest = extentReport.createTest(result.getName()+" execution started");
		etestThread.set(extentTest);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		etestThread.get().log(Status.PASS,result.getName()+" got passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		String testMethodName = result.getName();
		etestThread.get().fail(result.getThrowable());
		try {
			driver=(WebDriver)result.getTestClass().getRealClass().getDeclaredField("driver").get(result.getInstance());
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
		String scrnshot = takeScreenShot(testMethodName, driver);
		etestThread.get().addScreenCaptureFromPath(scrnshot, testMethodName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onFinish(ITestContext context) {
		extentReport.flush();
	}

}
