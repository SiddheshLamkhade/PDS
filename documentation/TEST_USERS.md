# Test User Credentials for E-Ration PDS

## 👤 Citizen Account

**Username**: `sidlamkhade`  
**Password**: (Check database - password is stored in User table)  
**Role**: CITIZEN  
**Ration Card**: RC2024001

---

## 🏪 Dealer Account

### Existing Dealer (Inactive)
**Username**: `ramdealer`  
**Password**: (Check database)  
**Status**: PENDING_APPROVAL / Inactive  
**Note**: Cannot login until activated by admin

### To Create Working Test Dealer:

#### Method 1: Via Frontend Registration
1. Go to http://localhost:5175/register
2. Select "Dealer" role
3. Use these credentials:
   - **Username**: `testdealer`
   - **Password**: `Dealer@123`
   - **Email**: `dealer@test.com`
   - **Shop Name**: Test Fair Price Shop
   - **Shop License**: LIC2024001
   - **Region**: Mumbai Zone-A
   - **Phone**: `9876543210`
   - **Aadhaar**: `123456789012`
   - **Address**: Shop No. 5, Market Area, Mumbai

4. After registration, login as admin to approve the dealer

#### Method 2: SQL Script (Direct Database Insert)

Run this SQL in your PostgreSQL database:

```sql
-- Insert User for Dealer
INSERT INTO users (username, email, password, full_name, role, active, created_at, updated_at)
VALUES ('testdealer', 'dealer@test.com', 'Dealer@123', 'Test Dealer', 'DEALER', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
RETURNING id;

-- Use the returned user ID in the next query (assume it's 100 for this example)
INSERT INTO dealers (user_id, shop_name, shop_license, address, phone_number, region, active, status, approved_at, created_at, updated_at)
VALUES (100, 'Test Fair Price Shop', 'LIC2024001', 'Shop No. 5, Market Area, Mumbai', '9876543210', 'Mumbai Zone-A', true, 'APPROVED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
```

**After Insert, Use:**
- **Username**: `testdealer`
- **Password**: `Dealer@123`

---

## 👨‍💼 Admin Account

### Existing Admin
**Username**: `admin`  
**Password**: (Check database - likely `Admin@123` or similar)  
**Role**: ADMIN

### To Create New Admin (if needed):

```sql
INSERT INTO users (username, email, password, full_name, role, active, created_at, updated_at)
VALUES ('testadmin', 'admin@test.com', 'Admin@123', 'Test Administrator', 'ADMIN', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
```

**Credentials:**
- **Username**: `testadmin`
- **Password**: `Admin@123`

---

## 🔐 Password Requirements

All passwords must contain:
- Minimum 6 characters
- At least 1 uppercase letter (A-Z)
- At least 1 lowercase letter (a-z)
- At least 1 digit (0-9)
- At least 1 special character (@$!%*?&)

**Valid Examples:**
- `Test@123`
- `Dealer@123`
- `Admin@123`
- `Citizen@123`

---

## 🧪 Quick Login Test

### Test Existing Users:

```bash
# Test Admin Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"Admin@123"}'

# Test Citizen Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"sidlamkhade","password":"ACTUAL_PASSWORD"}'
```

### Check User Table for Passwords:

If you have direct database access, check the users table:

```sql
SELECT id, username, email, full_name, password, role, active 
FROM users 
WHERE role = 'DEALER' OR role = 'ADMIN';
```

**Note**: Passwords are currently stored in plain text (TODO: implement password hashing with BCrypt)

---

## 📞 Support

If you can't access any accounts:
1. Check the `users` table in PostgreSQL for actual passwords
2. Create new test users using the SQL scripts above
3. Ensure the user's `active` field is `true`
4. For dealers, ensure `status` is `APPROVED` in the dealers table
