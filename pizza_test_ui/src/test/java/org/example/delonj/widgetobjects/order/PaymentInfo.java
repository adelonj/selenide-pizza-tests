package org.example.delonj.widgetobjects.order;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class PaymentInfo {
    SelenideElement payment = $("#payment ul");
    SelenideElement termsAndConditionsCheckbox = $("input#terms[type='checkbox']");

    @Step("Выбрать метод оплаты [{method}]")
    public void choosePaymentMethod(String method) {payment.$(byText(method)).click();}
    @Step("Подтверить пользовательское соглашение")
    public void acceptTermAndConditions() {termsAndConditionsCheckbox.setSelected(true);}
}
