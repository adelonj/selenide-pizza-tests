package org.example.delonj;

import io.qameta.allure.*;
import org.example.delonj.dao.UserAccount;
import org.example.delonj.dao.ItemInBasket;
import org.example.delonj.widgetobjects.*;
import org.example.delonj.widgetobjects.basket.Basket;
import org.example.delonj.widgetobjects.basket.Totals;
import org.example.delonj.widgetobjects.order.PaymentInfo;
import org.example.delonj.widgetobjects.order.ReceivedOrder;
import org.example.delonj.widgetobjects.order.RegisterOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

@Epic("Основные процессы покупки")
@Story("Работа промокода")
@DisplayName("Применение промокода в корзине")
@TmsLink("PST-123")
public class PromoCodeScenario extends BaseTest {
    private ItemInBasket addedArticle;

    @BeforeEach
    @DisplayName("Открыть сайт пиццы и положить что-нибудь в корзину")
    void openPizzaPageAndAddSomethingToBasket() {
        clearBrowserCookies();
        open("http://pizzeria.skillbox.cc/product-category/menu/pizza/");
//        new SearchArticle().searchFor("какао");
        addedArticle = new ArticlesFound().addFirstArticleToBasket();
        new Menu().goToMenuTab("Корзина");
        new Totals().totalAmount.shouldHave(exactText(addedArticle.getTotalPriceWithCurr()));
        Assertions.assertIterableEquals(List.of(addedArticle), new Basket().itemListInBasket(),
                "Проверяем содержимое корзины (товары, суммы, количества)");
    }

    @Test
    @DisplayName("При вводе действующего промокода применяется скидка")
    void userCanGetDiscountWithValidPromoCode() {
        new Basket().enterPromoCode("GIVEMEHALYAVA").applyPromoCode();
        addedArticle.setTotalPrice(addedArticle.getTotalPrice() * 0.9);
        new Totals().totalAmount.shouldHave(exactText(addedArticle.getTotalPriceWithCurr()));
    }

    @Test
    @DisplayName("При вводе несуществующего промокода выводится ошибка")
    void userCannotGetDiscountWithInvalidPromoCode() {
        new Basket().enterPromoCode("WRONGCODE123").applyPromoCode();
        new Basket().alertHint.shouldHave(exactText("Неверный купон."));
        new Totals().totalAmount.shouldHave(exactText(addedArticle.getTotalPriceWithCurr()));
    }

    @Test
    @Issue("")
    @DisplayName("Проверка, что купон можно применить только один раз")
    void userCanGetDiscountWithValidPromoCodeOnlyOnce() {
        new Menu().goToMenuTab("Акции");
        new Discounts().promoCodeDescription.shouldHave(text("Промокод на первый заказ: GIVEMEHALYAVA"));
        new Menu().goToMenuTab("Мой аккаунт");
        new MyAccount().goToRegistration();
        UserAccount user = new UserAccount().getNewUser();
        new Registration().register(user.getUserName(), user.getEmail(), "pa$$word123");
        new Registration().infoMessage.shouldHave(text("Регистрация завершена"));
        new Header().welcomeMsg.shouldHave(text("Привет " + user.getUserName()));
        new Menu().goToMenuTab("Корзина");
        new Basket().enterPromoCode("GIVEMEHALYAVA").applyPromoCode();
        addedArticle.setTotalPrice(addedArticle.getTotalPrice() * 0.9);
        new Totals().totalAmount.shouldHave(exactText(addedArticle.getTotalPriceWithCurr()));
        new Totals().goToPayment();
        new RegisterOrder()
                .fillContactInfo("Charlie", "Chaplin", user.getPhone(), user.getEmail())
                .fillDeliveryAddress("Russia", "Тверская ул., д.40, кв. 5", "Москва", "Москва", "123123")
                .chooseDeliveryDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        new PaymentInfo().choosePaymentMethod("Оплата при доставке");
        new PaymentInfo().acceptTermAndConditions();
        new RegisterOrder().confirmOrder();
        new ReceivedOrder().statusMsg.shouldHave(text("Заказ получен"), Duration.ofSeconds(7));

        new SearchArticle().searchFor("какао");
        addedArticle = new ArticlesFound().addFirstArticleToBasket();
        new Menu().goToMenuTab("Корзина");
        new Totals().totalAmount.shouldHave(exactText(addedArticle.getTotalPriceWithCurr()));
        new Basket().enterPromoCode("GIVEMEHALYAVA").applyPromoCode();
        new Basket().alertHint.shouldHave(exactText("Coupon code already applied!"));
        new Totals().totalAmount.shouldHave(exactText(addedArticle.getTotalPriceWithCurr()));
    }
}
