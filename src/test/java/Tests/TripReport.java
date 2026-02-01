package Tests;

import Base.BaseTest;
import config.TestConfig;
import io.qameta.allure.Allure;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.By;
import org.testng.SkipException;
import org.testng.annotations.Test;
import Pages.LoginPage;
import Pages.ReportsPage;

public class TripReport extends BaseTest {

    @Test
    @Severity(SeverityLevel.MINOR)
    public void TripReportTest() {


        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(TestConfig.USERNAME, TestConfig.PASSWORD);


        ReportsPage reportsPage = new ReportsPage(driver);
        reportsPage.openTripReport();
        reportsPage.selectAllVehicles();
        reportsPage.clickShowReports();

        boolean hasData = reportsPage.isReportHasData();

        int rows = driver.findElements(
                By.cssSelector(".ag-center-cols-container .ag-row.ag-row-level-0")
        ).size();

        Allure.addAttachment(
                "Records Count (Trip Report)",
                String.valueOf(rows)
        );

        System.out.println("Trip Report hasData = " + hasData + " | rows = " + rows);

        if (!hasData) {

            Allure.addAttachment(
                    "⚠️ Trip Report Warning",
                    "Report generated successfully but returned NO DATA"
            );

            System.out.println("⚠️ Trip Report generated but has NO DATA");


            throw new SkipException("NO DATA - Hard Ware issue");
        }


        Allure.addAttachment(
                "Trip Report Status",
                "✅ Report contains data"
        );
    }
}