package com.monk_commerce.coupons.model.dto;

public class ApplicableCouponResponse {

    private Long couponId;
    private String type;
    private double discount;

    public ApplicableCouponResponse() {}

    public ApplicableCouponResponse(Long couponId, String type, double discount) {
        this.couponId = couponId;
        this.type = type;
        this.discount = discount;
    }

    public Long getCouponId() { return couponId; }

    public void setCouponId(Long couponId) { this.couponId = couponId; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public double getDiscount() { return discount; }

    public void setDiscount(double discount) { this.discount = discount; }
}
