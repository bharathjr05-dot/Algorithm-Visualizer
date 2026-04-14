let currentArray = [10, 20, 30, 40, 50];
let currentSteps = [];
let currentStep = 0;
let isPlaying = false;
let playInterval;

function updateArray() {
    const input = document.getElementById('arrayInput').value;
    try {
        currentArray = input.split(',').map(num => parseInt(num.trim()));
        renderArray(currentArray);
        document.getElementById('stepDescription').textContent = 'Array updated successfully';
        resetVisualization();
    } catch (error) {
        alert('Please enter valid numbers separated by commas');
    }
}

function renderArray(array, highlightIndex = -1, operation = '') {
    const container = document.getElementById('arrayContainer');
    container.innerHTML = '';
    
    array.forEach((value, index) => {
        const element = document.createElement('div');
        element.className = 'array-element';
        element.textContent = value;
        
        if (index === highlightIndex) {
            element.classList.add(operation);
        }
        
        const indexLabel = document.createElement('div');
        indexLabel.className = 'array-index';
        indexLabel.textContent = `[${index}]`;
        element.appendChild(indexLabel);
        
        container.appendChild(element);
    });
}

function performAccess() {
    const index = parseInt(document.getElementById('accessIndex').value);
    
    currentSteps = [];
    
    if (index < 0 || index >= currentArray.length) {
        currentSteps.push({
            description: `❌ Index ${index} is out of bounds! Array size is ${currentArray.length} (valid indices: 0-${currentArray.length-1})`,
            currentState: JSON.stringify(currentArray),
            highlightedElements: '',
            operation: 'ACCESS'
        });
    } else {
        currentSteps.push({
            description: `📍 Accessing array at index ${index}...`,
            currentState: JSON.stringify(currentArray),
            highlightedElements: '',
            operation: 'ACCESS'
        });
        
        currentSteps.push({
            description: `✅ Array[${index}] = ${currentArray[index]}. Direct access in O(1) time!`,
            currentState: JSON.stringify(currentArray),
            highlightedElements: index.toString(),
            operation: 'ACCESS'
        });
    }
    
    currentStep = 0;
    enablePlayback();
    showCurrentStep();
}

function performUpdate() {
    const index = parseInt(document.getElementById('updateIndex').value);
    const newValue = parseInt(document.getElementById('updateValue').value);
    
    currentSteps = [];
    
    if (index < 0 || index >= currentArray.length) {
        currentSteps.push({
            description: `❌ Index ${index} is out of bounds! Array size is ${currentArray.length} (valid indices: 0-${currentArray.length-1})`,
            currentState: JSON.stringify(currentArray),
            highlightedElements: '',
            operation: 'UPDATE'
        });
    } else {
        const oldValue = currentArray[index];
        
        currentSteps.push({
            description: `📝 Updating array at index ${index} from ${oldValue} to ${newValue}...`,
            currentState: JSON.stringify(currentArray),
            highlightedElements: index.toString(),
            operation: 'UPDATE'
        });
        
        currentArray[index] = newValue;
        document.getElementById('arrayInput').value = currentArray.join(',');
        
        currentSteps.push({
            description: `✅ Array[${index}] updated successfully! New value: ${newValue}. Update completed in O(1) time!`,
            currentState: JSON.stringify(currentArray),
            highlightedElements: index.toString(),
            operation: 'UPDATE'
        });
    }
    
    currentStep = 0;
    enablePlayback();
    showCurrentStep();
}

