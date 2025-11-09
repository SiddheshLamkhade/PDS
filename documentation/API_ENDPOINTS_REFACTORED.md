# 📘 Complete API Endpoints Documentation (Refactored with User + Profile Pattern)

## 🔑 Architecture Changes

**New Pattern**: Single User table with `CITIZEN`, `DEALER`, and `ADMIN` roles + Profile tables

```
User (Authentication & Role)
├── Citizen Profile (if role = CITIZEN)
├── Dealer Profile (if role = DEALER)
└── Admin (if role = ADMIN - no profile needed)
```

---

## 📋 API Summary

This document contains **70+ API endpoints** organized into 9 categories:

1. **User Management APIs** (10 endpoints) - User CRUD operations
2. **Citizen Management APIs** (11 endpoints) - Citizen profile operations
3. **Dealer Management APIs** (11 endpoints) - Dealer profile operations
4. **Product Management APIs** (8 endpoints) - Product catalog management
5. **Inventory Management APIs** (9 endpoints) - Dealer stock tracking
6. **Distribution APIs** (8 endpoints) - Ration distribution operations
7. **Admin/Reports APIs** (7 endpoints) - Dashboard and analytics
8. **Prediction APIs** (3 endpoints) - Demand forecasting
9. **Quota Management APIs** (10 endpoints) 🆕 - Monthly entitlement tracking

---

## 1️⃣ **User Management APIs**

### Base URL: `/api/users`

### **1.1 Create User**
```http
POST /api/users
Content-Type: application/json

{
  "username": "admin_user",
  "email": "admin@example.com",
  "password": "Admin@123",
  "fullName": "System Administrator",
  "role": "ADMIN"
}
```

**Roles**: `CITIZEN`, `DEALER`, `ADMIN`

**Response**:
```json
{
  "success": true,
  "message": "User created successfully",
  "data": {
    "id": 1,
    "username": "admin_user",
    "email": "admin@example.com",
    "fullName": "System Administrator",
    "role": "ADMIN",
    "active": true,
    "createdAt": "2025-11-09T10:30:00",
    "updatedAt": "2025-11-09T10:30:00"
  }
}
```

---

### **1.2 Get User by ID**
```http
GET /api/users/{id}
```

**Example**:
```http
GET /api/users/1
```

---

### **1.3 Get User by Username**
```http
GET /api/users/username/{username}
```

**Example**:
```http
GET /api/users/username/admin_user
```

---

### **1.4 Get User by Email**
```http
GET /api/users/email/{email}
```

**Example**:
```http
GET /api/users/email/admin@example.com
```

---

### **1.5 Get All Users**
```http
GET /api/users
```

---

### **1.6 Get Users by Role**
```http
GET /api/users/role/{role}
```

**Example**:
```http
GET /api/users/role/CITIZEN
GET /api/users/role/DEALER
GET /api/users/role/ADMIN
```

---

### **1.7 Update User**
```http
PUT /api/users/{id}
Content-Type: application/json

{
  "username": "updated_username",
  "email": "updated@example.com",
  "password": "NewPass@123",
  "fullName": "Updated Full Name",
  "role": "ADMIN"
}
```

---

### **1.8 Delete User**
```http
DELETE /api/users/{id}
```

---

### **1.9 Deactivate User**
```http
PATCH /api/users/{id}/deactivate
```

---

### **1.10 Activate User**
```http
PATCH /api/users/{id}/activate
```

---

## 2️⃣ **Citizen Management APIs**

### Base URL: `/api/citizens`

### **2.1 Register Citizen (Creates User + Citizen Profile)**
```http
POST /api/citizens
Content-Type: application/json

{
  "username": "citizen123",
  "email": "citizen@example.com",
  "password": "Pass@123",
  "fullName": "John Doe",
  "rationCardNumber": "RAT123456",
  "address": "123 Main Street, Mumbai",
  "phoneNumber": "9876543210",
  "familySize": 4,
  "category": "BPL",
  "dealerId": 1
}
```

**Categories**: `BPL` (Below Poverty Line), `APL` (Above Poverty Line)

