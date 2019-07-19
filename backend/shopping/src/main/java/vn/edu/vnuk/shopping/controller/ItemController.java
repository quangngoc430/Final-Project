package vn.edu.vnuk.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import vn.edu.vnuk.shopping.exception.category.CategoryNotFoundException;
import vn.edu.vnuk.shopping.exception.item.ItemNotFoundException;
import vn.edu.vnuk.shopping.exception.item.ItemValidationException;
import vn.edu.vnuk.shopping.model.Item;
import vn.edu.vnuk.shopping.repository.RatingRepository;
import vn.edu.vnuk.shopping.service.admin.AdminItemService;
import vn.edu.vnuk.shopping.service.user.ItemService;
import vn.edu.vnuk.shopping.service.user.RatingService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private AdminItemService adminItemService;

    @Autowired
    private RatingRepository ratingRepository;

    @GetMapping(value = "/api/items-suggestion", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<Item>> getItemsSuggestion(
            @RequestParam(name = "size", required = false) Long size,
            @RequestParam(name = "accountId", required = false) Long accountId,
            Pageable pageable) {
        // TODO: check rating exist => best buy
        // check has at least one rating

        if (accountId == null || ratingRepository.findAllByAccountId(accountId).size() == 0) {
            Pageable sortByUpdatedAt = PageRequest.of(0, 4, Sort.by("updatedAt").descending());
            return new ResponseEntity<>(itemService.getAll("", sortByUpdatedAt), HttpStatus.OK);
        }

        final String uri = "http://localhost:5000/user/" + accountId + "?size=" + size;

        RestTemplate restTemplate = new RestTemplate();
        String[] result = restTemplate.getForObject(uri, String[].class);

        List<Long> itemIds = new ArrayList<>();

        for (String el : result) {
            itemIds.add(Long.valueOf(el));
        }

        return new ResponseEntity<>(itemService.getAll("", pageable, itemIds), HttpStatus.OK);
    }

    @GetMapping(value = "/api/items", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<Item>> getAll(@RequestParam(name = "categoryId", required = false) Long categoryId,
                                             @RequestParam(name = "itemIds", required = false) List<Long> itemIds,
                                             @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
                                             Pageable pageable) throws CategoryNotFoundException {
        if (categoryId == null)
            if (itemIds == null)
                return new ResponseEntity<>(itemService.getAll(keyword, pageable), HttpStatus.OK);
            else
                return new ResponseEntity<>(itemService.getAll(keyword, pageable, itemIds), HttpStatus.OK);
        return new ResponseEntity<>(itemService.getAll(keyword, categoryId, pageable), HttpStatus.OK);
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
