package com.application.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.text.MessageFormat;

import static com.application.common.DataStoreUtils.storeStringToScenarioDataStore;

public class OrgWikiProfilePage extends OrgWikiBasePage {

    //buttons
    @FindBy(xpath = "//a[@id='ctl00_ctl00_header1_lbLogOff' or @id='Header_Logout_Link']")
    public WebElement logOutButton;
    @FindBy(id = "employee-header-edit-icon")
    public WebElement editButton;
    @FindBy(xpath = "(//button[text()='Save'])[last()]")
    public WebElement saveButton;
    @FindBy(className = "office-name")
    public WebElement officeNameButton;
    @FindBy(xpath = "//i[@class='pull-right remove-item icon-remove']")
    public WebElement officeNameXButton;

    //headers
    @FindBy(id = "employee-header-full-name")
    public WebElement nameHeader;

    //fields
    @FindBy(id = "header-info-mobile-phone")
    public WebElement mobilePhoneInfo;
    @FindBy(id = "employee-header-location-and-time")
    public WebElement officeInfo;
    @FindBy(id = "mobile_phone")
    public WebElement mobilePhoneInput;
    @FindBy(xpath = "//input[@placeholder='Search Offices...']")
    public WebElement officeInput;

    private String searchItemXPath = "//div[@class=\"searchItem itemName\" and contains(text(),\"{0}\")]";

    //dropdowns
    @FindBy(id = "primary_location_type")
    public WebElement primaryWorkLocationDropdown;

    public OrgWikiProfilePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Gets the name of the employee from the header
     *
     * @return text as String
     */
    public String getHeaderName() {
        return getTextOfAnElement(nameHeader);
    }

    /**
     * Edits the mobile phone number
     *
     * @param phone
     */
    public void editMobile(String phone) {
        fillInputField(mobilePhoneInput, phone);
        storeStringToScenarioDataStore("mobile", phone);
    }

    /**
     * Selects the primary work location
     *
     * @param location
     */
    public void setPrimaryWorkLocation(String location) {
        selectDropdownValue(primaryWorkLocationDropdown, location);
        By btnXLocator = By.xpath("//i[@class='pull-right remove-item icon-remove']");
        if (isElementDisplayed(officeNameXButton)) {
            clickElement(officeNameXButton);
        }
    }

    /**
     * Selects office location
     *
     * @param office
     */
    public void selectOffice(String office) {
        fillInputField(officeInput, office);
        String xpath = MessageFormat.format(searchItemXPath, office);
        By locator = By.xpath(xpath);
        waitElementVisibility(locator);
        WebElement searchItem = driver.findElement(locator);
        clickElement(searchItem);
        waitElementVisibility(officeNameButton);
        storeStringToScenarioDataStore("office", officeNameButton.getText().trim());
    }

    /**
     * Saves changes to Profile
     */
    public void saveProfile() {
        clickElement(saveButton);
        waitUntilFinishedLoading();
        waitElementVisibility(editButton);
    }
}
