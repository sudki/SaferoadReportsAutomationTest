package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

public class LoginPage {

    private WebDriver driver;

    // تخمين للـ locators – عدّلها من الـ DevTools عندك إذا لزم
    private By usernameField = By.xpath("//input[@type='text' or @name='username' or contains(@placeholder,'User')]");
    private By passwordField = By.xpath("//input[@type='password' or @name='password']");
    private By loginButton = By.xpath("//button[contains(.,'Sign in') or contains(.,'Login') or @type='submit']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void hideChatWidgetIfExists() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(
                    "const selectors = ['#zsiqchat', '.zsiq_float', '.zsiq_theme1', '.zsiqf', 'iframe[id*=zsiq]', 'iframe[src*=salesiq]', 'iframe[src*=zoho]'];" +
                            "selectors.forEach(s => document.querySelectorAll(s).forEach(el => el.style.display='none'));" +
                            "document.querySelectorAll('iframe').forEach(f => { try { if((f.src||'').includes('salesiq')||(f.src||'').includes('zoho')||(f.id||'').includes('zsiq')) f.style.display='none'; } catch(e){} });"
            );
        } catch (Exception ignored) {
        }
    }

    public void login(String user, String pass) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement u = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        u.clear();
        u.sendKeys(user);

        WebElement p = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        p.clear();
        p.sendKeys(pass);

        // اخفاء شات Zoho (احتياط)
        hideZohoChatHard();

        // ✅ الحل النهائي: لا نضغط زر Sign in أصلاً
        // 1) جرّب ENTER
        try {
            p.sendKeys(Keys.ENTER);
        } catch (Exception ignored) {}

        // 2) إذا ما اشتغل ENTER، نفذ submit للفورم بالـ JS (يتجاوز أي overlay)
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "const btn = document.evaluate(arguments[0], document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;" +
                            "if(btn){ const f = btn.closest('form'); if(f) f.submit(); }",
                    getLoginButtonXPath()
            );
        } catch (Exception ignored) {}
    }

    /** XPath زر الدخول كـ String (نستخدمه داخل JS) */
    private String getLoginButtonXPath() {
        return "//button[contains(.,'Sign in') or contains(.,'Login') or @type='submit']";
    }

    /** إخفاء Zoho SalesIQ / zsiq widgets + iframes */
    private void hideZohoChatHard() {
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "const sels=[" +
                            "'#zsiqchat','[id*=zsiq]','[class*=zsiq]'," +
                            "'iframe[id*=zsiq]','iframe[src*=salesiq]','iframe[src*=zoho]'" +
                            "];" +
                            "sels.forEach(s=>document.querySelectorAll(s).forEach(el=>{" +
                            "el.style.display='none';el.style.visibility='hidden';el.style.pointerEvents='none';" +
                            "}));"
            );
        } catch (Exception ignored) {}
    }
}

