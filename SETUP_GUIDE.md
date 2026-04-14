# 🚀 Quick Setup Guide for Team Members

## 📋 Prerequisites Checklist

Before starting, ensure you have these installed:

- [ ] **Java 17+** ([Download](https://www.oracle.com/java/technologies/downloads/))
- [ ] **Maven 3.6+** ([Download](https://maven.apache.org/download.cgi))
- [ ] **MySQL 8.0+** ([Download](https://dev.mysql.com/downloads/mysql/))
- [ ] **Git** ([Download](https://git-scm.com/downloads))
- [ ] **IDE** (IntelliJ IDEA, VS Code, or Eclipse)

## ⚡ Quick Start (5 Minutes)

### 1. Clone & Navigate
```bash
git clone <repository-url>
cd AlgorithmVisualizer
```

### 2. Database Setup
```sql
-- Open MySQL Command Line or Workbench
CREATE DATABASE algorithm_visualizer;
USE algorithm_visualizer;

-- Run the setup script
SOURCE database_setup_complete.sql;
```

### 3. Configure Application
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

### 4. Run Application
```bash
# Option 1: Maven
mvn spring-boot:run

# Option 2: Windows Batch File
run_application.bat
```

### 5. Access Application
Open browser: `http://localhost:8082`

## 🔧 Development Environment Setup

### IntelliJ IDEA
1. **Import Project**: File → Open → Select project folder
2. **Set JDK**: File → Project Structure → Project → SDK (Java 17+)
3. **Enable Auto-Import**: Settings → Build → Build Tools → Maven → Import Maven projects automatically
4. **Run Configuration**: Create Spring Boot run configuration

### VS Code
1. **Install Extensions**:
   - Extension Pack for Java
   - Spring Boot Extension Pack
   - MySQL (for database management)
2. **Open Project**: File → Open Folder
3. **Configure Java**: Ctrl+Shift+P → "Java: Configure Runtime"

## 📊 Database Schema Overview

### Tables Created:
- **algorithms** - Available algorithms metadata
- **users** - User information and profiles
- **user_sessions** - Learning session tracking
- **user_progress** - Progress and achievements

### Sample Data Included:
- 10 algorithms (6 sorting + 4 searching)
- Sample users for testing
- Demo sessions and progress data

## 🌐 Application Structure

### Frontend Pages:
- **Home** (`/`) - Landing page with navigation
- **Sorting** (`/sorting`) - Sorting algorithm visualizations
- **Searching** (`/searching`) - Search algorithm visualizations
- **Data Structures** (`/datastructures`) - Data structure operations
- **Analytics** (`/analytics`) - Learning progress dashboard

### Key Features:
- **Step-by-step visualization** with play/pause controls
- **Interactive learning** with real-time feedback
- **Progress tracking** and achievement system
- **Responsive design** for all devices

## 🔌 API Testing

### Test Endpoints:
```bash
# Test sorting algorithm
curl -X POST http://localhost:8082/api/algorithms/bubble-sort \
  -H "Content-Type: application/json" \
  -d '[64,34,25,12,22,11,90]'

# Test user sessions
curl http://localhost:8082/api/algorithms/user/1/sessions

# Test analytics
curl http://localhost:8082/api/analytics/top-performers
```

## 🐛 Common Setup Issues

### Issue: "Port 8082 already in use"
**Solution**: Change port in `application.properties`
```properties
server.port=8083
```

### Issue: "Access denied for user 'root'"
**Solution**: Update MySQL credentials
```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Issue: "Table doesn't exist"
**Solution**: Run database setup script
```bash
mysql -u root -p algorithm_visualizer < database_setup_complete.sql
```

### Issue: Maven build fails
**Solution**: Clean and reinstall
```bash
mvn clean install -U
```

## 🤝 Team Collaboration

### Git Workflow:
1. **Pull latest changes**: `git pull origin main`
2. **Create feature branch**: `git checkout -b feature/your-feature`
3. **Make changes and commit**: `git commit -m "Add: feature description"`
4. **Push branch**: `git push origin feature/your-feature`
5. **Create Pull Request** on GitHub/GitLab

### Code Standards:
- **Java**: Follow Spring Boot conventions
- **JavaScript**: Use ES6+ features
- **CSS**: Use BEM methodology for class names
- **Comments**: Document complex algorithms

### File Organization:
- **Backend**: `src/main/java/com/algorithmvisualizer/`
- **Frontend**: `src/main/resources/templates/` and `src/main/resources/static/`
- **Database**: `database_setup_complete.sql`
- **Documentation**: `README.md` and this file

## 📈 Next Steps for Development

### Potential Enhancements:
1. **User Authentication** - Login/Register system
2. **More Algorithms** - Graph algorithms, dynamic programming
3. **Code Editor** - Let users write and test algorithms
4. **Multiplayer Mode** - Compete with other learners
5. **Mobile App** - React Native or Flutter version
6. **AI Tutor** - Personalized learning recommendations

### Areas to Contribute:
- **Algorithm Implementations** - Add new sorting/searching algorithms
- **UI/UX Improvements** - Better animations and interactions
- **Performance Optimization** - Faster visualizations
- **Testing** - Unit tests and integration tests
- **Documentation** - More detailed guides and tutorials

## 📞 Getting Help

### Resources:
- **Spring Boot Docs**: https://spring.io/projects/spring-boot
- **MySQL Docs**: https://dev.mysql.com/doc/
- **Thymeleaf Docs**: https://www.thymeleaf.org/documentation.html

### Team Communication:
- Create **GitHub Issues** for bugs and feature requests
- Use **Pull Request comments** for code reviews
- Document **decisions** in commit messages

---

**Ready to contribute? Let's build something amazing together! 🚀**