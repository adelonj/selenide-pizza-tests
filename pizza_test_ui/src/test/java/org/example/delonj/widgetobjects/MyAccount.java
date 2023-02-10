package org.example.delonj.widgetobjects;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class MyAccount {
    private final SelenideElement registrationButton=$("button.custom-register-button");

    @Step("Нажать «Зарегистрироваться»")
    public void goToRegistration(){registrationButton.click();}
}
