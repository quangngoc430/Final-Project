package vn.edu.vnuk.shopping.service.admin;

import vn.edu.vnuk.shopping.exception.category.CategoryIsExistException;
import vn.edu.vnuk.shopping.exception.category.CategoryNotFoundException;
import vn.edu.vnuk.shopping.exception.category.CategoryValidationException;
import vn.edu.vnuk.shopping.model.Category;

public interface AdminCategoryService {

    Category create(Category category) throws CategoryValidationException, CategoryIsExistException;

    Category update(Long categoryId, Category category) throws CategoryNotFoundException, CategoryValidationException, CategoryIsExistException;

    void delete(Long categoryId) throws CategoryNotFoundException;

}