# Quota Management System - Citizen Monthly Entitlement Tracking

## Overview

The Quota Management System allows the E-Ration PDS to:
- Set monthly product quotas for BPL and APL citizens
- Track how much citizens have redeemed each month
- Show remaining balance before distribution
- Prevent over-distribution beyond monthly limits

---

## How It Works

### 1. Admin Sets Monthly Quotas

Admin defines how much of each product citizens can receive per month based on their category (BPL/APL).

**Example Quotas for January 2024:**

| Product | Category | Quota Per Citizen | Unit |
|---------|----------|-------------------|------|
| Rice | BPL | 10.0 | kg |
| Rice | APL | 5.0 | kg |
| Wheat | BPL | 8.0 | kg |
| Wheat | APL | 4.0 | kg |
| Sugar | BPL | 2.0 | kg |
| Sugar | APL | 1.0 | kg |
| Kerosene | BPL | 5.0 | litre |
| Kerosene | APL | 2.0 | litre |

**Key Points:**
- BPL (Below Poverty Line) citizens get higher quotas
- APL (Above Poverty Line) citizens get lower quotas
- Quotas are set per product, per category, per month

---

### 2. Citizen Checks Current Month Balance

Before visiting the dealer, citizens can check their remaining quota using their ration card number.

**API Endpoint:**
```
GET /api/quotas/citizen/{rationCardNumber}/current
```

**Sample Request:**
```
GET /api/quotas/citizen/RC2024001/current
```

**Sample Response:**
```json
[
  {
    "productId": 1,
    "productName": "Rice",
    "unit": "kg",
    "category": "BPL",
    "month": 1,
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
    "month": 1,
    "year": 2024,
    "totalQuota": 8.0,
    "redeemedQuantity": 8.0,
    "remainingQuota": 0.0,
    "percentageUsed": 100.0,
    "status": "EXHAUSTED"
  },
  {
    "productId": 3,
    "productName": "Sugar",
    "unit": "kg",
    "category": "BPL",
    "month": 1,
    "year": 2024,
    "totalQuota": 2.0,
    "redeemedQuantity": 0.0,
    "remainingQuota": 2.0,
    "percentageUsed": 0.0,
    "status": "AVAILABLE"
  }
]
```

**Status Values:**
- `AVAILABLE` - Quota available for distribution
- `ALMOST_EXHAUSTED` - 90% or more used (warning)
- `EXHAUSTED` - No quota remaining (cannot distribute)

---

### 3. Distribution with Quota Validation

When a citizen requests ration, the system:
1. Checks dealer's stock availability
2. **Checks citizen's remaining monthly quota** ✨ NEW
3. Only distributes if both conditions are met

**Distribution Flow:**

```
Citizen John (BPL, RC2024001) requests 6 kg Rice on Jan 10, 2024

Step 1: Check Rice quota for BPL citizens in January 2024
        → Total Quota: 10 kg

Step 2: Calculate John's redeemed quantity in January 2024
        → Already redeemed: 5 kg (from previous visit on Jan 5)

Step 3: Calculate remaining quota
        → Remaining: 10 - 5 = 5 kg

Step 4: Validate requested quantity
        → Requested: 6 kg
        → Remaining: 5 kg
        → 6 > 5 ❌ REJECTED

Error: "Quota exceeded for this month. Check your monthly quota balance."
```

**If John requests 4 kg instead:**
```
Step 4: Validate requested quantity
        → Requested: 4 kg
        → Remaining: 5 kg
        → 4 ≤ 5 ✅ APPROVED

Distribution successful!
New balance: 10 - (5 + 4) = 1 kg remaining
```

---

## Quota Table Structure

### Database: `quotas` table

| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT | Primary key |
| product_id | BIGINT | Foreign key to products table |
| category | VARCHAR(10) | BPL or APL |
| month | INT | Month (1-12) |
| year | INT | Year (2024+) |
| quota_per_citizen | DOUBLE | Maximum quantity per citizen |
| active | BOOLEAN | Is this quota active? |
| description | VARCHAR(500) | Optional notes |
| created_at | DATETIME | Creation timestamp |
| updated_at | DATETIME | Last update timestamp |

