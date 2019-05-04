package vn.edu.vnuk.shopping.controller.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.vnuk.shopping.service.user.CategoryService;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "/api/categories", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getAll(@RequestParam("keyword") String keyword,
                                    Pageable pageable) {
        return new ResponseEntity<>(categoryService.getAll(keyword, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/api/categories/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        return new ResponseEntity<>(categoryService.getById(id), HttpStatus.OK);
    }
}
