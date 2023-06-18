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
    private boolean shippingAddressRequired = false;
    private boolean shippingAddressProvided = false;

    public void setShippingAddressRequired(boolean shippingAddressRequired) {
        this.shippingAddressRequired = shippingAddressRequired;
    }

    public boolean isShippingAddressRequired() {
        return shippingAddressRequired;
    }

    public void setShippingAddressProvided(boolean shippingAddressProvided) {
        this.shippingAddressProvided = shippingAddressProvided;
    }

    public boolean didShippingAddressProvided() {
        return shippingAddressProvided;
    }

}
