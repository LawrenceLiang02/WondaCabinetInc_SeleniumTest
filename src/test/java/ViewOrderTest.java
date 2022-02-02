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
import static org.hamcrest.core.IsNot.not;

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

    static String getAlphaNumericString(int n)
    {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
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

        Select statusField = new Select(driver.findElement(By.xpath("//select[@name='orderStatus']")));
        statusField.selectByVisibleText("In Progress");

        WebElement submitBtn = driver.findElement(By.xpath("//button[@name='submit']"));
        submitBtn.click();

        assertThat(driver.getCurrentUrl(), is("http://localhost:4200/update-orders/2"));


        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        driver.quit();
    }


    @Test
    @DisplayName("test_display_cancelled")
    void test_display_cancelled(TestInfo testInfo) throws Exception{
        driver.get("http://localhost:4200");
        driver.manage().window().maximize();

        String method = testInfo.getDisplayName();
        createSnapShot(driver,SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");

        WebElement viewOrdersBtn = driver.findElement(By.xpath("//a[@name='view-orders']"));
        viewOrdersBtn.click();

        WebElement cancelledTab = driver.findElementById("mat-tab-label-0-1");
        cancelledTab.click();
        try{
            Thread.sleep(5000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        WebElement orderStatus = driver.findElement(By.xpath("//td[@name='orderStatus']"));

        assertThat(orderStatus.getText(), is("Cancelled"));


        driver.quit();
    }

    @Test
    @DisplayName("test_active_cancelled")
    void test_display_active(TestInfo testInfo) throws Exception{
        driver.get("http://localhost:4200");
        driver.manage().window().maximize();

        String method = testInfo.getDisplayName();
        createSnapShot(driver,SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");

        WebElement viewOrdersBtn = driver.findElement(By.xpath("//a[@name='view-orders']"));
        viewOrdersBtn.click();

        WebElement cancelledTab = driver.findElementById("mat-tab-label-0-0");
        cancelledTab.click();
        try{
            Thread.sleep(5000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        WebElement orderStatus = driver.findElement(By.xpath("//td[@name='orderStatus']"));

        assertThat(orderStatus.getText(), not("Cancelled"));


        driver.quit();
    }

    @Test
    @DisplayName("test login")
    void test_login(TestInfo testInfo) throws Exception{
        driver.get("http://localhost:4200/login");
        driver.manage().window().maximize();

        String method = testInfo.getDisplayName();
        createSnapShot(driver,SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");

        WebElement usernameFill = driver.findElement(By.xpath("//input[@name='username']"));
        usernameFill.sendKeys("employeeadmin");

        WebElement passwordFill = driver.findElement(By.xpath("//input[@name='password']"));
        passwordFill.sendKeys("12345678");

        WebElement submit = driver.findElement(By.xpath("//button[@name='submit']"));
        submit.click();

        try{
            Thread.sleep(5000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

    }

    @Test
    @DisplayName("test login no username")
    void test_login_no_username(TestInfo testInfo) throws Exception{
        driver.get("http://localhost:4200/login");
        driver.manage().window().maximize();

        String method = testInfo.getDisplayName();
        createSnapShot(driver,SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");

        WebElement usernameFill = driver.findElement(By.xpath("//input[@name='username']"));
        usernameFill.sendKeys("");

        WebElement passwordFill = driver.findElement(By.xpath("//input[@name='password']"));
        passwordFill.sendKeys("12345678");

        WebElement submit = driver.findElement(By.xpath("//button[@name='submit']"));
        submit.click();

        try{
            Thread.sleep(5000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

    }

    @Test
    @DisplayName("test login no password")
    void test_login_no_password(TestInfo testInfo) throws Exception{
        driver.get("http://localhost:4200/login");
        driver.manage().window().maximize();

        String method = testInfo.getDisplayName();
        createSnapShot(driver,SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");

        WebElement usernameFill = driver.findElement(By.xpath("//input[@name='username']"));
        usernameFill.sendKeys("employeeadmin");

        WebElement passwordFill = driver.findElement(By.xpath("//input[@name='password']"));
        passwordFill.sendKeys("");

        WebElement submit = driver.findElement(By.xpath("//button[@name='submit']"));
        submit.click();

        try{
            Thread.sleep(5000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

    }

    @Test
    @DisplayName("test login bad user")
    void test_login_bad_user(TestInfo testInfo) throws Exception{
        driver.get("http://localhost:4200/login");
        driver.manage().window().maximize();

        String method = testInfo.getDisplayName();
        createSnapShot(driver,SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");

        WebElement usernameFill = driver.findElement(By.xpath("//input[@name='username']"));
        usernameFill.sendKeys("baduser");

        WebElement passwordFill = driver.findElement(By.xpath("//input[@name='password']"));
        passwordFill.sendKeys("badpass");

        WebElement submit = driver.findElement(By.xpath("//button[@name='submit']"));
        submit.click();

        try{
            Thread.sleep(5000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

    }

    @Test
    @DisplayName("test sign up")
    void test_sign_up(TestInfo testInfo) throws Exception{
        driver.get("http://localhost:4200/signup");
        driver.manage().window().maximize();

        String method = testInfo.getDisplayName();
        createSnapShot(driver,SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");

        WebElement usernameFill = driver.findElement(By.xpath("//input[@name='username']"));
        usernameFill.sendKeys(getAlphaNumericString(6));

        WebElement emailFill = driver.findElement(By.xpath("//input[@name='email']"));
        emailFill.sendKeys(getAlphaNumericString(5) + "@gmail.com");

        WebElement passwordFill = driver.findElement(By.xpath("//input[@name='password']"));
        passwordFill.sendKeys(getAlphaNumericString(6));

        WebElement submit = driver.findElement(By.xpath("//button[@name='submit']"));
        submit.click();

        try{
            Thread.sleep(5000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

    }

    @Test
    @DisplayName("test sign up empty fields")
    void test_sign_up_empty_fields(TestInfo testInfo) throws Exception{
        driver.get("http://localhost:4200/signup");
        driver.manage().window().maximize();

        String method = testInfo.getDisplayName();
        createSnapShot(driver,SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");

        WebElement usernameFill = driver.findElement(By.xpath("//input[@name='username']"));
        usernameFill.sendKeys("");

        WebElement emailFill = driver.findElement(By.xpath("//input[@name='email']"));
        emailFill.sendKeys("");

        WebElement passwordFill = driver.findElement(By.xpath("//input[@name='password']"));
        passwordFill.sendKeys("");

        WebElement submit = driver.findElement(By.xpath("//button[@name='submit']"));
        submit.click();

        try{
            Thread.sleep(5000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

    }

    @Test
    @DisplayName("test signup bad fields")
    void test_sign_up_bad_fields(TestInfo testInfo) throws Exception{
        driver.get("http://localhost:4200/signup");
        driver.manage().window().maximize();

        String method = testInfo.getDisplayName();
        createSnapShot(driver,SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");

        WebElement usernameFill = driver.findElement(By.xpath("//input[@name='username']"));
        usernameFill.sendKeys("a");

        WebElement emailFill = driver.findElement(By.xpath("//input[@name='email']"));
        emailFill.sendKeys( "notemail");

        WebElement passwordFill = driver.findElement(By.xpath("//input[@name='password']"));
        passwordFill.sendKeys("bad");

        WebElement submit = driver.findElement(By.xpath("//button[@name='submit']"));
        submit.click();

        try{
            Thread.sleep(5000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

    }

    @Test
    @DisplayName("test signup taken username")
    void test_sign_up_taken_username(TestInfo testInfo) throws Exception{
        driver.get("http://localhost:4200/signup");
        driver.manage().window().maximize();

        String method = testInfo.getDisplayName();
        createSnapShot(driver,SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");

        WebElement usernameFill = driver.findElement(By.xpath("//input[@name='username']"));
        usernameFill.sendKeys("employeeadmin");

        WebElement emailFill = driver.findElement(By.xpath("//input[@name='email']"));
        emailFill.sendKeys(getAlphaNumericString(5) + "@gmail.com");

        WebElement passwordFill = driver.findElement(By.xpath("//input[@name='password']"));
        passwordFill.sendKeys(getAlphaNumericString(6));

        WebElement submit = driver.findElement(By.xpath("//button[@name='submit']"));
        submit.click();

        try{
            Thread.sleep(5000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

    }

    @Test
    @DisplayName("test signup taken password")
    void test_sign_up_taken_email(TestInfo testInfo) throws Exception{
        driver.get("http://localhost:4200/signup");
        driver.manage().window().maximize();

        String method = testInfo.getDisplayName();
        createSnapShot(driver,SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");

        WebElement usernameFill = driver.findElement(By.xpath("//input[@name='username']"));
        usernameFill.sendKeys(getAlphaNumericString(6));

        WebElement emailFill = driver.findElement(By.xpath("//input[@name='email']"));
        emailFill.sendKeys("vpopa18@gmail.com");

        WebElement passwordFill = driver.findElement(By.xpath("//input[@name='password']"));
        passwordFill.sendKeys(getAlphaNumericString(6));

        WebElement submit = driver.findElement(By.xpath("//button[@name='submit']"));
        submit.click();

        try{
            Thread.sleep(5000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

    }

}
