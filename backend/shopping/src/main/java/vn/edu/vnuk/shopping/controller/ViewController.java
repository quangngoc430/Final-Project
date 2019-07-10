package vn.edu.vnuk.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import vn.edu.vnuk.shopping.exception.category.CategoryNotFoundException;
import vn.edu.vnuk.shopping.exception.item.ItemNotFoundException;
import vn.edu.vnuk.shopping.model.Category;
import vn.edu.vnuk.shopping.model.Item;
import vn.edu.vnuk.shopping.service.user.CategoryService;
import vn.edu.vnuk.shopping.service.user.ItemService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ViewController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ItemService itemService;

    @GetMapping(value = {"/", "/home"})
    public String home(Model model) throws CategoryNotFoundException {
        Page<Category> categoryPage = categoryService.getAll("", new PageRequest(0, 20));
        List<Category> categories = categoryPage.getContent();

        model.addAttribute("countOfCategories", categories.size());

        List<List<Item>> itemsList = new ArrayList<>();
        int index = 0;
        List<Category> categoryList = new ArrayList<>();

        for (Category category : categories) {
            List<Item> items = itemService.getAll(category.getId(), new PageRequest(0, 4)).getContent();

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

    @GetMapping(value = "/category/{id}")
    public String category(@PathVariable(value = "id") long id) {
        return "category";
    }

    @GetMapping(value = "item/{id}")
    public String item(@PathVariable(value = "id") long id,
                       Model model) throws ItemNotFoundException {
        Item item = itemService.getOne(id);

        model.addAttribute("item", item);
        return "item";
    }
}
