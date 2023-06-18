package com.example.somsom_market.controller.Order;

import com.example.somsom_market.domain.Account;
import com.example.somsom_market.domain.Cart;
import com.example.somsom_market.domain.Order;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

@Data
public class OrderForm implements Serializable {

    private final Order order = new Order();
}
