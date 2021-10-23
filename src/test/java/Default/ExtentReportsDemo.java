package Default;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentReportsDemo {
	
	
	static ExtentTest test;
	static ExtentReports reports;
	WebDriver driver;
	
	
	@BeforeClass
	
	public void startest()
	{
		reports = new ExtentReports(System.getProperty("user.dir")+"/test-output/Extentreports.html",true);
		test= reports.startTest("ExtentReportsDemo");
		reports.addSystemInfo("Host Name", "sindhu");
	}
	@BeforeMethod
	public void setup() {
		
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Automation\\ChromeDriver\\chromedriver.exe");
		 driver = new ChromeDriver();
		driver.get("https://www.google.com");
		driver.manage().window().maximize();
	}
	

@Test	
public void googletest()

{
	String title=driver.getTitle();
	System.out.println(title);
	Assert.assertEquals(title, "Google");
}
@AfterMethod
public void TearDown(ITestResult result) throws IOException {//Whatever count comes pass or fail it will store in ITestresult
	//driver.quit();
	
	if(result.getStatus()==ITestResult.FAILURE)
	{
	test.log(LogStatus.FAIL,"Test Failed"+result.getName());
	test.log(LogStatus.FAIL, result.getThrowable());
	String failedscreenshot = getscreenshot(driver, result.getName());
	test.log(LogStatus.FAIL,test.addScreenCapture(failedscreenshot));
	}
	else
	{
	test.log(LogStatus.PASS, "Test Passed");
	}
	}
	@AfterClass
	public static void endTest()
	{
	reports.endTest(test);
	reports.flush();
	}
	public String getscreenshot(WebDriver driver,String screenshotname) throws IOException
	{
		String  sfd = new SimpleDateFormat("yyyyMMddhmmss").format(new Date());
		
		TakesScreenshot ts =  (TakesScreenshot)driver;
		
		File src=ts.getScreenshotAs(OutputType.FILE);
		
		String Dest = System.getProperty("user.dir")+"/Failedscreenshots/"+ screenshotname+sfd+".png";
		File dest = new File(Dest);
		FileUtils.copyFile(src, dest);
		return Dest;
		
	}
	
	
}

