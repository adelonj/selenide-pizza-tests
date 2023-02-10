package org.example.delonj.widgetobjects.order;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class BillingAddress {
    public SelenideElement address = $("address");
    public SelenideElement phone = $("address .woocommerce-customer-details--phone");
    public SelenideElement email = $("address .woocommerce-customer-details--email");
}
