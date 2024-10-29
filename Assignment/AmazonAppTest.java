import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AmazonAppTest {
    WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "chrome_path");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.amazon.com");
    }

    @After
    public void tearDown() {
        driver.quit();
    }
    @Test
    public void testLoginWithValidCredentials() {
        driver.findElement(By.id("nav-link-accountList")).click();
        driver.findElement(By.id("ap_email")).sendKeys("give Email_ID");
        driver.findElement(By.id("continue")).click();
        driver.findElement(By.id("ap_password")).sendKeys("give password");
        driver.findElement(By.id("signInSubmit")).click();
        WebElement accountName = driver.findElement(By.id("nav-link-accountList-nav-line-1"));
        assertTrue(accountName.isDisplayed());
    }


    @Test
    public void testSearchFunctionality() {
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("laptop");
        driver.findElement(By.id("nav-search-submit-button")).click();
        String title = driver.getTitle();
        assertTrue(title.contains("laptop"));
    }


    @Test
    public void testAddToCart() {
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("laptop");
        driver.findElement(By.id("nav-search-submit-button")).click();
        driver.findElements(By.cssSelector(".s-title")).get(0).click();
        driver.findElement(By.id("add-to-cart-button")).click();
        WebElement confirmationMessage = driver.findElement(By.cssSelector("#huc-v2-order-row-confirm-text"));
        assertTrue(confirmationMessage.isDisplayed());
    }


    @Test
    public void testRemoveFromCart() {
        driver.get("https://www.amazon.com/gp/cart/view.html");
        WebElement deleteButton = driver.findElement(By.cssSelector(".sc-action-delete .a-declarative"));
        if (deleteButton.isDisplayed()) {
            deleteButton.click();
            WebElement emptyCartMessage = driver.findElement(By.cssSelector(".sc-your-amazon-cart-is-empty"));
            assertTrue(emptyCartMessage.isDisplayed());
        }
    }


    @Test
    public void testPaymentWithInvalidCreditCard() {

        driver.get("https://www.amazon.com/gp/cart/view.html");
        driver.findElement(By.name("proceedToRetailCheckout")).click();
        driver.findElement(By.id("addCreditCardNumber")).sendKeys("0000 0000 0000 0000");
        driver.findElement(By.id("addCreditCardExpirationYear")).sendKeys("2025");
        driver.findElement(By.id("addCreditCardSecurityCode")).sendKeys("0000");
        driver.findElement(By.cssSelector("input[type='submit']")).click();
        WebElement errorMessage = driver.findElement(By.className("a-alert-heading"));
        assertTrue(errorMessage.isDisplayed());
    }
}