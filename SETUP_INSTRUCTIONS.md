# Algorithm Visualizer - Setup Instructions

## Prerequisites

1. **Java 17 or higher**
   - Download from: https://adoptium.net/
   - Verify installation: `java -version`

2. **Maven 3.6+**
   - Download from: https://maven.apache.org/download.cgi
   - Verify installation: `mvn -version`

3. **MySQL 8.0+**
   - Download from: https://dev.mysql.com/downloads/mysql/
   - Create a database user with appropriate permissions

## Database Setup

1. **Start MySQL service**
2. **Create database and user:**
   ```sql
   CREATE DATABASE algorithm_visualizers;
   CREATE USER 'algo_user'@'localhost' IDENTIFIED BY 'your_password';
   GRANT ALL PRIVILEGES ON algorithm_visualizers.* TO 'algo_user'@'localhost';
   FLUSH PRIVILEGES;
   ```

3. **Run the database setup script:**
   ```bash
   mysql -u root -p < database_setup_complete.sql
   ```

4. **Update application.properties** (if needed):
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/algorithm_visualizers
   spring.datasource.username=root
   spring.datasource.password=your_password
   ```

## Running the Application

### Option 1: Using the batch script (Windows)
```bash
run_application.bat
```

### Option 2: Using Maven directly
```bash
mvn clean compile
mvn spring-boot:run
```

### Option 3: Using IDE
1. Import the project as a Maven project
2. Run `AlgorithmVisualizerApplication.java` as a Java application

## Accessing the Application

- **URL:** http://localhost:8080
- **Demo User:** 
  - Username: `demo`
  - Password: `demo123`

## Features Available

### 1. Sorting Algorithms
- Bubble Sort, Selection Sort, Insertion Sort
- Merge Sort, Quick Sort, Heap Sort
- Visual step-by-step execution
- Performance metrics

### 2. Data Structures
- **Arrays:** Insert, Delete, Search operations
- **Linked Lists:** Insert at head/tail, Delete, Search
- **Stacks:** Push, Pop, Peek operations (LIFO)
- **Queues:** Enqueue, Dequeue, Front operations (FIFO)

### 3. User Management
- User registration and login
- Progress tracking
- Performance analytics
- Skill level progression

### 4. Analytics
- User performance summaries
- Algorithm completion statistics
- Time tracking and leaderboards

## Project Structure

```
src/main/java/com/algorithmvisualizer/
├── algorithm/          # Sorting algorithm implementations
├── config/            # Security and configuration
├── controller/        # REST controllers and web controllers
├── datastructures/    # Data structure operations
├── model/            # JPA entities
├── repository/       # Data access layer
└── service/          # Business logic layer

src/main/resources/
├── static/           # CSS, JavaScript files
├── templates/        # Thymeleaf HTML templates
└── application.properties
```

## Troubleshooting

### Common Issues

1. **Port 8080 already in use:**
   - Change port in `application.properties`: `server.port=8081`

2. **Database connection failed:**
   - Verify MySQL is running
   - Check database credentials in `application.properties`
   - Ensure database exists

3. **Compilation errors:**
   - Verify Java 17+ is installed
   - Run `mvn clean install` to download dependencies

4. **Missing templates:**
   - All required templates are now included
   - Clear browser cache if pages don't load

### Performance Tips

1. **Database Optimization:**
   - Indexes are automatically created
   - Consider connection pooling for production

2. **Memory Settings:**
   - For large datasets: `export MAVEN_OPTS="-Xmx2g"`

## Development

### Adding New Algorithms
1. Implement in appropriate package (`algorithm/`, `datastructures/`)
2. Add controller endpoints
3. Create corresponding HTML templates
4. Update navigation menus

### Database Changes
1. Update entity classes
2. Run with `spring.jpa.hibernate.ddl-auto=update`
3. Create migration scripts for production

## Support

For issues or questions:
1. Check the console logs for error details
2. Verify all prerequisites are installed
3. Ensure database is properly configured
4. Check that all required files are present

## Security Notes

- Default security is configured for development
- Change default passwords before production deployment
- Consider HTTPS for production environments
- Review CORS settings for production use