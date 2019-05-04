package vn.edu.vnuk.shopping.serviceImpl.user;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import vn.edu.vnuk.shopping.exception.CategoryIsExistException;
import vn.edu.vnuk.shopping.exception.CategoryNotFoundException;
import vn.edu.vnuk.shopping.exception.CategoryValidationException;
import vn.edu.vnuk.shopping.model.Category;
import vn.edu.vnuk.shopping.repository.CategoryRepository;
import vn.edu.vnuk.shopping.service.user.CategoryService;
import vn.edu.vnuk.shopping.validation.CategoryValidation;

import javax.validation.groups.Default;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryValidation categoryValidation;

    @Override
    public List<Category> getAll(String keyword, Pageable pageable) {
        // TODO: fix code
        return categoryRepository.findAll();
    }

    @Override
    public Category getById(Long categoryId) throws CategoryNotFoundException {

        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

        if (!categoryOptional.isPresent()) throw new CategoryNotFoundException(categoryId);

        return categoryOptional.get();
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Category create(Category category) throws CategoryValidationException, CategoryIsExistException {

        categoryValidation.validate(category, Default.class);

        if (categoryRepository.existsByName(category.getName())) throw new CategoryIsExistException(category.getName());

        category.setCreatedAt(new Date());
        category.setUpdatedAt(new Date());

        return categoryRepository.save(category);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Category update(Long categoryId, Category category) throws CategoryNotFoundException, CategoryValidationException, CategoryIsExistException {

        categoryValidation.validate(category, Default.class);

        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

        if (!categoryOptional.isPresent()) throw new CategoryNotFoundException(categoryId);

        Category oldCategory = categoryOptional.get();

        if (!oldCategory.getName().equals(category.getName())
            && categoryRepository.existsByName(category.getName()))
            throw new CategoryIsExistException(category.getName());

        oldCategory.setName(category.getName());
        oldCategory.setUpdatedAt(new Date());

        return categoryRepository.save(oldCategory);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(Long categoryId) throws CategoryNotFoundException {
        if (!categoryRepository.existsById(categoryId)) throw new CategoryNotFoundException(categoryId);

        categoryRepository.deleteById(categoryId);
    }

}