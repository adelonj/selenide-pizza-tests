package org.example.delonj;

import io.qameta.allure.*;
import org.example.delonj.utils.MoneyUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.example.delonj.widgetobjects.*;
import org.example.delonj.widgetobjects.basket.Basket;
import org.example.delonj.widgetobjects.basket.Totals;
import org.example.delonj.widgetobjects.order.BillingAddress;
import org.example.delonj.widgetobjects.order.PaymentInfo;
import org.example.delonj.widgetobjects.order.ReceivedOrder;
import org.example.delonj.widgetobjects.order.RegisterOrder;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.CollectionCondition.textsInAnyOrder;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.open;

@Epic("Основные процессы покупки")
@Story("Поиск и оформление заказа")
@DisplayName("Заказать несколько позиций")
@TmsLink("")
public class BuyScenario extends BaseTest {

    @Test
    @DisplayName("Пользователь может добавить в корзину пиццу и какао и оформить заказ с оплатой при доставке")
    @Link(value = "Дефект о некорректной проверке на длину email", url = "https://telegra.ph/Nekorrektnaya-proverka-na-dlinu-email-03-21")
    @Issue("Ne-vsegda-proishodit-perehod-na-Oformlenie-zakaza-03-21")
    void userCanAddArticlesToBasket() {
        open("http://pizzeria.skillbox.cc/");
        new Menu().goToMenuTab("Меню", "Пицца");
        var addedPizza = new ArticlesFound().addArticleToBasketWithName("ветчина");
        new SearchArticle().searchFor("какао");
        var addedCacao = new ArticlesFound().addFirstArticleToBasket();
        var totalAmount = MoneyUtils.format(addedPizza.getTotalPrice() + addedCacao.getTotalPrice());
        Assertions.assertEquals(totalAmount, new Header().totalPriceInBasket(), "Проверяем сумму заказа рядом с иконкой корзины");
        new Menu().goToMenuTab("Корзина");
        Assertions.assertIterableEquals(Arrays.asList(addedPizza, addedCacao), new Basket().itemListInBasket(),
                "Проверяем содержимое корзины (товары, суммы, количества)");
        new Basket().changeQuantityFor(addedPizza, 2);
        new Basket().changeQuantityFor(addedCacao, 2);
        new Basket().updateBasket().shouldHave(not(enabled));
        Assertions.assertIterableEquals(Arrays.asList(addedPizza, addedCacao), new Basket().itemListInBasket(),
                "Проверяем содержимое корзины еще раз (товары, суммы, количества)");
        totalAmount = MoneyUtils.format(addedPizza.getTotalPrice() + addedCacao.getTotalPrice());
        new Totals().totalAmount.shouldHave(exactText(totalAmount));
        new Totals().goToPayment();
//        new RegisterOrder().infoMessage.shouldHave(ownText("Для оформления заказа необходимо авторизоваться."), Duration.ofSeconds(5));
        new Menu().goToMenuTab("Мой аккаунт");
        new MyAccount().goToRegistration();

        var username = "pet" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMddHHmmss"));
        var email = username + "@me.com";
        var phone = "7" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMddHHmmss"));
        new Registration().register(username, email, "pa$$word123");
        new Registration().infoMessage.shouldHave(text("Регистрация завершена"));
        new Header().welcomeMsg.shouldHave(text("Привет " + username));
        new Menu().goToMenuTab("Оформление заказа");

        new RegisterOrder().fillContactInfo("Петр", "Первый", phone, email);
        new RegisterOrder().fillDeliveryAddress("Russia", "Вознесенская ул., д.40, кв. 5", "п.Красная поляна", "Краснодарский край", "111000");
        // TODO Реализуйте выбор даты оформления заказа через календарик, чтобы тест работал корректно, даже если при этом придётся выбирать новый месяц.
        //Не забудьте, что:
        //Тест должен запускаться любое количество раз подряд   без необходимости подготовки сайта вручную непосредственно перед запуском.
        new RegisterOrder().chooseDeliveryDate(LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        new RegisterOrder().totalAmount.shouldHave(exactText(totalAmount));
        new PaymentInfo().choosePaymentMethod("Оплата при доставке");
        new PaymentInfo().acceptTermAndConditions();
        new RegisterOrder().confirmOrder();

        new ReceivedOrder().statusMsg.shouldHave(text("Заказ получен"), Duration.ofSeconds(7));
        new ReceivedOrder().orderedItems.shouldHave(size(2)).shouldHave(
                textsInAnyOrder(
                        addedPizza.getName() + " × " + addedPizza.getQuantity() + " " + addedPizza.getTotalPriceWithCurr(),
                        addedCacao.getName() + " × " + addedCacao.getQuantity() + " " + addedCacao.getTotalPriceWithCurr()));
        new ReceivedOrder().email.shouldHave(text(email));
        new BillingAddress().email.shouldHave(text(email));
        new BillingAddress().phone.shouldHave(text(phone));
        new BillingAddress().address
                .shouldHave(text("Петр Первый"))
                .shouldHave(text("Вознесенская ул., д.40, кв. 5"))
                .shouldHave(text("п.Красная поляна"))
                .shouldHave(text("Краснодарский край"))
                .shouldHave(text("111000"));
        new ReceivedOrder().totalAmount.shouldHave(exactText(totalAmount));
        new ReceivedOrder().paymentMethod.shouldHave(exactText("Оплата при доставке"));
    }
}
