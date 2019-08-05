package vn.edu.vnuk.shopping.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.vnuk.shopping.define.Define;
import vn.edu.vnuk.shopping.exception.category.CategoryNotFoundException;
import vn.edu.vnuk.shopping.exception.item.ItemNotFoundException;
import vn.edu.vnuk.shopping.exception.token.TokenIsExpiredException;
import vn.edu.vnuk.shopping.exception.token.TokenNotFoundException;
import vn.edu.vnuk.shopping.model.*;
import vn.edu.vnuk.shopping.repository.*;
import vn.edu.vnuk.shopping.service.user.CategoryService;
import vn.edu.vnuk.shopping.service.user.ItemService;
import vn.edu.vnuk.shopping.service.user.TokenService;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Controller
public class ViewController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OrderHasItemRepository orderHasItemRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @GetMapping(value = {"/", "/home"})
    public String home(Model model) throws CategoryNotFoundException {
        Page<Category> categoryPage = categoryService.getAll("", new PageRequest(0, 20));
        List<Category> categories = categoryPage.getContent();

        model.addAttribute("countOfCategories", categories.size());

        List<List<Item>> itemsList = new ArrayList<>();
        int index = 0;
        List<Category> categoryList = new ArrayList<>();

        for (Category category : categories) {
            List<Item> items = itemService.getAll("", category.getId(), new PageRequest(0, 4)).getContent();

            if (items.size() > 0) {
                itemsList.add(items);
                categoryList.add(category);
                index++;
            }
        }

        model.addAttribute("itemsList", itemsList);
        model.addAttribute("categories", categoryList);

        return "home";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "signin";
    }

    @GetMapping(value = "/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping(value = "/cart")
    public String cart() {
        return "cart";
    }

    @GetMapping(value = "/order/{id}")
    public String order(@PathVariable(value = "id") Long id,
                        Model model) {
        model.addAttribute("id", id);
        return "order";
    }

    @GetMapping(value = "/category/{id}")
    public String category(@PathVariable(value = "id") long id) {
        return "category";
    }

    @GetMapping(value = "/item/{id}")
    public String item(@PathVariable(value = "id") long id,
                       Model model) throws ItemNotFoundException {
        Item item = itemService.getOne(id);

        model.addAttribute("item", item);
        return "item";
    }

    @GetMapping(value = "/items")
    public String items(Model model) {
        model.addAttribute("categories", categoryService.getAll("", new PageRequest(0, 20)));
        return "items";
    }

    @GetMapping(value = "/items-bill-info")
    public String itemsBillInfo() {
        return "bill-user-info";
    }

    @GetMapping(value = "/confirmation")
    public String confirmation() {
        return "confirmation";
    }

    @GetMapping(value = "/account/forgot-password")
    public String forgotPassword() {
        return "forgot-password";
    }

    @PostMapping(value = "/api/reset-password", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> resetPassword(@RequestParam("token") String token,
                                           @RequestBody Account account) throws TokenIsExpiredException, TokenNotFoundException {
        OauthAccessToken accessToken = tokenService.get(token);

        Account oldAccount = accessToken.getAccount();
        oldAccount.setPassword(passwordEncoder.encode(account.getPassword()));
        accountRepository.save(oldAccount);

        tokenService.delete(token);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/account/reset-password")
    public String resetPassword() {
        return "reset-password";
    }

    @GetMapping(value = "/account")
    public String account() {
        return "account";
    }


    @GetMapping(value = "/account/{id}/activate")
    public String accountActive(@PathVariable("id") Long id,
                                @RequestParam("token") String accessToken,
                                Model model) {
        boolean isSuccess;
        Long status;
        try {
            OauthAccessToken oauthAccessToken = tokenService.get(accessToken);
            isSuccess = true;
            status = Define.STATUS_ACTIVATE_SUCCESS;
            Account account = accountRepository.findById(oauthAccessToken.getAccountId()).get();
            account.setStatus(Define.STATUS_ACTIVATE_SUCCESS);
            account.setUpdatedAt(new Date());
            accountRepository.save(account);

            model.addAttribute("fullname", account.getFullname());

            tokenService.delete(accessToken);
        } catch (TokenNotFoundException e) {
            isSuccess = false;
            status = Define.STATUS_ACTIVATE_TOKEN_NOT_FOUND;
            e.printStackTrace();
        } catch (TokenIsExpiredException e) {
            isSuccess = false;
            status = Define.STATUS_ACTIVATE_TOKEN_IS_EXPIRED;
            e.printStackTrace();
        }

        model.addAttribute("isSuccess", isSuccess);
        model.addAttribute("status", status);

        return "activate";
    }

    @GetMapping(value = "/admin/login")
    public String adminLogin() {
        return "admin/signin";
    }

    @GetMapping(value = "/admin")
    public String adminHome(Model model) {
        Long profit = 0L;
        Long usersCount = accountRepository.count();
        Long ordersCount = orderRepository.count();
        Long feedbacksCount = ratingRepository.count();
        Long todayMoney = 0L;
        Long monthMoney = 0L;
        Long yearMoney = 0L;
        Long[] ratings = {0L, 0L, 0L, 0L, 0L};
        Long[] profits = {0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L};
        Long[] accounts = {0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L};
        Long todayOrders = 0L;
        Long monthOrders = 0L;
        Long yearOrders = 0L;
        Long[] orders = {0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L};

        Date now = new Date();

        // orders
        Iterator<Ordering> orderingIterator = orderRepository.findAll().iterator();

        while (orderingIterator.hasNext()) {
            Ordering ordering = orderingIterator.next();

            Date time = ordering.getUpdatedAt();
            if (now.getYear() == now.getYear()) {
                yearOrders += 1;
                if (now.getMonth() == time.getMonth()) {
                    monthOrders += 1;
                    if (now.getDay() == time.getDay()) {
                        todayOrders += 1;
                    }
                }
            }

            orders[ordering.getCreatedAt().getMonth()] += 1;
        }


        // orders has items
        Iterator<OrderHasItem> orderHasItemIterator = orderHasItemRepository.findAll().iterator();



        while (orderHasItemIterator.hasNext()) {
            OrderHasItem orderHasItem = orderHasItemIterator.next();
            Date time = orderHasItem.getUpdatedAt();
            if (now.getYear() == now.getYear()) {
                yearMoney = yearMoney + orderHasItem.getQuantity() * orderHasItem.getPrice();
                if (now.getMonth() == time.getMonth()) {
                    monthMoney = monthMoney + orderHasItem.getQuantity() * orderHasItem.getPrice();
                    if (now.getDay() == time.getDay()) {
                        todayMoney = todayMoney + orderHasItem.getQuantity() * orderHasItem.getPrice();
                    }
                }
            }

            profit = profit + orderHasItem.getQuantity() * orderHasItem.getPrice();

            profits[orderHasItem.getUpdatedAt().getMonth()] += orderHasItem.getQuantity() * orderHasItem.getPrice();
        }

        // users
        Iterator<Account> accountIterator = accountRepository.findAll().iterator();
        while (accountIterator.hasNext()) {
            Account account = accountIterator.next();
            accounts[account.getCreatedAt().getMonth()]++;
        }

        // feedbacks
        Iterator<Rating> feedbackIterator = ratingRepository.findAll().iterator();

        while (feedbackIterator.hasNext()) {
            Rating rating = feedbackIterator.next();
            ratings[Integer.parseInt(String.valueOf(rating.getValue())) - 1] = ratings[Integer.parseInt(String.valueOf(rating.getValue())) - 1] + 1;
        }

        model.addAttribute("profit", profit);
        model.addAttribute("usersCount", usersCount);
        model.addAttribute("ordersCount", ordersCount);
        model.addAttribute("feedbacksCount", feedbacksCount);

        model.addAttribute("today", todayMoney);
        model.addAttribute("month", monthMoney);
        model.addAttribute("year", yearMoney);

        model.addAttribute("ordersToday", todayOrders);
        model.addAttribute("ordersMonth", monthOrders);
        model.addAttribute("ordersYear", yearOrders);

        model.addAttribute("ratings", ratings);
        model.addAttribute("profits", profits);
        model.addAttribute("accounts", accounts);
        model.addAttribute("orders", orders);
        return "admin/home";
    }

    @GetMapping(value = "/admin/item/new")
    public String adminItemNew(Model model) {
        model.addAttribute("categories", categoryService.getAll("", PageRequest.of(0, 100)).getContent());
        return "admin/item";
    }

    @GetMapping(value = "/admin/item/{id}")
    public String adminItem(@PathVariable("id") Long id,
                            Model model) {
        model.addAttribute("categories", categoryService.getAll("", PageRequest.of(0, 100)).getContent());
        model.addAttribute("id", id);
        return "admin/item";
    }

    @GetMapping(value = "/admin/items")
    public String adminItems() {
        return "admin/items";
    }

    @GetMapping(value = "/admin/account/{id}")
    public String adminAccount(@PathVariable("id") Long id, Model model) {
        model.addAttribute("id", id);
        return "admin/account";
    }

    @GetMapping(value = "/admin/accounts")
    public String adminAccounts() {
        return "admin/accounts";
    }

    @GetMapping(value = "/admin/orders")
    public String adminOrders(Model model) {


        return "admin/orders";
    }

    @GetMapping(value = "/admin/order/{id}")
    public String adminSingleOrder(@PathVariable(name = "id") Long id,
                                   Model model) {
        model.addAttribute("id", id);
        return "admin/order";
    }

}
