package vn.edu.vnuk.shopping.serviceImpl.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import vn.edu.vnuk.shopping.exception.category.CategoryNotFoundException;
import vn.edu.vnuk.shopping.model.Category;
import vn.edu.vnuk.shopping.repository.CategoryRepository;
import vn.edu.vnuk.shopping.service.user.CategoryService;


@Service

public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_NORMAL_USER')")
    public Page<Category> getAll(String keyword, Pageable pageable) {
        return categoryRepository.findAll(keyword, pageable);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_NORMAL_USER')")
    public Category getById(Long categoryId) throws CategoryNotFoundException {

        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

        if (!categoryOptional.isPresent()) throw new CategoryNotFoundException(categoryId);

        return categoryOptional.get();
    }

}