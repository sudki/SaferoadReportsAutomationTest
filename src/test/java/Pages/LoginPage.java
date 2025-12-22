package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // Locators بسيطة
    private By username = By.id("username");
    private By password = By.id("password");
    private By signInBtn = By.xpath("//button[contains(.,'Sign In')]");

    public void login(String user, String pass) {

        driver.get("https://track.saferoad.net/auth/signin");

        WebElement u = wait.until(ExpectedConditions.visibilityOfElementLocated(username));
        u.clear();
        u.sendKeys(user);

        WebElement p = wait.until(ExpectedConditions.visibilityOfElementLocated(password));
        p.clear();
        p.sendKeys(pass);

        wait.until(ExpectedConditions.elementToBeClickable(signInBtn)).click();

        // تأكيد الخروج من صفحة الدخول
        wait.until(ExpectedConditions.not(
                ExpectedConditions.urlContains("/auth/signin")
        ));
    }
}
