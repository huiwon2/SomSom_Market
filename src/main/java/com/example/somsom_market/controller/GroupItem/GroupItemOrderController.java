package com.example.somsom_market.controller.GroupItem;

import com.example.somsom_market.domain.item.GroupItem;
import com.example.somsom_market.service.GroupItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("userSession")
public class GroupItemOrderController {
    @Autowired
    private GroupItemService groupService;
    @RequestMapping("")
    public String changeStatusBySeller(@RequestParam("itemId") Long itemId, Model model){
        GroupItem groupItem = groupService.searchItem(itemId);
        groupService.changeStatus(groupItem);
        model.addAttribute("groupItem", groupItem);
        return "";
    }
}
