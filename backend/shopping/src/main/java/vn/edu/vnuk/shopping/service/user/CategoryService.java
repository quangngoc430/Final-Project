package vn.edu.vnuk.shopping.service.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.edu.vnuk.shopping.exception.category.CategoryNotFoundException;
import vn.edu.vnuk.shopping.model.Category;

public interface CategoryService {

    Page<Category> getAll(String keyword, Pageable pageable);

    Category getOne(Long categoryId) throws CategoryNotFoundException;

}