package com.example.somsom_market.controller.Review;

import com.example.somsom_market.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ReviewController {
    private ReviewService reviewService;

    @Autowired
    public void setReviewService(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    private final String ReviewForm = "/reviewForm";
    private final String Registered = "/review/registered";
    private final String Mypage = "user/myPage";
    //ReviewDao reviewDao = new ReviewDao();
    @ModelAttribute("reviewItem")
    public ReviewRequest formBacking(HttpServletRequest request){
        ReviewRequest reviewRequest = (ReviewRequest) request.getSession().getAttribute("reviewItem");
        if(reviewRequest == null){
            reviewRequest = new ReviewRequest();
        }
        return reviewRequest;
    }
    @GetMapping("review/create")
    public String showReviewForm(){
        return ReviewForm;
    }
    @PostMapping("/review/register")
    public String reviewRegister(@ModelAttribute("reviewItem") ReviewRequest reviewRequest, BindingResult result, Model model, SessionStatus status){
        new ReviewFormValidator.validate(reviewRequest, result);
        if(result.hasErrors()){
            return ReviewForm;
        }
        long reviewId = reviewService.registerNewReview(reviewRequest);
        model.addAttribute("reviewId", reviewId);
        status.setComplete();
        return Registered;
    }
    @RequestMapping("/review/delete")
    public String reviewDelete(@RequestParam("reviewId") long reviewId){
        reviewService.deleteReview(reviewId);
        return Mypage;
    }

}
