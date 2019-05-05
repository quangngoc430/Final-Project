package vn.edu.vnuk.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.vnuk.shopping.exception.account.AccountNotFoundException;
import vn.edu.vnuk.shopping.exception.category.CategoryIsExistException;
import vn.edu.vnuk.shopping.exception.category.CategoryNotFoundException;
import vn.edu.vnuk.shopping.exception.category.CategoryValidationException;
import vn.edu.vnuk.shopping.exception.common.UnauthorizedException;
import vn.edu.vnuk.shopping.exception.token.TokenIsExpiredException;
import vn.edu.vnuk.shopping.exception.token.TokenNotFoundException;
import vn.edu.vnuk.shopping.model.Category;
import vn.edu.vnuk.shopping.service.admin.AdminCategoryService;
import vn.edu.vnuk.shopping.service.user.CategoryService;
import vn.edu.vnuk.shopping.service.user.CommonService;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AdminCategoryService adminCategoryService;

    @Autowired
    private CommonService commonService;

    @GetMapping(value = "/api/categories", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<Category>> getAll(@RequestParam("keyword") String keyword,
                                       Pageable pageable) {
        return new ResponseEntity<>(categoryService.getAll(keyword, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/api/categories/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Category> get(@PathVariable("id") Long id) throws CategoryNotFoundException {
        return new ResponseEntity<>(categoryService.getOne(id), HttpStatus.OK);
    }

    @PostMapping(value = "/api/categories", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Category> create(@RequestBody Category category,
                                    @RequestParam(name = "accessToken") String accessToken,
                                    HttpServletRequest request) throws TokenIsExpiredException, AccountNotFoundException, TokenNotFoundException, UnauthorizedException, CategoryValidationException, CategoryIsExistException {
        commonService.authenticate(accessToken, request);
        return new ResponseEntity<>(adminCategoryService.create(category), HttpStatus.OK);
    }

    @PutMapping(value = "/api/categories/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Category> update(@RequestBody Category category,
                                           @PathVariable("id") Long id,
                                           @RequestParam(name = "accessToken") String accessToken,
                                           HttpServletRequest request) throws TokenIsExpiredException, AccountNotFoundException, TokenNotFoundException, UnauthorizedException, CategoryIsExistException, CategoryValidationException, CategoryNotFoundException {
        commonService.authenticate(accessToken, request);
        return new ResponseEntity<>(adminCategoryService.update(id, category), HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/categories/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> delete(@PathVariable("id") Long id,
                                       @RequestParam(name = "accessToken") String accessToken,
                                       HttpServletRequest request) throws TokenIsExpiredException, AccountNotFoundException, TokenNotFoundException, UnauthorizedException, CategoryNotFoundException {
        commonService.authenticate(accessToken, request);
        adminCategoryService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
