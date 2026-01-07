package Tests;

import Base.BaseTest;

import org.testng.Assert;
import org.testng.annotations.Test;
import Pages.LoginPage;
import Pages.ReportsPage;

import java.time.Duration;

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
        System.out.println("✅ Trip Report hasData = " + hasData);

        Assert.assertTrue(hasData, "Trip Report has NO data");
    }

}
