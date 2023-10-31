package com.cstore.domain.category.browse;

import com.cstore.dao.category.CategoryDao;
import com.cstore.dao.product.ProductDao;
import com.cstore.dao.property.PropertyDao;
import com.cstore.dto.product.ProductCard;
import com.cstore.model.category.Category;
import com.cstore.model.product.Product;
import com.cstore.model.product.Property;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CategoryBrowsingService {
    private final CategoryDao categoryDao;
    private final ProductDao productDao;
    private final PropertyDao propertyDao;

    public List<Category> getRootCategories() {

        return categoryDao.findAllRootCategories();

    }

    public List<Category> getChildCategories(Long categoryId) {

        return categoryDao.findAllDirectSubCategories(categoryId);

    }

    public List<ProductCard> getProductsByCategory(Long categoryId) {
        List<ProductCard> productCards = new ArrayList<ProductCard>();

        List<Product> products = productDao.findByCategoryId(categoryId);
        for (Product product : products) {
            ProductCard productCard = ProductCard
                .builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .basePrice(product.getBasePrice())
                .brand(product.getBrand())
                .mainImage(product.getMainImage())
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
            productCard.setUnmarketableProperties(propertyMap);

            productCards.add(productCard);
        }

        return productCards;
    }
}
