package com.cstore.domain.order.buynow;

import com.cstore.dao.inventory.InventoryDao;
import com.cstore.dao.order.OrderDao;
import com.cstore.dao.user.UserDao;
import com.cstore.dao.variant.VariantDao;
import com.cstore.dto.VariantProperiesDto;
import com.cstore.exception.NoSuchVariantException;
import com.cstore.exception.sparsestocks.SparseStocksException;
import com.cstore.model.product.Variant;
import com.cstore.model.user.Role;
import com.cstore.model.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Throwables.getRootCause;

@Service
@RequiredArgsConstructor
@Slf4j
public class BuyNowService {
    private final InventoryDao inventoryDao;
    private final OrderDao orderDao;
    private final UserDao userDao;
    private final VariantDao variantDao;

    public Long buyNow(
        Long userId,
        VariantProperiesDto properties
    ) throws DataAccessException, NoSuchVariantException, SparseStocksException {
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

        if (properties.getQuantity() > variantDao.countStocks(variant.getVariantId())) {
            log.info(
                "Requested quantity: {} exceeds available quantity: {}.",
                properties.getQuantity(),
                variantDao.countStocks(variant.getVariantId())
            );
            throw new SparseStocksException("Not enough stocks!");
        }

        if (userId == null) {
            User user = User
                .builder()
                .role(Role.GUEST_CUST)
                .build();

            userId = userDao.saveUser(user).getUserId();
        }

        return orderDao.buyNow(
            userId,
            variant,
            properties.getQuantity()
        );
    }
}