**Unique Constraint:** (product_id, category, month, year) - Only one quota per product per category per month

---

## Complete API Endpoints

### Admin Operations

#### 1. Create Monthly Quota
```
POST /api/quotas
Content-Type: application/json

{
  "productId": 1,
  "category": "BPL",
  "month": 1,
  "year": 2024,
  "quotaPerCitizen": 10.0,
  "description": "January 2024 Rice quota for BPL citizens"
}
```

#### 2. Get All Quotas
```
GET /api/quotas
```

#### 3. Get Quotas by Month and Year
```
GET /api/quotas/month/1/year/2024
```

#### 4. Get Active Quotas
```
GET /api/quotas/active
```

#### 5. Update Quota
```
PUT /api/quotas/{id}
Content-Type: application/json

{
  "productId": 1,
  "category": "BPL",
  "month": 1,
  "year": 2024,
  "quotaPerCitizen": 12.0,
  "description": "Updated quota"
}
```

#### 6. Delete Quota
```
DELETE /api/quotas/{id}
```

#### 7. Toggle Quota Status (Activate/Deactivate)
```
PATCH /api/quotas/{id}/toggle-status
```

### Citizen Operations

#### 8. Get Current Month Quota Status
```
GET /api/quotas/citizen/{rationCardNumber}/current

Example: GET /api/quotas/citizen/RC2024001/current
```

**Response:**
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
  }
]
```

#### 9. Get Specific Month Quota Status
```
GET /api/quotas/citizen/{rationCardNumber}/status?month=1&year=2024

Example: GET /api/quotas/citizen/RC2024001/status?month=1&year=2024
```

---

## Workflow with Sample Data

### Initial Setup (Admin)

**Step 1: Admin creates quotas for January 2024**

```sql
-- quotas table
INSERT INTO quotas (product_id, category, month, year, quota_per_citizen, active)
VALUES 
  (1, 'BPL', 1, 2024, 10.0, true),  -- Rice for BPL
  (1, 'APL', 1, 2024, 5.0, true),   -- Rice for APL
  (2, 'BPL', 1, 2024, 8.0, true),   -- Wheat for BPL
  (2, 'APL', 1, 2024, 4.0, true);   -- Wheat for APL
```

| id | product_id | category | month | year | quota_per_citizen | active |
|----|------------|----------|-------|------|-------------------|--------|
| 1 | 1 | BPL | 1 | 2024 | 10.0 | true |
| 2 | 1 | APL | 1 | 2024 | 5.0 | true |
| 3 | 2 | BPL | 1 | 2024 | 8.0 | true |
| 4 | 2 | APL | 1 | 2024 | 4.0 | true |

---

### Citizen Scenario 1: John (BPL) - Rice Distribution

**Citizen:** John Doe  
**Ration Card:** RC2024001  
**Category:** BPL  
**Month:** January 2024

**January 5, 2024 - First Visit:**
```
John requests: 5 kg Rice
System checks:
  - Total Rice quota for BPL: 10 kg
  - John's redeemed so far: 0 kg
  - Remaining: 10 kg
  - 5 kg ≤ 10 kg ✅ APPROVED

Distribution created:
  - Quantity: 5 kg
  - Amount: ₹150 (5 × ₹30)
  
New balance: 10 - 5 = 5 kg remaining
```

**January 15, 2024 - Second Visit:**
```
John requests: 4 kg Rice
System checks:
  - Total Rice quota for BPL: 10 kg
  - John's redeemed so far: 5 kg
  - Remaining: 5 kg
  - 4 kg ≤ 5 kg ✅ APPROVED

Distribution created:
  - Quantity: 4 kg
  - Amount: ₹120 (4 × ₹30)
  
