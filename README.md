# 🚂 Train Station Ticker System

A full-stack web application that displays real-time train departure information, built with Java Spring Boot backend and Next.js frontend. Perfect for learning full-stack development!

## 🎯 Learning Objectives

- **Backend Development**: Java Spring Boot, REST APIs, MySQL integration
- **Frontend Development**: React/Next.js, TypeScript, Tailwind CSS
- **Database Design**: Entity relationships, SQL queries
- **Full-Stack Integration**: API communication, data flow
- **Modern Development**: Git, environment configuration, testing

## 🏗️ Architecture

```
┌─────────────────┐    HTTP/REST    ┌──────────────────┐    JPA/Hibernate    ┌─────────────┐
│   Next.js       │ ──────────────► │  Spring Boot     │ ──────────────────► │   MySQL     │
│   Frontend      │                 │   Backend        │                     │  Database   │
│                 │ ◄────────────── │                  │ ◄────────────────── │             │
│ - React/TSX     │    JSON Data    │ - REST API       │    Entity Objects   │ - Tables    │
│ - Tailwind CSS  │                 │ - JPA Repository │                     │ - Relations │
│ - Auto-refresh  │                 │ - Business Logic │                     │ - Sample Data│
└─────────────────┘                 └──────────────────┘                     └─────────────┘
```

## 🚀 Features

- **Real-time Updates**: Auto-refreshes every 30 seconds
- **Scrolling Ticker**: Smooth horizontal scrolling display
- **Status Indicators**: Color-coded train status (On Time, Delayed, Cancelled)
- **Platform Information**: Shows departure platforms
- **Responsive Design**: Works on desktop and mobile
- **Error Handling**: Graceful error handling and retry mechanisms
- **Multiple Endpoints**: Various API endpoints for different data views

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

- **Java 17+** (OpenJDK or Oracle JDK)
- **Maven 3.6+** (for building Java project)
- **MySQL 8.0+** (database server)
- **Node.js 18+** (for Next.js frontend)
- **npm/yarn/pnpm** (package manager)

## 🛠️ Installation & Setup

### Step 1: Clone the Repository

```bash
git clone <your-repository-url>
cd train-station-ticker
```

### Step 2: Database Setup

1. **Start MySQL Server**
   ```bash
   # On macOS with Homebrew
   brew services start mysql
   
   # On Ubuntu/Debian
   sudo systemctl start mysql
   
   # On Windows, start MySQL from Services or MySQL Workbench
   ```

2. **Create Database and Tables**
   ```bash
   # Login to MySQL
   mysql -u root -p
   
   # Run the schema script
   source database/schema.sql
   
   # Insert sample data
   source database/sample_data.sql
   ```

3. **Verify Database Setup**
   ```sql
   USE train_station_db;
   SELECT COUNT(*) FROM trains;    -- Should return 10
   SELECT COUNT(*) FROM schedules; -- Should return 20
   ```

### Step 3: Backend Setup (Java Spring Boot)

1. **Navigate to Backend Directory**
   ```bash
   cd train-ticker-backend
   ```

2. **Configure Database Connection**
   Edit `src/main/resources/application.properties`:
   ```properties
   spring.datasource.password=your_mysql_password_here
   ```

3. **Build and Run the Backend**
   ```bash
   # Build the project
   mvn clean compile
   
   # Run the application
   mvn spring-boot:run
   ```

4. **Verify Backend is Running**
   ```bash
   # Test the API endpoint
   curl http://localhost:8080/api/trains
   
   # Should return JSON array of train schedules
   ```

### Step 4: Frontend Setup (Next.js)

1. **Navigate to Project Root**
   ```bash
   cd ..  # Go back to project root
   ```

2. **Install Dependencies**
   ```bash
   npm install
   # or
   yarn install
   # or
   pnpm install
   ```

3. **Configure Environment**
   The `.env.local` file is already created with:
   ```
   NEXT_PUBLIC_API_URL=http://localhost:8080/api
   ```

4. **Run the Frontend**
   ```bash
   npm run dev
   # or
   yarn dev
   # or
   pnpm dev
   ```

5. **Access the Application**
   - Open your browser and go to: `http://localhost:3000`
   - Click "View Live Departures" to see the ticker

## 🧪 Testing the Application

### Backend API Testing

```bash
# Test main endpoint
curl http://localhost:8080/api/trains

# Test specific date
curl http://localhost:8080/api/trains/2024-01-15

# Test upcoming trains
curl http://localhost:8080/api/trains/upcoming

# Test by destination
curl http://localhost:8080/api/trains/destination/Boston

# Test by platform
curl http://localhost:8080/api/trains/platform/A1

# Test delayed trains
curl http://localhost:8080/api/trains/delayed

# Test statistics
curl http://localhost:8080/api/trains/stats

# Health check
curl http://localhost:8080/api/health
```

### Frontend Testing

1. **Home Page**: Visit `http://localhost:3000`
2. **Ticker Page**: Visit `http://localhost:3000/train-ticker`
3. **Mobile Testing**: Resize browser window or use mobile device
4. **Error Testing**: Stop backend server and observe error handling
5. **Auto-refresh**: Watch the "Last updated" timestamp change every 30 seconds

