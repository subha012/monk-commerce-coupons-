package com.monk_commerce.coupons.service.strategy;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monk_commerce.coupons.model.Cart;
import com.monk_commerce.coupons.model.CartItem;
import com.monk_commerce.coupons.model.Coupon;

@Component
public class BxGyStrategy implements CouponStrategy {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean isApplicable(Cart cart, Coupon coupon) {
        return calculateDiscount(cart, coupon) > 0;
    }

    @Override
    public double calculateDiscount(Cart cart, Coupon coupon) {
        try {
            JsonNode node = mapper.readTree(coupon.getDetailsJson());
            int repetitionLimit = node.get("repetition_limit").asInt();

            List<Long> buyProducts = extractProductIds(node.get("buy_products"));
            List<Long> getProducts = extractProductIds(node.get("get_products"));

            int totalBuyCount = cart.getItems().stream()
                    .filter(i -> buyProducts.contains(i.getProductId()))
                    .mapToInt(CartItem::getQuantity)
                    .sum();

            int totalGetCount = cart.getItems().stream()
                    .filter(i -> getProducts.contains(i.getProductId()))
                    .mapToInt(CartItem::getQuantity)
                    .sum();

            int applicableTimes = Math.min(totalBuyCount, totalGetCount);
            applicableTimes = Math.min(applicableTimes, repetitionLimit);

            double discount = 0;

            for (var item : cart.getItems()) {
                if (getProducts.contains(item.getProductId())) {
                    int freeQty = Math.min(item.getQuantity(), applicableTimes);
                    discount += freeQty * item.getPrice();
                }
            }

            return discount;

        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public void apply(Cart cart, Coupon coupon) {
        double discount = calculateDiscount(cart, coupon);

        for (var item : cart.getItems()) {
            item.setTotalDiscount(discount); // simplified allocation
        }
    }

    private List<Long> extractProductIds(JsonNode node) {
        List<Long> ids = new ArrayList<>();
        for (JsonNode n : node) {
            ids.add(n.get("product_id").asLong());
        }
        return ids;
    }
}
