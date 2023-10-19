package com.cstore.service;

import com.cstore.dao.category.CategoryDao;
import com.cstore.exception.CategoryAlreadyExistsException;
import com.cstore.exception.NoSuchCategoryException;
import com.cstore.model.category.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryDao categoryDao;

    @Autowired
    public CategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public List<Category> getAllCategories() {
        return categoryDao.findAll();
    }

    private Category getCategory(Category unknown) throws SQLException {
        Optional<Category> category = categoryDao.findCategory(unknown);

        return category.orElse(null);
    }

    public Category getCategoryById(Long categoryId) {
        Optional<Category> category = categoryDao.findById(categoryId);

        if (category.isEmpty()) {
            throw new NoSuchCategoryException("Category with id " + categoryId + " not found.");
        }
        return category.get();
    }

    public Category addNewCategory(Category category) throws SQLException {
        try {
            if (getCategory(category) == null) {
                try {
                    categoryDao.save(category);

                    try {
                        return getCategory(category);
                    } catch (SQLException sqe) {
                        throw new SQLException("Category inserted but unable to retrieve.");
                    }
                } catch (SQLException sqe) {
                    throw new SQLException("Error while inserting the category.");
                }
            } else {
                throw new CategoryAlreadyExistsException("Category already exists.");
            }
        } catch (SQLException sqe) {
            throw new SQLException("Unable to check for the pre-existence of category.");
        }
    }

    public Category updateCategory(Long categoryId, Map<String, Object> newDetails) throws NoSuchCategoryException, SQLException {
        Category category = getCategoryById(categoryId);

        if (newDetails.get("categoryName") != null) {
            category.setCategoryName((String) newDetails.get("categoryName"));
        }
        if (newDetails.get("categoryDescription") != null) {
            category.setCategoryDescription((String) newDetails.get("categoryDescription"));
        }

        categoryDao.update(category);
        return category;
    }



    public void deleteCategory(Long categoryId) throws SQLException {
        categoryDao.delete(categoryId);
    }



    /*private List<ProductCard> convert(Category category) {
        List<BelongsTo> belongings = belongsToRepository.findByCategory(category);
        List<ProductCard> products = new ArrayList<>();

        for (BelongsTo belonging : belongings) {
            ProductCard productDTO = new ProductCard();
            List<ProductSelectionProperty> properties = new ArrayList<>();

            WholeProduct product = belonging.getProduct();
            List<VariesOn> variances = variesOnRepository.findByProduct(product);

            for (VariesOn variance : variances) {
                ProductSelectionProperty property = variance.getProperty();
                ProductSelectionProperty propertyDTO = new ProductSelectionProperty();

                propertyDTO.setPropertyName(property.getPropertyName());
                propertyDTO.setValue(property.getValue());
                propertyDTO.setImage(property.getImage());
                propertyDTO.setPriceIncrement(property.getPriceIncrement());

                properties.add(propertyDTO);
            }

            productDTO.setProductId(product.getProductId());
            productDTO.setProductName(product.getProductName());
            productDTO.setBasePrice(product.getBasePrice());
            productDTO.setBrand(product.getBrand());
            productDTO.setDescription(product.getDescription());
            productDTO.setMainImage(product.getMainImage());
            productDTO.setProperties(properties);
        }

        return products;
    }*/

    /*public List<ProductCard> getAllProducts(Long categoryId) {
        return productRepository.findAllProducts();
    }*/
}
