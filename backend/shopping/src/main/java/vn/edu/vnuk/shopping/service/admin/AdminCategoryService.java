package vn.edu.vnuk.shopping.service.admin;

import vn.edu.vnuk.shopping.model.Category;

public interface AdminCategoryService {

    Category create(Category category);

    Category update(Long categoryId, Category category);

    void deleteById(Long categoryId);

}