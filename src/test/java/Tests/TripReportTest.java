package Tests;

import Base.BaseTest;

import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import Pages.LoginPage;
import Pages.ReportsPage;


public class TripReportTest extends BaseTest {

    @Test
    public void testTripReportForAllVehiclesHasData() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Demo1234", "12345678");

        ReportsPage reportsPage = new ReportsPage(driver);
        reportsPage.openTripReport();
        reportsPage.selectAllVehicles();
        reportsPage.clickShowReports();

        boolean hasData = reportsPage.isReportHasData();
        int rows = driver.findElements(By.cssSelector(".ag-center-cols-container .ag-row.ag-row-level-0")).size();
        Allure.addAttachment("Records Count (Trip Report)", String.valueOf(rows));

        System.out.println("✅ Trip Report hasData = " + hasData + " | rows = " + rows);

        Assert.assertTrue(rows > 0, "Trip Report has NO data. Rows=" + rows);
    }

}
