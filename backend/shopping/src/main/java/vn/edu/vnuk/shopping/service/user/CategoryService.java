package vn.edu.vnuk.shopping.service.user;

import java.util.List;

import org.springframework.data.domain.Pageable;
import vn.edu.vnuk.shopping.exception.CategoryIsExistException;
import vn.edu.vnuk.shopping.exception.CategoryNotFoundException;
import vn.edu.vnuk.shopping.exception.CategoryValidationException;
import vn.edu.vnuk.shopping.model.Category;

public interface CategoryService {

    List<Category> getAll(String keyword, Pageable pageable);

    Category getById(Long categoryId) throws CategoryNotFoundException;

    Category create(Category category) throws CategoryValidationException, CategoryIsExistException;

    Category update(Long categoryId, Category category) throws CategoryNotFoundException, CategoryValidationException, CategoryIsExistException;

    void delete(Long categoryId) throws CategoryNotFoundException;
}