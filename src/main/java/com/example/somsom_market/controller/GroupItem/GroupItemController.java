package com.example.somsom_market.controller.GroupItem;

import com.example.somsom_market.controller.User.UserSession;
import com.example.somsom_market.domain.Account;
import com.example.somsom_market.domain.Wishlist;
import com.example.somsom_market.domain.item.GroupItem;
import com.example.somsom_market.repository.GroupItemRepository;
import com.example.somsom_market.service.GroupItemService;
import com.example.somsom_market.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
@SessionAttributes("userSession")
public class GroupItemController {
    @Autowired
    private GroupItemService groupService;
    @Autowired
    private GroupItemRepository groupItemRepository;
    @Autowired
    private WishlistService wishlistService;
    private final String REGIST_GROUP_FORM = "items/group/groupItemRegisterForm";
    private final String UPDATE_GROUP_FORM = "items/group/groupItemUpdateForm";
    private final String ITEM_NOT_FOUND = "items/group/notFound";
    private static final String LOGIN_FORM = "user/loginForm";
    private final String GROUP_ITEM_DETAIL = "items/group/groupDetail";
    private final String GROUP_LIST = "items/group/list";
    private final String MY_GROUP_LIST = "user/myPage/groupList";
    /*@ModelAttribute("groupItem")
    public GroupItemRequest formBackingObject(HttpServletRequest request){
        GroupItemRequest groupItemRequest = (GroupItemRequest) request.getSession().getAttribute("groupItem");
        if(groupItemRequest == null) {
            groupItemRequest = new GroupItemRequest();
        }
        return groupItemRequest;
    }*/

    //등록
    @GetMapping("/group/register")
    public ModelAndView showRegistForm(HttpServletRequest request){
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        ModelAndView mav = new ModelAndView();

        if (userSession != null) {
            mav.setViewName(REGIST_GROUP_FORM);
            mav.addObject("statusString", new String[] {"거래가능", "거래중", "거래완료"});
            GroupItemRequest groupItemRequest = new GroupItemRequest();
            groupItemRequest.setItemId((long) -1);
            groupItemRequest.setSalesNow(0);
            mav.addObject("groupItem", groupItemRequest);
            return mav;
        } else {
            mav.setViewName(LOGIN_FORM);
            return mav;
        }

    }

    @PostMapping("/group/register")
    public String register(HttpServletRequest req, @Valid @ModelAttribute("groupItem") GroupItemRequest comm,
                               Model model,
                               BindingResult bindingResult) throws IOException {
        if(bindingResult.hasErrors()){
            return "redirect:/items/groupItemRegister";
        }

        //userId from session
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(req, "userSession");
        Account account = userSession.getAccount();
        String userId = account.getId();

        comm.setSellerId(userId);
        comm.setStatus("INSTOCK"); // 재고 있음 으로 설정
        Calendar cal = Calendar.getInstance();
        comm.setStartDate(cal.getTime()); // 시작일 = 현재 시각으로 설정
        // comm 데이터 처리 ( 공동 구매 아이템 생성)
        GroupItem groupItem = groupService.registerNewGroupItem(comm, userId);
        long itemId = groupItem.getId();
        model.addAttribute("itemId", itemId);
        // view name : /item/{itemId};return "redirect:/group/detail/" + itemId; // 상품 상세 페이지로 이동
        return "redirect:/main";
    }

    //수정
    @GetMapping("/group/item/update")
    public ModelAndView showUpdateForm(HttpServletRequest request,
                                       @RequestParam("id") long itemId){
        ModelAndView mav = new ModelAndView();
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        if(userSession == null){
            mav.setViewName("redirect:/main");
            return mav;
        }
        GroupItem groupItem = groupService.searchItem(itemId);
        if(groupItem == null){
            mav.setViewName(ITEM_NOT_FOUND);
            return mav;
        }

        GroupItemRequest groupItemRequest = new GroupItemRequest();

        groupItemRequest.setItemId(groupItemRequest.getItemId());
        groupItemRequest.setSellerId(groupItem.getSellerId());
        groupItemRequest.setTitle(groupItem.getTitle());
        groupItemRequest.setPrice(groupItem.getPrice());
        groupItemRequest.setDescription(groupItem.getDescription());
        //groupItemRequest.setImgFile(groupItemRequest.getImgFile());
        //groupItemRequest.setImgPath(groupItemRequest.getImgPath());

        mav.setViewName(UPDATE_GROUP_FORM);
        mav.addObject("statusString", new String[] {"재고있음", "재고 주문중", "재고없음"});
        mav.addObject("groupItem", groupItemRequest);
        return mav;
    }

    @PostMapping("/group/item/update")
    public String update(HttpServletRequest request,
                               @ModelAttribute("groupItem") GroupItemRequest comm,
                               @RequestParam("id") long id,
                               BindingResult result) throws IOException {
        if(result.hasErrors()){
            return UPDATE_GROUP_FORM;
        }

        //userId from session
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        Account account = userSession.getAccount();
        String userId = account.getId();

        // comm 데이터 처리 ( 공동 구매 아이템 수정)
        GroupItem groupItem = groupService.updateGroupItem(comm, userId, id);
     //   long itemId = groupItem.getId();
       // view name : /item/{itemId}
        return "redirect:/main";
    }

    //삭제
    @RequestMapping("/group/item/delete")
    public String delete(@RequestParam("id") Long itemId){
        groupService.deleteGroupItem(itemId);
        return "redirect:/main";
    }

    //나의 리스트
   @GetMapping("/sell/myGroupItems")
    public ModelAndView showMyGroupList(HttpServletRequest req) {
        //userId from session
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(req, "userSession");
        Account account = userSession.getAccount();
        String userId = account.getId();

        List<GroupItem> list = groupService.showGroupItemList(userId);
        ModelAndView mav = new ModelAndView(MY_GROUP_LIST);
        mav.addObject("groupItemList", list);
        return mav;
    }

    // 공구 상품 목록
    @GetMapping("/group/list")
    public String showAllGroupList(HttpServletRequest req, Model model){
        List<GroupItem> list = groupService.showAllGroupItemList();
        model.addAttribute("groupItemList", list);
        return GROUP_LIST;
    }

    //수정해라
    @RequestMapping("/user/myPage/sell/group/updateStatus")
    public String changeStatusBySeller(@RequestParam("itemId") Long itemId, Model model){
        GroupItem groupItem = groupService.searchItem(itemId);
        groupService.changeStatus(groupItem);
        model.addAttribute("groupItem", groupItem);
        return GROUP_LIST;
    }

    //상세 페이지
    @GetMapping("/group/detail")
    public String showDetail(HttpServletRequest req, @RequestParam("id") long itemId, Model model){
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(req, "userSession");
        String userId;
        int isExistWish = 0;
        if(userSession != null) {
            Account account = userSession.getAccount();
            userId = account.getId();
            Wishlist wishlist = wishlistService.getGroupWishlistByAccountAndItem(userId, itemId);
            if (wishlist != null) isExistWish = 1;
        }else{
            userId="false";
        }
        model.addAttribute("userId", userId);
        GroupItem groupItem = groupService.searchItem(itemId);
        model.addAttribute("groupItem", groupItem);

        long cnt = groupService.getOrderCnt(itemId);
        model.addAttribute("cnt", cnt);

        GroupItem tmp = groupService.searchItem(itemId);
        long timeleft = getDateDiff(tmp.getStartDate(), tmp.getEndDate(), TimeUnit.DAYS);
        model.addAttribute("timeleft",timeleft);

        model.addAttribute("isExistWish", isExistWish);
        return GROUP_ITEM_DETAIL;
    }
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
}
