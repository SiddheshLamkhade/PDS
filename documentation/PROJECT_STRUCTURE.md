# 📁 Complete Project Structure

```
PDS/
│
├── 📄 ARCHITECTURE_FLOW.md          # System architecture & flow diagrams
├── 📄 API_DOCUMENTATION.md          # Complete API reference with examples
├── 📄 PROJECT_SUMMARY.md            # Project overview & summary
├── 📄 Readme.txt                    # Original readme
│
├── 📁 configserver/                 # Configuration Server
│   ├── src/
│   ├── pom.xml
│   └── HELP.md
│
├── 📁 eurekaserver/                 # Service Registry
│   ├── src/
│   ├── pom.xml
│   └── HELP.md
│
├── 📁 gateway/                      # API Gateway
│   ├── src/
│   │   └── main/
│   │       ├── java/com/gateway/
│   │       │   ├── GatewayApplication.java
│   │       │   └── config/CorsConfig.java
│   │       └── resources/
│   │           └── application.properties
│   ├── pom.xml
│   └── HELP.md
│
└── 📁 mainapp/                      # 🎯 MAIN APPLICATION
    ├── 📄 README.md                 # Setup & usage instructions
    │
    ├── 📁 src/
    │   ├── 📁 main/
    │   │   ├── 📁 java/com/mainapp/
    │   │   │   │
    │   │   │   ├── 🎮 controller/                    # REST API Controllers
    │   │   │   │   ├── UserController.java           # 13 endpoints (Citizens & Dealers)
    │   │   │   │   ├── ProductController.java        # 8 endpoints (Products CRUD)
    │   │   │   │   ├── InventoryController.java      # 6 endpoints (Stock management)
    │   │   │   │   ├── DistributionController.java   # 7 endpoints (Ration distribution)
    │   │   │   │   ├── PredictionController.java     # 6 endpoints (Demand forecasts)
    │   │   │   │   └── AdminController.java          # 10 endpoints (Reports & analytics)
    │   │   │   │
    │   │   │   ├── 🔧 service/                       # Business Logic Layer
    │   │   │   │   ├── UserService.java              # Citizen & Dealer management
    │   │   │   │   ├── ProductService.java           # Product operations
    │   │   │   │   ├── InventoryService.java         # Stock add/deduct/alerts
    │   │   │   │   ├── DistributionService.java      # Distribution workflow
    │   │   │   │   ├── PredictionService.java        # Forecasting algorithm
    │   │   │   │   └── AdminService.java             # Reporting & monitoring
    │   │   │   │
    │   │   │   ├── 💾 repository/                    # Data Access Layer (JPA)
    │   │   │   │   ├── CitizenRepository.java        # Citizen queries
    │   │   │   │   ├── DealerRepository.java         # Dealer queries
    │   │   │   │   ├── ProductRepository.java        # Product queries
    │   │   │   │   ├── InventoryRepository.java      # Inventory queries + low stock
    │   │   │   │   ├── DistributionRepository.java   # Distribution queries + date range
    │   │   │   │   └── StockPredictionRepository.java # Prediction queries
    │   │   │   │
    │   │   │   ├── 🗄️ model/                         # JPA Entities (Database Tables)
    │   │   │   │   ├── Citizen.java                  # Table: citizens
    │   │   │   │   ├── Dealer.java                   # Table: dealers
    │   │   │   │   ├── Product.java                  # Table: products
    │   │   │   │   ├── Inventory.java                # Table: inventory
    │   │   │   │   ├── Distribution.java             # Table: distributions
    │   │   │   │   └── StockPrediction.java          # Table: stock_predictions
    │   │   │   │
    │   │   │   ├── 📦 dto/                           # Data Transfer Objects
    │   │   │   │   ├── CitizenRequest.java           # Request DTO
    │   │   │   │   ├── CitizenResponse.java          # Response DTO
    │   │   │   │   ├── DealerRequest.java
    │   │   │   │   ├── DealerResponse.java
    │   │   │   │   ├── ProductRequest.java
    │   │   │   │   ├── ProductResponse.java
    │   │   │   │   ├── InventoryRequest.java
    │   │   │   │   ├── InventoryResponse.java
    │   │   │   │   ├── DistributionRequest.java
    │   │   │   │   ├── DistributionResponse.java
    │   │   │   │   ├── PredictionRequest.java
    │   │   │   │   ├── PredictionResponse.java
    │   │   │   │   └── ApiResponse.java              # Generic wrapper
    │   │   │   │
    │   │   │   ├── ⚠️ exception/                     # Exception Handling
    │   │   │   │   └── GlobalExceptionHandler.java  # Centralized error handling
    │   │   │   │
    │   │   │   ├── 🔐 SecurityConfig.java            # Security configuration
    │   │   │   └── 🚀 MainappApplication.java        # Main class
    │   │   │
    │   │   └── 📁 resources/
    │   │       └── application.properties            # Configuration (DB, Eureka, etc.)
    │   │
    │   └── 📁 test/
    │       └── java/com/mainapp/
    │           └── UserserviceApplicationTests.java
    │
    ├── 📁 target/                                    # Build output
    ├── 📄 pom.xml                                    # Maven dependencies
    └── 📄 HELP.md
```

