package org.example.delonj.widgetobjects.basket;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.example.delonj.BaseTest;
import org.example.delonj.dao.ItemInBasket;
import org.example.delonj.utils.MoneyUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.example.delonj.utils.MoneyUtils.*;

public class Basket {

    public ElementsCollection basketItems = $$("#primary tr.cart_item");
    public SelenideElement alertHint = $(".woocommerce-notices-wrapper [role=alert]");
    SelenideElement promoCodeInput = $(".coupon input");
    SelenideElement updateButton = $("button[name=update_cart]");
    SelenideElement applyPromoCodeButton = $(".coupon button");


    @Step("Получить список товаров в козине")
    public List<ItemInBasket> itemListInBasket() {
        ArrayList<ItemInBasket> orderList = new ArrayList<>();
        for (SelenideElement el : basketItems) {
            ItemInBasket item = new ItemInBasket();
            item.setName(el.$(("td[data-title='Товар']")).getText());
            item.setPrice(MoneyUtils.parse(el.$(("td[data-title='Цена']")).getText()));
            item.setQuantity(Integer.parseInt(Objects.requireNonNull(el.$("td[data-title='Количество'] input").getValue())));
            item.setTotalPrice(MoneyUtils.parse(el.$("td[data-title='Общая стоимость']").getText()));
            orderList.add(item);
        }
        return orderList;
    }

    @Step("Установить количество [{quantity}] для [{itemInBasket.name}]")
    public void changeQuantityFor(ItemInBasket itemInBasket, int quantity) {
        itemInBasket.setQuantity(quantity);
        itemInBasket.setTotalPrice(itemInBasket.getPrice() * quantity);
        basketItems.filterBy(text(itemInBasket.getName())).first().$("td[data-title='Количество'] input")
                .setValue(String.valueOf(quantity));
    }

    @Step("Обновить корзину")
    public SelenideElement updateBasket() {
        updateButton.click();
        return updateButton;
    }

    @Step("Ввести промокод [{promoCode}]")
    public Basket enterPromoCode(String promoCode) {
        promoCodeInput.setValue(promoCode);
        return this;
    }

    @Step("Применить промокод")
    public void applyPromoCode() {
        applyPromoCodeButton.click();
    }
}
