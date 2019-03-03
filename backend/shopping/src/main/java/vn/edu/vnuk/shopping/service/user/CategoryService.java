package vn.edu.vnuk.shopping.service.user;

import java.util.List;

import vn.edu.vnuk.shopping.model.Category;

public interface CategoryService {

    List<Category> getAll();

    Category getById(Long categoryId);
}