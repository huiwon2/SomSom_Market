package com.example.somsom_market.service;

import com.example.somsom_market.controller.User.UserRegistRequest;
import com.example.somsom_market.domain.Account;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class AccountFormValidator implements Validator {
    public boolean supports(Class<?> clazz) {
        return Account.class.isAssignableFrom(clazz);
    }

    public void validate(Object obj, Errors errors) {
        UserRegistRequest memReq = (UserRegistRequest) obj;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "ID_REQUIRED", "아이디는 필수 항목입니다.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "USER_NAME_REQUIRED", "이름은 필수 항목입니다.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nickName", "NICK_NAME_REQUIRED", "닉네임은 필수 항목입니다.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "EMAIL_REQUIRED", "이메일은 필수 항목입니다.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "PHONE_REQUIRED", "전화번호는 필수 항목입니다.");

        if(memReq.getPhoneNumber() != null && memReq.getPhoneNumber().length() > 0) {
            if(!memReq.getPhoneNumber().matches("^01([0-9])-(\\d{4})-(\\d{4})$"))
                errors.rejectValue("phoneNumber", "PHONE_NOT_CORRECT", "잘못된 전화번호 형식입니다. ex)010-1111-2222, 010-3333-4444");
        }

        if (memReq.getZipcode() != null && memReq.getZipcode().length() > 0) { // null 또는 공백이 아니면 확인
            if (!memReq.getZipcode().matches("^\\d{5}$"))
                errors.rejectValue("zipcode", "ZIPCODE_NOT_CORRECT", "잘못된 우편번호 형식입니다. ex)12345");
        }

        if (memReq.getPassword() != null && memReq.getPassword().length() > 0) {
            if (!memReq.getPassword().equals(memReq.getPasswordCheck())) { // 비번, 비번 확인이 다르면
                errors.rejectValue("passwordCheck", "PW_CHECK_DIFF", "패스워드가 일치하지 않습니다.");
            }
        }

        // 이메일 형식 확인 (추후)
    }
}
