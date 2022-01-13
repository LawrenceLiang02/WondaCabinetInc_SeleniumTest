import io.github.bonigarcia.seljup.SeleniumExtension;
import org.apache.commons.io.FileUtils;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;



import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(SeleniumExtension.class)
public class ViewOrderTest {

    ChromeDriver driver;
    private final String SCREENSHOTS = "./src/test/onDemandScreenShots";

    public ViewOrderTest(ChromeDriver driver){
        this.driver = driver;
        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        System.setProperty("sel.jup.screenshot.at.the.end.of.tests", "whenfailure");
        System.setProperty("sel.jup.screenshot.format", "png");
        System.setProperty("sel.jup.output.folder", "./src/test/failureScreenShots");
    }

    public static void createSnapShot(WebDriver webDriver, String fileWithPath) throws Exception{
        TakesScreenshot scrShot = ((TakesScreenshot) webDriver);
        File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
        File destFile = new File(fileWithPath);
        FileUtils.copyFile(srcFile, destFile);
    }

    @Test
    @DisplayName("test_wci_logo")
    void test_wci_logo(TestInfo testInfo) throws Exception{
        driver.get("http://localhost:4200");
        driver.manage().window().maximize();

        WebElement fbLogo = driver.findElement(By.className("wci-logo"));

        String method = testInfo.getDisplayName();
        createSnapShot(driver,SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");

        assertThat(fbLogo.isDisplayed(), is(true));

        try{
            Thread.sleep(5000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        driver.quit();
    }

    @Test
    @DisplayName("test_view_orders")
    void test_view_orders(TestInfo testInfo) throws Exception{
        driver.get("http://localhost:4200");
        driver.manage().window().maximize();

        String method = testInfo.getDisplayName();
        createSnapShot(driver,SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");

        WebElement viewOrdersBtn = driver.findElement(By.xpath("//a[@name='view-orders']"));
        viewOrdersBtn.click();
        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        driver.quit();
    }

    @Test
    @DisplayName("test_view_orders_details")
    void test_view_orders_details(TestInfo testInfo) throws Exception{
        driver.get("http://localhost:4200");
        driver.manage().window().maximize();

        String method = testInfo.getDisplayName();
        createSnapShot(driver,SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");

        WebElement viewOrdersBtn = driver.findElement(By.xpath("//a[@name='view-orders']"));
        viewOrdersBtn.click();

        WebElement viewOrdersDetailsBtn = driver.findElement(By.xpath("//td[@name='view-order-details']"));
        viewOrdersDetailsBtn.click();

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        driver.quit();
    }

    @Test
    @DisplayName("test_add_order")
    void test_add_order(TestInfo testInfo) throws Exception{
        driver.get("http://localhost:4200");
        driver.manage().window().maximize();

        String method = testInfo.getDisplayName();
        createSnapShot(driver,SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");

        WebElement addOrdersBtn = driver.findElement(By.xpath("//a[@name='add-orders']"));
        addOrdersBtn.click();

        WebElement idField = driver.findElement(By.xpath("//input[@name='orderId']"));
        idField.sendKeys("30");

        WebElement tnField = driver.findElement(By.xpath("//input[@name='trackingNo']"));
        tnField.sendKeys("887755");

        WebElement osField = driver.findElement(By.xpath("//input[@name='orderStatus']"));
        osField.sendKeys("Received");

        WebElement dField = driver.findElement(By.xpath("//input[@name='design']"));
        dField.sendKeys("Design");

        WebElement ctField = driver.findElement(By.xpath("//input[@name='cabinetType']"));
        ctField.sendKeys("Kitchen Cabinet");

        WebElement clField = driver.findElement(By.xpath("//input[@name='color']"));
        clField.sendKeys("Black");

        Select mField = new Select(driver.findElement(By.xpath("//select[@name='material']")));
        mField.selectByVisibleText("Oak");

        Select hField = new Select(driver.findElement(By.xpath("//select[@name='handleType']")));
        hField.selectByVisibleText("Square");

        WebElement iField = driver.findElement(By.xpath("//textarea[@name='additional_items']"));
        iField.sendKeys("Black");

        WebElement submitBtn = driver.findElement(By.xpath("//button[@name='submit']"));
        submitBtn.click();

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        driver.quit();


    }

    @Test
    @DisplayName("test_update_order")
    void test_update_order(TestInfo testInfo) throws Exception{
        driver.get("http://localhost:4200");
        driver.manage().window().maximize();

        String method = testInfo.getDisplayName();
        createSnapShot(driver,SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");

        WebElement viewBtn = driver.findElement(By.xpath("//a[@name='view-orders']"));
        viewBtn.click();

        WebElement updateBtn = driver.findElement(By.xpath("//td[@name='update-orders']"));
        updateBtn.click();

        WebElement statusField = driver.findElement(By.xpath("//input[@name='orderStatus']"));
        statusField.clear();
        statusField.sendKeys("In Progress");

        WebElement submitBtn = driver.findElement(By.xpath("//button[@name='submit']"));
        submitBtn.click();

        assertThat(driver.getCurrentUrl(), is("http://localhost:4200/update-orders/1"));


        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        driver.quit();
    }


}
