package com.example.somsom_market.controller.Review;

import com.example.somsom_market.controller.GroupItem.GroupItemRequest;
import com.example.somsom_market.controller.User.UserSession;
import com.example.somsom_market.domain.Account;
import com.example.somsom_market.domain.Review;
import com.example.somsom_market.service.ReviewService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@SessionAttributes("userSession")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    private final String REVIEW_FORM = "review/reviewForm";
    private final String MY_REVIEW_LIST = ""; // 나의 리뷰들
    private final String REVIEW_LIST = ""; // 상품 페이지에 들어갈 리뷰 리스트
    private static final String LOGIN_FORM = "user/loginForm";

    @ModelAttribute("reviewItem")
    public ReviewRequest formBacking(HttpServletRequest request) {
        ReviewRequest reviewRequest = (ReviewRequest) request.getSession().getAttribute("reviewItem");
        if (reviewRequest == null) {
            reviewRequest = new ReviewRequest();
        }
        return reviewRequest;
    }

    @GetMapping("/user/myPage/order/somsom/review/register")
    public String showReviewForm(HttpServletRequest request) {
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");

        if (userSession != null) {
            return REVIEW_FORM;
        } else {
            return LOGIN_FORM;
        }
    }

    @PostMapping("/user/myPage/order/somsom/review/register")
    public String register(HttpServletRequest request,
                           @ModelAttribute("reviewItem") ReviewRequest reviewRequest,
                           BindingResult result) {
        if (result.hasErrors()) {
            return REVIEW_FORM;
        }
        //userId from session
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        Account account = userSession.getAccount();
        String userId = account.getId();

        reviewRequest.setUserId(userId);
        long reviewId = reviewService.registerNewReview(reviewRequest);
        return MY_REVIEW_LIST;
    }

    @RequestMapping("/user/myPage/order/somsom/review/delete")
    public String reviewDelete(@RequestParam("reviewId") long reviewId) {
        reviewService.deleteReview(reviewId);
        return MY_REVIEW_LIST;
    }

    @RequestMapping("/user/myPage/order/somsom/review/update")
    public String reviewUpdate(@ModelAttribute("reviewItem") ReviewRequest reviewRequest,
                               BindingResult result) {
        //new ReviewFormValidator.validate(reviewRequest, result);
        if (result.hasErrors()) {
            return REVIEW_FORM;
        }
        long reviewId = reviewService.updateReview(reviewRequest);
        return MY_REVIEW_LIST;
    }

    @GetMapping("/review/list") // 수정요망
    public ModelAndView showMyReviewList(HttpServletRequest request) {
        //userId from session
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        Account account = userSession.getAccount();
        String userId = account.getId();

        List<Review> reviews = reviewService.findByUserId(userId);
        ModelAndView mav = new ModelAndView();
        mav.addObject("reviewList", reviews);
        mav.setViewName(MY_REVIEW_LIST);
        return mav;
    }

    @GetMapping("review/review") // 수정요망
    public ModelAndView showReviewListOfItem(HttpServletRequest request,
                                             @ModelAttribute("itemId") long itemId) {

        List<Review> reviews = reviewService.findByItemId(itemId);
        ModelAndView mav = new ModelAndView();
        mav.addObject("reviewList", reviews);
       // mav.setViewName();
        return mav;
    }

}
