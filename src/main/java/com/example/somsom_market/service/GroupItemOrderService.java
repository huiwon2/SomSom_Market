package com.example.somsom_market.service;

import com.example.somsom_market.dao.AccountDao;
import com.example.somsom_market.dao.GroupItemDao;
import com.example.somsom_market.dao.OrderDao;
import com.example.somsom_market.dao.SomsomItemDao;
import com.example.somsom_market.domain.Account;
import com.example.somsom_market.domain.Order;
import com.example.somsom_market.domain.OrderItem;
import com.example.somsom_market.domain.item.GroupItem;
import com.example.somsom_market.domain.item.SomsomItem;
import com.example.somsom_market.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GroupItemOrderService {
    @Autowired
    private final OrderDao orderDao;
    @Autowired
    private final AccountDao accountDao;
    @Autowired
    private final GroupItemDao groupItemDao;
    private AccountService accountService;

    /**
     * 주문
     */
    @Transactional
    public Long order(String memberId, Order order) {

        //엔티티 조회
        Account account = accountDao.findOne(memberId);

        for (int i = 0; i < order.getOrderItems().size(); i++) {
            OrderItem orderItem = (OrderItem) order.getOrderItems().get(i);
            Long itemId = orderItem.getId();
            int increment = orderItem.getQuantity();
            GroupItem groupItem = groupItemDao.getItem(itemId);
            groupItem.setStockQuantity(groupItem.getStockQuantity() - increment);
        }

        //주문 저장
        orderDao.save(order);

        return order.getId();
    }

    @Transactional
    public OrderItem insertOrder(String accountId, Long itemId, int count) {

        //엔티티 조회
        Account account = accountDao.findOne(accountId);
        GroupItem item = groupItemDao.getItem(itemId);

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = new Order();
        order.initOrder(account, orderItem);

        //주문 저장
        orderDao.save(order);

        return orderItem;
    }

    /**
     * 주문 취소
     */
    @Transactional
    public OrderItem cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Order order = orderDao.findOne(orderId);
        //주문 취소
        order.cancel();

        OrderItem orderItem = order.getOrderItems().get(0);
        return orderItem;
    }

    //현재 모금액 갱신
    @Transactional
    public void addToSalesNow(int salesNow, long itemId){
        GroupItem groupItem = groupItemDao.getItem(itemId);
        int presentSalesNow = groupItem.getSalesNow();
        presentSalesNow += salesNow;
        groupItem.setSalesNow(presentSalesNow);
        groupItemDao.updateGroupItem(groupItem);
    }

    //현재 재고 수량 빼기
    @Transactional
    public void minusToStockQuantity(int count, long itemId){
        GroupItem groupItem = groupItemDao.getItem(itemId);
        int presentQuantity = groupItem.getStockQuantity();
        presentQuantity -= count;
        groupItem.setStockQuantity(presentQuantity);
        groupItemDao.updateGroupItem(groupItem);
    }

    //검색
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderDao.findAllByString(orderSearch);
    }
}