package com.cstore.model.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {
    CATEGORY_BROWSE("category:browse"),
    CATEGORY_EDIT("category:edit"),
    PRODUCT_BROWSE("product:browse"),
    PRODUCT_SELECT("product:select"),
    PRODUCT_EDIT("product:edit"),
    CART_UPDATE("cart:update"),
    INVENTORY_UPDATE("inventory:update"),
    ORDER_BUYNOW("order:buynow"),
    ORDER_CHECKOUT("order:checkout"),
    REPORT_CUSTOMER_ORDER("report:customer-order"),
    REPORT_QUARTERLY_SALES("report:quarterly-sales"),
    REPORT_GENERAL("report:general");

    private final String permission;
}
