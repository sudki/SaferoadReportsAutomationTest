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
        loginPage.login("Demo1234","12345678");

        ReportsPage reportsPage = new ReportsPage(driver);
        reportsPage.openWHM_Reports();
        reportsPage.selectAllVehicles();
        reportsPage.clickShowReports();


        boolean hasData = reportsPage.isReportHasData();
        int rows = driver.findElements(By.cssSelector(".ag-center-cols-container .ag-row.ag-row-level-0")).size();
        Allure.addAttachment("Records Count (WHM report)", String.valueOf(rows));
        System.out.println("✅ WHM Report hasData = " + hasData + " | rows = " + rows);
        Assert.assertTrue(rows > 0, "WHM report has NO data. Rows=" + rows);

    }
}


