package org.example.delonj.widgetobjects;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.example.delonj.dao.ItemInBasket;
import org.example.delonj.utils.MoneyUtils;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

@Slf4j
public class ArticlesFound {
    ElementsCollection articles = $$("#primary .wc-products li");

    @Step("Добавить в корзину первый товар из списка")
    public ItemInBasket addFirstArticleToBasket() {
        ItemInBasket itemInBasket;
        if(articles.size() != 0) {
            itemInBasket = getItemData(articles.first());
            articles.first().$(".price-cart a").click();
            sleep(2000);
        } else {
            SelenideElement itemFounded = $(".entry-summary");
            itemInBasket = getItemData(itemFounded);
            $(".button[name=add-to-cart]").click();
        }
        return itemInBasket;
    }

    @Step("Добавить в корзину товар с названием {name}")
    public ItemInBasket addArticleToBasketWithName(String name) {
        SelenideElement item = articles.findBy(text(name));
        ItemInBasket itemInBasket = getItemData(item);
        item.$(".price-cart a").click();
        sleep(2000);
        return itemInBasket;
    }

    private ItemInBasket getItemData(SelenideElement selenideItem){
        ItemInBasket itemInBasket = new ItemInBasket();
        itemInBasket.setName(selenideItem.$("h3,h1").getText().replaceAll("[«»]", "\""));
        itemInBasket.setQuantity(1);
        itemInBasket.setPrice(MoneyUtils.parse(selenideItem.$(".price").getText()));
        itemInBasket.setTotalPrice(MoneyUtils.parse(selenideItem.$(".price").getText()));
        log.info("В корзину добавлен товар: " + itemInBasket.toString());
        return itemInBasket;
    }
}
