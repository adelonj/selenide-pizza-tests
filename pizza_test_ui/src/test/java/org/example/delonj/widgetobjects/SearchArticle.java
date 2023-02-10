package org.example.delonj.widgetobjects;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class SearchArticle {

    SelenideElement searchInput=$(".search-form input");

    @Step("Выполнить поиск товара по названию: {article}")
    public void searchFor(String article){
        searchInput.setValue(article).pressEnter();
    }

}
