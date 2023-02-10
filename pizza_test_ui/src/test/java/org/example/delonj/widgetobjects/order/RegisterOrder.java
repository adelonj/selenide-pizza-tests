package org.example.delonj.widgetobjects.order;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class RegisterOrder {
    public final SelenideElement infoMessage = $(".woocommerce");
    public final SelenideElement totalAmount = $(".order-total .amount");

    private final SelenideElement firstName = $("#billing_first_name");
    private final SelenideElement lastName = $("#billing_last_name");
    private final SelenideElement country = $("#billing_country");
    private final SelenideElement address = $("#billing_address_1");
    private final SelenideElement city = $("#billing_city");
    private final SelenideElement state = $("#billing_state");
    private final SelenideElement postcode = $("#billing_postcode");
    private final SelenideElement phone = $("#billing_phone");
    private final SelenideElement email = $("#billing_email");
    private final SelenideElement checkoutButton = $("#place_order");
    private final SelenideElement orderDate = $("#order_date");

    @Step("Заполнить контактную информацию")
    public RegisterOrder fillContactInfo(String firstName, String lastName, String phone, String email) {
        this.firstName.setValue(firstName);
        this.lastName.setValue(lastName);
        this.phone.setValue(phone);
        this.email.setValue(email);
        return this;
    }
    @Step("Заполнить адрес доставки")
    public RegisterOrder fillDeliveryAddress(String country, String address, String city, String state, String postcode) {
        this.country.selectOption(country);
        this.address.setValue(address);
        this.city.setValue(city);
        this.state.setValue(state);
        this.postcode.setValue(postcode);
        return this;
    }
    @Step("Подтвердить заказ")
    public void confirmOrder() {
        checkoutButton.click();
    }

    @Step("Указать дату доставки {date}")
    public RegisterOrder chooseDeliveryDate(String date) {
        orderDate.setValue(date);
        return this;
    }

}
