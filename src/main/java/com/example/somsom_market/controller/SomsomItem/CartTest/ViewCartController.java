package com.example.somsom_market.controller.SomsomItem.CartTest;

import com.example.somsom_market.controller.User.UserSession;
import com.example.somsom_market.domain.CartTest.CartTest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes({"sessionCart", "userSession"})
public class ViewCartController { 
	
	@ModelAttribute("sessionCart")
	public CartTest createCart(HttpSession session) {
		CartTest cart = (CartTest)session.getAttribute("sessionCart");
		if (cart == null) cart = new CartTest();
		return cart;
	}
	
	@RequestMapping("/cart")
	public ModelAndView viewCart(
			HttpServletRequest request,
			@RequestParam(value="page", required=false) String page,
			@ModelAttribute("sessionCart") CartTest cart)
			throws Exception {
		UserSession userSession =
			(UserSession) WebUtils.getSessionAttribute(request, "userSession");
		handleRequest(page, cart, userSession);
		return new ModelAndView("items/somsom/cart/cart", "cart", cart);
	}
	
	private void handleRequest(String page, CartTest cart, UserSession userSession)
			throws Exception {
		if (userSession != null) {
			if ("next".equals(page)) {
				userSession.getMySomsomList().nextPage();
			}
			else if ("previous".equals(page)) {
				userSession.getMySomsomList().previousPage();
			}
		}
		if ("nextCart".equals(page)) {
			cart.getCartItemList().nextPage();
		}
		else if ("previousCart".equals(page)) {
			cart.getCartItemList().previousPage();
		}
	}
}
