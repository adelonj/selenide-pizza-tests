package org.example.delonj.widgetobjects.basket;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class Totals {
    public SelenideElement totalAmount=$("#primary div.cart_totals tr td[data-title=Сумма]");
    SelenideElement checkoutButton=$(".checkout-button");

    @Step("Перейти на оплату заказа")
    public void goToPayment(){
        checkoutButton.click();
    }
}
