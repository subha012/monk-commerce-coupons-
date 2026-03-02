package com.monk_commerce.coupons;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.monk_commerce.coupons.model.Cart;
import com.monk_commerce.coupons.model.CartItem;
import com.monk_commerce.coupons.model.Coupon;
import com.monk_commerce.coupons.service.strategy.BxGyStrategy;



public class BxGyStrategyTest {

    @Test
    void shouldApplyBxGyDiscount() {

        BxGyStrategy strategy = new BxGyStrategy();

        Cart cart = new Cart();
        cart.setItems(List.of(
                new CartItem(1L, 2, 100, 0),  // buy
                new CartItem(3L, 2, 50, 0)    // get
        ));

        Coupon coupon = new Coupon();
        coupon.setDetailsJson("""
        {
          "buy_products":[{"product_id":1}],
          "get_products":[{"product_id":3}],
          "repetition_limit":2
        }
        """);

        coupon.setActive(true);

        double discount = strategy.calculateDiscount(cart, coupon);

        assertEquals(100, discount);
    }
}
