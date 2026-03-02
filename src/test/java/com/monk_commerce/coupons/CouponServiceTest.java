package com.monk_commerce.coupons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.monk_commerce.coupons.model.Coupon;
import com.monk_commerce.coupons.model.CouponType;
import com.monk_commerce.coupons.repository.CouponRepository;
import com.monk_commerce.coupons.service.CouponService;



@SpringBootTest
public class CouponServiceTest {

    @Autowired
    private CouponService service;

    @Autowired
    private CouponRepository repository;
    
    @Test
    void shouldSaveCoupon() {

        Coupon coupon = new Coupon();
        coupon.setType(CouponType.CART_WISE);
        coupon.setDetailsJson("{\"threshold\":100,\"discount\":10}");
        coupon.setActive(true);

        Coupon saved = service.create(coupon);

        assertNotNull(saved.getId());
        assertEquals(1, repository.findAll().size());
    }
}