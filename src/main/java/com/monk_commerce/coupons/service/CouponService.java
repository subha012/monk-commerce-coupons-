package com.monk_commerce.coupons.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.monk_commerce.coupons.model.Cart;
import com.monk_commerce.coupons.model.Coupon;
import com.monk_commerce.coupons.model.CouponType;
import com.monk_commerce.coupons.model.dto.ApplicableCouponResponse;
import com.monk_commerce.coupons.repository.CouponRepository;
import com.monk_commerce.coupons.service.strategy.CouponStrategy;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository repository;
    private final List<CouponStrategy> strategies;
    public CouponService(CouponRepository repository,
            List<CouponStrategy> strategies) {
				this.repository = repository;
				this.strategies = strategies;
			}

    public List<Coupon> getAll() {
        return repository.findAll();
    }

    public Coupon getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coupon not found"));
    }

    public List<ApplicableCouponResponse> getApplicableCoupons(Cart cart) {
        return repository.findAll().stream()
                .filter(Coupon::isActive)
                .map(coupon -> {
                    CouponStrategy strategy = findStrategy(coupon.getType());
                    if (strategy.isApplicable(cart, coupon)) {
                        return new ApplicableCouponResponse(
                                coupon.getId(),
                                coupon.getType().name(),
                                strategy.calculateDiscount(cart, coupon)
                        );
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .toList();
    }

    private CouponStrategy findStrategy(CouponType type) {
        return strategies.stream()
                .filter(s -> s.getClass().getSimpleName()
                        .toUpperCase().contains(type.name()))
                .findFirst()
                .orElseThrow();
    }

	public Coupon create(Coupon coupon) {
		return repository.save(coupon);
	}
}

