# 🏗️ E-Ration System - Complete Architecture Flow

```mermaid
flowchart TB
    subgraph Client["🖥️ Client Layer"]
        WEB[Web Application]
        MOBILE[Mobile App]
    end

    subgraph Gateway["🚪 API Gateway"]
        GW[Spring Cloud Gateway<br/>Port: 8080]
    end

    subgraph Discovery["🔍 Service Registry"]
        EUREKA[Eureka Server<br/>Port: 8761]
    end

    subgraph Config["⚙️ Configuration"]
        CONFIG[Config Server<br/>Port: 8888]
    end

    subgraph MainApp["📦 Main Application (Port: 8081)"]
        direction TB
        
        subgraph Controllers["🎮 Controllers Layer"]
            USERCTL[User Controller<br/>/api/users/**]
            PRODCTL[Product Controller<br/>/api/products/**]
            INVCTL[Inventory Controller<br/>/api/inventory/**]
            DISTCTL[Distribution Controller<br/>/api/distributions/**]
            ADMINCTL[Admin Controller<br/>/api/admin/**]
            PREDCTL[Prediction Controller<br/>/api/predictions/**]
        end

        subgraph Services["🔧 Business Logic Layer"]
            USERSVC[User Service]
            PRODSVC[Product Service]
            INVSVC[Inventory Service]
            DISTSVC[Distribution Service]
            ADMINSVC[Admin Service]
            PREDSVC[Prediction Service]
        end

        subgraph Repositories["💾 Data Access Layer"]
            USERREPO[Citizen Repository<br/>Dealer Repository]
            PRODREPO[Product Repository]
            INVREPO[Inventory Repository]
            DISTREPO[Distribution Repository]
            PREDREPO[Prediction Repository]
        end

        USERCTL --> USERSVC
        PRODCTL --> PRODSVC
        INVCTL --> INVSVC
        DISTCTL --> DISTSVC
        ADMINCTL --> ADMINSVC
        PREDCTL --> PREDSVC

        USERSVC --> USERREPO
        PRODSVC --> PRODREPO
        INVSVC --> INVREPO
        DISTSVC --> DISTREPO
        PREDSVC --> PREDREPO

        DISTSVC -.check stock.-> INVSVC
        DISTSVC -.verify citizen.-> USERSVC
        DISTSVC -.get product info.-> PRODSVC
        ADMINSVC -.aggregate data.-> DISTSVC
        ADMINSVC -.aggregate data.-> INVSVC
        PREDSVC -.historical data.-> DISTREPO
        PREDSVC -.current stock.-> INVREPO
    end

    subgraph Database["🗄️ Database Layer"]
        POSTGRES[(PostgreSQL Database<br/>Port: 5432)]
        
        subgraph Tables["Tables"]
            T1[citizens]
            T2[dealers]
            T3[products]
            T4[inventory]
            T5[distributions]
            T6[stock_predictions]
        end
    end

    WEB --> GW
    MOBILE --> GW
    GW --> MainApp
    MainApp --> EUREKA
    MainApp --> CONFIG
    Repositories --> POSTGRES
    POSTGRES --> Tables

    style MainApp fill:#e1f5ff
    style Controllers fill:#fff4e6
    style Services fill:#e8f5e9
    style Repositories fill:#f3e5f5
    style Database fill:#fce4ec
```

---

## 🔄 Typical Flow: Citizen Distribution Request

```mermaid
sequenceDiagram
    participant C as Citizen/Dealer
    participant GW as API Gateway
    participant DC as Distribution Controller
    participant DS as Distribution Service
    participant US as User Service
    participant IS as Inventory Service
    participant PS as Product Service
    participant DB as Database

    C->>GW: POST /api/distributions/distribute
    GW->>DC: Forward Request
    DC->>DS: distributeRation(request)
    
    DS->>US: getCitizenByRationCard()
    US->>DB: Query citizen
    DB-->>US: Citizen data
    US-->>DS: Citizen entity
    
    DS->>US: getDealerById()
    US->>DB: Query dealer
    DB-->>US: Dealer data
    US-->>DS: Dealer entity
    
    DS->>PS: getProductById()
    PS->>DB: Query product
    DB-->>PS: Product data
    PS-->>DS: Product entity
    
    DS->>IS: checkStock(dealerId, productId)
    IS->>DB: Query inventory
    DB-->>IS: Stock data
    IS-->>DS: Available stock
    
    alt Stock Available
        DS->>IS: deductStock(dealerId, productId, quantity)
        IS->>DB: Update inventory
        DB-->>IS: Success
        
        DS->>DB: Save distribution record
        DB-->>DS: Distribution saved
        DS-->>DC: Success Response
        DC-->>GW: HTTP 200
        GW-->>C: Distribution Success
    else Insufficient Stock
        DS-->>DC: Error: Insufficient Stock
        DC-->>GW: HTTP 400
        GW-->>C: Error Message
    end
```

