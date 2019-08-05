package vn.edu.vnuk.shopping.serviceImpl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import vn.edu.vnuk.shopping.model.Item;
import vn.edu.vnuk.shopping.model.OrderAddress;
import vn.edu.vnuk.shopping.model.OrderHasItem;
import vn.edu.vnuk.shopping.model.Ordering;
import vn.edu.vnuk.shopping.repository.ItemRepository;
import vn.edu.vnuk.shopping.repository.OrderAddressRepository;
import vn.edu.vnuk.shopping.repository.OrderHasItemRepository;
import vn.edu.vnuk.shopping.repository.OrderRepository;
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

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderAddressRepository orderAddressRepository;

    @Value("${image.host}")
    private String imageHost;

    @Override
    @Async
    public void create(List<OrderHasItem> orderHasItemList) {
        long total = 0;

        for (OrderHasItem orderHasItem : orderHasItemList) {
            Item item = itemRepository.findById(orderHasItem.getItemId()).get();
            item.setAmount(item.getAmount() - orderHasItem.getQuantity());
            itemRepository.save(item);

            orderHasItem.setPrice(item.getPrice());
            orderHasItem.setCreatedAt(new Date());
            orderHasItem.setUpdatedAt(new Date());
            orderHasItemRepository.save(orderHasItem);

            orderHasItem.setItem(item);

            total = total + orderHasItem.getQuantity() * orderHasItem.getPrice();
        }

        Ordering order = orderRepository.getById(orderHasItemList.get(0).getOrderId());
        OrderAddress orderAddress = orderAddressRepository.findById(order.getOrderAddressId()).get();

        Context context = new Context();
        context.setVariable("host", "http://localhost:8080");
        context.setVariable("orderHasItems", orderHasItemList);
        context.setVariable("orderAddress", orderAddress);
        context.setVariable("total", total);
        context.setVariable("imageHost", imageHost);
        String message = templateEngine.process("mailItemsTemplate", context);

        mailClient.prepareAndSend(orderAddress.getEmail(), "Order - " + order.getId(), message);
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
