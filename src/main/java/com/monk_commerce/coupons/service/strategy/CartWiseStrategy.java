package com.monk_commerce.coupons.service.strategy;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monk_commerce.coupons.model.Cart;
import com.monk_commerce.coupons.model.Coupon;

@Component
public class CartWiseStrategy implements CouponStrategy {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean isApplicable(Cart cart, Coupon coupon) {
        try {
            JsonNode node = mapper.readTree(coupon.getDetailsJson());
            double threshold = node.get("threshold").asDouble();
            return cart.getTotalAmount() > threshold;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public double calculateDiscount(Cart cart, Coupon coupon) {
        try {
            JsonNode node = mapper.readTree(coupon.getDetailsJson());
            double discountPercent = node.get("discount").asDouble();
            return cart.getTotalAmount() * discountPercent / 100;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public void apply(Cart cart, Coupon coupon) {
        double discount = calculateDiscount(cart, coupon);
        double perItemDiscount = discount / cart.getTotalAmount();

        for (var item : cart.getItems()) {
            double itemTotal = item.getPrice() * item.getQuantity();
            item.setTotalDiscount(itemTotal * perItemDiscount);
        }
    }
}

