package vn.edu.vnuk.shopping.serviceImpl.user;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.edu.vnuk.shopping.model.Category;
import vn.edu.vnuk.shopping.repository.CategoryRepository;
import vn.edu.vnuk.shopping.service.user.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getById(Long categoryId) {
        return categoryRepository.findById(categoryId).get();
    }

}