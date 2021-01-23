package ru.geekbrains.service;

import ru.geekbrains.service.model.LineItem;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public interface CartService extends Serializable {
    void removeProduct(LineItem lineItem);

    void removeAllProduct();

    List<LineItem> getLineItems();

    BigDecimal getSubTotal();

    void updateCart(LineItem lineItem);
}
