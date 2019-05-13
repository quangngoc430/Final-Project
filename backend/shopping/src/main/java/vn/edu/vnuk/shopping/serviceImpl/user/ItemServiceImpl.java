package vn.edu.vnuk.shopping.serviceImpl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.vnuk.shopping.exception.category.CategoryNotFoundException;
import vn.edu.vnuk.shopping.exception.item.ItemNotFoundException;
import vn.edu.vnuk.shopping.model.Category;
import vn.edu.vnuk.shopping.model.Item;
import vn.edu.vnuk.shopping.model.Rating;
import vn.edu.vnuk.shopping.repository.CategoryRepository;
import vn.edu.vnuk.shopping.repository.ItemRepository;
import vn.edu.vnuk.shopping.repository.RatingRepository;
import vn.edu.vnuk.shopping.service.user.ItemService;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public Page<Item> getAll(Pageable pageable) {
        return itemRepository.findAllBy(pageable);
    }

    @Override
    public Page<Item> getAll(Long categoryId, Pageable pageable) throws CategoryNotFoundException {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

        if (!categoryOptional.isPresent()) throw new CategoryNotFoundException(categoryId);

        return itemRepository.findAllByCategoryId(categoryId, pageable);
    }

    @Override
    public Item getOne(Long itemId) throws ItemNotFoundException {
        Optional<Item> itemOptional = itemRepository.findById(itemId);

        if (!itemOptional.isPresent()) throw new ItemNotFoundException(itemId);

        List<Rating> ratingList = ratingRepository.findAllByItemId(itemId);

        Item item = itemOptional.get();

        Float itemRating = 0.0f;
        for (Rating rating : ratingList) {
            itemRating += rating.getValue();
        }

        itemRating = itemRating / ratingList.size();

        item.setRating(itemRating);

        return item;
    }


}
