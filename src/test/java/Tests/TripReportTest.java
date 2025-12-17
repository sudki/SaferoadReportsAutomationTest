package Tests;

import Base.BaseTest;
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


        Assert.assertTrue(hasData, "trip Report has no data ");


    }
}
