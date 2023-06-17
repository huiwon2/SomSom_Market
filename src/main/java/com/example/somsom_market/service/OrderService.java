package com.example.somsom_market.service;

import com.example.somsom_market.dao.AccountDao;
import com.example.somsom_market.dao.OrderDao;
import com.example.somsom_market.dao.SomsomItemDao;
import com.example.somsom_market.domain.*;
import com.example.somsom_market.domain.item.SomsomItem;
import com.example.somsom_market.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderDao orderDao;
    private final AccountDao accountDao;
    private final SomsomItemDao somsomItemDao;
    private AccountService accountService;
    private SomsomItem item;

    /**
     * 주문
     */
    @Transactional
    public Long order(String memberId, Long itemId, int count) {

        //엔티티 조회
        Account account = accountDao.findOne(memberId);
        SomsomItem item = somsomItemDao.findOne(itemId);

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(account, orderItem);

        //주문 저장
        orderDao.save(order);

        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Order order = orderDao.findOne(orderId);
        //주문 취소
        order.cancel();
    }

    //검색
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderDao.findAllByString(orderSearch);
    }

    //장바구니
//    @Transactional
//    public OrderItem addCartOrder(int itemId, String accountId, CartItem cartItem) {
//
//        Account account = accountService.getAccount(accountId);
//    }
}
