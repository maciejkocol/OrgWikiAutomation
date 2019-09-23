package com.application.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.net.URL;

import static com.application.common.DataStoreUtils.storeStringToScenarioDataStore;

public class OrgWikiHomePage extends OrgWikiBasePage {

    //fields
    @FindBy(xpath = "//input[@placeholder='Search OrgWiki']")
    public WebElement searchBox;

    public OrgWikiHomePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Performs search of OrgWiki page
     *
     * @param name
     */
    public void searchOrgWiki(String name) {
        fillInputField(searchBox, name);
        searchBox.sendKeys(Keys.ENTER);
        waitURLContains("employees/");

        try {
            storeStringToScenarioDataStore("public_id",
                    new File(new URL(getPageURL()).getPath()).getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
