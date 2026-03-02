package com.monk_commerce.coupons.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.monk_commerce.coupons.model.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
