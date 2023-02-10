package org.example.delonj.dao;

import lombok.Data;
import org.example.delonj.utils.MoneyUtils;

@Data
public class ItemInBasket {
    private String name;
    private double price;
    private double totalPrice;
    private int quantity;

    public String getPriceWithCurr(){return MoneyUtils.format(price);}
    public String getTotalPriceWithCurr(){return MoneyUtils.format(totalPrice);}
}
