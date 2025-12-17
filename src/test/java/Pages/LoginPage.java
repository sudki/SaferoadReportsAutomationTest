package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private WebDriver driver;

    // تخمين للـ locators – عدّلها من الـ DevTools عندك إذا لزم
    private By usernameField = By.xpath("//input[@type='text' or @name='username' or contains(@placeholder,'User')]");
    private By passwordField = By.xpath("//input[@type='password' or @name='password']");
    private By loginButton   = By.xpath("//button[contains(.,'Sign in') or contains(.,'Login') or @type='submit']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void login(String username, String password) {
        driver.findElement(usernameField).clear();
        driver.findElement(usernameField).sendKeys(username);

        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);

        driver.findElement(loginButton).click();
    }
}
