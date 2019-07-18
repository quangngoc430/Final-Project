package vn.edu.vnuk.shopping.serviceImpl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.vnuk.shopping.model.Item;
import vn.edu.vnuk.shopping.model.OrderHasItem;
import vn.edu.vnuk.shopping.repository.ItemRepository;
import vn.edu.vnuk.shopping.repository.OrderHasItemRepository;
import vn.edu.vnuk.shopping.service.user.OrderHasItemService;

import java.util.Date;
import java.util.List;

@Service
public class OrderHasItemServiceImpl implements OrderHasItemService {

    @Autowired
    private OrderHasItemRepository orderHasItemRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public void create(List<OrderHasItem> orderHasItemList) {
        for (OrderHasItem orderHasItem : orderHasItemList) {
            Item item = itemRepository.findById(orderHasItem.getItemId()).get();
            item.setAmount(item.getAmount() - orderHasItem.getQuantity());
            itemRepository.save(item);

            orderHasItem.setCreatedAt(new Date());
            orderHasItem.setUpdatedAt(new Date());
            orderHasItemRepository.save(orderHasItem);
        }
    }

    @Override
    public Page<OrderHasItem> getAllByOrderId(Long orderId, Pageable pageable) {
        return orderHasItemRepository.getAllByOrderId(orderId, pageable);
    }

    @Override
    public Page<OrderHasItem> getAllBy(Pageable pageable) {
        return orderHasItemRepository.getAllBy(pageable);
    }

}
