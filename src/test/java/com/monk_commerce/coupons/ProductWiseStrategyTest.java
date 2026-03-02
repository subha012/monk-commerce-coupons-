package com.monk_commerce.coupons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.monk_commerce.coupons.model.Cart;
import com.monk_commerce.coupons.model.CartItem;
import com.monk_commerce.coupons.model.Coupon;
import com.monk_commerce.coupons.service.strategy.ProductWiseStrategy;



public class ProductWiseStrategyTest {

    @Test
    void shouldApplyProductWiseDiscount() {

        ProductWiseStrategy strategy = new ProductWiseStrategy();

        Cart cart = new Cart();
        cart.setItems(List.of(
                new CartItem(1L, 2, 100, 0),
                new CartItem(2L, 1, 50, 0)
        ));

        Coupon coupon = new Coupon();
        coupon.setDetailsJson("{\"product_id\":1,\"discount\":10}");
        coupon.setActive(true);

        assertTrue(strategy.isApplicable(cart, coupon));

        double discount = strategy.calculateDiscount(cart, coupon);

        assertEquals(20, discount);
    }
}
