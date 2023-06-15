package com.example.somsom_market.service;

import com.example.somsom_market.controller.User.UserRegistRequest;
import com.example.somsom_market.dao.AccountDao;
import com.example.somsom_market.domain.*;
import com.example.somsom_market.repository.AccountRepository;
import com.example.somsom_market.repository.GroupItemRepository;
import com.example.somsom_market.repository.OrderRepository;
import com.example.somsom_market.repository.PersonalItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Autowired
    private AccountDao accountDao;
    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Autowired
    private PersonalItemRepository personalItemRepository;
    public void setPersonalItemRepository(PersonalItemRepository personalItemRepository) {
        this.personalItemRepository = personalItemRepository;
    }

    @Autowired
    private GroupItemRepository groupItemRepository;
    public void setGroupItemRepository(GroupItemRepository groupItemRepository) {
        this.groupItemRepository = groupItemRepository;
    }

    @Autowired
    private OrderRepository orderRepository;
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // 사용자 PK로 계정 검색
    public Account getAccount(String id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) return account.get();
        return null;
    }

    // id, password로 계정 검색
    public Account getAccount(String id, String password) {
        Optional<Account> account = accountRepository.findByIdAndPassword(id, password);
        if (account.isPresent()) return account.get();
        return null;
    }

    // 새로운 계정 추가 후 다시 Account 반환 → 바로 로그인
    public Account insertAccount(UserRegistRequest memReq) {
        Account account = new Account();
        account.setName(memReq.getUserName());
        account.setNickName(memReq.getNickName());
        account.setId(memReq.getId());
        account.setPassword(memReq.getPassword());
        account.setEmail(memReq.getEmail());
        account.setAddress(memReq.getAddress());
        account.setZipcode(memReq.getZipcode());
        account.setBankName(memReq.getBankName());
        account.setBankAccount(memReq.getBankAccount());
        account.setPhone(memReq.getPhoneNumber());

        accountDao.insertAccount(account);

        return account;
    }

    // 아이디 존재하는지 확인
    public boolean isIdExist(String id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) return true;
        return false;
    }

    // 회원 정보 수정 후 다시 Account 반환
    public Account updateAccount(Account account) {
        return accountDao.updateAccount(account);
    }

    // 회원 정보 비밀번호 변경
    public void updatePassword(String id, String password) {
        accountDao.updatePassword(id, password);
    }

    // 회원 삭제
    public void deleteAccount(Account account) {
        accountDao.deleteAccount(account);
    }

    // 사용자 PK로 구매 내역, 판매 내역, 공동구매 내역, 위시리스트 개수 반환
    public int[] getMyPageList(String id) {
        int[] myPageList = new int[] {0, 0, 0, 0};

        List<Order> orderList = getOrderList(id);
        if (orderList != null) {
            myPageList[0] = orderList.size();
        }
        List<PersonalItem> personalList = getSellItemList(id);
        if (personalList != null) {
            myPageList[1] = personalList.size();
        }
        List<GroupItem> groupList = getSellGroupList(id);
        if (groupList != null) {
            myPageList[2] = groupList.size();
        }

        // 위시리스트 추후에..

        return myPageList;
    }

    // 사용자 PK로 판매 내역 리스트 검색
    public List<PersonalItem> getSellItemList(String id) {
        return personalItemRepository.findBySellerId(id);
    }

    // 사용자 PK로 공동구매 판매 내역 리스트 검색
    public List<GroupItem> getSellGroupList(String id) {
        return groupItemRepository.findBySellerIdOrderByStartDate(id);
    }

    // 사용자 PK로 구매 내역 리스트 검색
    public List<Order> getOrderList(String id) {
        return null;
    }

}
