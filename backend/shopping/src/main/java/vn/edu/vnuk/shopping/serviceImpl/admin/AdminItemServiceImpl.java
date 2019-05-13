package vn.edu.vnuk.shopping.serviceImpl.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.vnuk.shopping.exception.item.ItemNotFoundException;
import vn.edu.vnuk.shopping.exception.item.ItemValidationException;
import vn.edu.vnuk.shopping.model.Item;
import vn.edu.vnuk.shopping.repository.ItemRepository;
import vn.edu.vnuk.shopping.service.admin.AdminItemService;
import vn.edu.vnuk.shopping.validation.item.ItemValidation;

import javax.validation.groups.Default;
import java.util.Date;
import java.util.Optional;

@Service
public class AdminItemServiceImpl implements AdminItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemValidation itemValidation;

    @Override
    public Item create(Item item) throws ItemValidationException {
        itemValidation.validate(item, Default.class);

        item.setCreatedAt(new Date());
        item.setUpdatedAt(new Date());

        return itemRepository.save(item);
    }

    @Override
    public Item update(Long itemId, Item item) throws ItemValidationException, ItemNotFoundException {
        itemValidation.validate(item, Default.class);

        Optional<Item> itemOptional = itemRepository.findById(itemId);

        if (!itemOptional.isPresent()) throw new ItemNotFoundException(itemId);

        Item oldItem = itemOptional.get();
        oldItem.setName(item.getName());
        oldItem.setAmount(item.getAmount());
        oldItem.setDescription(item.getDescription());
        oldItem.setImageURLs(item.getImageURLs());
        oldItem.setPrice(item.getPrice());
        oldItem.setUpdatedAt(new Date());

        return itemRepository.save(oldItem);
    }

    @Override
    public void delete(Long itemId) throws ItemNotFoundException {
        Optional<Item> itemOptional = itemRepository.findById(itemId);

        if (!itemOptional.isPresent()) throw new ItemNotFoundException(itemId);

        itemRepository.deleteById(itemId);
    }
}
