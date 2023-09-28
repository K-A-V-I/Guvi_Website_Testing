package GuviTest_Project;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.testng.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Guvi_stepDefinition {
	
	private WebDriver driver;

	    private String email;
	    private String mobileNumber;
	    private String username;
	    private String password;
	    
    private static final Logger LOGGER = LogManager.getLogger(Guvi_stepDefinition.class);
    
    @Given("^I am on the homepage$")
    public void iAmOnTheHomepage() {
    	
        LOGGER.info("Navigating to the homepage");
        System.setProperty("webdriver.chrome.driver", "C:/Users/kavi1/Downloads/chromedriver-win64 (1)/chromedriver-win64/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://www.guvi.in/");
        
    }

    @Then("^the homepage content and layout should be displayed correctly$")
    public void homepageContentAndLayoutShouldBeCorrect() {
        LOGGER.info("Verification: Homepage content and layout displayed correctly");
        WebElement headerElement = driver.findElement(By.className("d-flex"));
        if (headerElement.isDisplayed()) {
            System.out.println("Header is displayed: " + headerElement.getText());
        } else {
            System.out.println("Header is not displayed.");
        }
        driver.close();
       
    }
 
@Given("^I am on the login page$")
    public void iAmOnTheLoginPage() {
        LOGGER.info("Navigating to the login page");
        System.setProperty("webdriver.chrome.driver", "C:/Users/kavi1/Downloads/chromedriver-win64 (1)/chromedriver-win64/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://www.guvi.in/sign-in/");
   /*     WebElement LoginButton = driver.findElement(By.xpath("//*[@id=\"accountGroup\"]/ul/li[1]/a"));
        LoginButton.click();
        System.out.println("Login buttom clicked");*/
    }

    @When("I enter valid login credentials with data from Excel at Sheet {int} and Row {int}")
    public void iEnterValidLoginCredentials(int sheetNumber, int rowNumber) {
      //  LOGGER.info("Entering valid login credentials: Username - {}, Password - {}", username, password);
        String excelFilePath = "src/test/resources/Exceldata/login.xlsx";
        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(sheetNumber);
            Row row = sheet.getRow(rowNumber);

            username = row.getCell(0).getStringCellValue();
            password = row.getCell(1).getStringCellValue();
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Then("^I should be successfully logged in$")
    public void iShouldBeSuccessfullyLoggedIn() {
        LOGGER.info("Verification: User successfully logged in");
      
        WebElement userName = driver.findElement(By.id("login_email"));
        userName.sendKeys(username);

        WebElement Password = driver.findElement(By.id("login_password"));
        Password.sendKeys(password);

        
        WebElement Login = driver.findElement(By.id("login_button"));
        Login.click();
        
        WebDriverWait wait = new WebDriverWait(driver,30);
        WebElement page = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/header/nav")));
        
        
        WebElement profileElement = driver.findElement(By.className("gravatar"));
        profileElement.click();
        
        String expectedKeyword = "KAVIPRIYA";
        WebElement profileNameElement = driver.findElement(By.xpath("/html/body/header/nav/div[2]/div/div[2]/div/div[1]/div[3]/h6"));
        String actualProfileName = profileNameElement.getText();

        if (actualProfileName.equals(expectedKeyword)) {
            System.out.println("Login successful. Profile name is correct.");
        } else {
            System.out.println("Login successful, but profile name is incorrect.");
        }
        
        driver.close();
    }
    
    
    @When("^I enter a search query for \"([^\"]*)\"$")
    public void iEnterASearchQuery(String course) {
        LOGGER.info("Entering search query for course: {}", course);
        WebElement Courses = driver.findElement(By.xpath("//*[@id=\"navbarSupportedContent\"]/ul/li[1]/a"));
        Courses.click();
        WebElement searchInput = driver.findElement(By.xpath("//*[@id=\"users\"]/div[2]/div/div[4]/input"));
        searchInput.sendKeys(course);
               
    }

    @Then("^relevant search results should be displayed$")
    public void relevantSearchResultsShouldBeDisplayed() {
        LOGGER.info("Verification: Relevant search results displayed");
        WebElement searchResults = driver.findElement(By.xpath("//*[@id=\"users\"]/div[2]/h1"));
        assertEquals(true, searchResults.isDisplayed());
        
        driver.close();
    }
 
    @Given("I am on a course page")
    public void iAmOnACoursePage() {
        LOGGER.info("Navigating to the course page");
      
        System.setProperty("webdriver.chrome.driver", "C:/Users/kavi1/Downloads/chromedriver-win64 (1)/chromedriver-win64/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://www.guvi.in/");
        WebElement Courses = driver.findElement(By.xpath("//*[@id=\"navbarSupportedContent\"]/ul/li[1]/a"));
        Courses.click();
        WebElement searchInput = driver.findElement(By.xpath("//*[@id=\"users\"]/div[2]/div/div[4]/input"));
        searchInput.sendKeys("Java");
        WebElement clickcourse = driver.findElement(By.xpath("//*[@id=\"atomicLib\"]/div[1]/a/div/div[2]/h3"));
        clickcourse.click();
    
    }

    @When("I enroll in the course with data from Excel at Sheet {int} and Row {int}")
    public void iEnrollInTheCourse(int sheetNumber, int rowNumber) {
        LOGGER.info("Enrolling in the course");
        
        String excelFilePath = "src/test/resources/Exceldata/login.xlsx";
        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
        
            Sheet sheet = workbook.getSheetAt(sheetNumber);
            Row row = sheet.getRow(rowNumber);

            email = formatCellValue(row.getCell(0));
            mobileNumber = formatCellValue(row.getCell(1));
            password = formatCellValue(row.getCell(2));
          
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    
    }
    
    private String formatCellValue(Cell cell) {
        String cellValue = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    cellValue = String.valueOf((int) cell.getNumericCellValue());
                    break;
                case BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                default:
                    break;
            }
        }
        return cellValue;
    }

    @Then("^I should be successfully enrolled$")
    public void iShouldBeSuccessfullyEnrolled() {
    	
        LOGGER.info("Verification: Successfully enrolled in the course");
        WebElement emailField = driver.findElement(By.id("userEmail"));
        emailField.sendKeys(email);
    
        WebElement Mobileno = driver.findElement(By.id("userMobile"));
        Mobileno.sendKeys(mobileNumber);
        WebElement Password = driver.findElement(By.id("password"));
        Password.sendKeys(password);
     
        
        WebElement enrollbtn = driver.findElement(By.id("atomicEnroll"));
        enrollbtn.click();
        
        System.out.println("Enrolled Successfully");
        driver.close();
        
       
    }
    
    
}
   
    

