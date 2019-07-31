package vn.edu.vnuk.shopping.serviceImpl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import vn.edu.vnuk.shopping.model.Item;
import vn.edu.vnuk.shopping.model.OrderHasItem;
import vn.edu.vnuk.shopping.repository.ItemRepository;
import vn.edu.vnuk.shopping.repository.OrderHasItemRepository;
import vn.edu.vnuk.shopping.service.user.MailClient;
import vn.edu.vnuk.shopping.service.user.OrderHasItemService;

import java.util.Date;
import java.util.List;

@Service
public class OrderHasItemServiceImpl implements OrderHasItemService {

    @Autowired
    private OrderHasItemRepository orderHasItemRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private MailClient mailClient;

    @Override
    @Async
    public void create(List<OrderHasItem> orderHasItemList) {
        for (OrderHasItem orderHasItem : orderHasItemList) {
            Item item = itemRepository.findById(orderHasItem.getItemId()).get();
            item.setAmount(item.getAmount() - orderHasItem.getQuantity());
            itemRepository.save(item);

            orderHasItem.setCreatedAt(new Date());
            orderHasItem.setUpdatedAt(new Date());
            orderHasItemRepository.save(orderHasItem);

            orderHasItem.setItem(item);
        }

        Context context = new Context();
        context.setVariable("orderHasItems", orderHasItemList);
        String message = templateEngine.process("mailItemsTemplate", context);

        mailClient.prepareAndSend("ngoc.bui150019@vnuk.edu.vn", "Item - 1", message);
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
