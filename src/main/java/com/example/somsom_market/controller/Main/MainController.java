package com.example.somsom_market.controller.Main;

import com.example.somsom_market.domain.item.Item;
import com.example.somsom_market.service.MainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@SessionAttributes("userSession")
public class MainController {
    @Autowired
    private MainService mainService;

    @Autowired
    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping("/main")
    public String getMainItems(Model model) {
        List<Item> items = mainService.getMainItems();
        model.addAttribute("items", items);
        return "home";
    }

//    @GetMapping("/main/items")
//    public String getAllItems(Model model) {
//        List<Item> items = mainService.getAllItems();
//        model.addAttribute("items", items);
//        return "items/somsom/somsomItemList";
//    }

    @GetMapping("/search")
    public String searchItems(@RequestParam("query") String query,
                              @RequestParam("type") String type, Model model) {
        List<Item> items = mainService.searchItemByQuery(query, type);
        model.addAttribute("items", items);
        return "home";
    }

    @GetMapping("/category")
    public String showItemsByCategory(@PathVariable("categoryName") String categoryName, Model model) {
        List<Item> items = mainService.searchItemByQuery(null, categoryName);
        model.addAttribute("items", items);
        return "home";
    }

    // 아이템 상세 페이지
    @GetMapping("/item/{itemId}")
    public String showItemDetail(@PathVariable("itemId") Long itemId, Model model) {
        Item item = mainService.getItemDetail(itemId);
        if (item == null) {
            // TODO: 2023/05/04 상품이 존재하지 않을 경우 예외 처리 로직
            return "error";
        }
        model.addAttribute("item", item);
        return "itemDetail";
    }
}
