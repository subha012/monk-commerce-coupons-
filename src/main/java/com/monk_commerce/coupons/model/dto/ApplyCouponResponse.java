package com.monk_commerce.coupons.model.dto;

import com.monk_commerce.coupons.model.Cart;

public class ApplyCouponResponse {

    private Cart updatedCart;
    private double totalDiscount;
    private double finalPrice;

    public ApplyCouponResponse() {}

    public ApplyCouponResponse(Cart updatedCart, double totalDiscount, double finalPrice) {
        this.updatedCart = updatedCart;
        this.totalDiscount = totalDiscount;
        this.finalPrice = finalPrice;
    }

    public Cart getUpdatedCart() { return updatedCart; }

    public void setUpdatedCart(Cart updatedCart) { this.updatedCart = updatedCart; }

    public double getTotalDiscount() { return totalDiscount; }

    public void setTotalDiscount(double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public double getFinalPrice() { return finalPrice; }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }
}
