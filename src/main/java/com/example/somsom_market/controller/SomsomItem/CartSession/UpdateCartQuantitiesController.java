package com.example.somsom_market.controller.SomsomItem.CartSession;

import com.example.somsom_market.domain.CartSession.CartItemSession;
import com.example.somsom_market.domain.CartSession.CartSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;

/**
 * @author Juergen Hoeller
 * @since 30.11.2003
 * @modified-by Changsup Park
 */
@Controller
@SessionAttributes({"sessionCart", "orderForm", "userSession"})
public class UpdateCartQuantitiesController { 

	@RequestMapping("/cart/updateCartQuantities")
	public ModelAndView handleRequest(
			HttpServletRequest request,	
			@ModelAttribute("sessionCart") CartSession cart) throws Exception {
		Iterator<CartItemSession> cartItems = cart.getAllCartItems();
		while (cartItems.hasNext()) {
			CartItemSession cartItem = (CartItemSession) cartItems.next();
			Long itemId = cartItem.getItem().getId();
			try {
				int quantity = Integer.parseInt(request.getParameter(itemId.toString()));
				cart.setQuantityByItemId(itemId, quantity);
				if (quantity < 1) {
					cartItems.remove();
				}
			}
			catch (NumberFormatException ex) {
				// ignore on purpose
			}
		}
		return new ModelAndView("items/somsom/cart/cart", "cart", cart);
	}

}