---

## 📊 Data Model Relationships

```mermaid
erDiagram
    CITIZEN ||--o{ DISTRIBUTION : "receives"
    DEALER ||--o{ DISTRIBUTION : "distributes"
    DEALER ||--o{ INVENTORY : "maintains"
    PRODUCT ||--o{ INVENTORY : "tracked_in"
    PRODUCT ||--o{ DISTRIBUTION : "includes"
    DEALER ||--o{ STOCK_PREDICTION : "forecasted_for"
    PRODUCT ||--o{ STOCK_PREDICTION : "predicted"

    CITIZEN {
        Long id PK
        String rationCardNumber UK
        String name
        String address
        String phoneNumber
        int familySize
        String category
        Long dealerId FK
    }

    DEALER {
        Long id PK
        String shopName
        String shopLicense UK
        String ownerName
        String address
        String phoneNumber
        String region
    }

    PRODUCT {
        Long id PK
        String productName UK
        String unit
        double pricePerUnit
        String category
    }

    INVENTORY {
        Long id PK
        Long dealerId FK
        Long productId FK
        double currentStock
        double openingStock
        double stockReceived
        double stockDistributed
        DateTime lastUpdated
    }

    DISTRIBUTION {
        Long id PK
        Long citizenId FK
        Long dealerId FK
        Long productId FK
        double quantity
        double totalAmount
        DateTime distributionDate
        String status
    }

    STOCK_PREDICTION {
        Long id PK
        Long dealerId FK
        Long productId FK
        double predictedDemand
        String predictionMonth
        DateTime generatedAt
    }
```

---

## 🎯 Key Features Flow

### 1️⃣ User Management Flow
- **Register Citizen** → Validate → Save to DB → Link to Dealer
- **Register Dealer** → Validate Shop License → Save to DB
- **Get All Citizens** → Query DB → Return List
- **Get Citizen by Ration Card** → Search → Return Profile

### 2️⃣ Product Management Flow
- **Add Product** → Validate → Save to Catalog
- **Update Product** → Verify Existence → Update Price/Details
- **Get All Products** → Query Catalog → Return List

### 3️⃣ Inventory Management Flow
- **Add Stock** → Dealer + Product → Update Inventory
- **Check Stock** → Query Current Stock → Return Availability
- **Get Stock by Dealer** → Fetch All Products for Dealer
- **Deduct Stock** → Validate → Update Stock → Track Distribution

### 4️⃣ Distribution Flow
- **Distribute Ration**:
  1. Verify Citizen (ration card)
  2. Verify Dealer
  3. Check Product availability
  4. Check Inventory stock
  5. Calculate entitlement (based on category)
  6. Deduct stock
  7. Record transaction
  8. Return receipt

### 5️⃣ Admin & Reporting Flow
- **Get All Distributions** → Query DB → Aggregate
- **Dealer Performance Report** → Calculate metrics
- **Product Usage Report** → Aggregate by product
- **Low Stock Alert** → Check thresholds → Notify

### 6️⃣ Prediction Flow
- **Generate Prediction**:
  1. Fetch historical distribution data
  2. Fetch current stock levels
  3. Apply forecasting algorithm (simple average/trend)
  4. Save predictions
  5. Return forecast

---

## 🔐 Role-Based Access (For Future Security)

| Endpoint | Citizen | Dealer | Admin |
|----------|---------|--------|-------|
| POST /api/users/citizens | ❌ | ❌ | ✅ |
| GET /api/users/citizens/:id | ✅ (own) | ❌ | ✅ |
| POST /api/distributions/distribute | ❌ | ✅ | ✅ |
| GET /api/inventory/* | ❌ | ✅ (own) | ✅ |
| GET /api/admin/* | ❌ | ❌ | ✅ |
| POST /api/predictions/generate | ❌ | ❌ | ✅ |

---

## 🚀 Technology Stack

- **Backend**: Spring Boot 3.5.5
- **Database**: PostgreSQL
- **ORM**: Spring Data JPA
- **Service Discovery**: Eureka
- **API Gateway**: Spring Cloud Gateway
- **Config Management**: Spring Cloud Config
- **Validation**: Spring Boot Validation
- **Build Tool**: Maven

---

✅ **This architecture ensures**:
- Modular design (all functionality in one app but organized)
- Clear separation of concerns (Controller → Service → Repository)
- Scalable structure (easy to split into microservices later)
- Inter-service communication patterns ready
- Data integrity and validation at every layer
