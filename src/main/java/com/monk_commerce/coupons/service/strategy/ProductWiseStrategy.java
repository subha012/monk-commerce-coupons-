package com.monk_commerce.coupons.service.strategy;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monk_commerce.coupons.model.Cart;
import com.monk_commerce.coupons.model.Coupon;

@Component
public class ProductWiseStrategy implements CouponStrategy {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean isApplicable(Cart cart, Coupon coupon) {
        try {
            JsonNode node = mapper.readTree(coupon.getDetailsJson());
            Long productId = node.get("product_id").asLong();

            return cart.getItems().stream()
                    .anyMatch(i -> i.getProductId().equals(productId));
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public double calculateDiscount(Cart cart, Coupon coupon) {
        try {
            JsonNode node = mapper.readTree(coupon.getDetailsJson());
            Long productId = node.get("product_id").asLong();
            double discountPercent = node.get("discount").asDouble();

            return cart.getItems().stream()
                    .filter(i -> i.getProductId().equals(productId))
                    .mapToDouble(i -> i.getPrice() * i.getQuantity() * discountPercent / 100)
                    .sum();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public void apply(Cart cart, Coupon coupon) {
        try {
            JsonNode node = mapper.readTree(coupon.getDetailsJson());
            Long productId = node.get("product_id").asLong();
            double discountPercent = node.get("discount").asDouble();

            for (var item : cart.getItems()) {
                if (item.getProductId().equals(productId)) {
                    double discount = item.getPrice() * item.getQuantity() * discountPercent / 100;
                    item.setTotalDiscount(discount);
                }
            }
        } catch (Exception ignored) {}
    }
}
