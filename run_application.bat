@echo off
echo Starting Algorithm Visualizer Application...
echo.

echo Checking Java version...
java -version
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java 17 or higher
    pause
    exit /b 1
)

echo.
echo Checking Maven...
mvn -version
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Maven is not installed or not in PATH
    echo Please install Maven
    pause
    exit /b 1
)

echo.
echo Cleaning and compiling project...
mvn clean compile
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Compilation failed
    pause
    exit /b 1
)

echo.
echo Starting Spring Boot application...
echo Application will be available at: http://localhost:8080
echo.
echo NOTE: Make sure MySQL is running and database is set up
echo Run database_setup_complete.sql if not already done
echo.

mvn spring-boot:run

pause