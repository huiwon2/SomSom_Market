package com.example.somsom_market.controller.GroupItem;

import com.example.somsom_market.domain.GroupItem;
import com.example.somsom_market.repository.GroupItemRepository;
import com.example.somsom_market.service.GroupItemService;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("groupItem")
public class GroupItemController {
    @Autowired
    private GroupItemService groupService;
    @Autowired
    private GroupItemRepository groupItemRepository;

    private final String GroupForm = "/groupItemRegisterForm";
    private final String UpdateGroupForm = "/groupItemUpdateForm";
    private final String Mypage = "/user/myPage";
    private final String MyGroupItems = "/sell/groupList";
    private final String All = "/group";
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
        return GroupForm;
    }

    @GetMapping("/group/update")
    public String showUpdateForm(){
        return GroupForm;
    }

    @PostMapping("/group/register")
    public ModelAndView regist(HttpServletRequest req, HttpSession session,
                               @ModelAttribute("groupItem") GroupItemRequest comm,
                               BindingResult result,
                               SessionStatus status) throws Exception{
        //new RegistGroupItemFormValidator.validate(comm, result); // groupItemRequest 에 대한 오류 검증
        ModelAndView mav = new ModelAndView();
        if(result.hasErrors()){
            mav.setViewName(GroupForm);
            return mav;
            //return "group/groupItemRegisterForm";
        }

        //userId from session
        String userId = (String) session.getAttribute("userId");

        // comm 데이터 처리 ( 공동 구매 아이템 생성)
        Long itemId = groupService.registerNewGroupItem(comm, userId);
        mav.setViewName("/item/" + itemId); // view name : /item/{itemId}
        //map.put(String.valueOf(itemId), comm);
        comm.setSellerId(userId);
        comm.setStatus("INSTOCK");
        Calendar cal = Calendar.getInstance();
        comm.setStartDate(cal.getTime());
        mav.addObject("groupItem", comm);
        //mav.addObject("groupItemMap", map);
        status.setComplete();
        return mav;
    }

    @PostMapping("/group/update")
    public ModelAndView update(HttpServletRequest req, HttpSession session,
                               @ModelAttribute("groupItem") GroupItemRequest comm,
                               BindingResult result, SessionStatus status) throws Exception{
        ModelAndView mav = new ModelAndView();

        //new UpdateGroupItemFormValidator.validate(comm, result); // comm 에 대한 오류 검증
        if(result.hasErrors()){
            mav.setViewName(UpdateGroupForm);
            return mav;
        }

        //userId from session
        String userId = (String)session.getAttribute("userId");

        // comm 데이터 처리 ( 공동 구매 아이템 수정)
        Long itemId = groupService.updateGroupItem(comm, userId);
        mav.setViewName("/item/" + itemId); // view name : /item/{itemId}
        mav.addObject("groupItem", comm);
        status.setComplete(); // session 종료 ("groupItem" 객체 참조가 삭제됨)
        return mav;
    }

    @RequestMapping("/group/delete")
    public String delete(@RequestParam("itemId") Long itemId) throws Exception{
        //아이템 삭제
        groupService.deleteGroupItem(itemId);
        //map.remove(itemId);

        return MyGroupItems;
    }

    @RequestMapping("/group/status") // 모집현황 조회
    public ModelAndView checkRecruitStatus(@RequestParam("itemId") Long itemId){
        ModelAndView mav = new ModelAndView("/item/" + itemId);
        //...모집현황 조회 페이지를 따로 만드나..? 아니면 그냥 item 상세 페이지 반환?
        return mav;
    }

    @RequestMapping("/groupList")
    public ModelAndView showMyGroupList(@RequestParam("userId")String userId) {
        List<GroupItem> list = groupService.showGroupItemList(userId);
        ModelAndView mav = new ModelAndView(MyGroupItems);
        mav.addObject("myGroupItemList", list);
        return mav;
    }

}
