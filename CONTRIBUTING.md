# 🤝 Contributing to Algorithm Visualizer

Thank you for your interest in contributing to the Algorithm Visualizer project! This guide will help you get started with contributing effectively.

## 📋 Table of Contents
- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [Development Workflow](#development-workflow)
- [Coding Standards](#coding-standards)
- [Adding New Features](#adding-new-features)
- [Testing Guidelines](#testing-guidelines)
- [Pull Request Process](#pull-request-process)
- [Issue Reporting](#issue-reporting)

## 🤝 Code of Conduct

### Our Pledge
We are committed to making participation in this project a harassment-free experience for everyone, regardless of age, body size, disability, ethnicity, gender identity and expression, level of experience, nationality, personal appearance, race, religion, or sexual identity and orientation.

### Expected Behavior
- Use welcoming and inclusive language
- Be respectful of differing viewpoints and experiences
- Gracefully accept constructive criticism
- Focus on what is best for the community
- Show empathy towards other community members

## 🚀 Getting Started

### 1. Fork the Repository
```bash
# Fork the repo on GitHub, then clone your fork
git clone https://github.com/YOUR_USERNAME/algorithm-visualizer.git
cd algorithm-visualizer
```

### 2. Set Up Development Environment
```bash
# Add upstream remote
git remote add upstream https://github.com/ORIGINAL_OWNER/algorithm-visualizer.git

# Install dependencies
mvn clean install

# Set up database (see SETUP_GUIDE.md)
mysql -u root -p algorithm_visualizer < database_setup_complete.sql
```

### 3. Create a Branch
```bash
# Create and switch to a new branch
git checkout -b feature/your-feature-name
```

## 🔄 Development Workflow

### Branch Naming Convention
- **Features**: `feature/algorithm-name` or `feature/description`
- **Bug Fixes**: `bugfix/issue-description`
- **Documentation**: `docs/update-description`
- **Refactoring**: `refactor/component-name`

### Commit Message Format
```
Type: Brief description (50 chars max)

Detailed explanation of what and why (if needed)
- Use bullet points for multiple changes
- Reference issues with #issue-number

Examples:
Add: Binary search algorithm implementation
Fix: Sorting animation timing issue #123
Update: README with new setup instructions
Refactor: Extract common visualization logic
```

### Commit Types
- **Add**: New features or files
- **Fix**: Bug fixes
- **Update**: Modifications to existing features
- **Remove**: Deleted features or files
- **Refactor**: Code restructuring without functionality changes
- **Docs**: Documentation changes
- **Test**: Adding or modifying tests

## 📝 Coding Standards

### Java (Backend)
```java
// Class naming: PascalCase
public class SortingAlgorithms {
    
    // Method naming: camelCase
    public List<AlgorithmStep> bubbleSort(int[] array) {
        // Variable naming: camelCase
        List<AlgorithmStep> steps = new ArrayList<>();
        
        // Constants: UPPER_SNAKE_CASE
        private static final int MAX_ARRAY_SIZE = 1000;
        
        // Comments for complex logic
        // Bubble sort: Compare adjacent elements and swap if needed
        for (int i = 0; i < array.length - 1; i++) {
            // ... implementation
        }
        
        return steps;
    }
}
```

### JavaScript (Frontend)
```javascript
// Function naming: camelCase
function performBubbleSort() {
    // Variable naming: camelCase
    const arrayElements = document.querySelectorAll('.array-element');
    
    // Constants: UPPER_SNAKE_CASE
    const ANIMATION_DELAY = 500;
    
    // Use modern ES6+ features
    const steps = [];
    
    // Arrow functions for callbacks
    arrayElements.forEach((element, index) => {
        // ... implementation
    });
}

// Use async/await for API calls
async function fetchAlgorithmSteps(algorithm, data) {
    try {
        const response = await fetch(`/api/algorithms/${algorithm}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        return await response.json();
    } catch (error) {
        console.error('Error fetching algorithm steps:', error);
        throw error;
    }
}
```

### CSS
```css
/* Use BEM methodology for class names */
.algorithm-visualizer {
    /* Block */
}

.algorithm-visualizer__container {
    /* Element */
}

.algorithm-visualizer__container--active {
    /* Modifier */
}

/* Use CSS custom properties for theming */
:root {
    --primary-color: #667eea;
    --secondary-color: #764ba2;
    --animation-duration: 0.3s;
}

/* Mobile-first responsive design */
.array-element {
    /* Base styles for mobile */
}

@media (min-width: 768px) {
    .array-element {
        /* Tablet styles */
    }
}

@media (min-width: 1024px) {
    .array-element {
        /* Desktop styles */
    }
}
```

### HTML (Thymeleaf)
```html
<!-- Use semantic HTML elements -->
<main class="algorithm-container">
    <section class="controls-section">
        <h2>Algorithm Controls</h2>
        <!-- Use proper form structure -->
        <form class="algorithm-form">
            <div class="input-group">
                <label for="arrayInput">Array Input:</label>
                <input type="text" id="arrayInput" 
                       th:value="${defaultArray}" 
                       placeholder="Enter comma-separated numbers">
            </div>
        </form>
    </section>
    
    <section class="visualization-section">
        <h2>Visualization</h2>
        <div class="array-container" id="arrayContainer">
            <!-- Dynamic content -->
        </div>
    </section>
</main>
```

## ✨ Adding New Features

### Adding a New Algorithm

#### 1. Backend Implementation
```java
// In SortingAlgorithms.java or SearchingAlgorithms.java
public static List<AlgorithmStep> newAlgorithm(int[] array) {
    List<AlgorithmStep> steps = new ArrayList<>();
    int[] workingArray = array.clone();
    
    // Add initial state
    steps.add(new AlgorithmStep(
        "New Algorithm",
        1,
        "🚀 Starting new algorithm visualization",
        Arrays.toString(workingArray),
        ""
    ));
    
    // Algorithm implementation with step tracking
    // ... your algorithm logic here
    
    return steps;
}
```

#### 2. Controller Endpoint
```java
// In AlgorithmController.java
@PostMapping("/new-algorithm")
public List<AlgorithmStep> newAlgorithm(@RequestBody int[] array) {
    return SortingAlgorithms.newAlgorithm(array);
}
```

#### 3. Frontend Integration
```javascript
// In sorting.js or searching.js
async function performNewAlgorithm() {
    const array = getCurrentArray();
    
    try {
        const response = await fetch('/api/algorithms/new-algorithm', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(array)
        });
        
        const steps = await response.json();
        startVisualization(steps);
        
    } catch (error) {
        console.error('Error:', error);
        showErrorMessage('Failed to execute algorithm');
    }
}
```

#### 4. UI Integration
```html
<!-- Add button to HTML template -->
<button class="algorithm-btn" onclick="performNewAlgorithm()">
    New Algorithm
</button>
```

### Adding a New Data Structure

#### 1. Create Entity Class
```java
@Entity
@Table(name = "data_structures")
public class DataStructure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String description;
    private String complexity;
    
    // Constructors, getters, setters
}
```

#### 2. Create Repository
```java
@Repository
public interface DataStructureRepository extends JpaRepository<DataStructure, Long> {
    List<DataStructure> findByNameContaining(String name);
}
```

#### 3. Create Service
```java
@Service
public class DataStructureService {
    @Autowired
    private DataStructureRepository repository;
    
    public List<DataStructure> getAllDataStructures() {
        return repository.findAll();
    }
}
```

#### 4. Create Controller
```java
@RestController
@RequestMapping("/api/datastructures")
public class DataStructureController {
    @Autowired
    private DataStructureService service;
    
    @GetMapping
    public List<DataStructure> getAllDataStructures() {
        return service.getAllDataStructures();
    }
}
```

## 🧪 Testing Guidelines

### Unit Tests
```java
@SpringBootTest
class SortingAlgorithmsTest {
    
    @Test
    void testBubbleSort() {
        // Arrange
        int[] input = {64, 34, 25, 12, 22, 11, 90};
        int[] expected = {11, 12, 22, 25, 34, 64, 90};
        
        // Act
        List<AlgorithmStep> steps = SortingAlgorithms.bubbleSort(input);
        
        // Assert
        assertNotNull(steps);
        assertFalse(steps.isEmpty());
        
        // Verify final state
        AlgorithmStep lastStep = steps.get(steps.size() - 1);
        // Parse and verify sorted array
    }
}
```

### Integration Tests
```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class AlgorithmControllerIntegrationTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    void testBubbleSortEndpoint() {
        // Arrange
        int[] input = {3, 1, 4, 1, 5};
        
        // Act
        ResponseEntity<AlgorithmStep[]> response = restTemplate.postForEntity(
            "/api/algorithms/bubble-sort", 
            input, 
            AlgorithmStep[].class
        );
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}
```

### Frontend Testing
```javascript
// Test algorithm visualization
describe('Algorithm Visualization', () => {
    test('should render array elements correctly', () => {
        const array = [3, 1, 4, 1, 5];
        renderArray(array);
        
        const elements = document.querySelectorAll('.array-element');
        expect(elements).toHaveLength(5);
        expect(elements[0].textContent).toBe('3');
    });
    
    test('should highlight current element during sorting', () => {
        const step = {
            highlightedElements: '0,1',
            operation: 'COMPARE'
        };
        
        visualizeStep(step);
        
        const highlighted = document.querySelectorAll('.array-element.current');
        expect(highlighted).toHaveLength(2);
    });
});
```

## 📥 Pull Request Process

### Before Submitting
1. **Update your branch** with latest changes
   ```bash
   git fetch upstream
   git rebase upstream/main
   ```

2. **Run tests** and ensure they pass
   ```bash
   mvn test
   ```

3. **Check code style** and fix any issues
   ```bash
   mvn checkstyle:check
   ```

4. **Update documentation** if needed

### PR Template
```markdown
## Description
Brief description of changes made.

## Type of Change
- [ ] Bug fix (non-breaking change which fixes an issue)
- [ ] New feature (non-breaking change which adds functionality)
- [ ] Breaking change (fix or feature that would cause existing functionality to not work as expected)
- [ ] Documentation update

## Testing
- [ ] Unit tests pass
- [ ] Integration tests pass
- [ ] Manual testing completed

## Screenshots (if applicable)
Add screenshots to help explain your changes.

## Checklist
- [ ] My code follows the style guidelines of this project
- [ ] I have performed a self-review of my own code
- [ ] I have commented my code, particularly in hard-to-understand areas
- [ ] I have made corresponding changes to the documentation
- [ ] My changes generate no new warnings
- [ ] I have added tests that prove my fix is effective or that my feature works
```

### Review Process
1. **Automated checks** must pass (CI/CD pipeline)
2. **Code review** by at least one team member
3. **Testing** in development environment
4. **Documentation** review if applicable
5. **Merge** after approval

## 🐛 Issue Reporting

### Bug Reports
Use this template for bug reports:

```markdown
**Bug Description**
A clear and concise description of what the bug is.

**To Reproduce**
Steps to reproduce the behavior:
1. Go to '...'
2. Click on '....'
3. Scroll down to '....'
4. See error

**Expected Behavior**
A clear and concise description of what you expected to happen.

**Screenshots**
If applicable, add screenshots to help explain your problem.

**Environment:**
- OS: [e.g. Windows 10, macOS, Ubuntu]
- Browser: [e.g. Chrome, Firefox, Safari]
- Java Version: [e.g. 17.0.1]
- MySQL Version: [e.g. 8.0.28]

**Additional Context**
Add any other context about the problem here.
```

### Feature Requests
```markdown
**Feature Description**
A clear and concise description of what you want to happen.

**Problem Statement**
Explain the problem this feature would solve.

**Proposed Solution**
Describe the solution you'd like to see implemented.

**Alternatives Considered**
Describe any alternative solutions or features you've considered.

**Additional Context**
Add any other context or screenshots about the feature request here.
```

## 📚 Resources

### Learning Materials
- **Spring Boot**: https://spring.io/guides
- **Algorithm Visualization**: https://visualgo.net/
- **Java Best Practices**: https://www.oracle.com/java/technologies/javase/codeconventions-contents.html
- **JavaScript Modern Features**: https://developer.mozilla.org/en-US/docs/Web/JavaScript

### Tools
- **IDE**: IntelliJ IDEA, VS Code, Eclipse
- **Database**: MySQL Workbench, phpMyAdmin
- **API Testing**: Postman, Insomnia
- **Version Control**: Git, GitHub Desktop

---

**Thank you for contributing to Algorithm Visualizer! 🚀**