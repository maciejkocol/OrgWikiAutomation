package com.application.steps;

import com.application.common.CommonAssert;
import com.application.common.CommonSteps;
import com.application.pages.OrgWikiProfilePage;
import com.thoughtworks.gauge.ContinueOnFailure;
import com.thoughtworks.gauge.Step;
import org.apache.log4j.Logger;
import org.assertj.core.api.Assertions;

import static com.application.common.DataStoreUtils.fetchStringFromScenarioDataStore;

public class OrgWikiProfileSteps extends CommonSteps {

    private final Logger logger = Logger.getLogger(OrgWikiProfileSteps.class);
    private OrgWikiProfilePage orgWikiProfilePage;

    @ContinueOnFailure
    @Step("Go to edit profile")
    public void goEdit() {
        orgWikiProfilePage = new OrgWikiProfilePage(driver);
        orgWikiProfilePage.clickElement(orgWikiProfilePage.editButton);
        Assertions.assertThat(orgWikiProfilePage.getPageTitle().equalsIgnoreCase("Edit Employee Tanay Nagjee | OrgWiki"))
                        .as("Landed on Org Wiki edit page correctly").isTrue();
        CommonAssert.assertTrue("Edit page url not found", driver.getCurrentUrl().contains("/edit"));
    }

    @ContinueOnFailure
    @Step("Edit mobile phone as <phone>")
    public void editMobilePhone(String phone) {
        orgWikiProfilePage = new OrgWikiProfilePage(driver);
        orgWikiProfilePage.editMobile(phone);
    }

    @ContinueOnFailure
    @Step("Set primary work location as <location>")
    public void setPrimaryWorkLocation(String location) {
        orgWikiProfilePage = new OrgWikiProfilePage(driver);
        orgWikiProfilePage.setPrimaryWorkLocation(location);
    }

    @ContinueOnFailure
    @Step("Select office as <office>")
    public void selectOffice(String office) {
        orgWikiProfilePage = new OrgWikiProfilePage(driver);
        orgWikiProfilePage.selectOffice(office);
    }

    @ContinueOnFailure
    @Step("Save profile")
    public void save() {
        orgWikiProfilePage = new OrgWikiProfilePage(driver);
        String expectMobilePhone = fetchStringFromScenarioDataStore("mobile");
        String expectOffice = fetchStringFromScenarioDataStore("office");

        orgWikiProfilePage.saveProfile();

        orgWikiProfilePage.assertElementExists("Mobile phone not found!", orgWikiProfilePage.mobilePhoneInfo);
        orgWikiProfilePage.assertElementExists("Office location not found!", orgWikiProfilePage.officeInfo);

        String actualMobilePhone = orgWikiProfilePage.mobilePhoneInfo.getText();
        String actualOffice = orgWikiProfilePage.officeInfo.getText();

        CommonAssert.assertEqualsText(actualMobilePhone, expectMobilePhone);
        CommonAssert.assertTrue("Office location not saved correctly!", actualOffice.contains(expectOffice));
    }
}
