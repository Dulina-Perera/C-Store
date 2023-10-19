package com.cstore.dao.property;

import com.cstore.model.product.Property;
import org.springframework.dao.DataAccessException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PropertyDao {
    public Optional<Property> findById(Long propertyId);

    List<Property> findByProductId(Long productId) throws DataAccessException;

    Property save(Property property);

    List<Property> findUnmarketableProperties(Long productId) throws DataAccessException;
}
