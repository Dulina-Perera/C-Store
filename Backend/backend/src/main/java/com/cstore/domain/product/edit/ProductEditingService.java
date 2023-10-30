package com.cstore.domain.product.edit;

import com.cstore.dao.category.BelongsToDao;
import com.cstore.dao.product.ProductDao;
import com.cstore.dao.image.ImageDao;
import com.cstore.dao.product.image.ProductImageDao;
import com.cstore.dao.property.PropertyDao;
import com.cstore.dao.variant.VariantDao;
import com.cstore.dao.varieson.VariesOnDao;
import com.cstore.dto.NewProductDto;
import com.cstore.dto.ProductDto;
import com.cstore.dto._Property;
import com.cstore.model.product.*;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductEditingService {
    private final BelongsToDao belongsToDao;
    private final ImageDao imageDao;
    private final ProductDao productDao;
    private final ProductImageDao productImageDao;
    private final PropertyDao propertyDao;
    private final VariantDao variantDao;
    private final VariesOnDao variesOnDao;

    public Long addBareProduct(
        ProductAddRequest toAdd
    ) throws DataAccessException {
        Product product = Product
            .builder()
            .productName(toAdd.getProductName())
            .basePrice(toAdd.getBasePrice())
            .brand(toAdd.getBrand())
            .description(toAdd.getDescription())
            .mainImage(toAdd.getMainImageUrl())
            .build();

        product = productDao.save(product);

        for (String imageUrl : toAdd.getOtherImageUrls()) {
            Image image = Image
                .builder()
                .url(imageUrl)
                .build();

            image = imageDao.save(image);

            productImageDao.save(image.getImageId(), product.getProductId());
        }

        return product.getProductId();
    }

    public void defineCategories(
        Long productId,
        List<Long> categoryIds
    ) throws DataAccessException, SQLIntegrityConstraintViolationException {
        for (Long categoryId : categoryIds) {
            belongsToDao.save(productId, categoryId);
        }
    }

    public PropertyMap populateWithProperties(
        List<Property> properties
    ) throws DataAccessException {
        PropertyMap propertyMap = new PropertyMap();

        for (Property property : properties) {
            property = propertyDao.save(property);

            if (!propertyMap.containsKey(property.getPropertyName())) {
                propertyMap.put(property.getPropertyName(), new ArrayList<>());
            }

            _Property _property = _Property
                .builder()
                .propertyId(property.getPropertyId())
                .value(property.getValue())
                .build();
            propertyMap.get(property.getPropertyName()).add(_property);
        }

        return propertyMap;
    }

    public ProductDto addNewProduct(NewProductDto product) {
        return null;
    }

    public void addVariants(Long productId, List<Variant_> variants) {
        for (Variant_ variant_ : variants) {
            Variant variant = variantDao.save(Variant
                .builder()
                .weight(variant_.getWeight())
                .build());

            for (Long propertyId : variant_.getPropertyIds()) {
                VariesOnId variesOnId = VariesOnId
                    .builder()
                    .productId(productId)
                    .variantId(variant.getVariantId())
                    .propertyId(propertyId)
                    .build();

                variesOnDao.save(variesOnId);
            }
        }
    }
}
