package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // Locators
    private final By username = By.id("username");
    private final By password = By.id("password");
    private final By signInBtn =
            By.xpath("//button[@type='submit' or contains(.,'Sign In')]");

    public void login(String user, String pass) {

        driver.get("https://track.saferoad.net/auth/signin");

        WebElement u = wait.until(
                ExpectedConditions.presenceOfElementLocated(username)
        );
        WebElement p = wait.until(
                ExpectedConditions.presenceOfElementLocated(password)
        );

        u.clear();
        u.sendKeys(user);
        p.clear();
        p.sendKeys(pass);

        // ✅ click عادي
        wait.until(
                ExpectedConditions.elementToBeClickable(signInBtn)
        ).click();

        // تأكيد نجاح تسجيل الدخول
        wait.until(
                ExpectedConditions.not(
                        ExpectedConditions.urlContains("/auth/signin")
                )
        );
    }
}