function performSearch() {
    const element = parseInt(document.getElementById('searchElement').value);
    
    currentSteps = [];
    let found = false;
    
    currentSteps.push({
        description: `🔍 Starting linear search for element ${element} in array [${currentArray.join(', ')}]`,
        currentState: JSON.stringify(currentArray),
        highlightedElements: '',
        operation: 'SEARCH'
    });
    
    for (let i = 0; i < currentArray.length; i++) {
        if (currentArray[i] === element) {
            currentSteps.push({
                description: `✅ Found element ${element} at index ${i}! Search completed.`,
                currentState: JSON.stringify(currentArray),
                highlightedElements: i.toString(),
                operation: 'SEARCH'
            });
            found = true;
            break;
        } else {
            currentSteps.push({
                description: `Checking index ${i}: ${currentArray[i]} ≠ ${element}, continue searching...`,
                currentState: JSON.stringify(currentArray),
                highlightedElements: i.toString(),
                operation: 'SEARCH'
            });
        }
    }
    
    if (!found) {
        currentSteps.push({
            description: `❌ Element ${element} not found in the array. Search completed.`,
            currentState: JSON.stringify(currentArray),
            highlightedElements: '',
            operation: 'SEARCH'
        });
    }
    
    currentStep = 0;
    enablePlayback();
    showCurrentStep();
}

function showCurrentStep() {
    if (currentSteps.length === 0) return;
    
    const step = currentSteps[currentStep];
    document.getElementById('stepDescription').textContent = step.description;
    
    // Parse current state and highlighted elements
    let arrayState = [];
    try {
        if (step.currentState.startsWith('[') && step.currentState.endsWith(']')) {
            arrayState = JSON.parse(step.currentState);
        } else {
            arrayState = step.currentState.replace(/\[|\]/g, '').split(',').map(s => parseInt(s.trim())).filter(n => !isNaN(n));
        }
    } catch (e) {
        arrayState = step.currentState.replace(/\[|\]/g, '').split(',').map(s => parseInt(s.trim())).filter(n => !isNaN(n));
    }
    
    const highlightedElements = step.highlightedElements ? 
        step.highlightedElements.split(',').map(i => parseInt(i.trim())).filter(i => !isNaN(i)) : [];
    
    // Determine operation type for styling
    let operation = '';
    if (step.operation === 'ACCESS') operation = 'accessing';
    else if (step.operation === 'UPDATE') operation = 'updating';
    else if (step.operation === 'SEARCH') operation = 'searching';
    
    // Render array with highlighting
    const container = document.getElementById('arrayContainer');
    container.innerHTML = '';
    
    arrayState.forEach((value, index) => {
        const element = document.createElement('div');
        element.className = 'array-element';
        element.textContent = value;
        
        if (highlightedElements.includes(index)) {
            element.classList.add(operation);
        }
        
        const indexLabel = document.createElement('div');
        indexLabel.className = 'array-index';
        indexLabel.textContent = `[${index}]`;
        element.appendChild(indexLabel);
        
        container.appendChild(element);
    });
    
    updateStepInfo();
}

function enablePlayback() {
    document.getElementById('prevBtn').disabled = false;
    document.getElementById('nextBtn').disabled = false;
    document.getElementById('playBtn').disabled = false;
}

function resetVisualization() {
    currentSteps = [];
    currentStep = 0;
    document.getElementById('prevBtn').disabled = true;
    document.getElementById('nextBtn').disabled = true;
    document.getElementById('playBtn').disabled = true;
    document.getElementById('stepInfo').textContent = 'Step: 0 / 0';
}

function nextStep() {
    if (currentStep < currentSteps.length - 1) {
        currentStep++;
        showCurrentStep();
    }
    
    if (currentStep === currentSteps.length - 1) {
        stopPlay();
    }
}

function previousStep() {
    if (currentStep > 0) {
        currentStep--;
        showCurrentStep();
    }
}

function togglePlay() {
    if (isPlaying) {
        stopPlay();
    } else {
        startPlay();
    }
}

function startPlay() {
    isPlaying = true;
    document.getElementById('playBtn').textContent = 'Pause';
    
    playInterval = setInterval(() => {
        if (currentStep < currentSteps.length - 1) {
            nextStep();
        } else {
            stopPlay();
        }
    }, 1500);
}

function stopPlay() {
    isPlaying = false;
    document.getElementById('playBtn').textContent = 'Play';
    clearInterval(playInterval);
}

function updateStepInfo() {
    document.getElementById('stepInfo').textContent = 
        `Step: ${currentStep + 1} / ${currentSteps.length}`;
}

// Initialize
document.addEventListener('DOMContentLoaded', function() {
    renderArray(currentArray);
});