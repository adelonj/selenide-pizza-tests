package org.example.delonj.widgetobjects.order;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ReceivedOrder {
    public SelenideElement statusMsg = $("h2.post-title");
    public SelenideElement email = $(".order_details .email");
    // ORDER DETAILS
    public ElementsCollection orderedItems = $$("tbody tr");
    public ElementsCollection productsName = $$("tbody .product-name");
    public SelenideElement productQuantity = $("tbody .product-quantity");
    public SelenideElement totalAmount = getRowBy("Total");
    public SelenideElement paymentMethod = getRowBy("Payment method");

    private SelenideElement getRowBy(String name) {
        return $$("table tr th[scope=row]").findBy(exactText(name + ":")).sibling(0);
    }

}
