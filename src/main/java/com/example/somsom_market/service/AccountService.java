package com.example.somsom_market.service;

import com.example.somsom_market.controller.User.UserRegistRequest;
import com.example.somsom_market.domain.*;

import java.util.List;

public class AccountService {
    // 실제 메소드 구현은 추후에..

    // 사용자 PK로 계정 검색
    public Account getAccount(int userId) {
        return null;
    }

    // id, password로 계정 검색
    public Account getAccount(String id, String password) {
        return null;
    }

    // 새로운 계정 추가 후 다시 Account 반환 → 바로 로그인
    public Account insertAccount(UserRegistRequest memReq) {
        return null;
    }

    // 회원 정보 수정 후 다시 Account 반환
    public Account updateAccount(Account account) {
        return null;
    }

    // 회원 정보 비밀번호 변경
    public void updatePassword(int userId, String password) {

    }

    // 회원 삭제
    public void deleteAccount(Account account) {

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