---

## 📊 Statistics

### **Code Files Created:**
- **Controllers**: 6 files (60+ endpoints)
- **Services**: 6 files (business logic)
- **Repositories**: 6 files (data access)
- **Entities**: 6 files (database models)
- **DTOs**: 13 files (request/response)
- **Exception Handlers**: 1 file
- **Total Java Files**: **38 files**

### **Lines of Code (Approximate):**
- Controllers: ~1,200 lines
- Services: ~1,500 lines
- Models: ~600 lines
- DTOs: ~500 lines
- Repositories: ~300 lines
- **Total**: **~4,100+ lines of code**

### **API Endpoints:**
- User Management: 13 endpoints
- Product Management: 8 endpoints
- Inventory Management: 6 endpoints
- Distribution: 7 endpoints
- Predictions: 6 endpoints
- Admin & Reports: 10 endpoints
- **Total**: **50+ REST endpoints**

### **Database Tables:**
- citizens
- dealers
- products
- inventory
- distributions
- stock_predictions
- **Total**: **6 tables**

---

## 🎯 Key Components Breakdown

### **1. Controller Layer** (API Endpoints)
```
UserController          → /api/users/**
ProductController       → /api/products/**
InventoryController     → /api/inventory/**
DistributionController  → /api/distributions/**
PredictionController    → /api/predictions/**
AdminController         → /api/admin/**
```

### **2. Service Layer** (Business Logic)
```
UserService         → Citizen & Dealer CRUD + validation
ProductService      → Product management
InventoryService    → Stock operations + alerts
DistributionService → Ration distribution workflow
PredictionService   → Demand forecasting
AdminService        → Reporting & analytics
```

### **3. Repository Layer** (Database Access)
```
CitizenRepository      → findByRationCardNumber, findByDealer, etc.
DealerRepository       → findByRegion, findByActive, etc.
ProductRepository      → findByCategory, findByActive, etc.
InventoryRepository    → findLowStock, findByDealer, etc.
DistributionRepository → findByDateRange, findByCitizen, etc.
PredictionRepository   → findByMonth, findByDealer, etc.
```

### **4. Model Layer** (Database Entities)
```
Citizen         → id, rationCardNumber, name, dealerId, category
Dealer          → id, shopName, shopLicense, region
Product         → id, productName, unit, pricePerUnit
Inventory       → id, dealerId, productId, currentStock
Distribution    → id, citizenId, dealerId, quantity, amount
StockPrediction → id, dealerId, productId, predictedDemand
```

---

## 🔄 Data Flow Example: Distribute Ration

```
1. Client Request
   ↓
2. API Gateway (Port 8080)
   ↓
3. DistributionController (/api/distributions/distribute)
   ↓
4. DistributionService
   ├─→ UserService (verify citizen)
   ├─→ UserService (verify dealer)
   ├─→ ProductService (get product details)
   ├─→ InventoryService (check stock)
   ├─→ InventoryService (deduct stock)
   └─→ Save distribution record
   ↓
5. Repository Layer
   ├─→ CitizenRepository
   ├─→ DealerRepository
   ├─→ ProductRepository
   ├─→ InventoryRepository
   └─→ DistributionRepository
   ↓
6. PostgreSQL Database
   ├─→ UPDATE inventory
   └─→ INSERT into distributions
   ↓
7. Response → Client
```

---

## 🗂️ Package Organization

```
com.mainapp
├── controller       # HTTP request handling
├── service          # Business logic
├── repository       # Data access
├── model            # Database entities
├── dto              # Data transfer objects
├── exception        # Error handling
├── SecurityConfig   # Security setup
└── MainappApplication # Entry point
```

---

## 📚 Dependencies (pom.xml)

- ✅ spring-boot-starter-web
- ✅ spring-boot-starter-data-jpa
- ✅ spring-boot-starter-validation
- ✅ spring-boot-starter-security
- ✅ spring-cloud-starter-netflix-eureka-client
- ✅ spring-cloud-starter-config
- ✅ postgresql
- ✅ lombok

---

## 🎨 Design Patterns Used

1. **MVC Pattern** (Controller-Service-Repository)
2. **DTO Pattern** (Request/Response separation)
3. **Repository Pattern** (Data access abstraction)
4. **Builder Pattern** (Entity creation with Lombok)
5. **Singleton Pattern** (Spring Beans)
6. **Facade Pattern** (Service layer hiding complexity)
7. **Strategy Pattern** (Different prediction algorithms)

---

✅ **Complete project structure documented!**

For detailed API endpoints, see: `API_DOCUMENTATION.md`  
For setup instructions, see: `mainapp/README.md`  
For architecture details, see: `ARCHITECTURE_FLOW.md`
