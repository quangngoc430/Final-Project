package vn.edu.vnuk.shopping.service.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.edu.vnuk.shopping.exception.category.CategoryNotFoundException;
import vn.edu.vnuk.shopping.exception.item.ItemNotFoundException;
import vn.edu.vnuk.shopping.model.Item;

import java.util.List;

public interface ItemService {

    Page<Item> getAll(String keyword, Pageable pageable);

    Page<Item> getAll(String keyword, Pageable pageable, List<Long> itemIds);

    Page<Item> getAll(String keyword, Long categoryId, Pageable pageable) throws CategoryNotFoundException;

    Item getOne(Long itemId) throws ItemNotFoundException;
}
