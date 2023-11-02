package com.cstore.domain.cart.update;

import com.cstore.dao.cart.CartDao;
import com.cstore.dao.cart.item.CartItemDao;
import com.cstore.dao.inventory.InventoryDao;
import com.cstore.dao.variant.VariantDao;
import com.cstore.dto.VariantProperiesDto;
import com.cstore.exception.NoSuchVariantException;
import com.cstore.model.cart.CartItem;
import com.cstore.model.product.Variant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CartUpdatingService {
    private final CartDao cartDao;
    private final CartItemDao cartItemDao;
    private final InventoryDao inventoryDao;
    private final VariantDao variantDao;

    public List<CartItemResponse> getItems(
        Long userId
    ) {
        return cartItemDao.findByUserId(userId);
    }

    public Long addVariant(
        Long userId,
        VariantProperiesDto properties
    ) {
        List<Long> propertyIds = properties.getPropertyIds();
        Set<Variant> variants = new HashSet<Variant>();

        for (Long propertyId : propertyIds) {
            Set<Variant> tempVariants = new HashSet<>(variantDao.findByPropertyId(propertyId));

            if (variants.isEmpty()) {
                variants = tempVariants;
            } else {
                variants.retainAll(tempVariants);
            }
        }

        if (variants.size() != 1) {
            throw new NoSuchVariantException("No unique variant with the given set of properties found.");
        }

        Variant variant = variants.iterator().next();
        cartDao.addToCart(userId, variant.getVariantId(), properties.getQuantity());

        return variant.getVariantId();
    }

    public CartItemDto updateVariant(Long userId, CartItemUpdateRequest request) {
        Optional<CartItem> item = cartItemDao.findByUIdAndVId(userId, request.getVariantId());

        if (item.isEmpty()) {
            throw new NoSuchVariantException(
                "User with id " + userId + " does not have a variant with id " + request.getVariantId() + "in his cart."
            );
        }

        if (request.getNewCount() > 0) {
            cartItemDao.updateCount(userId, request.getVariantId(), request.getNewCount());
        }
        else {
            cartItemDao.deleteItem(userId, request.getVariantId());
        }

        return CartItemDto
            .builder()
            .variantId(request.getVariantId())
            .count(request.getNewCount())
            .build();
    }

    public List<CartItemResponse> refresh(
        Long userId
    ) {
        List<CartItemResponse> cartItems = cartItemDao.findByUserId(userId);

        for (CartItemResponse cartItemResponse : cartItems) {
            cartItemResponse.setQuantity(variantDao.countStocks(cartItemResponse.getVariantId()));
        }

        return cartItems;
    }

}