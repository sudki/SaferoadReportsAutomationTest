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
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
    }

    private final By username = By.id("username");
    private final By password = By.id("password");

    private final By signInBtn =
            By.xpath("//button[@type='submit' or contains(.,'Sign In')]");


    private void disableZohoChat() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;

            js.executeScript(
                    "document.querySelectorAll('[class*=\"zsiq\"], iframe').forEach(e => e.remove());"
            );

        } catch (Exception ignored) {
        }
    }

    public void login(String user, String pass) {

        driver.get("https://track.saferoad.net/auth/signin");

        disableZohoChat();

        WebElement u = wait.until(
                ExpectedConditions.visibilityOfElementLocated(username)
        );
        WebElement p = wait.until(
                ExpectedConditions.visibilityOfElementLocated(password)
        );

        u.clear();
        u.sendKeys(user);

        p.clear();
        p.sendKeys(pass);

        disableZohoChat();

        WebElement signIn = wait.until(
                ExpectedConditions.elementToBeClickable(signInBtn)
        );

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", signIn);

        wait.until(
                ExpectedConditions.not(
                        ExpectedConditions.urlContains("/auth/signin")
                )
        );
    }
}