New balance: 10 - 9 = 1 kg remaining
```

**January 25, 2024 - Third Visit:**
```
John requests: 3 kg Rice
System checks:
  - Total Rice quota for BPL: 10 kg
  - John's redeemed so far: 9 kg
  - Remaining: 1 kg
  - 3 kg > 1 kg ❌ REJECTED

Error: "Quota exceeded for this month. Check your monthly quota balance."
```

**John checks his balance:**
```
GET /api/quotas/citizen/RC2024001/current

Response:
{
  "productName": "Rice",
  "totalQuota": 10.0,
  "redeemedQuantity": 9.0,
  "remainingQuota": 1.0,
  "percentageUsed": 90.0,
  "status": "ALMOST_EXHAUSTED"
}
```

---

### Citizen Scenario 2: Priya (APL) - Rice Distribution

**Citizen:** Priya Sharma  
**Ration Card:** RC2024002  
**Category:** APL  
**Month:** January 2024

**January 10, 2024:**
```
Priya requests: 5 kg Rice
System checks:
  - Total Rice quota for APL: 5 kg (lower than BPL)
  - Priya's redeemed so far: 0 kg
  - Remaining: 5 kg
  - 5 kg ≤ 5 kg ✅ APPROVED

Distribution created:
  - Quantity: 5 kg
  - Amount: ₹150

New balance: 5 - 5 = 0 kg remaining
```

**January 20, 2024:**
```
Priya requests: 2 kg Rice
System checks:
  - Total Rice quota for APL: 5 kg
  - Priya's redeemed so far: 5 kg
  - Remaining: 0 kg
  - 2 kg > 0 kg ❌ REJECTED

Error: "Quota exceeded for this month. Check your monthly quota balance."
```

---

### Monthly Distribution Summary

**distributions table (January 2024):**

| id | citizen_id | ration_card | product | quantity | date | citizen_category |
|----|------------|-------------|---------|----------|------|------------------|
| 1 | 1 | RC2024001 | Rice | 5.0 | 2024-01-05 | BPL |
| 2 | 2 | RC2024002 | Rice | 5.0 | 2024-01-10 | APL |
| 3 | 1 | RC2024001 | Rice | 4.0 | 2024-01-15 | BPL |

**Quota Usage Summary (January 2024):**

| Citizen | Category | Product | Total Quota | Redeemed | Remaining | Status |
|---------|----------|---------|-------------|----------|-----------|--------|
| John (RC2024001) | BPL | Rice | 10.0 kg | 9.0 kg | 1.0 kg | ALMOST_EXHAUSTED |
| John (RC2024001) | BPL | Wheat | 8.0 kg | 0.0 kg | 8.0 kg | AVAILABLE |
| Priya (RC2024002) | APL | Rice | 5.0 kg | 5.0 kg | 0.0 kg | EXHAUSTED |
| Priya (RC2024002) | APL | Wheat | 4.0 kg | 0.0 kg | 4.0 kg | AVAILABLE |

---

## Key Features

### 1. Automatic Monthly Reset
- Each month starts with fresh quotas
- Previous month's usage doesn't affect new month
- Example: John used 9kg Rice in January → February starts fresh with 10kg quota

### 2. Category-Based Quotas
- BPL citizens get higher quotas (more subsidized)
- APL citizens get lower quotas
- Admin can set different quotas for different categories

### 3. Real-Time Balance Tracking
- Citizens can check balance anytime
- Shows exactly how much quota is left
- Prevents wasted trips to dealer

### 4. Distribution Validation
- System blocks distribution if quota exceeded
- Clear error messages to citizens
- Prevents misuse of the system

### 5. Product-Specific Tracking
- Each product has separate quota
- Rice quota independent of Wheat quota
- Citizens can track all products separately

---

## Benefits

### For Citizens
✅ Know exact monthly entitlement  
✅ Check balance before visiting dealer  
✅ Plan ration collection throughout the month  
✅ Transparent system - no confusion  

### For Dealers
✅ System validates quota automatically  
✅ Cannot accidentally over-distribute  
✅ Reduced disputes with citizens  
✅ Clear audit trail  

### For Administrators
✅ Control monthly distribution limits  
✅ Different quotas for BPL/APL categories  
✅ Monitor usage patterns  
✅ Prevent misuse and black marketing  

---

## Example: Citizen Mobile App Flow

```
Citizen App → Home Screen
├── My Quota Balance (Current Month)
│   ├── Rice: 5 kg / 10 kg (50% used) ✅
│   ├── Wheat: 0 kg / 8 kg (0% used) ✅
│   ├── Sugar: 2 kg / 2 kg (100% used) ❌
│   └── Kerosene: 1 L / 5 L (20% used) ✅
│
├── Distribution History
│   ├── Jan 15: Rice 4 kg (₹120)
│   ├── Jan 10: Sugar 2 kg (₹80)
│   └── Jan 5: Rice 5 kg (₹150)
│
└── Request Ration
    → Scan QR at dealer shop
    → Shows available quota
    → Confirms distribution
