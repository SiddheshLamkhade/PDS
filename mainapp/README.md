# 🌾 E-Ration System - Main Application

## 📋 Project Overview

This is the main application for the **E-Ration / Public Distribution System** with Smart Inventory Management. The application handles all core business logic including user management, product catalog, inventory tracking, ration distribution, and demand prediction.

---

## 🏗️ Architecture

This application consolidates all microservice functionalities into a **single monolithic application** with modular design:

- **User Service** - Citizen & Dealer management
- **Product Service** - Ration item catalog
- **Inventory Service** - Stock management per dealer
- **Distribution Service** - Ration distribution transactions
- **Prediction Service** - ML-based demand forecasting
- **Admin Service** - Reporting & monitoring

---

## 🛠️ Technology Stack

- **Framework**: Spring Boot 3.5.5
- **Language**: Java 17
- **Database**: PostgreSQL
- **ORM**: Spring Data JPA
- **Build Tool**: Maven
- **Service Discovery**: Eureka Client
- **API Gateway**: Spring Cloud Gateway (separate service)
- **Config Server**: Spring Cloud Config (separate service)

---

## 📦 Project Structure

```
mainapp/
├── src/
│   ├── main/
│   │   ├── java/com/mainapp/
│   │   │   ├── controller/          # REST Controllers
│   │   │   │   ├── UserController.java
│   │   │   │   ├── ProductController.java
│   │   │   │   ├── InventoryController.java
│   │   │   │   ├── DistributionController.java
│   │   │   │   ├── PredictionController.java
│   │   │   │   └── AdminController.java
│   │   │   ├── service/             # Business Logic
│   │   │   │   ├── UserService.java
│   │   │   │   ├── ProductService.java
│   │   │   │   ├── InventoryService.java
│   │   │   │   ├── DistributionService.java
│   │   │   │   ├── PredictionService.java
│   │   │   │   └── AdminService.java
│   │   │   ├── repository/          # Data Access Layer
│   │   │   │   ├── CitizenRepository.java
│   │   │   │   ├── DealerRepository.java
│   │   │   │   ├── ProductRepository.java
│   │   │   │   ├── InventoryRepository.java
│   │   │   │   ├── DistributionRepository.java
│   │   │   │   └── StockPredictionRepository.java
│   │   │   ├── model/               # JPA Entities
│   │   │   │   ├── Citizen.java
│   │   │   │   ├── Dealer.java
│   │   │   │   ├── Product.java
│   │   │   │   ├── Inventory.java
│   │   │   │   ├── Distribution.java
│   │   │   │   └── StockPrediction.java
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   │   ├── CitizenRequest.java / CitizenResponse.java
│   │   │   │   ├── DealerRequest.java / DealerResponse.java
│   │   │   │   ├── ProductRequest.java / ProductResponse.java
│   │   │   │   ├── InventoryRequest.java / InventoryResponse.java
│   │   │   │   ├── DistributionRequest.java / DistributionResponse.java
│   │   │   │   ├── PredictionRequest.java / PredictionResponse.java
│   │   │   │   └── ApiResponse.java
│   │   │   ├── exception/           # Exception Handling
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   ├── SecurityConfig.java  # Security Configuration
│   │   │   └── MainappApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```

---

## ⚙️ Prerequisites

1. **Java 17** or higher
2. **Maven 3.8+**
3. **PostgreSQL 14+**
4. **Eureka Server** (running on port 8761)
5. **Config Server** (optional, running on port 8888)
6. **API Gateway** (running on port 8080)

---

## 🚀 Setup Instructions

### 1. Database Setup

Create a PostgreSQL database:

```sql
CREATE DATABASE erationdb;
```

Update `application.properties` with your database credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/erationdb
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 2. Install Dependencies

```bash
cd mainapp
mvn clean install
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

Or run the JAR file:

```bash
java -jar target/mainapp-0.0.1-SNAPSHOT.jar
```

### 4. Verify Application

- Application will start on **port 8081**
- Check: `http://localhost:8081/actuator/health` (if actuator is enabled)
- Eureka Dashboard: `http://localhost:8761` - should show `MAINAPP` registered

