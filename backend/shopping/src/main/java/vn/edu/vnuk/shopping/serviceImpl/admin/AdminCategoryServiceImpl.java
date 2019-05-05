package vn.edu.vnuk.shopping.serviceImpl.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import vn.edu.vnuk.shopping.exception.category.CategoryIsExistException;
import vn.edu.vnuk.shopping.exception.category.CategoryNotFoundException;
import vn.edu.vnuk.shopping.exception.category.CategoryValidationException;
import vn.edu.vnuk.shopping.model.Category;
import vn.edu.vnuk.shopping.repository.CategoryRepository;
import vn.edu.vnuk.shopping.service.admin.AdminCategoryService;
import vn.edu.vnuk.shopping.validation.CategoryValidation;

import javax.validation.groups.Default;
import java.util.Date;
import java.util.Optional;

@Service
public class AdminCategoryServiceImpl implements AdminCategoryService {


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryValidation categoryValidation;

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
