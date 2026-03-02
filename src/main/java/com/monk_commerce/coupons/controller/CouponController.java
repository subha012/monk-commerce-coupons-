package com.monk_commerce.coupons.controller;

import java.util.List;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monk_commerce.coupons.model.Coupon;
import com.monk_commerce.coupons.service.CouponService;

@RestController
@RequestMapping("/coupons")
public class CouponController {

    private final CouponService service;

    public CouponController(CouponService service) {
        this.service = service;
    }

    @PostMapping
    public Coupon create(@RequestBody Coupon coupon) {
        return service.create(coupon);
    }

    @GetMapping
    public List<Coupon> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Coupon getById(@PathVariable Long id) {
        return service.getById(id);
    }
}
