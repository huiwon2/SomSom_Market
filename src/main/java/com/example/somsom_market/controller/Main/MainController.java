package com.example.somsom_market.controller.Main;

import com.example.somsom_market.domain.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    private MainService mainService;

    @Autowired
    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping("/main")
    public String showHomePage(Model model) {
        List<Item> items = mainService.getAllItems();
        model.addAttribute("items", items);
        return "main";
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam("query") String query,
            @RequestParam("type") String type, Model model) {
        List<Item> items = mainService.searchItems(query, type);
        model.addAttribute("items", items);
        return "main";
    }

    @GetMapping("/category/{categoryName}")
    public String showProductsByCategory(@PathVariable("categoryName") String categoryName, Model model) {
        List<Item> items = mainService.getProductsByCategory(categoryName);
        model.addAttribute("items", items);
        return "category";
    }

    // 아이템 상세 페이지
    @GetMapping("/item/{itemId}")
    public String showItemDetail(@PathVariable("itemId") int itemId, Model model) {
        Item item = mainService.getItemById(itemId);
        if (item == null) {
            // TODO: 2023/05/04 상품이 존재하지 않을 경우 예외 처리 로직
            return "error";
        }
        model.addAttribute("item", item);
        return "itemDetail";
    }
}
