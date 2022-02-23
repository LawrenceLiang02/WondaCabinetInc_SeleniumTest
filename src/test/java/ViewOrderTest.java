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
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SeleniumExtension.class)
public class ViewOrderTest {

    ChromeDriver driver;
    private final String SCREENSHOTS = "./src/test/onDemandScreenShots";

    private String baseUrl = "https://wonda-cabinet-inc-front-end.vercel.app";

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
    @DisplayName("test_wci_logo return home")
    void test_wci_logo(TestInfo testInfo) throws Exception{
        driver.get(baseUrl + "/home");
        driver.manage().window().maximize();

        WebElement contactBtn = driver.findElement(By.xpath("//button[@name='contact']"));
        contactBtn.click();

        WebElement fbLogo = driver.findElement(By.className("wci-logo"));
        fbLogo.click();



        String method = testInfo.getDisplayName();
        createSnapShot(driver,SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");

        assertThat(fbLogo.isDisplayed(), is(true));
        assertThat(driver.getCurrentUrl(), is(baseUrl + "/home"));

//        try{
//            Thread.sleep(5000);
//        }
//        catch(InterruptedException e){
//            e.printStackTrace();
//        }
        driver.quit();
    }

    @Test
    @DisplayName("test_login")
    void test_login(TestInfo testInfo) throws Exception{
        driver.get(baseUrl + "/home");
        driver.manage().window().maximize();

        WebElement loginHeaderButton = driver.findElement(By.xpath("//button[@name='logout']"));
        loginHeaderButton.click();

        WebElement usernameField = driver.findElement(By.xpath("//input[@id='username']"));
        usernameField.sendKeys("EmployeeAdmin");

        WebElement passwordField = driver.findElement(By.xpath("//input[@id='password']"));
        passwordField.sendKeys("12345678");

        WebElement loginButton = driver.findElement(By.xpath("//button[@name='submit']"));
        loginButton.click();

        String method = testInfo.getDisplayName();
        createSnapShot(driver,SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");

        try{
            Thread.sleep(100);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        WebElement loginConfirmation = driver.findElement(By.xpath("//div[@name='success']"));
        assertThat(loginConfirmation.isDisplayed(), is(true));

        driver.quit();
    }

    @Test
    @DisplayName("test login bad credentials")
    void test_login_bad_creds(TestInfo testInfo) throws Exception{
        driver.get(baseUrl + "/home");
        driver.manage().window().maximize();

        WebElement loginHeaderButton = driver.findElement(By.xpath("//button[@name='logout']"));
        loginHeaderButton.click();

        WebElement usernameField = driver.findElement(By.xpath("//input[@id='username']"));
        usernameField.sendKeys("EmployeeAdmin");

        WebElement passwordField = driver.findElement(By.xpath("//input[@id='password']"));
        passwordField.sendKeys("1234567");

        WebElement loginButton = driver.findElement(By.xpath("//button[@name='submit']"));
        loginButton.click();

        String method = testInfo.getDisplayName();
        createSnapShot(driver,SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");

        try{
            Thread.sleep(500);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        WebElement loginConfirmation = driver.findElement(By.xpath("//div[@name='failedLogin']"));
        WebElement badCreds = this.driver.findElement(By.xpath("//*[contains(text(), 'Login failed:')]"));

        assertThat(loginConfirmation.isDisplayed(), is(true));

        driver.quit();
    }

    @Test
    @DisplayName("test login require username and password")
    void test_login_require_fields(TestInfo testInfo) throws Exception{
        driver.get(baseUrl + "/home");
        driver.manage().window().maximize();

        WebElement loginHeaderButton = driver.findElement(By.xpath("//button[@name='logout']"));
        loginHeaderButton.click();

//        WebElement usernameField = driver.findElement(By.xpath("//input[@id='username']"));
//        usernameField.sendKeys("EmployeeAdmin");
//
//        WebElement passwordField = driver.findElement(By.xpath("//input[@id='password']"));
//        passwordField.sendKeys("1234567");

        WebElement loginButton = driver.findElement(By.xpath("//button[@name='submit']"));
        loginButton.click();

        String method = testInfo.getDisplayName();
        createSnapShot(driver,SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");

        try{
            Thread.sleep(500);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        WebElement usernameReq = this.driver.findElement(By.xpath("//*[contains(text(), 'Username is required!')]"));
        WebElement pwdRq = this.driver.findElement(By.xpath("//*[contains(text(), 'Password is required')]"));
        assertThat(usernameReq.isDisplayed(), is(true));
        assertThat(pwdRq.isDisplayed(), is(true));

        driver.quit();
    }



    @Test
    @DisplayName("test_view_orders")
    void test_view_orders(TestInfo testInfo) throws Exception{
        login();

        String method = testInfo.getDisplayName();
        createSnapShot(driver,SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");

        WebElement viewOrdersBtn = driver.findElement(By.xpath("//button[@name='view-orders']"));
        viewOrdersBtn.click();
        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        assertThat(driver.getCurrentUrl(), is(baseUrl + "/view-orders"));

        driver.quit();
    }

    @Test
    @DisplayName("test_view_orders_details")
    void test_view_orders_details(TestInfo testInfo) throws Exception{
        login();

        WebElement viewOrdersBtn = driver.findElement(By.xpath("//button[@name='view-orders']"));
        viewOrdersBtn.click();

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        WebElement viewOrdersDetailBtn = driver.findElement(By.xpath("//button[@name='details']"));
        viewOrdersDetailBtn.click();

        String method = testInfo.getDisplayName();
        createSnapShot(driver,SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");
        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        WebElement trackingNo = this.driver.findElement(By.xpath("//*[contains(text(), 'Tracking Number:')]"));
        WebElement status = this.driver.findElement(By.xpath("//*[contains(text(), 'Status:')]"));

        assertThat(trackingNo.isDisplayed(), is(true));
        assertThat(status.isDisplayed(), is(true));

        driver.quit();
    }


    @Test
    @DisplayName("test_add_order")
    void test_add_order(TestInfo testInfo) throws Exception{
        login();

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        WebElement orderNowBtn = driver.findElement(By.xpath("//button[@name='add-orders']"));
        orderNowBtn.click();

        WebElement cabinetType = driver.findElement(By.xpath("//input[@name='cabinetType']"));
        cabinetType.sendKeys("seleniumCabinet");

        WebElement clField = driver.findElement(By.xpath("//input[@name='color']"));
        clField.sendKeys("Black");

        WebElement emailField = driver.findElement(By.xpath("//input[@name='email']"));
        emailField.clear();
        emailField.sendKeys("noreply.wondacabinetinc@gmail.com");

        Select mField = new Select(driver.findElement(By.xpath("//select[@name='material']")));
        mField.selectByVisibleText("Oak");

        Select hField = new Select(driver.findElement(By.xpath("//select[@name='handleType']")));
        hField.selectByVisibleText("Square");

        WebElement addressField = driver.findElement(By.xpath("//input[@name='address']"));
        addressField.sendKeys("123 Address St");

        WebElement cityField = driver.findElement(By.xpath("//input[@name='city']"));
        cityField.sendKeys("Brossard");

        WebElement submitBtn = driver.findElement(By.xpath("//button[@name='submit']"));
        submitBtn.click();

        try{
            Thread.sleep(5000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        String method = testInfo.getDisplayName();
        createSnapShot(driver,SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");

        WebElement referenceNameIsSelenium = driver.findElement(By.xpath("//*[contains(text(), 'seleniumCabinet')]"));

        assertEquals(referenceNameIsSelenium.isDisplayed(), true );
        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        driver.quit();


    }

    @Test
    @DisplayName("test track order with tracking number")
    void test_track_order(TestInfo testInfo) throws Exception{
        login();

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        WebElement viewOrdersBtn = driver.findElement(By.xpath("//button[@name='view-orders']"));
        viewOrdersBtn.click();

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }


        WebElement trackingNumber = driver.findElement(By.xpath("//td[@name='trackingNo']"));
        String trackingNo = trackingNumber.getText();

        WebElement trackOrderBtn = driver.findElement(By.xpath("//button[@name='view-order-with-tracking-no']"));
        trackOrderBtn.click();

        WebElement inputTrackingNo = driver.findElement(By.xpath("//input[@name='trackingNo-input']"));
        inputTrackingNo.sendKeys(trackingNo);

        WebElement trackButton = driver.findElement(By.xpath("//button[@name='submit-track']"));
        trackButton.click();

        String method = testInfo.getDisplayName();
        createSnapShot(driver,SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        WebElement successMessage = driver.findElement(By.xpath("//div[@name='orderFound']"));

        assertEquals(successMessage.isDisplayed(), true);

        driver.quit();

    }

    @Test
    @DisplayName("test track order wrong tracking number")
    void test_track_order_wrong_number(TestInfo testInfo) throws Exception{

        driver.get(baseUrl + "/home");
        driver.manage().window().maximize();

        WebElement trackOrderBtn = driver.findElement(By.xpath("//button[@name='view-order-with-tracking-no']"));
        trackOrderBtn.click();

        WebElement inputTrackingNo = driver.findElement(By.xpath("//input[@name='trackingNo-input']"));
        inputTrackingNo.sendKeys("123");

        WebElement trackButton = driver.findElement(By.xpath("//button[@name='submit-track']"));
        trackButton.click();

        String method = testInfo.getDisplayName();
        createSnapShot(driver,SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        WebElement failMessage = driver.findElement(By.xpath("//div[@name='errorFound']"));

        assertEquals(failMessage.isDisplayed(), true);

        driver.quit();
    }

    @Test
    @DisplayName("test track order clear input")
    void test_track_order_clear_input(TestInfo testInfo) throws Exception{

        driver.get(baseUrl + "/home");
        driver.manage().window().maximize();

        WebElement trackOrderBtn = driver.findElement(By.xpath("//button[@name='view-order-with-tracking-no']"));
        trackOrderBtn.click();

        WebElement inputTrackingNo = driver.findElement(By.xpath("//input[@name='trackingNo-input']"));
        inputTrackingNo.sendKeys("123");

        WebElement clearButton = driver.findElement(By.xpath("//button[@name='clear']"));
        clearButton.click();


        String method = testInfo.getDisplayName();
        createSnapShot(driver,SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        String text = inputTrackingNo.getText();

        assertEquals(text, "");

        driver.quit();
    }

    @Test
    @DisplayName("test update order status to cancelled")
    void test_update_order(TestInfo testInfo) throws Exception{
        login();

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        WebElement viewOrdersBtn = driver.findElement(By.xpath("//button[@name='view-orders']"));
        viewOrdersBtn.click();

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        WebElement referenceNameIsSelenium = driver.findElement(By.xpath("//*[contains(text(), 'seleniumCabinet')]"));
        WebElement updateBtn = referenceNameIsSelenium.findElement(By.xpath("//button[@name='update']"));
        updateBtn.click();

        Select statusField = new Select(driver.findElement(By.xpath("//select[@name='orderStatus']")));
        statusField.selectByVisibleText("Cancelled");

        WebElement submitBtn = driver.findElement(By.xpath("//button[@name='submit']"));
        submitBtn.click();

        try{
            Thread.sleep(2000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        WebElement cancelledTab = driver.findElement(By.xpath("//*[contains(text(), 'Cancelled Orders')]"));
        cancelledTab.click();

        try{
            Thread.sleep(2000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        String method = testInfo.getDisplayName();
        createSnapShot(driver,SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");

        WebElement referenceNameIsSelenium2 = driver.findElement(By.xpath("//*[contains(text(), 'seleniumCabinet')]"));

        assertEquals(referenceNameIsSelenium2.isDisplayed(), true );

        driver.quit();
    }

    @Test
    @DisplayName("test delete order with wrong password")
    void test_delete_order_wrong_password(TestInfo testInfo) throws Exception{
        login();

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        WebElement viewOrdersBtn = driver.findElement(By.xpath("//button[@name='view-orders']"));
        viewOrdersBtn.click();

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        WebElement allOrdersTab = driver.findElement(By.xpath("//*[contains(text(), 'All Orders')]"));
        allOrdersTab.click();

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        WebElement referenceNameIsSelenium = driver.findElement(By.xpath("//*[contains(text(), 'seleniumCabinet')]"));
        WebElement deleteBtn = referenceNameIsSelenium.findElement(By.xpath("//button[@name='delete']"));
        deleteBtn.click();

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        WebElement deleteButtonDialog1 = driver.findElement(By.xpath("//button[@id='delete-prompt-1']"));
        deleteButtonDialog1.click();

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        WebElement password = driver.findElement(By.xpath("//input[@name='password']"));
        password.sendKeys("123456");

        WebElement deleteButtonDialog2 = driver.findElement(By.xpath("//button[@name='delete-prompt-2']"));
        deleteButtonDialog2.click();

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        String method = testInfo.getDisplayName();
        createSnapShot(driver,SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");

        WebElement badCreds = this.driver.findElement(By.xpath("//*[contains(text(), 'Login failed:')]"));

        assertEquals(badCreds.isDisplayed(), true);

        driver.quit();

    }


    @Test
    @DisplayName("test delete order cancel button")
    void test_delete_order_cancel(TestInfo testInfo) throws Exception{
        login();

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        WebElement viewOrdersBtn = driver.findElement(By.xpath("//button[@name='view-orders']"));
        viewOrdersBtn.click();

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        WebElement allOrdersTab = driver.findElement(By.xpath("//*[contains(text(), 'All Orders')]"));
        allOrdersTab.click();

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        WebElement referenceNameIsSelenium = driver.findElement(By.xpath("//*[contains(text(), 'seleniumCabinet')]"));
        WebElement deleteBtn = referenceNameIsSelenium.findElement(By.xpath("//button[@name='delete']"));
        deleteBtn.click();

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        WebElement deleteButtonDialog1 = driver.findElement(By.xpath("//button[@id='cancel-prompt-1']"));
        deleteButtonDialog1.click();

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        WebElement referenceNameIsSelenium2 = driver.findElement(By.xpath("//*[contains(text(), 'seleniumCabinet')]"));

        assertEquals(referenceNameIsSelenium2.isDisplayed(), true );

        String method = testInfo.getDisplayName();
        createSnapShot(driver,SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");

        driver.quit();
    }

    @Test
    @DisplayName("test delete order")
    void test_delete_order(TestInfo testInfo) throws Exception{
        login();

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        WebElement viewOrdersBtn = driver.findElement(By.xpath("//button[@name='view-orders']"));
        viewOrdersBtn.click();

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        WebElement allOrdersTab = driver.findElement(By.xpath("//*[contains(text(), 'All Orders')]"));
        allOrdersTab.click();

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        WebElement referenceNameIsSelenium = driver.findElement(By.xpath("//*[contains(text(), 'seleniumCabinet')]"));
        WebElement deleteBtn = referenceNameIsSelenium.findElement(By.xpath("//button[@name='delete']"));
        deleteBtn.click();

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        WebElement deleteButtonDialog1 = driver.findElement(By.xpath("//button[@id='delete-prompt-1']"));
        deleteButtonDialog1.click();

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        WebElement password = driver.findElement(By.xpath("//input[@name='password']"));
        password.sendKeys("12345678");

        WebElement deleteButtonDialog2 = driver.findElement(By.xpath("//button[@name='delete-prompt-2']"));
        deleteButtonDialog2.click();

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        String method = testInfo.getDisplayName();
        createSnapShot(driver,SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");

        assertThat(driver.getCurrentUrl(), is(baseUrl + "/view-orders"));

        driver.quit();
    }

    @Test
    @DisplayName("logout")
    void logout(TestInfo testInfo) throws Exception{
        login();

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        WebElement logoutBtn = driver.findElement(By.xpath("//button[@name='logout']"));
        logoutBtn.click();

        String method = testInfo.getDisplayName();
        createSnapShot(driver,SCREENSHOTS + "\\" + method + "_" + System.currentTimeMillis() + ".png");

        assertThat(driver.getCurrentUrl(), is(baseUrl + "/login"));

        driver.quit();
    }

    void login(){
        driver.get(baseUrl + "/home");
        driver.manage().window().maximize();

        WebElement loginHeaderButton = driver.findElement(By.xpath("//button[@name='logout']"));
        loginHeaderButton.click();

        WebElement usernameField = driver.findElement(By.xpath("//input[@id='username']"));
        usernameField.sendKeys("EmployeeAdmin");

        WebElement passwordField = driver.findElement(By.xpath("//input[@id='password']"));
        passwordField.sendKeys("12345678");

        WebElement loginButton = driver.findElement(By.xpath("//button[@name='submit']"));
        loginButton.click();

        try{
            Thread.sleep(2000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