**Response**:
```json
{
  "success": true,
  "message": "Citizen registered successfully",
  "data": {
    "id": 1,
    "userId": 2,
    "username": "citizen123",
    "email": "citizen@example.com",
    "fullName": "John Doe",
    "rationCardNumber": "RAT123456",
    "address": "123 Main Street, Mumbai",
    "phoneNumber": "9876543210",
    "familySize": 4,
    "category": "BPL",
    "dealerId": 1,
    "dealerShopName": "Ration Shop A",
    "createdAt": "2025-11-09T10:30:00",
    "updatedAt": "2025-11-09T10:30:00"
  }
}
```

---

### **2.2 Get Citizen by ID**
```http
GET /api/citizens/{id}
```

---

### **2.3 Get Citizen by Ration Card Number**
```http
GET /api/citizens/ration-card/{rationCardNumber}
```

**Example**:
```http
GET /api/citizens/ration-card/RAT123456
```

---

### **2.4 Get Citizen by User ID**
```http
GET /api/citizens/user/{userId}
```

**Example**:
```http
GET /api/citizens/user/2
```

---

### **2.5 Get All Citizens**
```http
GET /api/citizens
```

---

### **2.6 Get Citizens by Dealer ID**
```http
GET /api/citizens/dealer/{dealerId}
```

**Example**:
```http
GET /api/citizens/dealer/1
```

---

### **2.7 Get Citizens by Category**
```http
GET /api/citizens/category/{category}
```

**Example**:
```http
GET /api/citizens/category/BPL
GET /api/citizens/category/APL
```

---

### **2.8 Update Citizen**
```http
PUT /api/citizens/{id}
Content-Type: application/json

{
  "username": "updated_citizen",
  "email": "updated@example.com",
  "password": "NewPass@123",
  "fullName": "John Updated Doe",
  "rationCardNumber": "RAT123456",
  "address": "456 New Street, Mumbai",
  "phoneNumber": "9876543211",
  "familySize": 5,
  "category": "APL",
  "dealerId": 2
}
```

---

### **2.9 Assign Dealer to Citizen**
```http
PATCH /api/citizens/{citizenId}/assign-dealer/{dealerId}
```

**Example**:
```http
PATCH /api/citizens/1/assign-dealer/3
```

---

### **2.10 Delete Citizen**
```http
DELETE /api/citizens/{id}
```

---

## 3️⃣ **Dealer Management APIs**

### Base URL: `/api/dealers`

### **3.1 Register Dealer (Creates User + Dealer Profile)**
```http
POST /api/dealers
Content-Type: application/json

{
  "username": "dealer_shop1",
  "email": "dealer@example.com",
  "password": "Dealer@123",
  "fullName": "Rajesh Kumar",
  "shopName": "Kumar Ration Shop",
  "shopLicense": "LIC2024001",
  "address": "456 Market Road, Delhi",
  "phoneNumber": "9123456789",
  "region": "North Delhi"
}
```

**Response**:
```json
{
  "success": true,
  "message": "Dealer registered successfully",
  "data": {
    "id": 1,
    "userId": 3,
    "username": "dealer_shop1",
    "email": "dealer@example.com",
    "fullName": "Rajesh Kumar",
    "shopName": "Kumar Ration Shop",
    "shopLicense": "LIC2024001",
    "address": "456 Market Road, Delhi",
    "phoneNumber": "9123456789",
    "region": "North Delhi",
    "active": true,
    "createdAt": "2025-11-09T10:30:00",
    "updatedAt": "2025-11-09T10:30:00"
  }
}
```

---

### **3.2 Get Dealer by ID**
```http
GET /api/dealers/{id}
```

---

### **3.3 Get Dealer by Shop License**
```http
GET /api/dealers/license/{shopLicense}
```

**Example**:
```http
GET /api/dealers/license/LIC2024001
```

---

### **3.4 Get Dealer by User ID**
```http
GET /api/dealers/user/{userId}
```

**Example**:
```http
GET /api/dealers/user/3
```

---

### **3.5 Get All Dealers**
```http
GET /api/dealers
```

---

### **3.6 Get Dealers by Region**
```http
GET /api/dealers/region/{region}
```

**Example**:
```http
GET /api/dealers/region/North%20Delhi
```

---

