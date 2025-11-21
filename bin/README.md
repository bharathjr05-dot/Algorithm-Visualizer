# Algorithm Visualizer

An interactive web application that helps students learn Data Structures and Algorithms through step-by-step visual representations.

## Features

- **Interactive Visualizations**: Watch algorithms execute step by step
- **Multiple Sorting Algorithms**: Bubble Sort, Selection Sort (more coming soon)
- **Playback Controls**: Play, pause, step forward/backward through algorithm execution
- **Custom Input**: Enter your own arrays to visualize
- **Detailed Explanations**: Each step includes clear descriptions of what's happening

## Technology Stack

- **Backend**: Java Spring Boot
- **Database**: MySQL
- **Frontend**: HTML, CSS, JavaScript
- **Build Tool**: Maven

## Setup Instructions

### Prerequisites
- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher

### Database Setup
1. Install MySQL and start the service
2. Run the SQL script: `mysql -u root -p < database_setup.sql`
3. Update database credentials in `src/main/resources/application.properties`

### Running the Application
1. Clone the repository
2. Navigate to project directory: `cd AlgorithmVisualizer`
3. Run: `mvn spring-boot:run`
4. Open browser and go to: `http://localhost:8080`

## Usage

1. **Home Page**: Choose from different algorithm categories
2. **Sorting Page**: 
   - Enter custom array or use default
   - Select sorting algorithm
   - Use playback controls to visualize step-by-step execution
3. **Visual Elements**:
   - Blue bars represent array elements
   - Red bars indicate elements being compared/swapped
   - Height represents the value of each element

## Project Structure

```
AlgorithmVisualizer/
├── src/main/java/com/algorithmvisualizer/
│   ├── AlgorithmVisualizerApplication.java
│   ├── algorithm/
│   │   └── SortingAlgorithms.java
│   ├── controller/
│   │   ├── AlgorithmController.java
│   │   └── WebController.java
│   └── model/
│       └── AlgorithmStep.java
├── src/main/resources/
│   ├── static/
│   │   ├── css/style.css
│   │   └── js/sorting.js
│   ├── templates/
│   │   ├── index.html
│   │   └── sorting.html
│   └── application.properties
├── database_setup.sql
└── pom.xml
```

## Future Enhancements

- Search algorithms (Binary Search, Linear Search)
- Data structures (Stack, Queue, Tree, Graph)
- User authentication and progress tracking
- Algorithm complexity analysis
- More sorting algorithms (Quick Sort, Merge Sort, Heap Sort)
- Mobile responsive design

## Contributing

Feel free to contribute by adding new algorithms, improving visualizations, or enhancing the user interface!