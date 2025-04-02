package com.CME;


import com.CME.base.BaseTest;
import com.CME.config.ConfigReader;
import com.CME.utils.LocatorUtil;
import com.CME.utils.TestDataUtil;

import static com.CME.base.BaseTest.fs;

public class BaseClass {

    public static void setup(){
        BaseTest.setUp("web", "src"+fs+"main"+fs+"resources"+fs+"config.yml", false);
        ConfigReader.setConfig("src"+fs+"main"+fs+"resources"+fs+"config.yml");
        new TestDataUtil("src"+fs+"main"+fs+"java"+fs+"com"+fs+"winner"+fs+"TestData"+fs+"data.json");
        new LocatorUtil("src"+fs+"test"+fs+"resources"+fs+"ObjectRepositories"+fs+"locators.properties");
    }
}
