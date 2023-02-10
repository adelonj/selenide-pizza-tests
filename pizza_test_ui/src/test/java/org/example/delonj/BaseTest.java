package org.example.delonj;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class BaseTest {

    @BeforeAll
    @DisplayName("Init parameters for test")
    static void init() throws IOException {
        Properties appProps = new Properties();
        appProps.load(new FileInputStream(Objects.requireNonNull(BaseTest.class.getClassLoader()
                .getResource("application.properties")).getPath()));
        Configuration.browser = appProps.getProperty("browser.name");
        Configuration.browserVersion = appProps.getProperty("browser.version");
        Configuration.headless = Boolean.parseBoolean(appProps.getProperty("browser.headless"));
        Configuration.browserSize = appProps.getProperty("browser.size");
//        Configuration.remote = appProps.getProperty("selenoid.remote");
//        var caps = new DesiredCapabilities();
//        var options = Map.of("enableVnc", true, "enableVideo", true);
//        caps.setCapability("selenoid:options", options);
//        Configuration.browserCapabilities = caps;
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    @DisplayName("Kill driver")
    static void tearDown() {
        Selenide.closeWebDriver();
    }
}
