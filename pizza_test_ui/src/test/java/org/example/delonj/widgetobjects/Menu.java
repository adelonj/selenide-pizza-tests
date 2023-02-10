package org.example.delonj.widgetobjects;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class Menu {
    SelenideElement menu = $("#menu-primary-menu");

    @Step("Перейти на вкладку меню [{0}]")
    public void goToMenuTab(String name){
        menu.find(byText(name)).click();
    }
    @Step("Через вкладку меню [{name}] выбрать из выпадающего списка [{dropdownMenuValue}]")
    public void goToMenuTab(String name, String dropdownMenuValue){
        menu.find(byText(name)).hover();
        $(".sub-menu").$(byText(dropdownMenuValue)).click();
    }
}
