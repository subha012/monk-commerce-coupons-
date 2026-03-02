package com.monk_commerce.coupons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.monk_commerce.coupons.model.Cart;
import com.monk_commerce.coupons.model.CartItem;
import com.monk_commerce.coupons.model.Coupon;
import com.monk_commerce.coupons.service.strategy.CartWiseStrategy;


public class CartWiseStrategyTest {

    @Test
    void shouldApplyCartWiseDiscount() {

        CartWiseStrategy strategy = new CartWiseStrategy();

        Cart cart = new Cart();
        cart.setItems(Arrays.asList(
                new CartItem(1L, 2, 100.0, 0.0)
        ));

        Coupon coupon = new Coupon();
        coupon.setDetailsJson("{\"threshold\":100,\"discount\":10}");
        coupon.setActive(true);

        assertTrue(strategy.isApplicable(cart, coupon));

        double discount = strategy.calculateDiscount(cart, coupon);

        assertEquals(20, discount);
    }
}
