package com.cstore.domain.product.select;

import com.cstore.dao.category.CategoryDao;
import com.cstore.dao.product.ProductDao;
import com.cstore.dao.image.ImageDao;
import com.cstore.dao.property.PropertyDao;
import com.cstore.dto.ProductSelectionCategory;
import com.cstore.dto.SelectedProduct;
import com.cstore.exception.NoSuchProductException;
import com.cstore.model.category.Category;
import com.cstore.model.product.Image;
import com.cstore.model.product.Product;
import com.cstore.model.product.Property;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductSelectionService {
    private final CategoryDao categoryDao;
    private final ImageDao imageDao;
    private final ProductDao productDao;
    private final PropertyDao propertyDao;

    public SelectedProduct getProductById(
        Long productId
    ) throws DataAccessException {
        Optional<Product> tempProduct = productDao.findById(productId);

        if (tempProduct.isEmpty()) {
            throw new NoSuchProductException("Product with id " + productId + " not found.");
        }
        Product product = tempProduct.get();

        List<Image> otherImages = imageDao.findByProductId(product.getProductId());

        List<Category> categories = categoryDao.findByProductId(product.getProductId());
        List<ProductSelectionCategory> productSelectionCategories = new ArrayList<ProductSelectionCategory>();
        for (Category category : categories) {
            ProductSelectionCategory productSelectionCategory = ProductSelectionCategory
                .builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .build();

            productSelectionCategories.add(productSelectionCategory);
        }

        List<Property> properties = propertyDao.findByProductId(product.getProductId());
        /*PropertySelectionMap map = new PropertySelectionMap();

        for (Property property : properties) {
            if (map.containsKey(property.getPropertyName())) {
                map.get(property.getPropertyName()).add(property);
            } else {
                List<Property> propertyList = new ArrayList<>();

                propertyList.add(property);
                map.put(property.getPropertyName(), propertyList);
            }
        }*/

        Integer stockCount = productDao.countStocks(product.getProductId());

        return SelectedProduct
            .builder()
            .productId(product.getProductId())
            .productName(product.getProductName())
            .basePrice(product.getBasePrice())
            .brand(product.getBrand())
            .description(product.getDescription())
            .imageUrl(product.getMainImage())
            .otherImages(otherImages)
            .categories(productSelectionCategories)
            .properties(properties)
            .stockCount(stockCount)
            .build();
    }

}
