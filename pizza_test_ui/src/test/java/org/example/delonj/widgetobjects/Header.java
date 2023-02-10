package org.example.delonj.widgetobjects;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class Header {
    public SelenideElement welcomeMsg = $("header .welcome-user");
    private final SelenideElement basket = $(".cart-contents");

    @Step("Найти общую сумма заказа")
    public String totalPriceInBasket(){
        return basket.getText().replaceAll("[\\[\\]]", "").trim();
    }
}
