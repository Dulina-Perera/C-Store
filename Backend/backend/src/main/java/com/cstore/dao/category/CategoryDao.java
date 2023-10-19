package com.cstore.dao.category;

import com.cstore.model.category.Category;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Optional;

public interface CategoryDao {
    List<Category> findAll();

    Optional<Category> findCategory(Category unknown);

    Optional<Category> findById(Long categoryId);

    Long save(Category category);

    Category update(Category category);

    Category delete(Long categoryId);

    List<Category> findAllRootCategories();

    List<Category> findAllDirectSubCategories(Long categoryId) throws DataAccessException;

    List<Category> findByProductId(Long productId);
}
