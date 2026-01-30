package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class ReportsPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public ReportsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
    }

    // ===================== Locators =====================
    private By sidebarToggleBtn =
            By.xpath("//div[@class='sidebar-toggle' and @data-toggle='sidebar']");

    private By reportsMenu =
            By.xpath("//span[@class='item-name' and normalize-space()='Reports']");

    private By utilizationReports = By.id("Utilization_reports_key");
    private By tripReport = By.id("trip_Report");

    private By openOverSpeedReport = By.id("Speeding_reports_key");
    private By SpeedReport = By.id("Over_Speed_Report_key");

    private By WHMReport = By.id("hours_And_Mileage_Daily_Reports");

    private By selectAllVehicles =
            By.xpath("//span[contains(@class,'rc-tree-checkbox')][1]");

    private By showReportsBtn =
            By.xpath("//button[contains(@class,'btn-primary') and contains(.,'Show Reports')]");

    private By tableRows =
            By.cssSelector(".ag-center-cols-container .ag-row.ag-row-level-0");

    private By agNoRowsOverlay =
            By.cssSelector(".ag-overlay-no-rows-center");

    private By agLoadingOverlay =
            By.cssSelector(".ag-overlay-loading-center");

    // ===================== Common Actions =====================
    private void jsClick(By locator) {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));
        try {
            el.click(); // click عادي أولاً
        } catch (Exception e) {
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", el);
        }
    }

    private void openReportsMenu() {
        jsClick(sidebarToggleBtn);
        jsClick(reportsMenu);
    }

    // ===================== Open Reports =====================
    public void openTripReport() {
        openReportsMenu();
        jsClick(utilizationReports);
        jsClick(tripReport);
    }

    public void openOverSpeedReport() {
        openReportsMenu();
        jsClick(openOverSpeedReport);
        jsClick(SpeedReport);
    }

    public void openWHM_Reports() {
        openReportsMenu();
        jsClick(utilizationReports);
        jsClick(WHMReport);
    }

    // ===================== Report Actions =====================
    public void selectAllVehicles() {
        jsClick(selectAllVehicles);
    }

    public void clickShowReports() {
        WebElement btn =
                wait.until(ExpectedConditions.presenceOfElementLocated(showReportsBtn));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", btn);

        jsClick(showReportsBtn);
        System.out.println("✅ Clicked: Show Reports");
    }

    // ===================== Validation =====================
    public boolean isReportHasData() {

        try {
            int timeoutSeconds =
                    Integer.parseInt(System.getProperty("WAIT_SECONDS", "90"));

            WebDriverWait longWait =
                    new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));

            longWait.until(d -> {
                boolean loading =
                        !d.findElements(agLoadingOverlay).isEmpty();

                if (loading) return false;

                int rows =
                        d.findElements(tableRows).size();

                boolean noRows =
                        !d.findElements(agNoRowsOverlay).isEmpty();

                return rows > 0 || noRows;
            });

            int rowsCount = driver.findElements(tableRows).size();

            if (rowsCount > 0) {
                System.out.println("✅ Report has data | rows=" + rowsCount);
                return true;
            } else {
                System.out.println("⚠️ Report generated but has NO DATA");
                return false;
            }

        } catch (TimeoutException e) {

            boolean loading =
                    !driver.findElements(agLoadingOverlay).isEmpty();

            boolean noRows =
                    !driver.findElements(agNoRowsOverlay).isEmpty();

            int rows =
                    driver.findElements(tableRows).size();

            System.out.println("❌ Timeout waiting for report result");
            System.out.println("   loading=" + loading +
                    " | noRows=" + noRows +
                    " | rows=" + rows);

            return false;
        }
    }
}
