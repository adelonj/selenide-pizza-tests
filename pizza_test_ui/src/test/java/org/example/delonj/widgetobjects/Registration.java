package org.example.delonj.widgetobjects;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class Registration {
    public SelenideElement infoMessage = $(".content-page");
    SelenideElement usernameInput = $("#reg_username");
    SelenideElement emailInput = $("#reg_email");
    SelenideElement passwordInput = $("#reg_password");
    SelenideElement registerBtn = $("button[name=register]");

    @Step("Зарегистрировать пользователя")
    public void register(String username, String email, String password) {
        usernameInput.setValue(username);
        emailInput.setValue(email);
        passwordInput.setValue(password);
        registerBtn.click();
    }
}
