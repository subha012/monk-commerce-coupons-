# 🛒 Coupons Management API

### Backend Developer Task -- Monk Commerce 2025

------------------------------------------------------------------------

## 📌 Overview

This project is a RESTful API built to manage and apply different types
of discount coupons in an e-commerce system.

The goal was not just to implement coupon logic, but to design the
system in a way that is clean, extensible, and easy to maintain. The
implementation focuses on correctness, handling edge cases, and keeping
the architecture flexible for future growth.

Currently, the system supports:

-   Cart-wise coupons\
-   Product-wise coupons\
-   Buy X Get Y (BxGy) coupons

------------------------------------------------------------------------

# 🏗️ Tech Stack

-   Java 17\
-   Spring Boot 3\
-   Spring Data JPA\
-   H2 In-Memory Database\
-   Maven\
-   JUnit 5

H2 is used for simplicity so the project runs without any external
setup.

------------------------------------------------------------------------

# 🧠 Design Decisions

## Strategy Pattern for Coupon Logic

Each coupon type implements a common interface:

    CouponStrategy

Each implementation defines:

-   When the coupon is applicable\
-   How the discount is calculated\
-   How the cart should be updated

This keeps the logic clean and makes the system easy to extend.

To add a new coupon type in the future:

1.  Add a new value in `CouponType`
2.  Create a new class implementing `CouponStrategy`
3.  Add the business logic in that class

------------------------------------------------------------------------

## Flexible Coupon Storage

Coupon-specific data is stored as JSON:

    private String detailsJson;

Since each coupon type has different attributes, storing JSON avoids
creating multiple database columns for each variation.

Example structures:

Cart-Wise: {"threshold":100,"discount":10}

Product-Wise: {"product_id":1,"discount":20}

BxGy: { "buy_products":\[{"product_id":1}\],
"get_products":\[{"product_id":3}\], "repetition_limit":2 }

------------------------------------------------------------------------

# ✅ Implemented Coupon Types

## 🛒 Cart-Wise Coupon

Applies a percentage discount if the cart total exceeds a specified
threshold.

Example:\
"10% off on orders above ₹500"

Logic: - Calculate total cart value\
- Compare with threshold\
- Apply percentage discount

Edge Cases Considered: - Cart total exactly equal to threshold\
- Empty cart\
- Inactive coupon\
- Expired coupon

Time Complexity: O(n)

------------------------------------------------------------------------

## 📦 Product-Wise Coupon

Applies discount only to a specific product.

Example:\
"20% off on Product ID 1"

Logic: - Check if product exists in cart\
- Apply discount only to matching items

Edge Cases Considered: - Product not present\
- Quantity = 0\
- Multiple quantities\
- Discount = 0%

Time Complexity: O(n)

------------------------------------------------------------------------

## 🎁 Buy X Get Y (BxGy)

Allows customers to buy certain products and get other products free.

Example:\
"Buy 2 of Product A, get 1 Product B free (max 2 times)"

Core Logic:

    timesApplicable = totalBuyQuantity / requiredBuyQuantity
    timesApplicable = min(timesApplicable, repetitionLimit)

Free items are then allocated based on availability.

Edge Cases Considered: - Buy items present but no get items\
- Insufficient buy quantity\
- Repetition limit exceeded\
- Partial application

Time Complexity: O(n)

------------------------------------------------------------------------

# 🔌 API Endpoints

Create Coupon\
POST /coupons

Get All Coupons\
GET /coupons

Get Coupon By ID\
GET /coupons/{{id}}

Update Coupon\
PUT /coupons/{{id}}

Delete Coupon\
DELETE /coupons/{{id}}

Get Applicable Coupons\
POST /applicable-coupons

Apply Specific Coupon\
POST /apply-coupon/{{id}}

------------------------------------------------------------------------

# 🧪 Unit Testing

Unit tests are written for:

-   CartWiseStrategy\
-   ProductWiseStrategy\
-   BxGyStrategy

The tests verify: - Correct applicability\
- Correct discount calculation\
- Repetition limit handling\
- Invalid scenarios

------------------------------------------------------------------------

# ❌ Identified but Not Implemented

-   Coupon stacking\
-   Priority-based selection\
-   Maximum discount cap\
-   Fixed amount discounts\
-   Category or brand-based coupons\
-   User-specific coupons\
-   Usage limits\
-   Coupon code system\
-   Inventory validation\
-   Tax-inclusive pricing\
-   Multi-currency support\
-   Concurrency handling\
-   Analytics tracking

------------------------------------------------------------------------

# ⚠ Current Limitations

-   H2 in-memory database (data resets on restart)\
-   No authentication\
-   No concurrency handling\
-   Only one coupon applied at a time\
-   No coupon usage tracking

------------------------------------------------------------------------

# 🧠 Assumptions

-   Prices are pre-tax\
-   Single currency\
-   Cart is provided as request input\
-   No inventory mutation\
-   Coupons cannot result in negative totals\
-   Server time is used for expiry validation

------------------------------------------------------------------------

# 🚀 Future Improvements

-   Redis caching\
-   Coupon stacking engine\
-   Rule engine integration\
-   Optimistic locking\
-   Admin dashboard\
-   Analytics system\
-   Docker support\
-   CI/CD pipeline

------------------------------------------------------------------------

# 🧪 Running the Project

Build and run:

    mvn clean install
    mvn spring-boot:run

H2 Console:

    http://localhost:8080/h2-console

JDBC URL:

    jdbc:h2:mem:coupons-db

------------------------------------------------------------------------

# 🏁 Final Thoughts

This implementation focuses on clean design, correctness, and
extensibility.

Instead of over-engineering, the goal was to build a strong foundation
that can evolve into a production-ready coupon engine with stacking,
concurrency handling, and analytics in future iterations.

