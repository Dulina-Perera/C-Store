package com.cstore.domain.category.edit;

import com.cstore.dao.category.CategoryDao;
import com.cstore.exception.CategoryAlreadyExistsException;
import com.cstore.exception.NoSuchCategoryException;
import com.cstore.model.category.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoryEditingService {
    private final CategoryDao categoryDao;

    @Autowired
    public CategoryEditingService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    private Category getCategory(Category unknown) {
        return categoryDao.findCategory(unknown).orElse(null);
    }

    public List<Category> getAllCategories() {
        return categoryDao.findAll();
    }

    public Category getCategoryById(Long categoryId) {
        Optional<Category> category = categoryDao.findById(categoryId);

        if (category.isPresent()) {
            return category.get();
        }
        throw new NoSuchCategoryException("Category with id " + categoryId + " not found.");
    }

    public Long addNewCategory(Category category) {
        if (getCategory(category) == null) {

            return categoryDao.save(category);
        }
        throw new CategoryAlreadyExistsException("Category already exists.");
    }

    public Category updateCategory(Long categoryId, Map<String, Object> newDetails) throws NoSuchCategoryException {
        Category category = getCategoryById(categoryId);

        if (newDetails.get("categoryName") != null) {
            category.setCategoryName((String) newDetails.get("categoryName"));
        }
        if (newDetails.get("categoryDescription") != null) {
            category.setCategoryDescription((String) newDetails.get("categoryDescription"));
        }

        return categoryDao.update(category);
    }

    public Category deleteCategory(Long categoryId) throws NoSuchCategoryException {
        Category category = getCategoryById(categoryId);

        return categoryDao.delete(categoryId);
    }
}

