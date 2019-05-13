package vn.edu.vnuk.shopping.service.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.edu.vnuk.shopping.exception.category.CategoryNotFoundException;
import vn.edu.vnuk.shopping.exception.item.ItemNotFoundException;
import vn.edu.vnuk.shopping.model.Item;

public interface ItemService {

    Page<Item> getAll(Pageable pageable);

    Page<Item> getAll(Long categoryId, Pageable pageable) throws CategoryNotFoundException;

    Item getOne(Long itemId) throws ItemNotFoundException;
}
