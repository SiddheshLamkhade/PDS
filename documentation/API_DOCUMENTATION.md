# 📚 E-Ration System - Complete API Documentation

## Base URL
```
http://localhost:8081/api
```

---

## 🔐 Authentication
> **Note:** Security is not implemented yet. All endpoints are currently open.

---

# 📋 Table of Contents
1. [User Management APIs](#1-user-management-apis)
   - [Citizen APIs](#citizen-apis)
   - [Dealer APIs](#dealer-apis)
2. [Product Management APIs](#2-product-management-apis)
3. [Inventory Management APIs](#3-inventory-management-apis)
4. [Distribution APIs](#4-distribution-apis)
5. [Prediction APIs](#5-prediction-apis)
6. [Admin & Reporting APIs](#6-admin--reporting-apis)

---

# 1. User Management APIs

## Citizen APIs

### 1.1 Create Citizen
**POST** `/api/users/citizens`

**Request Body:**
```json
{
  "rationCardNumber": "RC12345678",
  "name": "Rahul Sharma",
  "address": "123, MG Road, Pune, Maharashtra",
  "phoneNumber": "9876543210",
  "familySize": 4,
  "category": "BPL",
  "dealerId": 1
}
```

**Response (201 Created):**
```json
{
  "success": true,
  "message": "Citizen created successfully",
  "data": {
    "id": 1,
    "rationCardNumber": "RC12345678",
    "name": "Rahul Sharma",
    "address": "123, MG Road, Pune, Maharashtra",
    "phoneNumber": "9876543210",
    "familySize": 4,
    "category": "BPL",
    "dealerId": 1,
    "dealerName": "Sai Ration Shop",
    "createdAt": "2025-11-09T10:30:00",
    "updatedAt": "2025-11-09T10:30:00"
  }
}
```

---

### 1.2 Get Citizen by ID
**GET** `/api/users/citizens/{id}`

**Example:** `/api/users/citizens/1`

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Citizen retrieved successfully",
  "data": {
    "id": 1,
    "rationCardNumber": "RC12345678",
    "name": "Rahul Sharma",
    "address": "123, MG Road, Pune, Maharashtra",
    "phoneNumber": "9876543210",
    "familySize": 4,
    "category": "BPL",
    "dealerId": 1,
    "dealerName": "Sai Ration Shop",
    "createdAt": "2025-11-09T10:30:00",
    "updatedAt": "2025-11-09T10:30:00"
  }
}
```

---

### 1.3 Get Citizen by Ration Card Number
**GET** `/api/users/citizens/ration-card/{rationCardNumber}`

**Example:** `/api/users/citizens/ration-card/RC12345678`

**Response (200 OK):** Same as 1.2

---

### 1.4 Get All Citizens
**GET** `/api/users/citizens`

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Citizens retrieved successfully",
  "data": [
    {
      "id": 1,
      "rationCardNumber": "RC12345678",
      "name": "Rahul Sharma",
      "familySize": 4,
      "category": "BPL",
      "dealerId": 1,
      "dealerName": "Sai Ration Shop"
    },
    {
      "id": 2,
      "rationCardNumber": "RC87654321",
      "name": "Priya Gupta",
      "familySize": 3,
      "category": "APL",
      "dealerId": 1,
      "dealerName": "Sai Ration Shop"
    }
  ]
}
```

---

### 1.5 Get Citizens by Dealer
**GET** `/api/users/citizens/dealer/{dealerId}`

**Example:** `/api/users/citizens/dealer/1`

**Response (200 OK):** Same structure as 1.4

---

### 1.6 Update Citizen
**PUT** `/api/users/citizens/{id}`

**Example:** `/api/users/citizens/1`

**Request Body:**
```json
{
  "rationCardNumber": "RC12345678",
  "name": "Rahul Kumar Sharma",
  "address": "456, New Address, Pune",
  "phoneNumber": "9876543210",
  "familySize": 5,
  "category": "BPL",
  "dealerId": 1
}
```

**Response (200 OK):** Same structure as 1.1

---

### 1.7 Delete Citizen
**DELETE** `/api/users/citizens/{id}`

**Example:** `/api/users/citizens/1`

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Citizen deleted successfully",
  "data": null
}
```

---

## Dealer APIs

### 1.8 Create Dealer
**POST** `/api/users/dealers`

**Request Body:**
```json
{
  "shopName": "Sai Ration Shop",
  "shopLicense": "LIC2025001",
  "ownerName": "Suresh Patil",
  "address": "Market Road, Pune, Maharashtra",
  "phoneNumber": "9123456789",
  "region": "Pune Central"
}
```

**Response (201 Created):**
```json
{
  "success": true,
  "message": "Dealer created successfully",
  "data": {
    "id": 1,
    "shopName": "Sai Ration Shop",
    "shopLicense": "LIC2025001",
    "ownerName": "Suresh Patil",
    "address": "Market Road, Pune, Maharashtra",
    "phoneNumber": "9123456789",
    "region": "Pune Central",
    "active": true,
    "createdAt": "2025-11-09T09:00:00",
    "updatedAt": "2025-11-09T09:00:00"
  }
}
```

---

### 1.9 Get Dealer by ID
**GET** `/api/users/dealers/{id}`

**Example:** `/api/users/dealers/1`

**Response (200 OK):** Same structure as 1.8

---

### 1.10 Get All Dealers
**GET** `/api/users/dealers`

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Dealers retrieved successfully",
  "data": [
    {
      "id": 1,
      "shopName": "Sai Ration Shop",
      "shopLicense": "LIC2025001",
      "ownerName": "Suresh Patil",
      "region": "Pune Central",
      "active": true
    }
  ]
}
```

---

### 1.11 Get Dealers by Region
**GET** `/api/users/dealers/region/{region}`

**Example:** `/api/users/dealers/region/Pune Central`

**Response (200 OK):** Same structure as 1.10

---

### 1.12 Update Dealer
**PUT** `/api/users/dealers/{id}`

**Request Body:** Same as 1.8
**Response (200 OK):** Same structure as 1.8

---

### 1.13 Delete Dealer
**DELETE** `/api/users/dealers/{id}`

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Dealer deleted successfully",
  "data": null
}
```

---

# 2. Product Management APIs

### 2.1 Create Product
**POST** `/api/products`

**Request Body:**
```json
{
  "productName": "Rice",
  "unit": "KG",
  "pricePerUnit": 25.50,
  "category": "GRAIN"
}
```

**Response (201 Created):**
```json
{
  "success": true,
  "message": "Product created successfully",
  "data": {
    "id": 1,
    "productName": "Rice",
    "unit": "KG",
    "pricePerUnit": 25.50,
    "category": "GRAIN",
    "active": true,
    "createdAt": "2025-11-09T08:00:00",
    "updatedAt": "2025-11-09T08:00:00"
  }
}
```

---

### 2.2 Get Product by ID
**GET** `/api/products/{id}`

**Example:** `/api/products/1`

**Response (200 OK):** Same structure as 2.1

---

### 2.3 Get All Products
**GET** `/api/products`

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Products retrieved successfully",
  "data": [
    {
      "id": 1,
      "productName": "Rice",
      "unit": "KG",
      "pricePerUnit": 25.50,
      "category": "GRAIN",
      "active": true
    },
    {
      "id": 2,
      "productName": "Wheat",
      "unit": "KG",
      "pricePerUnit": 20.00,
      "category": "GRAIN",
      "active": true
    },
    {
      "id": 3,
      "productName": "Sugar",
      "unit": "KG",
      "pricePerUnit": 35.00,
      "category": "SUGAR",
      "active": true
    }
  ]
}
```

---

### 2.4 Get Active Products
**GET** `/api/products/active`

**Response (200 OK):** Same structure as 2.3 (only active products)

---

### 2.5 Get Products by Category
**GET** `/api/products/category/{category}`

**Example:** `/api/products/category/GRAIN`

**Response (200 OK):** Same structure as 2.3 (filtered by category)

---

### 2.6 Update Product
**PUT** `/api/products/{id}`

**Request Body:** Same as 2.1
**Response (200 OK):** Same structure as 2.1

---

### 2.7 Delete Product
**DELETE** `/api/products/{id}`

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Product deleted successfully",
  "data": null
}
```

---

### 2.8 Toggle Product Status (Active/Inactive)
**PATCH** `/api/products/{id}/toggle-status`

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Product status toggled successfully",
  "data": {
    "id": 1,
    "productName": "Rice",
    "active": false
  }
}
```

---

# 3. Inventory Management APIs

### 3.1 Add Stock
**POST** `/api/inventory/add-stock`

**Request Body:**
```json
{
  "dealerId": 1,
  "productId": 1,
  "quantity": 500.0
}
```

**Response (201 Created):**
```json
{
  "success": true,
  "message": "Stock added successfully",
  "data": {
    "id": 1,
    "dealerId": 1,
    "dealerName": "Sai Ration Shop",
    "productId": 1,
    "productName": "Rice",
    "currentStock": 500.0,
    "openingStock": 0.0,
    "stockReceived": 500.0,
    "stockDistributed": 0.0,
    "lastUpdated": "2025-11-09T11:00:00"
  }
}
```

---

### 3.2 Check Stock
**GET** `/api/inventory/check-stock?dealerId={dealerId}&productId={productId}`

**Example:** `/api/inventory/check-stock?dealerId=1&productId=1`

**Response (200 OK):** Same structure as 3.1

---

### 3.3 Get Inventory by Dealer
**GET** `/api/inventory/dealer/{dealerId}`

**Example:** `/api/inventory/dealer/1`

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Inventory retrieved successfully",
  "data": [
    {
      "id": 1,
      "dealerId": 1,
      "dealerName": "Sai Ration Shop",
      "productId": 1,
      "productName": "Rice",
      "currentStock": 450.0,
      "stockReceived": 500.0,
      "stockDistributed": 50.0
    },
    {
      "id": 2,
      "dealerId": 1,
      "dealerName": "Sai Ration Shop",
      "productId": 2,
      "productName": "Wheat",
      "currentStock": 300.0,
      "stockReceived": 300.0,
      "stockDistributed": 0.0
    }
  ]
}
```

---

### 3.4 Get All Inventory
**GET** `/api/inventory`

**Response (200 OK):** Same structure as 3.3 (all dealers)

---

### 3.5 Get Low Stock Alerts
**GET** `/api/inventory/low-stock?threshold={threshold}`

**Example:** `/api/inventory/low-stock?threshold=50.0`

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Low stock alerts retrieved successfully",
  "data": [
    {
      "id": 3,
      "dealerId": 2,
      "dealerName": "Krishna Store",
      "productId": 1,
      "productName": "Rice",
      "currentStock": 30.0
    }
  ]
}
```

---

### 3.6 Get Low Stock Alerts by Dealer
**GET** `/api/inventory/low-stock/dealer/{dealerId}?threshold={threshold}`

**Example:** `/api/inventory/low-stock/dealer/1?threshold=50.0`

**Response (200 OK):** Same structure as 3.5

---

# 4. Distribution APIs

### 4.1 Distribute Ration
**POST** `/api/distributions/distribute`

**Request Body:**
```json
{
  "rationCardNumber": "RC12345678",
  "dealerId": 1,
  "productId": 1,
  "quantity": 10.0,
  "remarks": "November month ration"
}
```

**Response (201 Created):**
```json
{
  "success": true,
  "message": "Ration distributed successfully",
  "data": {
    "id": 1,
    "citizenId": 1,
    "citizenName": "Rahul Sharma",
    "rationCardNumber": "RC12345678",
    "dealerId": 1,
    "dealerName": "Sai Ration Shop",
    "productId": 1,
    "productName": "Rice",
    "quantity": 10.0,
    "totalAmount": 255.0,
    "distributionDate": "2025-11-09T12:00:00",
    "status": "COMPLETED",
    "remarks": "November month ration"
  }
}
```

---

### 4.2 Get Distribution by ID
**GET** `/api/distributions/{id}`

**Example:** `/api/distributions/1`

**Response (200 OK):** Same structure as 4.1

---

### 4.3 Get All Distributions
**GET** `/api/distributions`

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Distributions retrieved successfully",
  "data": [
    {
      "id": 1,
      "citizenName": "Rahul Sharma",
      "rationCardNumber": "RC12345678",
      "dealerName": "Sai Ration Shop",
      "productName": "Rice",
      "quantity": 10.0,
      "totalAmount": 255.0,
      "distributionDate": "2025-11-09T12:00:00",
      "status": "COMPLETED"
    }
  ]
}
```

---

### 4.4 Get Distributions by Citizen ID
**GET** `/api/distributions/citizen/{citizenId}`

**Example:** `/api/distributions/citizen/1`

**Response (200 OK):** Same structure as 4.3

---

### 4.5 Get Distributions by Ration Card Number
**GET** `/api/distributions/ration-card/{rationCardNumber}`

**Example:** `/api/distributions/ration-card/RC12345678`

**Response (200 OK):** Same structure as 4.3

---

### 4.6 Get Distributions by Dealer
**GET** `/api/distributions/dealer/{dealerId}`

**Example:** `/api/distributions/dealer/1`

**Response (200 OK):** Same structure as 4.3

---

### 4.7 Get Distributions by Date Range
**GET** `/api/distributions/date-range?startDate={startDate}&endDate={endDate}`

**Example:** `/api/distributions/date-range?startDate=2025-11-01T00:00:00&endDate=2025-11-30T23:59:59`

**Response (200 OK):** Same structure as 4.3

---

# 5. Prediction APIs

### 5.1 Generate Prediction
**POST** `/api/predictions/generate`

**Request Body:**
```json
{
  "dealerId": 1,
  "productId": 1,
  "predictionMonth": "2025-12"
}
```

**Response (201 Created):**
```json
{
  "success": true,
  "message": "Prediction generated successfully",
  "data": {
    "id": 1,
    "dealerId": 1,
    "dealerName": "Sai Ration Shop",
    "productId": 1,
    "productName": "Rice",
    "predictedDemand": 150.0,
    "predictionMonth": "2025-12",
    "algorithm": "SIMPLE_AVERAGE",
    "generatedAt": "2025-11-09T14:00:00"
  }
}
```

---

### 5.2 Generate Predictions for All Products (Dealer)
**POST** `/api/predictions/generate-for-dealer?dealerId={dealerId}&predictionMonth={month}`

**Example:** `/api/predictions/generate-for-dealer?dealerId=1&predictionMonth=2025-12`

**Response (201 Created):**
```json
{
  "success": true,
  "message": "Predictions generated successfully for all products",
  "data": [
    {
      "id": 1,
      "dealerId": 1,
      "productName": "Rice",
      "predictedDemand": 150.0,
      "predictionMonth": "2025-12"
    },
    {
      "id": 2,
      "dealerId": 1,
      "productName": "Wheat",
      "predictedDemand": 120.0,
      "predictionMonth": "2025-12"
    }
  ]
}
```

---

### 5.3 Get Prediction by ID
**GET** `/api/predictions/{id}`

**Response (200 OK):** Same structure as 5.1

---

### 5.4 Get Predictions by Dealer
**GET** `/api/predictions/dealer/{dealerId}`

**Example:** `/api/predictions/dealer/1`

**Response (200 OK):** Array of predictions (structure as 5.1)

---

### 5.5 Get Predictions by Month
**GET** `/api/predictions/month/{predictionMonth}`

**Example:** `/api/predictions/month/2025-12`

**Response (200 OK):** Array of predictions

---

### 5.6 Get All Predictions
**GET** `/api/predictions`

**Response (200 OK):** Array of all predictions

---

# 6. Admin & Reporting APIs

### 6.1 Get Dashboard Statistics
**GET** `/api/admin/dashboard/stats`

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Dashboard statistics retrieved successfully",
  "data": {
    "totalCitizens": 150,
    "totalDealers": 25,
    "totalProducts": 8,
    "totalDistributions": 450,
    "activeDealers": 24,
    "activeProducts": 8
  }
}
```

---

### 6.2 Get Dealer Report
**GET** `/api/admin/reports/dealer/{dealerId}`

**Example:** `/api/admin/reports/dealer/1`

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Dealer report generated successfully",
  "data": {
    "dealerId": 1,
    "shopName": "Sai Ration Shop",
    "ownerName": "Suresh Patil",
    "region": "Pune Central",
    "totalDistributions": 45,
    "totalRevenue": 11475.50,
    "inventoryItems": 5,
    "totalCurrentStock": 1200.0,
    "linkedCitizens": 30
  }
}
```

---

### 6.3 Get Dealer Distributions
**GET** `/api/admin/reports/dealer/{dealerId}/distributions`

**Response (200 OK):** Array of distributions

---

### 6.4 Get Product Report
**GET** `/api/admin/reports/product/{productId}`

**Example:** `/api/admin/reports/product/1`

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Product report generated successfully",
  "data": {
    "productId": 1,
    "productName": "Rice",
    "unit": "KG",
    "pricePerUnit": 25.50,
    "category": "GRAIN",
    "totalDistributions": 120,
    "totalQuantityDistributed": 1500.0
  }
}
```

---

### 6.5 Get Citizen Report
**GET** `/api/admin/reports/citizen/{rationCardNumber}`

**Example:** `/api/admin/reports/citizen/RC12345678`

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Citizen report generated successfully",
  "data": {
    "citizenId": 1,
    "name": "Rahul Sharma",
    "rationCardNumber": "RC12345678",
    "familySize": 4,
    "category": "BPL",
    "totalDistributions": 8,
    "totalAmountPaid": 2040.0,
    "distributionHistory": [
      {
        "productName": "Rice",
        "quantity": 10.0,
        "totalAmount": 255.0,
        "distributionDate": "2025-11-09T12:00:00"
      }
    ]
  }
}
```

---

### 6.6 Get Low Stock Alerts
**GET** `/api/admin/alerts/low-stock`

**Response (200 OK):** Array of low stock items

---

### 6.7 Get Low Stock Alerts by Dealer
**GET** `/api/admin/alerts/low-stock/dealer/{dealerId}`

**Response (200 OK):** Array of low stock items for specific dealer

---

### 6.8 Get Distributions by Date Range
**GET** `/api/admin/reports/distributions/date-range?startDate={start}&endDate={end}`

**Response (200 OK):** Array of distributions

---

### 6.9 Get Monthly Report
**GET** `/api/admin/reports/monthly/{year}/{month}`

**Example:** `/api/admin/reports/monthly/2025/11`

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Monthly report generated successfully",
  "data": {
    "month": "2025-11",
    "totalDistributions": 150,
    "totalRevenue": 38250.0,
    "distributions": []
  }
}
```

