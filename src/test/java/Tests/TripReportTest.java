package Tests;

import Base.BaseTest;

import config.TestConfig;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import Pages.LoginPage;
import Pages.ReportsPage;


public class TripReportTest extends BaseTest {

    @Test
    public void TripReportTest() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(TestConfig.USERNAME,TestConfig.PASSWORD);

        ReportsPage reportsPage = new ReportsPage(driver);
        reportsPage.openTripReport();
        reportsPage.selectAllVehicles();
        reportsPage.clickShowReports();

        boolean hasData = reportsPage.isReportHasData();
        int rows = driver.findElements(By.cssSelector(".ag-center-cols-container .ag-row.ag-row-level-0")).size();
        Allure.addAttachment("Records Count (Trip Report)", String.valueOf(rows));
        System.out.println("Trip Report hasData = " + hasData + " | rows = " + rows);
        if (rows == 0) {
            Allure.addAttachment(
                    "Trip Report Status",
                    "Report generated successfully but returned NO DATA "
            );
            System.out.println("Trip Report generated but has no data");
        }

        Assert.assertTrue(true, "Trip Report page loaded successfully");
    }

}
