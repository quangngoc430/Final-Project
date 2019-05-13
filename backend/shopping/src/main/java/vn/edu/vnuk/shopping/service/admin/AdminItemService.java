package vn.edu.vnuk.shopping.service.admin;

import org.springframework.stereotype.Service;
import vn.edu.vnuk.shopping.exception.item.ItemNotFoundException;
import vn.edu.vnuk.shopping.exception.item.ItemValidationException;
import vn.edu.vnuk.shopping.model.Item;

@Service
public interface AdminItemService {

    Item create(Item item) throws ItemValidationException;

    Item update(Long itemId, Item item) throws ItemValidationException, ItemNotFoundException;

    void delete(Long itemId) throws ItemNotFoundException;

}
