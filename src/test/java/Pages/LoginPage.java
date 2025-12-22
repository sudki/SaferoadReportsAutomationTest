package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

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
    private final By signInBtn = By.xpath("//button[@type='submit' or contains(.,'Sign In') or contains(.,'Login')]");

    // محاولة اغلاق زرار الشات لو موجود (أحيانًا موجود)
    private final By chatCloseBtn = By.id("zs-tip-close");

    public void login(String user, String pass) {

        driver.get("https://track.saferoad.net/auth/signin");

        // 1) تعطيل الشات/الأوفرلاي قبل أي تفاعل
        killZohoOverlays();

        // 2) اكتب البيانات (بدون elementToBeClickable عشان الأوفرلاي)
        WebElement u = wait.until(ExpectedConditions.presenceOfElementLocated(username));
        WebElement p = wait.until(ExpectedConditions.presenceOfElementLocated(password));

        u.clear();
        u.sendKeys(user);

        p.clear();
        p.sendKeys(pass);

        // 3) قبل الضغط، عطّل الأوفرلاي مرة ثانية (لأنه ممكن يظهر متأخر)
        killZohoOverlays();

        // 4) اضغط Sign In بـ JS click لتجاوز ElementClickIntercepted
        jsClick(signInBtn);

        // 5) تأكيد الخروج من صفحة الدخول
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/auth/signin")));
    }

    private void jsClick(By locator) {
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }

    /**
     * حل جذري: إزالة/إخفاء أي عناصر Zoho (zsiq) + تعطيل iframes إن وجدت
     * لأن الخطأ عندك يظهر من <p class="zsiq-..."> يغطي زر Sign In.
     */
    private void killZohoOverlays() {
        // A) جرّب تضغط زر الإغلاق لو موجود (في الصفحة الرئيسية أو داخل iframe)
        tryClickChatCloseEverywhere();

        // B) أخفِ/ازل كل عناصر Zoho الشائعة (حتى لو ما كان في زر close)
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "try {" +
                            // اخفاء كل عناصر zsiq
                            "document.querySelectorAll('[class*=\"zsiq\"], [id*=\"zsiq\"], #zsiq_float, #zsiqchat, #zsiqbtn').forEach(e=>{" +
                            "  e.style.display='none'; e.style.visibility='hidden'; e.style.pointerEvents='none';" +
                            "});" +
                            // اخفاء أي iframe متعلق بالشات (احتياط)
                            "document.querySelectorAll('iframe').forEach(f=>{" +
                            "  const s = (f.getAttribute('src')||'') + ' ' + (f.id||'') + ' ' + (f.className||'');" +
                            "  if(s.toLowerCase().includes('zoho') || s.toLowerCase().includes('salesiq') || s.toLowerCase().includes('zsiq')){" +
                            "    f.style.display='none'; f.style.visibility='hidden'; f.style.pointerEvents='none';" +
                            "  }" +
                            "});" +
                            "} catch(e) {}"
            );
        } catch (Exception ignored) { }
    }

    private void tryClickChatCloseEverywhere() {
        // 1) على الصفحة الرئيسية
        try {
            List<WebElement> closeBtns = driver.findElements(chatCloseBtn);
            for (WebElement b : closeBtns) {
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", b);
                } catch (Exception ignored) {}
            }
        } catch (Exception ignored) {}

        // 2) داخل أي iframe (لأن Zoho أحيانًا يكون iframe)
        try {
            List<WebElement> frames = driver.findElements(By.tagName("iframe"));
            for (WebElement frame : frames) {
                try {
                    driver.switchTo().frame(frame);
                    List<WebElement> btns = driver.findElements(chatCloseBtn);
                    for (WebElement b : btns) {
                        try {
                            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", b);
                        } catch (Exception ignored) {}
                    }
                } catch (Exception ignored) {
                } finally {
                    driver.switchTo().defaultContent();
                }
            }
        } catch (Exception ignored) {}
    }
}
