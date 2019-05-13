package vn.edu.vnuk.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.vnuk.shopping.exception.category.CategoryNotFoundException;
import vn.edu.vnuk.shopping.exception.item.ItemNotFoundException;
import vn.edu.vnuk.shopping.exception.item.ItemValidationException;
import vn.edu.vnuk.shopping.model.Item;
import vn.edu.vnuk.shopping.service.admin.AdminItemService;
import vn.edu.vnuk.shopping.service.user.ItemService;

@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private AdminItemService adminItemService;

    @GetMapping(value = "/api/items", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<Item>> getAll(@RequestParam(name = "categoryId", required = false) Long categoryId,
                                             Pageable pageable) throws CategoryNotFoundException {
        if (categoryId == null)
            return new ResponseEntity<>(itemService.getAll(pageable), HttpStatus.OK);
        return new ResponseEntity<>(itemService.getAll(categoryId, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/api/items/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Item> getOne(@PathVariable("id") Long id) throws ItemNotFoundException {
        return new ResponseEntity<>(itemService.getOne(id), HttpStatus.OK);
    }

    @PostMapping(value = "/api/items", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Item> create(@RequestBody Item item) throws ItemValidationException {
        return new ResponseEntity<>(adminItemService.create(item), HttpStatus.OK);
    }

    @PutMapping(value = "/api/items/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Item> update(@PathVariable("id") Long id,
                                       @RequestBody Item item) throws ItemValidationException, ItemNotFoundException {
        return new ResponseEntity<>(adminItemService.update(id, item), HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/items/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) throws ItemNotFoundException {
        adminItemService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
