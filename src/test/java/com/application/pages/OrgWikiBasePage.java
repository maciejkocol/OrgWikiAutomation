package com.application.pages;

import com.application.common.CommonAssert;
import com.application.common.CommonObjects;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Maciej Kocol
 * <p>
 * This is the step class that will be extended by all the other steps.
 */
public class OrgWikiBasePage extends CommonObjects {
    private final Logger logger = Logger.getLogger(OrgWikiBasePage.class);

    @FindBy(xpath = "//h2[starts-with(text(),'Description')]")
    public WebElement planPreviewHeader;
    @FindBy(xpath = "//div[@class='card-header' and normalize-space(text())='Benefits Preview']")
    public WebElement benefitsPreviewHeader;
    @FindBy(xpath = "//h2[normalize-space(text())='OOPC Preview']")
    public WebElement oopcPreviewHeader;
    @FindBy(xpath = "//h2[normalize-space(text())='Pre-2020 Plan Data']")
    public WebElement pre2020PlanDataHeader;
    @FindBy(xpath = "//h2[normalize-space(text())='Handbook Preview']")
    public WebElement handbookPreviewHeader;
    @FindBy(xpath = "//div[@role='heading' and normalize-space(text())='Benefits Preview Report']")
    public WebElement benefitsPreviewReportHeader;

    //nav links
    @FindBy(xpath = "//span[@class='nav-text' and normalize-space(text())='Dashboard']")
    public WebElement dashboardNavLink;
    @FindBy(xpath = "//span[@class='nav-text' and normalize-space(text())='Drug Pricing Preview']")
    public WebElement drugPricingPreviewNavLink;
    @FindBy(xpath = "//span[@class='nav-text' and normalize-space(text())='Benefits Preview']")
    public WebElement benefitsPreviewNavLink;
    @FindBy(xpath = "//span[@class='nav-text' and normalize-space(text())='OOPC Preview']")
    public WebElement oopcPreviewNavLink;
    @FindBy(xpath = "//span[@class='nav-text' and normalize-space(text())='Pre-2020 Plan Data']")
    public WebElement pre2020PlanDataNavLink;
    @FindBy(xpath = "//span[@class='nav-text' and normalize-space(text())='Handbook Preview']")
    public WebElement handbookPreviewNavLink;
    @FindBy(xpath = "//span[@class='nav-text' and normalize-space(text())='Benefits Preview Report']")
    public WebElement benefitsPreviewReportNavLink;
    @FindBy(xpath = "//span[@class='nav-text' and normalize-space(text())='Documentation']")
    public WebElement documentationNavLink;

    //page links
    @FindBy(id = "dashboard_actions_pdp_link")
    public WebElement drugPricingPreviewActLink;
    @FindBy(id = "dashboard_actions_plan_benfits_data_link")
    public WebElement benefitsPreviewActLink;
    @FindBy(id = "dashboard_actions_oopc_preview_link")
    public WebElement oopcPreviewActLink;
    @FindBy(id = "dashboard_actions_pre2020_data_link")
    public WebElement pre2020PlanDataActLink;
    @FindBy(id = "dashboard_actions_reports_link")
    public WebElement reportsActLink;
    @FindBy(id = "dashboard_actions_documentation_link")
    public WebElement documentationActLink;

    //status totals
    @FindBy(xpath = "//span[text()='Reviewed']/following-sibling::node()")
    public WebElement totalVerified;
    @FindBy(xpath = "//span[text()='Not Reviewed']/following-sibling::node()")
    public WebElement totalNonVerified;
    @FindBy(xpath = "//span[text()='Concur']/following-sibling::node()")
    public WebElement totalConcurred;
    @FindBy(xpath = "//span[text()='Non-Concur']/following-sibling::node()")
    public WebElement totalNonConcurred;
    @FindBy(xpath = "//*[contains(text(),'Total Number of Contracts')]/parent::node()")
    public WebElement totalContracts;
    @FindBy(xpath = "//*[contains(text(),'Plans/segments Across All of Your Contracts')]/parent::node()")
    public WebElement totalPlans;

    public OrgWikiBasePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Verifies that a given element exists.
     *
     * @param message
     * @param element
     */
    public void assertElementExists(String message, WebElement element) {
        CommonAssert.assertElementExists(message, element);
        highLightElement(element);
    }
}

