package seleniumTests;

import dataAccess.client.Client;
import dataAccess.department.Department;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class SeleniumTest {
    protected final String appURL = "http://localhost:8080/";
    protected WebDriver driver;

    @BeforeClass
    public void setUp(){
        final String chromeDriverPath = "/usr/bin/chromedriver";
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1000, 1000));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    void assertClientIsUpdated(Client client, String pageText){
        String expectedMessage = String.format("The client '%s' is updated", client.getClientName());
        Assert.assertTrue(pageText.contains(expectedMessage));
    }

    void assertClientExists(Client client, String pageText){
        Assert.assertTrue(pageText.contains(client.getClientName()));
        Assert.assertTrue(pageText.contains(client.getClientType()));
    }

    @Test()
    public void clientAddEditDeleteTest() {
        Client client = new Client("Alla Pugachova", "natural");
        String newName = "Alla Galkina";

        driver.get(appURL);
        Assert.assertEquals(driver.getTitle(), "Index");

        driver.findElement(By.id("clientsList")).click();
        Assert.assertEquals(driver.getTitle(), "clientsList");

        // add new client
        driver.findElement(By.id("clientAddButton")).click();
        Assert.assertEquals(driver.getTitle(), "Client");
        driver.findElement(By.id("clientName")).sendKeys(client.getClientName());
        driver.findElement(By.id("clientType")).sendKeys(client.getClientType());
        driver.findElement(By.id("submitButton")).click();

        // go to a page with client info
        Assert.assertEquals(driver.getTitle(), "Client");
        // check saved client info
        assertClientIsUpdated(client, driver.findElement(By.tagName("body")).getText());

        // edit client info
        client.setClientName(newName);
        driver.findElement(By.id("clientName")).clear();
        driver.findElement(By.id("clientName")).sendKeys(client.getClientName());
        driver.findElement(By.id("submitButton")).click();
        Assert.assertEquals(driver.getTitle(), "Client");
        assertClientIsUpdated(client, driver.findElement(By.tagName("body")).getText());
        driver.findElement(By.id("clientsList")).click();
        Assert.assertEquals(driver.getTitle(), "clientsList");
        assertClientExists(client, driver.findElement(By.tagName("body")).getText());

        // delete client
        driver.findElement(By.linkText(client.getClientName())).click();
        Assert.assertEquals(driver.getTitle(), "Client");
        driver.findElement(By.id("deleteButton")).click();
        Assert.assertEquals(driver.getTitle(), "clientsList");
    }

    void assertDepartmentIsUpdated(Department department, String pageText){
        String expectedMessage = String.format("The department '%s' is updated", department.getAddress());
        Assert.assertTrue(pageText.contains(expectedMessage));
    }

    void assertDepartmentExists(Department department, String pageText){
        Assert.assertTrue(pageText.contains(department.getAddress()));
    }

    @Test()
    public void departmentAddEditDeleteTest() {
        Department department = new Department("London, Pushkinskaya, 1A");
        String newAddress = "London, Pushkinskaya, 3C 5d";

        driver.get(appURL);
        Assert.assertEquals(driver.getTitle(), "Index");

        driver.findElement(By.id("departmentsList")).click();
        Assert.assertEquals(driver.getTitle(), "departmentsList");

        // add new department
        driver.findElement(By.id("departmentAddButton")).click();
        Assert.assertEquals(driver.getTitle(), "Department");
        driver.findElement(By.id("departmentAddress")).sendKeys(department.getAddress());
        driver.findElement(By.id("submitButton")).click();

        // go to a page with department info
        Assert.assertEquals(driver.getTitle(), "Department");
        // check saved department info
        assertDepartmentIsUpdated(department, driver.findElement(By.tagName("body")).getText());

        // edit department info
        department.setAddress(newAddress);
        driver.findElement(By.id("departmentAddress")).clear();
        driver.findElement(By.id("departmentAddress")).sendKeys(department.getAddress());
        driver.findElement(By.id("submitButton")).click();
        Assert.assertEquals(driver.getTitle(), "Department");
        assertDepartmentIsUpdated(department, driver.findElement(By.tagName("body")).getText());
        driver.findElement(By.id("departmentsList")).click();
        Assert.assertEquals(driver.getTitle(), "departmentsList");
        assertDepartmentExists(department, driver.findElement(By.tagName("body")).getText());

        // delete department
        driver.findElement(By.linkText(department.getAddress())).click();
        Assert.assertEquals(driver.getTitle(), "Department");
        driver.findElement(By.id("deleteButton")).click();
        Assert.assertEquals(driver.getTitle(), "departmentsList");
    }

    void assertAccountIsUpdated(String clientName, String pageText){
        String expectedMessage = String.format("The account of '%s' is updated", clientName);
        Assert.assertTrue(pageText.contains(expectedMessage));
    }

    void assertAccountExists(
            String clientName, String creditType, String departmentAddress, String pageText){
        Assert.assertTrue(pageText.contains(clientName));
        Assert.assertTrue(pageText.contains(creditType));
        Assert.assertTrue(pageText.contains(departmentAddress));
    }

    @Test()
    public void accountAddEditDeleteTest() {
        driver.get(appURL);
        Assert.assertEquals(driver.getTitle(), "Index");

        driver.findElement(By.id("accountsList")).click();
        Assert.assertEquals(driver.getTitle(), "accountsList");

        // add new department
        driver.findElement(By.id("accountAddButton")).click();
        Assert.assertEquals(driver.getTitle(), "Account");

        Select clientDropdown = new Select(driver.findElement(By.id("selectClient")));
        String clientName = clientDropdown.getOptions().get(1).getText();
        clientDropdown.selectByIndex(1);  // select first client

        Select creditTypeDropdown = new Select(driver.findElement(By.id("selectCreditType")));
        String creditType = clientDropdown.getOptions().get(1).getText();
        creditTypeDropdown.selectByIndex(1);

        Select departmentDropdown = new Select(driver.findElement(By.id("selectDepartment")));
        String departmentAddress = clientDropdown.getOptions().get(1).getText();
        departmentDropdown.selectByIndex(1);

        int balance = 69000;
        int newBalance = 60000;
        driver.findElement(By.id("balance")).sendKeys(Integer.toString(balance));

        driver.findElement(By.id("submitButton")).click();
        Assert.assertEquals(driver.getTitle(), "Account");
        assertAccountIsUpdated(clientName, driver.findElement(By.tagName("body")).getText());

        // edit account info
        driver.findElement(By.id("balance")).clear();
        driver.findElement(By.id("balance")).sendKeys(Integer.toString(newBalance));
        driver.findElement(By.id("submitButton")).click();
        Assert.assertEquals(driver.getTitle(), "Account");
        assertAccountIsUpdated(clientName, driver.findElement(By.tagName("body")).getText());
        String lastChange = driver.findElement(By.id("lastChange")).getText();
        Assert.assertTrue(lastChange.contains(Integer.toString(newBalance - balance)));  // check balance changing

        driver.findElement(By.id("accountsList")).click();
        Assert.assertEquals(driver.getTitle(), "accountsList");
        assertAccountExists(clientName, creditType, departmentAddress,
                driver.findElement(By.tagName("body")).getText());

        // add new department and get an error
        driver.findElement(By.id("accountAddButton")).click();
        Assert.assertEquals(driver.getTitle(), "Account");

        clientDropdown = new Select(driver.findElement(By.id("selectClient")));
        clientDropdown.selectByIndex(0);  // select first client
        creditTypeDropdown = new Select(driver.findElement(By.id("selectCreditType")));
        creditTypeDropdown.selectByIndex(0);
        driver.findElement(By.id("balance")).sendKeys(Integer.toString(newBalance));
        Assert.assertEquals(driver.getTitle(), "Account");  // nothing changed because of an empty field
    }

    @Test()
    public void errorTest(){
        // this client does not exist
        driver.get(appURL+"/client?clientID=9999");
        Assert.assertEquals(driver.getTitle(), "errorPage");

        // this department does not exist
        driver.get(appURL+"/department?departmentID=9999");
        Assert.assertEquals(driver.getTitle(), "errorPage");

        // this account does not exist
        driver.get(appURL+"/account?accountID=9999");
        Assert.assertEquals(driver.getTitle(), "errorPage");
    }

    @AfterClass
    public void clear() {
        driver.quit();
    }
}
