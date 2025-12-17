package Tests;

import Base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import Pages.LoginPage;
import Pages.ReportsPage;

public class SpeedReportTest extends BaseTest{

    @Test
    public void checkOverSpeedReport() {

        // 1 — تسجيل الدخول
        LoginPage login = new LoginPage(driver);
        login.login("Demo1234", "12345678");

        // 2 — فتح تقرير Over Speed Report
        ReportsPage reportsPage = new ReportsPage(driver);
        reportsPage.openOverSpeedReport();
        reportsPage.selectAllSpeedVehicles();
        reportsPage.clickFilter();

        boolean  speedHasData = reportsPage.isReportHasData();
        // 3 — الضغط على Filter / Show

        // 4 — التحقق من البيانات
        Assert.assertTrue(reportsPage.speedHasData(), "⚠️ لا يوجد بيانات في Over Speed Report!");
    }
}