### **3.7 Update Dealer**
```http
PUT /api/dealers/{id}
Content-Type: application/json

{
  "username": "updated_dealer",
  "email": "updated_dealer@example.com",
  "password": "NewPass@123",
  "fullName": "Rajesh Updated Kumar",
  "shopName": "Updated Ration Shop",
  "shopLicense": "LIC2024001",
  "address": "789 New Market Road, Delhi",
  "phoneNumber": "9123456790",
  "region": "Central Delhi"
}
```

---

### **3.8 Deactivate Dealer**
```http
PATCH /api/dealers/{id}/deactivate
```

---

### **3.9 Activate Dealer**
```http
PATCH /api/dealers/{id}/activate
```

---

### **3.10 Delete Dealer**
```http
DELETE /api/dealers/{id}
```

---

## 4️⃣ **Product Management APIs**

### Base URL: `/api/products`

### **4.1 Create Product**
```http
POST /api/products
Content-Type: application/json

{
  "name": "Rice",
  "category": "GRAIN",
  "unit": "KG",
  "pricePerUnit": 30.0,
  "description": "Premium quality rice"
}
```

**Response**:
```json
{
  "success": true,
  "message": "Product created successfully",
  "data": {
    "id": 1,
    "name": "Rice",
    "category": "GRAIN",
    "unit": "KG",
    "pricePerUnit": 30.0,
    "description": "Premium quality rice",
    "active": true,
    "createdAt": "2025-11-09T10:30:00",
    "updatedAt": "2025-11-09T10:30:00"
  }
}
```

---

### **4.2 Get Product by ID**
```http
GET /api/products/{id}
```

---

### **4.3 Get All Products**
```http
GET /api/products
```

---

### **4.4 Get Active Products**
```http
GET /api/products/active
```

---

### **4.5 Get Products by Category**
```http
GET /api/products/category/{category}
```

**Example**:
```http
GET /api/products/category/GRAIN
```

---

### **4.6 Update Product**
```http
PUT /api/products/{id}
Content-Type: application/json

{
  "name": "Premium Rice",
  "category": "GRAIN",
  "unit": "KG",
  "pricePerUnit": 35.0,
  "description": "Extra premium quality rice"
}
```

---

### **4.7 Delete Product**
```http
DELETE /api/products/{id}
```

---

## 5️⃣ **Inventory Management APIs**

### Base URL: `/api/inventory`

### **5.1 Add Stock**
```http
POST /api/inventory/add
Content-Type: application/json

{
  "dealerId": 1,
  "productId": 1,
  "quantity": 100.0,
  "notes": "Monthly stock allocation"
}
```

**Response**:
```json
{
  "success": true,
  "message": "Stock added successfully",
  "data": {
    "id": 1,
    "dealerId": 1,
    "dealerShopName": "Kumar Ration Shop",
    "productId": 1,
    "productName": "Rice",
    "currentStock": 100.0,
    "unit": "KG",
    "lastUpdated": "2025-11-09T10:30:00"
  }
}
```

---

### **5.2 Remove Stock**
```http
POST /api/inventory/remove
Content-Type: application/json

{
  "dealerId": 1,
  "productId": 1,
  "quantity": 10.0,
  "notes": "Sold to customer"
}
```

---

### **5.3 Get Inventory by Dealer ID**
```http
GET /api/inventory/dealer/{dealerId}
```

---

### **5.4 Get Specific Product Stock of Dealer**
```http
GET /api/inventory/dealer/{dealerId}/product/{productId}
```

---

### **5.5 Get Low Stock Items**
```http
GET /api/inventory/low-stock/{threshold}
```

**Example**:
```http
GET /api/inventory/low-stock/20
```

---

## 6️⃣ **Distribution Management APIs**

### Base URL: `/api/distribution`

### **6.1 Distribute Ration**
```http
POST /api/distribution/distribute
Content-Type: application/json

{
  "citizenId": 1,
  "dealerId": 1,
  "productId": 1,
  "quantity": 5.0,
  "distributionDate": "2025-11-09",
  "notes": "Monthly ration"
}
```

