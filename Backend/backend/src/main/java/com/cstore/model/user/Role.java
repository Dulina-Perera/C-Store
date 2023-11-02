package com.cstore.model.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.cstore.model.user.Permission.*;

@RequiredArgsConstructor
@Getter
public enum Role {
    ADMIN(
        Set.of(
            CATEGORY_BROWSE,
            CATEGORY_EDIT,
            PRODUCT_BROWSE,
            PRODUCT_SELECT,
            PRODUCT_EDIT,
            INVENTORY_UPDATE,
            REPORT_CUSTOMER_ORDER,
            REPORT_QUARTERLY_SALES,
            REPORT_GENERAL,
            USER_DETAILS
        )
    ),

    GUEST_CUST(
        Set.of(
            CATEGORY_BROWSE,
            PRODUCT_BROWSE,
            PRODUCT_SELECT,
            ORDER_BUYNOW,
            REPORT_GENERAL
        )
    ),
    REG_CUST(
        Set.of(
            CATEGORY_BROWSE,
            PRODUCT_BROWSE,
            PRODUCT_SELECT,
            CART_UPDATE,
            ORDER_BUYNOW,
            ORDER_CHECKOUT,
            REPORT_CUSTOMER_ORDER,
            REPORT_GENERAL,
            USER_DETAILS
        )
    );

    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = getPermissions()
            .stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }
}