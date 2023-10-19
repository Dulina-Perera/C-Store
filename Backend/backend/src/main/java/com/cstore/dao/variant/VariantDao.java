package com.cstore.dao.variant;

import com.cstore.model.product.Variant;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Optional;

public interface VariantDao {
    Optional<Variant> findById(Long variantId);

    List<Variant> findByPropertyId(Long propertyId) throws DataAccessException;

    Variant save(Variant variant);

    Integer countStocks(Long varaintId);
}
