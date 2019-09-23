package utils;

import com.application.common.CommonApi;
import com.application.common.CommonSteps;
import com.application.pages.LoginPage;
import com.thoughtworks.gauge.Step;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.Cookie;
import utils.driver.Driver;

import java.util.ArrayList;

/**
 * @author Maciej Kocol
 * <p>
 * This is the core class to launch the OrgWiki base page using drivers.
 */
public class AppLauncher extends CommonSteps {

    public static String BASE_URL = System.getenv("base_url");
    public static String HOME_URL = System.getenv("home_url");
    private LoginPage loginPage;

    @Step("Launch application page")
    public void launchTheApplication() {
        loginPage = new LoginPage(driver);
        Driver.webDriver.navigate().refresh();
        Driver.webDriver.get(BASE_URL);

        Cookie ck = CommonApi.getCookie("owSessionKey");

        if (ck == null) {
            Assertions.assertThat(loginPage.getPageTitle()).isEqualTo("OrgWiki Login | OrgWiki");
            loginPage.login();
        } else {
            driver.manage().addCookie(ck);
            driver.navigate().to(HOME_URL);
        }

        loginPage.waitURLContains("#");
        loginPage.waitUntilFinishedLoading();

        CommonApi.storeCookies(new ArrayList<>(driver.manage().getCookies()));
        Assertions.assertThat(loginPage.getPageTitle().equalsIgnoreCase("OrgWiki"))
                .as("Landed on Org Wiki home page correctly").isTrue();
    }
}
