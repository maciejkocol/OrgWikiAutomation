package com.application.steps;

import com.application.common.CommonApi;
import com.application.common.CommonAssert;
import com.application.common.CommonSteps;
import com.application.pages.OrgWikiHomePage;
import com.application.pages.OrgWikiProfilePage;
import com.thoughtworks.gauge.ContinueOnFailure;
import com.thoughtworks.gauge.Step;
import org.apache.log4j.Logger;

public class OrgWikiHomeSteps extends CommonSteps {

    private final Logger logger = Logger.getLogger(OrgWikiHomeSteps.class);
    private OrgWikiHomePage orgWikiHomePage;
    private OrgWikiProfilePage orgWikiProfilePage;

    @ContinueOnFailure
    @Step("Search for <name>")
    public void searchForName(String name) {
        orgWikiHomePage = new OrgWikiHomePage(driver);
        orgWikiProfilePage = new OrgWikiProfilePage(driver);
        orgWikiHomePage.searchOrgWiki(name);
        String actualName = orgWikiProfilePage.getHeaderName();
        String expectName = name;
        CommonAssert.assertEqualsText(actualName, expectName);
    }

    @ContinueOnFailure
    @Step("Reset employee data")
    public void resetEmployeeData() {
        orgWikiHomePage = new OrgWikiHomePage(driver);
        String apiURL = "https://orgwiki-test.pentesting.theorgwikidev.com/api/v2/employees/tanay_nagjee/";

        CommonApi.setEmployeeData(apiURL, "mobile_phone", "");
        CommonApi.setEmployeeData(apiURL, "primary_location.*", "");
        CommonApi.validateEmployeeData(apiURL, "mobile_phone", "");
        CommonApi.validateEmployeeData(apiURL, "location_description", null);
    }
}
