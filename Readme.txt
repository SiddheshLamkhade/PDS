                                 **---------Implementation Details----------**

Users
● id (PK), email, password_hash, name, role, aadhaar_ref (nullable), phone,
created_at

Dealers
● id, user_id (FK), shop_name, address, pincode, active

Products
● id, name, hs_code, unit, pack_size

Inventory
● id, product_id, owner_type (DEALER/WAREHOUSE), owner_id, quantity,
last_updated

Inventory_movements
● id, inventory_id, type (IN/OUT/ADJUSTMENT), quantity, reason, reference_id,
timestamp

Distributions
● id, citizen_id, dealer_id, product_id, quantity, date, issued_by, receipt_no

Quotas
● id, product_id, month, quota_per_citizen, effective_from

Forecast_series (optional persisted forecasts)
● id, product_id, owner_id, period_start, period_end, predicted_demand,
confidence_lower, confidence_upper, created_at


                                 **---------🛒 E-Ratio Details----------**

The **Public Distribution System (PDS)** in India provides subsidized food grains through **Fair Price Shops (FPS)** 
but suffers from inefficiencies, manual processes, and corruption.  

The proposed **e-Ration Shop** is a digital platform allowing ration card holders to order items online,
ensuring transparency, efficiency, and a user-friendly experience.  


It is modern, secure, and intelligent Public Distribution System
(PDS) for India that not only handles the distribution of ration to citizens, but also predicts
and manages stock intelligently using time-series forecasting.

---------------------------------------

The current PDS is plagued by:  
- Urban bias  
- Fake ration cards  
- Malpractice by FPS dealers  

Beneficiaries often receive less than their entitled share, and there is little recourse for complaints.  
Due to lack of automation and monitoring, corruption and diversion of food grains are common.  
A **digital solution** is needed to ensure fair and accountable distribution.  

---

### Objectives of the e-Ration Platform
- Enable ration card holders to order groceries online  
- Prevent corruption and reduce manual errors  
- Provide real-time stock visibility and transparency  
- Ensure rightful entitlements reach beneficiaries  
- Empower users with grievance redressal tools  

---

The **e-Ration Shop** aims to transform PDS by ensuring **fair, transparent,
and digital access** to essential goods.  
It reduces corruption, improves user access, and supports government goals under **Digital India**.  
This system can play a vital role in making **food security more reliable and citizen-focused**.  

---


##  We’ll create a web-based platform with different portals for different users:

**1. Citizen Portal —** 
where beneficiaries can register, check their quotas, see distribution history, and download receipts.

**2. Dealer Portal —** 
where ration shop owners can manage stock, distribute items, and request replenishment.

**3. Admin Portal —** 
where the government admin can monitor the whole network, track 
stock, approve reorders, and use Smart Inventory Forecasting to anticipate shortages before they happen.


## 🏗️ Milestone Roadmap (Suggested Sprints)  

- **Sprint 0 (1 week):**    Requirements, UX sketches, ER diagram, choose infra & libs.  
- **Sprint 1 (2 weeks):**   Auth Service + API Gateway + basic React shell + MySQL schema.  
- **Sprint 2 (2 weeks):**   User, Dealer, Product services + basic Dealer UI pages.  
- **Sprint 3 (2 weeks):**   Inventory and Distribution services + Dealer flows to issue ration.  
- **Sprint 4 (2 weeks):**   Admin dashboard skeleton, Reporting service, audit logs. 
- **Sprint 5 (1–2 weeks):** Integrate notification, add export/receipt PDFs.  
- **Sprint 6 (2–3 weeks):** Build Forecasting service core: data pipeline, baseline model (Prophet/SARIMA), forecast API  
- **Sprint 7 (2 weeks):**   Admin Smart Inventory UI: charts, reorder suggestions, alerts.  
- **Sprint 8 (1–2 weeks):** Testing (E2E), security audit, performance tuning, containerize.  
- **Sprint 9 (1 week):**    Deploy to staging, load testing, fix issues, production rollout.  