## 📁 Project Structure

```
train-station-ticker/
├── database/                          # Database scripts
│   ├── schema.sql                     # Database schema
│   └── sample_data.sql               # Sample data
├── train-ticker-backend/             # Java Spring Boot backend
│   ├── pom.xml                       # Maven dependencies
│   └── src/main/java/com/example/trainticker/
│       ├── TrainTickerApplication.java    # Main application
│       ├── model/                    # Entity classes
│       ├── repository/               # Data access layer
│       ├── service/                  # Business logic
│       ├── controller/               # REST API endpoints
│       └── dto/                      # Data transfer objects
├── src/                              # Next.js frontend
│   ├── app/                          # App router pages
│   ├── components/                   # React components
│   └── lib/                          # Utility functions
├── .env.local                        # Environment variables
├── package.json                      # Node.js dependencies
└── README.md                         # This file
```

## 🔧 Configuration

### Backend Configuration (`application.properties`)

```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/train_station_db
spring.datasource.username=root
spring.datasource.password=your_password

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true

# Server
server.port=8080

# CORS (for frontend access)
spring.web.cors.allowed-origins=http://localhost:3000
```

### Frontend Configuration (`.env.local`)

```bash
NEXT_PUBLIC_API_URL=http://localhost:8080/api
NEXT_PUBLIC_REFRESH_INTERVAL=30000
```

## 🎨 Customization

### Adding New Train Routes

1. **Add to Database**:
   ```sql
   INSERT INTO trains (train_number, train_name, route) 
   VALUES ('T999', 'Your Train', 'City A - City B');
   ```

2. **Add Schedule**:
   ```sql
   INSERT INTO schedules (train_id, destination, departure_time, arrival_time, platform, status, schedule_date)
   VALUES (11, 'City B', '14:30:00', '18:45:00', 'A5', 'ON_TIME', CURDATE());
   ```

### Modifying Ticker Display

Edit `src/components/TrainTicker.tsx`:
- Change colors in `getStatusColor()` function
- Modify animation speed in CSS `@keyframes scroll`
- Add new data fields to display

### Adding New API Endpoints

1. **Add method to Service** (`TrainService.java`)
2. **Add endpoint to Controller** (`TrainController.java`)
3. **Update frontend** to use new endpoint

## 🐛 Troubleshooting

### Common Issues

1. **Backend won't start**
   - Check MySQL is running: `brew services list | grep mysql`
   - Verify database exists: `mysql -u root -p -e "SHOW DATABASES;"`
   - Check application.properties password

2. **Frontend can't connect to backend**
   - Verify backend is running: `curl http://localhost:8080/api/health`
   - Check CORS configuration in application.properties
   - Verify .env.local file exists and has correct API URL

3. **No data showing**
   - Check database has sample data: `mysql -u root -p train_station_db -e "SELECT COUNT(*) FROM schedules;"`
   - Verify date in sample data is current: Update `schedule_date` to today's date

4. **Port conflicts**
   - Backend (8080): Change `server.port` in application.properties
   - Frontend (3000): Use `npm run dev -- -p 3001`

### Debug Mode

**Backend Debug**:
```bash
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
```

**Frontend Debug**:
- Open browser developer tools (F12)
- Check Console tab for JavaScript errors
- Check Network tab for API call failures

## 📚 Learning Resources

### Java Spring Boot
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA Guide](https://spring.io/guides/gs/accessing-data-jpa/)
- [Building REST APIs](https://spring.io/guides/gs/rest-service/)

### React/Next.js
- [Next.js Documentation](https://nextjs.org/docs)
- [React Hooks Guide](https://reactjs.org/docs/hooks-intro.html)
- [TypeScript Handbook](https://www.typescriptlang.org/docs/)

### Database Design
- [MySQL Documentation](https://dev.mysql.com/doc/)
- [Database Design Basics](https://www.lucidchart.com/pages/database-diagram/database-design)

## 🚀 Next Steps & Enhancements

### Beginner Enhancements
1. **Add more train data** to the database
2. **Change colors and styling** in the ticker
3. **Add sound notifications** for delayed trains
4. **Create a mobile app** version

### Intermediate Enhancements
1. **User Authentication**: Login system for staff
2. **Admin Panel**: CRUD operations for trains and schedules
3. **Real-time WebSocket**: Live updates without polling
4. **Email Notifications**: Alert system for delays
5. **Booking System**: Allow passengers to book tickets

### Advanced Enhancements
1. **Microservices Architecture**: Split into multiple services
2. **Docker Containerization**: Easy deployment
3. **Cloud Deployment**: AWS/Azure/GCP hosting
4. **Performance Monitoring**: Metrics and logging
5. **Load Testing**: Handle multiple concurrent users

## 🤝 Contributing

This is a learning project! Feel free to:
- Add new features
- Fix bugs
- Improve documentation
- Share your enhancements

## 📄 License

This project is created for educational purposes. Feel free to use and modify as needed for learning.

---

**Happy Learning! 🚂✨**

Built with ❤️ using Java Spring Boot, MySQL, Next.js, and Tailwind CSS
