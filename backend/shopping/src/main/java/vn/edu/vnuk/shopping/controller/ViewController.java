package vn.edu.vnuk.shopping.controller;

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
import vn.edu.vnuk.shopping.model.Account;
import vn.edu.vnuk.shopping.model.Category;
import vn.edu.vnuk.shopping.model.Item;
import vn.edu.vnuk.shopping.model.OauthAccessToken;
import vn.edu.vnuk.shopping.repository.AccountRepository;
import vn.edu.vnuk.shopping.repository.CategoryRepository;
import vn.edu.vnuk.shopping.repository.OauthAccessTokenRepository;
import vn.edu.vnuk.shopping.service.user.CategoryService;
import vn.edu.vnuk.shopping.service.user.ItemService;
import vn.edu.vnuk.shopping.service.user.TokenService;

import java.util.ArrayList;
import java.util.Date;
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
    private ItemService itemService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    public String adminHome() {
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


}