```

---

## Technical Implementation

### Quota Validation Logic

```java
public boolean isQuotaAvailable(Long citizenId, Long productId, 
                                Double requestedQuantity, 
                                Integer month, Integer year) {
    // 1. Get citizen's category (BPL/APL)
    Citizen citizen = citizenRepository.findById(citizenId);
    
    // 2. Find quota for this product, category, month
    Quota quota = quotaRepository.findByProductIdAndCategoryAndMonthAndYear(
        productId, citizen.getCategory(), month, year
    );
    
    // 3. If no quota set, allow distribution (no restriction)
    if (quota == null || !quota.getActive()) {
        return true;
    }
    
    // 4. Calculate already redeemed quantity this month
    Double redeemedQuantity = calculateRedeemedQuantity(
        citizenId, productId, month, year
    );
    
    // 5. Check if requested quantity fits in remaining quota
    Double remainingQuota = quota.getQuotaPerCitizen() - redeemedQuantity;
    
    return remainingQuota >= requestedQuantity;
}
```

### Calculating Redeemed Quantity

```java
private Double calculateRedeemedQuantity(Long citizenId, Long productId, 
                                         Integer month, Integer year) {
    // Get all distributions for this citizen and product in this month
    LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0);
    LocalDateTime endDate = startDate.plusMonths(1).minusSeconds(1);
    
    return distributionRepository
        .findByCitizenIdAndProductId(citizenId, productId)
        .stream()
        .filter(dist -> {
            LocalDateTime distDate = dist.getDistributionDate();
            return !distDate.isBefore(startDate) && !distDate.isAfter(endDate);
        })
        .mapToDouble(Distribution::getQuantity)
        .sum();
}
```

---

## Testing the Quota System

### Test Case 1: Create Quota
```bash
curl -X POST http://localhost:8080/api/quotas \
  -H "Content-Type: application/json" \
  -d '{
    "productId": 1,
    "category": "BPL",
    "month": 11,
    "year": 2024,
    "quotaPerCitizen": 10.0,
    "description": "November 2024 Rice quota for BPL"
  }'
```

### Test Case 2: Check Citizen Balance
```bash
curl http://localhost:8080/api/quotas/citizen/RC2024001/current
```

### Test Case 3: Attempt Distribution
```bash
# This will now validate quota before distributing
curl -X POST http://localhost:8080/api/distributions \
  -H "Content-Type: application/json" \
  -d '{
    "rationCardNumber": "RC2024001",
    "dealerId": 1,
    "productId": 1,
    "quantity": 5.0,
    "remarks": "Regular distribution"
  }'
```

---

## Conclusion

The Quota Management System ensures:
1. **Fair Distribution** - Everyone gets their entitled amount
2. **Transparency** - Citizens know their balance
3. **Control** - Admin sets and monitors limits
4. **Accountability** - Complete audit trail
5. **Efficiency** - Automated validation

Citizens can now easily check "**how much quota is left**" and "**how much is redeemed**" for each month! 🎯

---

**Document Version:** 1.0  
**Last Updated:** November 2024  
**Feature Status:** ✅ Fully Implemented
