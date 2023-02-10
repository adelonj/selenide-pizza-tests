package org.example.delonj.utils;

import java.text.DecimalFormat;

public class MoneyUtils {

    public static String sum(String... prices) {
        DecimalFormat moneyFormat = new DecimalFormat("0.00");
        double amount = 0;
        for (var price : prices) {
            amount+= parse(price);
        }
        return moneyFormat.format(amount).replace(".", ",") + "₽";
    }

    public static double parse(String price) {
        return Double.parseDouble(price.replace("₽", "").replace(",","."));
    }

    public static String format(Double amount) {
        DecimalFormat moneyFormat = new DecimalFormat("0.00");
        return moneyFormat.format(amount).replace(".", ",") + "₽";
    }

}
