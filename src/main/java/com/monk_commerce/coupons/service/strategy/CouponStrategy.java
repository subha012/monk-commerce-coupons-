package com.monk_commerce.coupons.service.strategy;

import com.monk_commerce.coupons.model.Cart;
import com.monk_commerce.coupons.model.Coupon;

public interface CouponStrategy {

    boolean isApplicable(Cart cart, Coupon coupon);

    double calculateDiscount(Cart cart, Coupon coupon);

    void apply(Cart cart, Coupon coupon);
}