---

## 📊 Database Tables

The application will automatically create the following tables:

1. **citizens** - Citizen profiles
2. **dealers** - Dealer shop information
3. **products** - Ration item catalog
4. **inventory** - Stock tracking per dealer
5. **distributions** - Distribution transaction records
6. **stock_predictions** - ML-generated demand forecasts

---

## 🔌 API Endpoints

### Base URL: `http://localhost:8081/api`

### User Management
- `POST /api/users/citizens` - Create citizen
- `GET /api/users/citizens` - Get all citizens
- `GET /api/users/citizens/{id}` - Get citizen by ID
- `POST /api/users/dealers` - Create dealer
- `GET /api/users/dealers` - Get all dealers

### Product Management
- `POST /api/products` - Create product
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID

### Inventory Management
- `POST /api/inventory/add-stock` - Add stock
- `GET /api/inventory/dealer/{dealerId}` - Get dealer inventory
- `GET /api/inventory/low-stock` - Low stock alerts

### Distribution
- `POST /api/distributions/distribute` - Distribute ration
- `GET /api/distributions` - Get all distributions
- `GET /api/distributions/citizen/{citizenId}` - Citizen's history

### Predictions
- `POST /api/predictions/generate` - Generate prediction
- `GET /api/predictions/dealer/{dealerId}` - Dealer predictions

### Admin & Reports
- `GET /api/admin/dashboard/stats` - Dashboard statistics
- `GET /api/admin/reports/dealer/{dealerId}` - Dealer report
- `GET /api/admin/reports/product/{productId}` - Product report

📖 **Full API documentation**: See `API_DOCUMENTATION.md`

---

## 🧪 Testing

### Sample Data Creation Flow:

1. **Create a Dealer**
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

2. **Create a Product**
```bash
curl -X POST http://localhost:8081/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "productName": "Rice",
    "unit": "KG",
    "pricePerUnit": 25.50,
    "category": "GRAIN"
  }'
```

3. **Add Stock**
```bash
curl -X POST http://localhost:8081/api/inventory/add-stock \
  -H "Content-Type: application/json" \
  -d '{
    "dealerId": 1,
    "productId": 1,
    "quantity": 500.0
  }'
```

4. **Create a Citizen**
```bash
curl -X POST http://localhost:8081/api/users/citizens \
  -H "Content-Type: application/json" \
  -d '{
    "rationCardNumber": "RC12345678",
    "name": "Rahul Sharma",
    "address": "Pune, Maharashtra",
    "phoneNumber": "9876543210",
    "familySize": 4,
    "category": "BPL",
    "dealerId": 1
  }'
```

5. **Distribute Ration**
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

## 🔐 Security

Currently, **security is disabled** in `SecurityConfig.java`. All endpoints are publicly accessible.

To enable security:
1. Integrate Keycloak or Spring Security
2. Configure JWT token validation
3. Add role-based access control (ADMIN, DEALER, CITIZEN)

---

## 📈 Future Enhancements

- [ ] Implement authentication with Keycloak
- [ ] Add role-based authorization
- [ ] Integrate ML models for better predictions (ARIMA, Prophet, LSTM)
- [ ] Add SMS/Email notifications for low stock
- [ ] Implement audit logging
- [ ] Add file upload for bulk citizen registration
- [ ] Generate PDF reports
- [ ] Add analytics dashboard with charts

---

## 🐛 Troubleshooting

### Issue: Application not starting
- Check if PostgreSQL is running
- Verify database credentials in `application.properties`
- Check if port 8081 is available

### Issue: Cannot connect to Eureka
- Ensure Eureka Server is running on port 8761
- Check `eureka.client.service-url.defaultZone` property

### Issue: Database connection error
- Verify PostgreSQL is running: `sudo service postgresql status`
- Check database exists: `psql -U postgres -l`
- Test connection: `psql -U postgres -d erationdb`

---

## 📞 Support

For issues or questions, contact the development team.

---

## 📄 License

This project is developed for educational/demonstration purposes.

---

✅ **Application is ready to run!**