**Response**:
```json
{
  "success": true,
  "message": "Ration distributed successfully",
  "data": {
    "id": 1,
    "citizenId": 1,
    "citizenName": "John Doe",
    "rationCardNumber": "RAT123456",
    "dealerId": 1,
    "dealerShopName": "Kumar Ration Shop",
    "productId": 1,
    "productName": "Rice",
    "quantity": 5.0,
    "unit": "KG",
    "totalAmount": 150.0,
    "distributionDate": "2025-11-09",
    "notes": "Monthly ration",
    "distributedAt": "2025-11-09T10:30:00"
  }
}
```

---

### **6.2 Get Distribution by ID**
```http
GET /api/distribution/{id}
```

---

### **6.3 Get Distributions by Citizen ID**
```http
GET /api/distribution/citizen/{citizenId}
```

---

### **6.4 Get Distributions by Dealer ID**
```http
GET /api/distribution/dealer/{dealerId}
```

---

### **6.5 Get Distributions by Date Range**
```http
GET /api/distribution/date-range?startDate=2025-11-01&endDate=2025-11-30
```

---

### **6.6 Get All Distributions**
```http
GET /api/distribution
```

---

## 7️⃣ **Admin/Reporting APIs**

### Base URL: `/api/admin`

### **7.1 Get Dealer Report**
```http
GET /api/admin/reports/dealer/{dealerId}
```

**Response**:
```json
{
  "success": true,
  "message": "Report generated successfully",
  "data": {
    "dealerId": 1,
    "dealerName": "Kumar Ration Shop",
    "totalCitizensServed": 50,
    "totalDistributions": 150,
    "totalRevenue": 45000.0,
    "stockStatus": [
      {
        "productName": "Rice",
        "currentStock": 90.0,
        "unit": "KG"
      }
    ],
    "reportGeneratedAt": "2025-11-09T10:30:00"
  }
}
```

---

### **7.2 Get Citizen Report**
```http
GET /api/admin/reports/citizen/{citizenId}
```

---

### **7.3 Get Product Report**
```http
GET /api/admin/reports/product/{productId}
```

---

### **7.4 Get System Statistics**
```http
GET /api/admin/statistics
```

**Response**:
```json
{
  "success": true,
  "message": "Statistics fetched successfully",
  "data": {
    "totalCitizens": 500,
    "totalDealers": 50,
    "totalProducts": 10,
    "totalDistributions": 5000,
    "activeCitizens": 480,
    "activeDealers": 48,
    "totalRevenue": 1500000.0
  }
}
```

---

### **7.5 Get Stock Shortage Alerts**
```http
GET /api/admin/alerts/stock-shortage
```

---

### **7.6 Get Dealer Performance**
```http
GET /api/admin/performance/dealer/{dealerId}
```

---

## 8️⃣ **Prediction Service APIs**

### Base URL: `/api/predictions`

### **8.1 Get Demand Prediction for Dealer**
```http
POST /api/predictions/dealer/{dealerId}
Content-Type: application/json

{
  "productId": 1,
  "monthsAhead": 3
}
```

**Response**:
```json
{
  "success": true,
  "message": "Prediction generated successfully",
  "data": {
    "dealerId": 1,
    "dealerName": "Kumar Ration Shop",
    "productId": 1,
    "productName": "Rice",
    "currentStock": 90.0,
    "predictions": [
      {
        "month": "2025-12",
        "predictedDemand": 85.0,
        "confidence": 0.85
      },
      {
        "month": "2026-01",
        "predictedDemand": 90.0,
        "confidence": 0.80
      },
      {
        "month": "2026-02",
        "predictedDemand": 88.0,
        "confidence": 0.78
      }
    ],
    "recommendations": [
      "Stock 270 KG for next 3 months",
      "Current stock sufficient for 1 month"
    ]
  }
}
```

---

### **8.2 Get Overall Demand Prediction**
```http
GET /api/predictions/overall
```

---

## 🎯 Database Schema Summary

### **users**
- id (PK)
- username (unique)
- email (unique)
- password
- full_name
- role (ENUM: CITIZEN, DEALER, ADMIN)
- active (boolean)
- created_at
- updated_at

### **citizen_profiles**
- id (PK)
- user_id (FK → users.id, unique)
- ration_card_number (unique)
- address
- phone_number
- family_size
- category (ENUM: BPL, APL)
- dealer_id (FK → dealer_profiles.id, nullable)
- created_at
- updated_at

