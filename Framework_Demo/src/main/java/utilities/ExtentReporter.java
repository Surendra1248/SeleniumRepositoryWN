package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporter {
	static ExtentReports extentreport;
	
	public static ExtentReports getExtentReport() {
		
		String extentReportpath=System.getProperty("user.dir")+"\\reports\\extentReport.html";
		
		ExtentSparkReporter reporter = new ExtentSparkReporter(extentReportpath);
		reporter.config().setReportName("Login report");
		reporter.config().setDocumentTitle("Framework results");
		
		extentreport = new ExtentReports();
		extentreport.attachReporter(reporter);
		extentreport.setSystemInfo("Operating system", "Windows11");
		extentreport.setSystemInfo("Tested by", "Surendra");
		
		return extentreport;
	}

}