---

### 6.10 Get Category-wise Report
**GET** `/api/admin/reports/category/{category}`

**Example:** `/api/admin/reports/category/BPL`

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Category-wise report generated successfully",
  "data": {
    "category": "BPL",
    "totalCitizens": 85,
    "citizens": [
      {
        "id": 1,
        "name": "Rahul Sharma",
        "rationCardNumber": "RC12345678",
        "familySize": 4
      }
    ]
  }
}
```

---

# 📊 Error Response Format

All error responses follow this structure:

```json
{
  "success": false,
  "message": "Error description here",
  "data": null
}
```

**Common HTTP Status Codes:**
- `200 OK` - Success
- `201 Created` - Resource created successfully
- `400 Bad Request` - Invalid request data
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server error

---

# 🧪 Testing with Postman/cURL

## Example cURL Commands:

### Create a Dealer:
```bash
curl -X POST http://localhost:8081/api/users/dealers \
  -H "Content-Type: application/json" \
  -d '{
    "shopName": "Sai Ration Shop",
    "shopLicense": "LIC2025001",
    "ownerName": "Suresh Patil",
    "address": "Market Road, Pune",
    "phoneNumber": "9123456789",
    "region": "Pune Central"
  }'
```

### Add Stock:
```bash
curl -X POST http://localhost:8081/api/inventory/add-stock \
  -H "Content-Type: application/json" \
  -d '{
    "dealerId": 1,
    "productId": 1,
    "quantity": 500.0
  }'
```

### Distribute Ration:
```bash
curl -X POST http://localhost:8081/api/distributions/distribute \
  -H "Content-Type: application/json" \
  -d '{
    "rationCardNumber": "RC12345678",
    "dealerId": 1,
    "productId": 1,
    "quantity": 10.0,
    "remarks": "November ration"
  }'
```

---

# 📝 Notes

1. **Date Format**: All dates use ISO-8601 format: `YYYY-MM-DDTHH:mm:ss`
2. **Prediction Month Format**: Use `YYYY-MM` (e.g., `2025-12`)
3. **Category Values**: `BPL` or `APL` (case-insensitive, stored as uppercase)
4. **Product Categories**: `GRAIN`, `OIL`, `SUGAR`, etc. (can be custom)
5. **Phone Numbers**: Must be exactly 10 digits
6. **Stock Quantities**: All quantities are in Double (decimal numbers)

---

✅ **All endpoints are ready for testing!**
