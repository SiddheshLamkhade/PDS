# E-Ration PDS System - Complete System Flow Diagrams

## Table of Contents
1. [System Architecture Overview](#system-architecture-overview)
2. [User Authentication Flow](#user-authentication-flow)
3. [Registration Flows](#registration-flows)
4. [Distribution Flow](#distribution-flow)
5. [Inventory Management Flow](#inventory-management-flow)
6. [Quota Management Flow](#quota-management-flow)
7. [Admin Dashboard Flow](#admin-dashboard-flow)
8. [Database Schema Relationships](#database-schema-relationships)

---

## System Architecture Overview

```
┌─────────────────────────────────────────────────────────────────────┐
│                         E-Ration PDS System                          │
└─────────────────────────────────────────────────────────────────────┘

┌──────────────┐       ┌──────────────┐       ┌──────────────┐
│   Admin      │       │   Dealer     │       │   Citizen    │
│   Portal     │       │   Portal     │       │   Portal     │
└──────┬───────┘       └──────┬───────┘       └──────┬───────┘
       │                      │                      │
       │                      │                      │
       └──────────────────────┼──────────────────────┘
                              │
                    ┌─────────▼─────────┐
                    │   REST API Layer  │
                    │   (Controllers)   │
                    └─────────┬─────────┘
                              │
                    ┌─────────▼─────────┐
                    │   Service Layer   │
                    │ (Business Logic)  │
                    └─────────┬─────────┘
                              │
                    ┌─────────▼─────────┐
                    │ Repository Layer  │
                    │  (Data Access)    │
                    └─────────┬─────────┘
                              │
                    ┌─────────▼─────────┐
                    │   MySQL Database  │
                    │  (Data Storage)   │
                    └───────────────────┘
```

---

## User Authentication Flow

```
┌─────────┐                                             ┌──────────┐
│  User   │                                             │ Database │
└────┬────┘                                             └────┬─────┘
     │                                                       │
     │ 1. Enter username/password                           │
     ├──────────────────────────────────────────────────────▶
     │                                                       │
     │                    2. Query user by username         │
     │                    ──────────────────────────────────▶
     │                                                       │
     │                    3. Return user data               │
     │                    ◀──────────────────────────────────
     │                                                       │
     │ 4. Validate password (BCrypt)                        │
     │ ─────────────────▶                                   │
     │                                                       │
     │ 5. Generate JWT Token                                │
     │ ◀─────────────────                                   │
     │                                                       │
     │ 6. Return token + user role                          │
     │ ◀──────────────────────────────────────────────────────
     │                                                       │
     │ 7. Store token in session                            │
     │                                                       │
     │ 8. Redirect based on role:                           │
     │    - ADMIN → Admin Dashboard                         │
     │    - DEALER → Dealer Portal                          │
     │    - CITIZEN → Citizen Portal                        │
     │                                                       │
```

---

## Registration Flows

### Admin Creates Dealer

```
┌────────┐        ┌──────────────┐        ┌─────────────┐        ┌──────────┐
│ Admin  │        │DealerService │        │UserService  │        │ Database │
└───┬────┘        └──────┬───────┘        └──────┬──────┘        └────┬─────┘
    │                    │                       │                     │
    │ POST /api/dealers  │                       │                     │
    ├───────────────────▶│                       │                     │
    │  {                 │                       │                     │
    │   username,        │ 1. Validate request   │                     │
    │   email,           │ ────────────────▶     │                     │
    │   password,        │                       │                     │
    │   shopName,        │                       │ 2. Check username   │
    │   shopLicense      │                       │    exists?          │
    │  }                 │                       ├────────────────────▶│
    │                    │                       │                     │
    │                    │                       │ 3. No duplicate     │
    │                    │                       │◀────────────────────┤
    │                    │                       │                     │
    │                    │ 4. Create User        │                     │
    │                    │    role=DEALER        │                     │
    │                    ├──────────────────────▶│                     │
    │                    │                       │ 5. INSERT user      │
    │                    │                       ├────────────────────▶│
    │                    │                       │                     │
    │                    │                       │ 6. User created     │
    │                    │                       │◀────────────────────┤
    │                    │                       │                     │
    │                    │ 7. User entity        │                     │
    │                    │◀──────────────────────┤                     │
    │                    │                       │                     │
    │                    │ 8. Create DealerProfile                     │
    │                    │    linked to user_id                        │
    │                    ├────────────────────────────────────────────▶│
    │                    │                       │                     │
    │                    │ 9. Dealer profile created                   │
    │                    │◀────────────────────────────────────────────┤
    │                    │                       │                     │
    │ 10. Return combined│                       │                     │
    │     User + Profile │                       │                     │
    │◀───────────────────┤                       │                     │
    │                    │                       │                     │
```

### Citizen Self-Registration

```
┌─────────┐      ┌──────────────┐      ┌─────────────┐      ┌──────────┐
│ Citizen │      │CitizenService│      │UserService  │      │ Database │
└────┬────┘      └──────┬───────┘      └──────┬──────┘      └────┬─────┘
     │                  │                     │                   │
     │ POST /api/citizens                     │                   │
     ├─────────────────▶│                     │                   │
     │ {                │                     │                   │
     │  username,       │ 1. Validate data    │                   │
     │  email,          │ ──────────────▶     │                   │
     │  password,       │                     │                   │
     │  rationCard,     │                     │ 2. Verify         │
     │  dealerId        │                     │    dealerId exists│
     │ }                │                     ├──────────────────▶│
     │                  │                     │                   │
     │                  │                     │ 3. Dealer found   │
     │                  │                     │◀──────────────────┤
     │                  │                     │                   │
     │                  │ 4. Create User      │                   │
     │                  │    role=CITIZEN     │                   │
     │                  ├────────────────────▶│                   │
     │                  │                     │ 5. INSERT user    │
     │                  │                     ├──────────────────▶│
     │                  │                     │                   │
     │                  │                     │ 6. User created   │
     │                  │                     │◀──────────────────┤
     │                  │                     │                   │
     │                  │ 7. User entity      │                   │
     │                  │◀────────────────────┤                   │
     │                  │                     │                   │
     │                  │ 8. Create CitizenProfile                │
     │                  │    with dealer assignment               │
     │                  ├────────────────────────────────────────▶│
     │                  │                     │                   │
     │                  │ 9. Citizen profile created              │
     │                  │◀────────────────────────────────────────┤
     │                  │                     │                   │
     │ 10. Registration │                     │                   │
     │     successful   │                     │                   │
     │◀─────────────────┤                     │                   │
     │                  │                     │                   │
```

---

## Distribution Flow (Complete Transaction)

```
┌─────────┐  ┌────────┐  ┌──────────────────┐  ┌──────────────┐  ┌────────────┐  ┌──────────┐
│ Citizen │  │ Dealer │  │DistributionService│  │QuotaService  │  │InventoryService│  │ Database │
└────┬────┘  └───┬────┘  └─────────┬─────────┘  └──────┬───────┘  └──────┬─────┘  └────┬─────┘
     │           │                 │                   │                  │             │
     │ 1. Visit  │                 │                   │                  │             │
     │   shop    │                 │                   │                  │             │
     ├──────────▶│                 │                   │                  │             │
     │           │                 │                   │                  │             │
     │           │ 2. POST /api/distributions          │                  │             │
     │           ├────────────────▶│                   │                  │             │
     │           │ {               │                   │                  │             │
     │           │  rationCard,    │ 3. Validate citizen                  │             │
     │           │  dealerId,      ├──────────────────────────────────────────────────▶│
     │           │  productId,     │                   │                  │             │
     │           │  quantity       │ 4. Citizen found  │                  │             │
     │           │ }               │◀──────────────────────────────────────────────────┤
     │           │                 │                   │                  │             │
     │           │                 │ 5. Validate dealer│                  │             │
     │           │                 ├──────────────────────────────────────────────────▶│
     │           │                 │                   │                  │             │
     │           │                 │ 6. Dealer active  │                  │             │
     │           │                 │◀──────────────────────────────────────────────────┤
     │           │                 │                   │                  │             │
     │           │                 │ 7. Validate product                  │             │
     │           │                 ├──────────────────────────────────────────────────▶│
     │           │                 │                   │                  │             │
     │           │                 │ 8. Product active │                  │             │
     │           │                 │◀──────────────────────────────────────────────────┤
     │           │                 │                   │                  │             │
     │           │                 │ 9. Check quota available             │             │
     │           │                 ├──────────────────▶│                  │             │
     │           │                 │ (citizenId,       │                  │             │
     │           │                 │  productId,       │ 10. Query quotas │             │
     │           │                 │  quantity,        │    & distributions            │
     │           │                 │  month, year)     ├─────────────────────────────▶│
     │           │                 │                   │                  │             │
     │           │                 │                   │ 11. Calculate    │             │
     │           │                 │                   │     remaining    │             │
     │           │                 │                   │◀─────────────────────────────┤
     │           │                 │                   │                  │             │
     │           │                 │ 12. Quota OK      │                  │             │
     │           │                 │◀──────────────────┤                  │             │
     │           │                 │                   │                  │             │
     │           │                 │ 13. Check dealer's stock             │             │
     │           │                 ├──────────────────────────────────────▶│             │
     │           │                 │ (dealerId, productId)                │             │
     │           │                 │                   │                  │ 14. Query   │
     │           │                 │                   │                  │  inventory  │
     │           │                 │                   │                  ├────────────▶│
     │           │                 │                   │                  │             │
     │           │                 │                   │                  │ 15. Stock   │
     │           │                 │                   │                  │  available  │
     │           │                 │                   │                  │◀────────────┤
     │           │                 │                   │                  │             │
     │           │                 │ 16. Stock sufficient                 │             │
     │           │                 │◀──────────────────────────────────────┤             │
     │           │                 │                   │                  │             │
     │           │                 │ 17. @Transactional BEGIN             │             │
     │           │                 │ ═════════════════════════════════════════════════▶│
     │           │                 │                   │                  │             │
     │           │                 │ 18. INSERT distribution record       │             │
     │           │                 ├──────────────────────────────────────────────────▶│
     │           │                 │                   │                  │             │
     │           │                 │ 19. Record created│                  │             │
     │           │                 │◀──────────────────────────────────────────────────┤
     │           │                 │                   │                  │             │
     │           │                 │ 20. Deduct stock  │                  │             │
     │           │                 ├──────────────────────────────────────▶│             │
     │           │                 │                   │                  │             │
     │           │                 │                   │                  │ 21. UPDATE  │
     │           │                 │                   │                  │  inventory  │
     │           │                 │                   │                  │  SET stock  │
     │           │                 │                   │                  │  = stock-qty│
     │           │                 │                   │                  ├────────────▶│
     │           │                 │                   │                  │             │
     │           │                 │                   │                  │ 22. Updated │
     │           │                 │                   │                  │◀────────────┤
     │           │                 │                   │                  │             │
     │           │                 │ 23. Stock deducted│                  │             │
     │           │                 │◀──────────────────────────────────────┤             │
     │           │                 │                   │                  │             │
     │           │                 │ 24. @Transactional COMMIT            │             │
     │           │                 │ ═════════════════════════════════════════════════▶│
     │           │                 │                   │                  │             │
     │           │ 25. Distribution│                   │                  │             │
     │           │     successful  │                   │                  │             │
     │           │◀────────────────┤                   │                  │             │
     │           │ {               │                   │                  │             │
     │           │  id,            │                   │                  │             │
     │           │  quantity,      │                   │                  │             │
     │           │  totalAmount,   │                   │                  │             │
     │           │  date           │                   │                  │             │
     │           │ }               │                   │                  │             │
     │           │                 │                   │                  │             │
     │ 26. Receive│                │                   │                  │             │
     │    ration  │                │                   │                  │             │
     │◀──────────┤                 │                   │                  │             │
     │           │                 │                   │                  │             │
```

**Key Points:**
- ✅ All validations happen before transaction
- ✅ Quota check prevents over-distribution
- ✅ Stock deduction atomic with distribution record
- ✅ If any step fails, entire transaction rolls back

---

## Inventory Management Flow

### Admin Allocates Stock to Dealer

```
┌────────┐        ┌──────────────────┐        ┌──────────┐
│ Admin  │        │ InventoryService │        │ Database │
└───┬────┘        └─────────┬────────┘        └────┬─────┘
    │                       │                      │
    │ POST /api/inventory/add                      │
    ├──────────────────────▶│                      │
    │ {                     │                      │
    │  dealerId: 1,         │ 1. Validate dealer   │
    │  productId: 1,        ├─────────────────────▶│
    │  quantity: 100        │                      │
    │ }                     │ 2. Dealer exists     │
    │                       │◀─────────────────────┤
    │                       │                      │
    │                       │ 3. Validate product  │
    │                       ├─────────────────────▶│
    │                       │                      │
    │                       │ 4. Product exists    │
    │                       │◀─────────────────────┤
    │                       │                      │
    │                       │ 5. Check if inventory│
    │                       │    record exists     │
    │                       │ SELECT * FROM inventory
    │                       │ WHERE dealer_id=1    │
    │                       │   AND product_id=1   │
    │                       ├─────────────────────▶│
    │                       │                      │
    │                       │ 6a. If exists:       │
    │                       │     UPDATE stock     │
    │                       │     SET stock=stock+100
    │                       │                      │
    │                       │ 6b. If not exists:   │
    │                       │     INSERT new record│
    │                       │     stock=100        │
    │                       ├─────────────────────▶│
    │                       │                      │
    │                       │ 7. Updated/Created   │
    │                       │◀─────────────────────┤
    │                       │                      │
    │ 8. Success response   │                      │
    │◀──────────────────────┤                      │
    │ {                     │                      │
    │  dealerId: 1,         │                      │
    │  productId: 1,        │                      │
    │  currentStock: 100    │                      │
    │ }                     │                      │
    │                       │                      │
```

### Low Stock Alert Flow

```
┌────────┐        ┌──────────────────┐        ┌──────────┐
│ Admin  │        │ InventoryService │        │ Database │
└───┬────┘        └─────────┬────────┘        └────┬─────┘
    │                       │                      │
    │ GET /api/inventory/low-stock?threshold=50    │
    ├──────────────────────▶│                      │
    │                       │                      │
    │                       │ SELECT i.*, d.shop_name, p.product_name
    │                       │ FROM inventory i      │
    │                       │ JOIN dealers d        │
    │                       │ JOIN products p       │
    │                       │ WHERE i.current_stock < 50
    │                       ├─────────────────────▶│
    │                       │                      │
    │                       │ List of low stock items
    │                       │◀─────────────────────┤
    │                       │                      │
    │ Alert list            │                      │
    │◀──────────────────────┤                      │
    │ [                     │                      │
    │   {                   │                      │
    │    dealer: "Ram Store",                      │
    │    product: "Rice",   │                      │
    │    currentStock: 45   │                      │
    │   },                  │                      │
    │   {                   │                      │
    │    dealer: "Sita Store",                     │
    │    product: "Sugar",  │                      │
    │    currentStock: 30   │                      │
    │   }                   │                      │
    │ ]                     │                      │
    │                       │                      │
```

---

## Quota Management Flow

### Admin Sets Monthly Quota

```
┌────────┐        ┌─────────────┐        ┌──────────┐
│ Admin  │        │QuotaService │        │ Database │
└───┬────┘        └──────┬──────┘        └────┬─────┘
    │                    │                     │
    │ POST /api/quotas   │                     │
    ├───────────────────▶│                     │
    │ {                  │                     │
    │  productId: 1,     │ 1. Validate product │
    │  category: "BPL",  ├────────────────────▶│
    │  month: 11,        │                     │
    │  year: 2024,       │ 2. Product exists   │
    │  quotaPerCitizen: 10.0                   │
    │ }                  │◀────────────────────┤
    │                    │                     │
    │                    │ 3. Check duplicate  │
    │                    │ SELECT * FROM quotas│
    │                    │ WHERE product_id=1  │
    │                    │   AND category=BPL  │
    │                    │   AND month=11      │
    │                    │   AND year=2024     │
    │                    ├────────────────────▶│
    │                    │                     │
    │                    │ 4. No duplicate     │
    │                    │◀────────────────────┤
    │                    │                     │
    │                    │ 5. INSERT quota     │
    │                    ├────────────────────▶│
    │                    │                     │
    │                    │ 6. Quota created    │
    │                    │◀────────────────────┤
    │                    │                     │
    │ 7. Success         │                     │
    │◀───────────────────┤                     │
    │                    │                     │
```

### Citizen Checks Quota Balance

```
┌─────────┐      ┌─────────────┐      ┌──────────┐
│ Citizen │      │QuotaService │      │ Database │
└────┬────┘      └──────┬──────┘      └────┬─────┘
     │                  │                   │
     │ GET /api/quotas/citizen/RC2024001/current
     ├─────────────────▶│                   │
     │                  │                   │
     │                  │ 1. Find citizen   │
     │                  │ SELECT * FROM citizens
     │                  │ WHERE ration_card='RC2024001'
     │                  ├──────────────────▶│
     │                  │                   │
     │                  │ 2. Citizen found  │
     │                  │    category = BPL │
     │                  │◀──────────────────┤
     │                  │                   │
     │                  │ 3. Get current month quotas
     │                  │ SELECT * FROM quotas
     │                  │ WHERE category=BPL│
     │                  │   AND month=CURRENT
     │                  │   AND year=CURRENT│
     │                  ├──────────────────▶│
     │                  │                   │
     │                  │ 4. Quotas list    │
     │                  │◀──────────────────┤
     │                  │                   │
     │                  │ FOR EACH quota:   │
     │                  │                   │
     │                  │ 5. Calculate redeemed
     │                  │ SELECT SUM(quantity)
     │                  │ FROM distributions│
     │                  │ WHERE citizen_id=1│
     │                  │   AND product_id=X│
     │                  │   AND month=CURRENT
     │                  ├──────────────────▶│
     │                  │                   │
     │                  │ 6. Sum = 5.0      │
     │                  │◀──────────────────┤
     │                  │                   │
     │                  │ 7. Calculate:     │
     │                  │    totalQuota=10  │
     │                  │    redeemed=5     │
     │                  │    remaining=5    │
     │                  │    percentage=50% │
     │                  │                   │
     │ 8. Balance info  │                   │
     │◀─────────────────┤                   │
     │ [                │                   │
     │  {               │                   │
     │   product: "Rice",                   │
     │   totalQuota: 10.0,                  │
     │   redeemed: 5.0, │                   │
     │   remaining: 5.0,│                   │
     │   status: "AVAILABLE"                │
     │  }               │                   │
     │ ]                │                   │
     │                  │                   │
```

---

## Admin Dashboard Flow

```
┌────────┐        ┌─────────────┐        ┌──────────┐
│ Admin  │        │AdminService │        │ Database │
└───┬────┘        └──────┬──────┘        └────┬─────┘
    │                    │                     │
    │ GET /api/admin/dashboard                 │
    ├───────────────────▶│                     │
    │                    │                     │
    │                    │ 1. Count citizens   │
    │                    │ SELECT COUNT(*) FROM citizens
    │                    ├────────────────────▶│
    │                    │ = 150               │
    │                    │◀────────────────────┤
    │                    │                     │
    │                    │ 2. Count dealers    │
    │                    │ SELECT COUNT(*) FROM dealers
    │                    ├────────────────────▶│
    │                    │ = 25                │
    │                    │◀────────────────────┤
    │                    │                     │
    │                    │ 3. Count products   │
    │                    │ SELECT COUNT(*) FROM products
    │                    ├────────────────────▶│
    │                    │ = 10                │
    │                    │◀────────────────────┤
    │                    │                     │
    │                    │ 4. Count distributions
    │                    │ SELECT COUNT(*) FROM distributions
    │                    ├────────────────────▶│
    │                    │ = 5280              │
    │                    │◀────────────────────┤
    │                    │                     │
    │                    │ 5. Active dealers   │
    │                    │ SELECT COUNT(*) FROM dealers
    │                    │ WHERE active=true   │
    │                    ├────────────────────▶│
    │                    │ = 23                │
    │                    │◀────────────────────┤
    │                    │                     │
    │                    │ 6. Total revenue    │
    │                    │ SELECT SUM(total_amount)
    │                    │ FROM distributions  │
    │                    │ WHERE MONTH=CURRENT │
    │                    ├────────────────────▶│
    │                    │ = 158400.00         │
    │                    │◀────────────────────┤
    │                    │                     │
    │ 7. Dashboard data  │                     │
    │◀───────────────────┤                     │
    │ {                  │                     │
    │  totalCitizens: 150,                     │
    │  totalDealers: 25, │                     │
    │  totalProducts: 10,│                     │
    │  totalDistributions: 5280,               │
    │  activeDealers: 23,│                     │
    │  monthlyRevenue: 158400.00               │
    │ }                  │                     │
    │                    │                     │
```

---

## Database Schema Relationships

```
┌──────────────────┐
│      users       │
├──────────────────┤
│ id (PK)          │
│ username (UQ)    │
│ email (UQ)       │
│ password         │
│ role (ENUM)      │──┐
│ aadhaar_ref      │  │
│ phone            │  │
└──────────────────┘  │
                      │
         ┌────────────┴────────────┐
         │                         │
         │                         │
┌────────▼──────────┐    ┌────────▼──────────┐
│ citizen_profiles  │    │ dealer_profiles   │
├───────────────────┤    ├───────────────────┤
│ id (PK)           │    │ id (PK)           │
│ user_id (FK,UQ)   │    │ user_id (FK,UQ)   │
│ ration_card (UQ)  │    │ shop_name         │
│ name              │    │ owner_name        │
│ family_size       │    │ shop_license (UQ) │
│ category (ENUM)   │    │ region            │
│ dealer_id (FK) ───┼───▶│ active            │
│ address           │    │ contact_number    │
│ phone_number      │    │ address           │
└───────────────────┘    └───────────────────┘
         │                         │
         │                         │
         │                         │
         │               ┌─────────┴─────────┐
         │               │                   │
┌────────▼───────────────▼──────┐  ┌────────▼──────────┐
│      distributions            │  │   inventory       │
├───────────────────────────────┤  ├───────────────────┤
│ id (PK)                       │  │ id (PK)           │
│ citizen_id (FK)               │  │ dealer_id (FK)    │
│ dealer_id (FK)                │  │ product_id (FK)   │
│ product_id (FK) ──────────────┼──│ current_stock     │
│ quantity                      │  │ last_updated      │
│ total_amount                  │  └───────────────────┘
│ distribution_date             │
│ status                        │
│ remarks                       │
└───────────────────────────────┘
         │
         │
┌────────▼──────────┐
│     products      │
├───────────────────┤
│ id (PK)           │
│ product_name      │
│ category          │
│ unit              │
│ price_per_unit    │
│ active            │
│ description       │
└───────────────────┘
         │
         │
┌────────▼──────────┐
│      quotas       │
├───────────────────┤
│ id (PK)           │
│ product_id (FK)   │
│ category (ENUM)   │
│ month             │
│ year              │
│ quota_per_citizen │
│ active            │
│ UNIQUE(product_id,│
│  category, month, │
│  year)            │
└───────────────────┘
```

**Relationships:**
- **1:1** - User ↔ CitizenProfile, User ↔ DealerProfile
- **1:N** - Dealer → Citizens (one dealer serves many citizens)
- **1:N** - Dealer → Inventory (dealer has multiple products)
- **1:N** - Citizen → Distributions (citizen gets multiple distributions)
- **1:N** - Product → Inventory (product stocked by multiple dealers)
- **1:N** - Product → Quotas (product has quotas for BPL/APL)

---

## Security Flow (Future Implementation)

```
┌──────────┐                                    ┌────────────┐
│  Client  │                                    │   Server   │
└────┬─────┘                                    └─────┬──────┘
     │                                                │
     │ 1. POST /api/auth/login                       │
     │    { username, password }                     │
     ├──────────────────────────────────────────────▶│
     │                                                │
     │                          2. Validate user     │
     │                             credentials       │
     │                                                │
     │ 3. JWT Token + Refresh Token                  │
     │◀──────────────────────────────────────────────┤
     │                                                │
     │ 4. Store tokens in localStorage/cookies       │
     │                                                │
     │ 5. API Request with Bearer token              │
     │    Authorization: Bearer <token>              │
     ├──────────────────────────────────────────────▶│
     │                                                │
     │                          6. Validate token    │
     │                             Check role/perms  │
     │                                                │
     │ 7. Protected resource                         │
     │◀──────────────────────────────────────────────┤
     │                                                │
     │ 8. Token expired? Use refresh token           │
     │    POST /api/auth/refresh                     │
     ├──────────────────────────────────────────────▶│
     │                                                │
     │ 9. New JWT Token                              │
     │◀──────────────────────────────────────────────┤
     │                                                │
```

---

## Error Handling Flow

```
Request → Controller → Service → Repository → Database
   │          │          │           │            │
   │          │          │           │            └─ SQLException
   │          │          │           │                    │
   │          │          │           │                    ▼
   │          │          │           └─ DataAccessException
   │          │          │                        │
   │          │          │                        ▼
   │          │          └─ BusinessLogicException
   │          │                      (InsufficientStockException,
   │          │                       QuotaExceededException, etc.)
   │          │                                   │
   │          │                                   ▼
   │          └─ ValidationException              │
   │                  (@Valid failures)           │
   │                         │                    │
   │                         ▼                    │
   └─ GlobalExceptionHandler ◀──────────────────┘
              │
              ▼
   {
     "error": "Quota exceeded",
     "message": "Monthly quota exhausted for Rice",
     "timestamp": "2024-11-09T10:30:00",
     "status": 400
   }
```

---

**Document Version:** 1.0  
**Last Updated:** November 2024  
**Diagrams:** 10 comprehensive flow diagrams  
**Status:** ✅ Complete System Documentation