### **dealer_profiles**
- id (PK)
- user_id (FK → users.id, unique)
- shop_name
- shop_license (unique)
- address
- phone_number
- region
- active (boolean)
- created_at
- updated_at

### **products**
- id (PK)
- name
- category
- unit
- price_per_unit
- description
- active (boolean)
- created_at
- updated_at

### **inventory**
- id (PK)
- dealer_id (FK → dealer_profiles.id)
- product_id (FK → products.id)
- current_stock
- created_at
- updated_at

### **distributions**
- id (PK)
- citizen_id (FK → citizen_profiles.id)
- dealer_id (FK → dealer_profiles.id)
- product_id (FK → products.id)
- quantity
- total_amount
- distribution_date
- notes
- distributed_at

### **quotas** 🆕
- id (PK)
- product_id (FK → products.id)
- category (ENUM: BPL, APL)
- month (1-12)
- year (2024+)
- quota_per_citizen
- active (boolean)
- description
- created_at
- updated_at
- **Unique Constraint**: (product_id, category, month, year)

---

## 9️⃣ **Quota Management APIs** 🆕

### Base URL: `/api/quotas`

Quota Management System allows tracking monthly product entitlements for citizens based on their category (BPL/APL).

---

### **9.1 Create Quota (Admin)**
```http
POST /api/quotas
Content-Type: application/json

{
  "productId": 1,
  "category": "BPL",
  "month": 11,
  "year": 2024,
  "quotaPerCitizen": 10.0,
  "description": "November 2024 Rice quota for BPL citizens"
}
```

**Response**:
```json
{
  "id": 1,
  "productId": 1,
  "productName": "Rice",
  "category": "BPL",
  "month": 11,
  "year": 2024,
  "quotaPerCitizen": 10.0,
  "unit": "kg",
  "active": true,
  "description": "November 2024 Rice quota for BPL citizens",
  "createdAt": "2024-11-01T10:00:00",
  "updatedAt": "2024-11-01T10:00:00"
}
```

---

### **9.2 Get Quota by ID**
```http
GET /api/quotas/{id}
```

**Example**:
```http
GET /api/quotas/1
```

**Response**:
```json
{
  "id": 1,
  "productId": 1,
  "productName": "Rice",
  "category": "BPL",
  "month": 11,
  "year": 2024,
  "quotaPerCitizen": 10.0,
  "unit": "kg",
  "active": true,
  "description": "November 2024 Rice quota for BPL citizens",
  "createdAt": "2024-11-01T10:00:00",
  "updatedAt": "2024-11-01T10:00:00"
}
```

---

### **9.3 Get All Quotas**
```http
GET /api/quotas
```

**Response**:
```json
[
  {
    "id": 1,
    "productId": 1,
    "productName": "Rice",
    "category": "BPL",
    "month": 11,
    "year": 2024,
    "quotaPerCitizen": 10.0,
    "unit": "kg",
    "active": true,
    "createdAt": "2024-11-01T10:00:00"
  },
  {
    "id": 2,
    "productId": 1,
    "productName": "Rice",
    "category": "APL",
    "month": 11,
    "year": 2024,
    "quotaPerCitizen": 5.0,
    "unit": "kg",
    "active": true,
    "createdAt": "2024-11-01T10:00:00"
  }
]
```

---

### **9.4 Get Quotas by Month and Year**
```http
GET /api/quotas/month/{month}/year/{year}
```

**Example**:
```http
GET /api/quotas/month/11/year/2024
```

**Response**: List of quotas for November 2024

---

### **9.5 Get Active Quotas**
```http
GET /api/quotas/active
```

**Response**: List of all active quotas

---

### **9.6 Update Quota (Admin)**
```http
PUT /api/quotas/{id}
Content-Type: application/json

{
  "productId": 1,
  "category": "BPL",
  "month": 11,
  "year": 2024,
  "quotaPerCitizen": 12.0,
  "description": "Updated quota for BPL citizens"
}
```

**Response**: Updated quota details

---

### **9.7 Delete Quota (Admin)**
```http
DELETE /api/quotas/{id}
```

**Example**:
```http
DELETE /api/quotas/1
```

