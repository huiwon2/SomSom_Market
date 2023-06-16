package com.example.somsom_market.controller.GroupItem;

import com.example.somsom_market.controller.User.UserSession;
import com.example.somsom_market.domain.Account;
import com.example.somsom_market.domain.item.GroupItem;
import com.example.somsom_market.repository.GroupItemRepository;
import com.example.somsom_market.service.GroupItemService;
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
import java.util.Calendar;
import java.util.List;

@Controller
@SessionAttributes("userSession")
public class GroupItemController {
    @Autowired
    private GroupItemService groupService;
    @Autowired
    private GroupItemRepository groupItemRepository;

    private final String REGIST_GROUP_FORM = "items/group/groupItemRegisterForm";
    private final String UPDATE_GROUP_FORM = "items/group/groupItemUpdateForm";
    private static final String LOGIN_FORM = "user/loginForm";
    private final String MY_GROUP_LIST = "/group/list"; // 수정해라
    @ModelAttribute("groupItem")
    public GroupItemRequest formBackingObject(HttpServletRequest request){
        GroupItemRequest groupItemRequest = (GroupItemRequest) request.getSession().getAttribute("groupItem");
        if(groupItemRequest == null) {
            groupItemRequest = new GroupItemRequest();
        }
        return groupItemRequest;
    }

    @GetMapping("/group/register")
    public ModelAndView showRegistForm(HttpServletRequest request){
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        ModelAndView mav = new ModelAndView();

        if (userSession != null) {
            mav.setViewName(REGIST_GROUP_FORM);
            mav.addObject("statusString", new String[] {"거래가능", "거래중", "거래완료"});
            GroupItemRequest groupItemRequest = new GroupItemRequest();
            groupItemRequest.setItemId((long) -1);
            mav.addObject("groupItem", groupItemRequest);
            return mav;
        } else {
            mav.setViewName(LOGIN_FORM);
            return mav;
        }

    }

    @PostMapping("/group/register")
    public String regist(HttpServletRequest req, @ModelAttribute("groupItem") GroupItemRequest comm,
                               BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return REGIST_GROUP_FORM; // 입력값 검증 추가해라
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

        // view name : /item/{itemId}
        return "redirect:/group/detail/" + itemId; // 상품 상세 페이지로 이동
    }

    @GetMapping("/user/myPage/sell/group/update")
    public ModelAndView showUpdateForm(HttpServletRequest request,
                                       @RequestParam("itemId") long itemId){
        ModelAndView mav = new ModelAndView();

        GroupItem groupItem = groupService.searchItem(itemId);

        GroupItemRequest groupItemRequest = new GroupItemRequest();

        groupItemRequest.setItemId(groupItemRequest.getItemId());
        groupItemRequest.setSellerId(groupItem.getSellerId());
        groupItemRequest.setTitle(groupItem.getTitle());
        groupItemRequest.setPrice(groupItem.getPrice());
        groupItemRequest.setDescription(groupItem.getDescription());
        if (groupItem.getStatus().toString().equals("INSTOCK")) {
            groupItemRequest.setStatus("거래가능");
        } else if (groupItem.getStatus().toString().equals("ING")) {
            groupItemRequest.setStatus("거래중");
        } else {
            groupItemRequest.setStatus("거래완료");
        }

        mav.setViewName(UPDATE_GROUP_FORM);
        mav.addObject("statusString", new String[] {"거래가능", "거래중", "거래완료"});
        mav.addObject("groupItem", groupItemRequest);

        return mav;
    }

    @PostMapping("/user/myPage/sell/group/update")
    public String update(HttpServletRequest request,
                               @ModelAttribute("groupItem") GroupItemRequest comm,
                               BindingResult result){
        //검증 추가해라
        if(result.hasErrors()){
            return UPDATE_GROUP_FORM;
        }

        //userId from session
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        Account account = userSession.getAccount();
        String userId = account.getId();

        // comm 데이터 처리 ( 공동 구매 아이템 수정)
        GroupItem groupItem = groupService.updateGroupItem(comm, userId);
        long itemId = groupItem.getId();
       // view name : /item/{itemId}
        return "redirect:/group/detail/" + itemId;
    }

    @RequestMapping("/user/myPage/sell/group/delete")
    public String delete(@RequestParam("itemId") Long itemId){
        groupService.deleteGroupItem(itemId);
        return "redirect:" + MY_GROUP_LIST;
    }

    @GetMapping("/group/list") // 수정해라
    public ModelAndView showMyGroupList(HttpServletRequest req) {
        //userId from session
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(req, "userSession");
        Account account = userSession.getAccount();
        String userId = account.getId();

        List<GroupItem> list = groupService.showGroupItemList(userId);
        ModelAndView mav = new ModelAndView(MY_GROUP_LIST);
        mav.addObject("myGroupItemList", list);
        return mav;
    }

    //수정해라
    @RequestMapping("/user/myPage/sell/group/updateStatus")
    public String changeStatusBySeller(@RequestParam("itemId") Long itemId, Model model){
        GroupItem groupItem = groupService.searchItem(itemId);
        groupService.changeStatus(groupItem);
        model.addAttribute("groupItem", groupItem);
        return MY_GROUP_LIST;
    }
}
