package com.example.somsom_market.service;

import com.example.somsom_market.controller.User.UserRegistRequest;
import com.example.somsom_market.dao.AccountDao;
import com.example.somsom_market.domain.*;
import com.example.somsom_market.repository.AccountRepository;
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

    // 사용자 PK로 계정 검색
    public Account getAccount(int userId) {
        Optional<Account> account = accountRepository.findById(userId);
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
    public void insertAccount(UserRegistRequest memReq) {
        Account account = new Account();
        account.setUserName(memReq.getUserName());
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
    }

    // 회원 정보 수정 후 다시 Account 반환
    public void updateAccount(Account account) {
        accountDao.updateAccount(account);
    }

    // 회원 정보 비밀번호 변경
    public void updatePassword(int userId, String password) {
        accountDao.updatePassword(userId, password);
    }

    // 회원 삭제
    public void deleteAccount(Account account) {
        accountDao.deleteAccount(account);
    }

    // 사용자 PK로 구매 내역, 판매 내역, 공동구매 내역, 위시리스트 개수 반환
    public List<Integer> getMyPageList(int userId) {
        return null;
    }

    // 사용자 PK로 판매 내역 리스트 검색
    public List<PersonalItem> getSellItemList(int userId) {
        return null;
    }

    // 사용자 PK로 공동구매 판매 내역 리스트 검색
    public List<GroupItem> getSellGroupList(int userId) {
        return null;
    }

    // 사용자 PK로 구매 내역 리스트 검색
    public List<Order> getOrderList(int userId) {
        return null;
    }

    // 구매 취소
    public void cancelOrder(int userId, int orderId) {

    }

    // 사용자 PK로 개인 판매 위시리스트 검색
    public List<PersonalItem> getPersonalWishlist(int userId) {
        return null;
    }

    // 사용자 PK로 공동구매 위시리스트 검색
    public List<GroupItem> getGroupWishlist(int userId) {
        return null;
    }

    // 사용자 PK로 학교 굿즈 위시리스트 검색
    public List<SomsomItem> getSomsomWishlist(int userId) {
        return null;
    }

    // 해당 사용자에 해당 아이템 위시리스트 추가
    public void addWishlist(int userId, int itemId) {

    }

    // 해당 사용자에 해당 아이템 위시리스트 삭제
    public void cancelWishlist(int userId, int itemId) {

    }


}
