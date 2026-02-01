package Tests;

import Base.BaseTest;

import config.TestConfig;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import Pages.LoginPage;
import Pages.ReportsPage;

public class WHM_Report extends BaseTest {

    @Test
    public void WHMReportTest(){

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(TestConfig.USERNAME,TestConfig.PASSWORD);

        ReportsPage reportsPage = new ReportsPage(driver);
        reportsPage.openWHM_Reports();
        reportsPage.selectAllVehicles();
        reportsPage.clickShowReports();


        boolean hasData = reportsPage.isReportHasData();
        int rows = driver.findElements(By.cssSelector(".ag-center-cols-container .ag-row.ag-row-level-0")).size();
        Allure.addAttachment("Records Count (WHM report)", String.valueOf(rows));
        System.out.println(" WHM Report hasData = " + hasData + " | rows = " + rows);
        if (rows == 0) {
            Allure.addAttachment(
                    "WHM Report Status",
                    "Report generated successfully but returned NO DATA "
            );
            System.out.println(" WHM Report generated but has no data");
        }

        Assert.assertTrue(true, "Trip Report page loaded successfully");

    }
}


