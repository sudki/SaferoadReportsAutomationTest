package Tests;

import Base.BaseTest;

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
        System.out.println("✅ WHM report hasData = " + hasData);

        Assert.assertTrue(hasData, "❌ WHM Report has NO data");
    }
}


