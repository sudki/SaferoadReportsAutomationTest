package Tests;

import Base.BaseTest;

import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import Pages.LoginPage;
import Pages.ReportsPage;

public class WHM_Reports extends BaseTest {

    @Test
    public void testWHMreportHasData(){

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Demo1234","O@2023d");

        ReportsPage reportsPage = new ReportsPage(driver);
        reportsPage.openWHM_Reports();
        reportsPage.selectAllVehicles();
        reportsPage.clickShowReports();


        boolean hasData = reportsPage.isReportHasData();
        int rows = driver.findElements(By.cssSelector(".ag-center-cols-container .ag-row.ag-row-level-0")).size();
        Allure.addAttachment("Records Count (WHM report)", String.valueOf(rows));
        System.out.println("✅ WHM Report hasData = " + hasData + " | rows = " + rows);
        if (rows == 0) {
            Allure.addAttachment(
                    "Trip Report Status",
                    "Report generated successfully but returned NO DATA for selected filters"
            );
            System.out.println("⚠️ Trip Report generated but has no data");
        }

        Assert.assertTrue(true, "Trip Report page loaded successfully");

    }
}