**Response**: 204 No Content

---

### **9.8 Toggle Quota Status (Admin)**
```http
PATCH /api/quotas/{id}/toggle-status
```

**Example**:
```http
PATCH /api/quotas/1/toggle-status
```

**Response**:
```json
{
  "id": 1,
  "productId": 1,
  "productName": "Rice",
  "category": "BPL",
  "month": 11,
  "year": 2024,
  "quotaPerCitizen": 10.0,
  "active": false,
  "updatedAt": "2024-11-05T14:30:00"
}
```

---

### **9.9 Get Citizen Quota Status for Specific Month** 🌟
```http
GET /api/quotas/citizen/{rationCardNumber}/status?month={month}&year={year}
```

**Example**:
```http
GET /api/quotas/citizen/RC2024001/status?month=11&year=2024
```

**Response**:
```json
[
  {
    "productId": 1,
    "productName": "Rice",
    "unit": "kg",
    "category": "BPL",
    "month": 11,
    "year": 2024,
    "totalQuota": 10.0,
    "redeemedQuantity": 5.0,
    "remainingQuota": 5.0,
    "percentageUsed": 50.0,
    "status": "AVAILABLE"
  },
  {
    "productId": 2,
    "productName": "Wheat",
    "unit": "kg",
    "category": "BPL",
    "month": 11,
    "year": 2024,
    "totalQuota": 8.0,
    "redeemedQuantity": 8.0,
    "remainingQuota": 0.0,
    "percentageUsed": 100.0,
    "status": "EXHAUSTED"
  }
]
```

**Status Values**:
- `AVAILABLE` - Quota available
- `ALMOST_EXHAUSTED` - 90% or more used
- `EXHAUSTED` - No quota remaining

---

### **9.10 Get Citizen Current Month Quota Status** 🌟
```http
GET /api/quotas/citizen/{rationCardNumber}/current
```

**Example**:
```http
GET /api/quotas/citizen/RC2024001/current
```

**Response**:
```json
[
  {
    "productId": 1,
    "productName": "Rice",
    "unit": "kg",
    "category": "BPL",
    "month": 11,
    "year": 2024,
    "totalQuota": 10.0,
    "redeemedQuantity": 5.0,
    "remainingQuota": 5.0,
    "percentageUsed": 50.0,
    "status": "AVAILABLE"
  },
  {
    "productId": 3,
    "productName": "Sugar",
    "unit": "kg",
    "category": "BPL",
    "month": 11,
    "year": 2024,
    "totalQuota": 2.0,
    "redeemedQuantity": 2.0,
    "remainingQuota": 0.0,
    "percentageUsed": 100.0,
    "status": "EXHAUSTED"
  }
]
```

**Use Case**: Citizens check their remaining monthly quota before visiting dealer

---

### **Quota System Features**:
- ✅ Monthly product entitlements per category (BPL/APL)
- ✅ Real-time tracking of redeemed quantity
- ✅ Automatic validation during distribution
- ✅ Prevents over-distribution beyond monthly limits
- ✅ Transparent balance checking for citizens

---

## ✅ Key Benefits of This Architecture

1. **Single Login**: All users (citizen/dealer/admin) authenticate the same way
2. **Role-Based Access**: Easy to implement authorization
3. **Profile Extension**: Clean separation of auth and profile data
4. **Scalability**: Easy to add new roles (e.g., WAREHOUSE_MANAGER)
5. **Data Integrity**: No duplicate credentials
6. **Maintainability**: Centralized user management

---

## 🔐 Security Notes

- ⚠️ **Passwords should be hashed** (use BCrypt in production)
- 🔑 **Add JWT authentication** for secure API access
- 🛡️ **Implement role-based access control** (@PreAuthorize annotations)
- 🔒 **Use HTTPS** in production
- ✅ **Validate all inputs** (already done with @Valid)

---

## 🚀 Next Steps

1. **Add Authentication Service** (Login/Logout/Token Generation)
2. **Implement JWT Security**
3. **Add Role-Based Authorization**
4. **Integrate with Spring Security**
5. **Add Pagination for List APIs**
6. **Implement Audit Logging**

---

**✨ Your E-Ration System is now properly architected with clean separation of concerns!**
