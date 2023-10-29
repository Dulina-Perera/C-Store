package com.cstore.domain.product.browse;

import com.cstore.dao.product.ProductDao;
import com.cstore.dao.property.PropertyDao;
import com.cstore.dto.product.ProductCard;
import com.cstore.model.product.Product;
import com.cstore.model.product.Property;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jfree.util.Log;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductBrowsingService {

    private final ProductDao productDao;
    private final PropertyDao propertyDao;

    public List<ProductCard> getProducts() {
        List<ProductCard> productCards = new ArrayList<ProductCard>();

        List<Product> products = productDao.findAll();
        for (Product product : products) {
            ProductCard productCard = ProductCard
                .builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .basePrice(product.getBasePrice())
                .brand(product.getBrand())
                .mainImage(product.getImageUrl())
                .build();

            Map<String, List<String>> propertyMap = new HashMap<>();

            List<Property> properties = propertyDao.findUnmarketableProperties(product.getProductId());
            System.out.println(properties);
            for (Property property : properties) {
                if (propertyMap.containsKey(property.getPropertyName())) {
                    propertyMap.get(property.getPropertyName()).add(property.getValue());
                } else {
                    List<String> propertyValues = new ArrayList<>() {{
                        add(property.getValue());
                    }};

                    propertyMap.put(property.getPropertyName(), propertyValues);
                }
            }
            productCard.setProperties(propertyMap);

            productCards.add(productCard);
        }

        return productCards;
}

    public List<ProductCard> getProductsByName(
        String productName
    ) {

        List<ProductCard> productCards = new ArrayList<ProductCard>();

        List<Product> products = productDao.findByName(productName);
        for (Product product : products) {
            ProductCard productCard = ProductCard
                .builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .basePrice(product.getBasePrice())
                .brand(product.getBrand())
                .mainImage(product.getImageUrl())
                .build();

            Map<String, List<String>> propertyMap = new HashMap<>();

            List<Property> properties = propertyDao.findUnmarketableProperties(product.getProductId());
            for (Property property : properties) {
                if (propertyMap.containsKey(property.getPropertyName())) {
                    propertyMap.get(property.getPropertyName()).add(property.getValue());
                } else {
                    List<String> propertyValues = new ArrayList<>() {{
                        add(property.getValue());
                    }};

                    propertyMap.put(property.getPropertyName(), propertyValues);
                }
            }
            productCard.setProperties(propertyMap);

            productCards.add(productCard);
        }

        return productCards;

    }

}
