package com.example.somsom_market.controller.GroupItem;

import com.example.somsom_market.domain.GroupItem;
import com.example.somsom_market.service.GroupItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes({"groupItem", "groupItemMap"})
public class GroupItemController {
    @Autowired
    private GroupItemService groupService;
    public void setGroupItemService(GroupItemService g){
        this.groupService = g;
    }

    @ModelAttribute("groupItem")
    public GroupItemRequest formBackingObject(HttpServletRequest request){
        GroupItemRequest groupItemRequest = (GroupItemRequest) request.getSession().getAttribute("groupItem");
        if(groupItemRequest == null) {
            groupItemRequest = new GroupItemRequest();
        }
        return groupItemRequest;
    }

    @GetMapping("/group/register")
    public String showRegistForm(){
        return "groupItemRegisterForm";
    }

    @GetMapping("/user/myPage/sell/group/update")
    public String showUpdateForm(){
        return "groupItemUpdateForm";
    }

    @PostMapping("/group/register")
    public ModelAndView regist(HttpServletRequest req, HttpSession session,
                               @ModelAttribute("groupItem") GroupItemRequest comm,
                               @ModelAttribute("groupItemMap") Map<String, GroupItemRequest> map,
                               BindingResult result,
                               SessionStatus status) throws Exception{
        new RegistGroupItemFormValidator.validate(comm, result); // groupItemRequest 에 대한 오류 검증
        ModelAndView mav = new ModelAndView();
        if(result.hasErrors()){
            mav.setViewName("group/groupItemRegisterForm");
            return mav;
            //return "group/groupItemRegisterForm";
        }

        //userId from session
        int userId = (int) session.getAttribute("userId");

        // comm 데이터 처리 ( 공동 구매 아이템 생성)
        String itemId = groupService.registerNewGroupItem(comm, userId);
        mav.setViewName("/item/" + itemId); // view name : /item/{itemId}

        map.put(itemId, comm);
        mav.addObject("groupItem", comm);
        mav.addObject("groupItemMap", map);
        status.setComplete();
        return mav;
    }

    @PostMapping("/user/myPage/sell/group/update")
    public ModelAndView update(HttpServletRequest req, HttpSession session,
                               @ModelAttribute("groupItem") GroupItemRequest comm,
                               BindingResult result, SessionStatus status) throws Exception{
        ModelAndView mav = new ModelAndView();

        new UpdateGroupItemFormValidator.validate(comm, result); // comm 에 대한 오류 검증
        if(result.hasErrors()){
            mav.setViewName("group/groupItemUpdateForm");
            return mav;
        }

        //userId from session
        int userId = (int) session.getAttribute("userId");

        // comm 데이터 처리 ( 공동 구매 아이템 수정)
		String itemId = groupService.updateGroupItem(comm, userId);
        mav.setViewName("/item/" + itemId); // view name : /item/{itemId}
        mav.addObject("groupItem", comm);
        status.setComplete(); // session 종료 ("groupItem" 객체 참조가 삭제됨)
        return mav;
    }

    @RequestMapping("/user/myPage/sell/group/delete")
    public String delete(@RequestParam("itemId") String itemId,
                         @ModelAttribute("groupItemList") Map<Integer, GroupItemRequest> map) throws Exception{
        //아이템 삭제
        groupService.deleteGroupItem(itemId);
        map.remove(itemId);

        return "user/myPage/sell/groupList";
    }

    @RequestMapping("/user/myPage/sell/group/status") // 모집현황 조회
    public ModelAndView checkRecruitStatus(@RequestParam("itemId") int itemId){
        ModelAndView mav = new ModelAndView("/item/" + itemId);
        //...모집현황 조회 페이지를 따로 만드나..? 아니면 그냥 item 상세 페이지 반환?
        return mav;
    }

    @RequestMapping("/user/myPage/sell/groupList")
    public ModelAndView showMyGroupList(@RequestParam("userId")int userId){
        List<GroupItem> list = groupService.showGroupItemList(userId);
        


    }
}
