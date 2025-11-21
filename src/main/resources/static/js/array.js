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

async function performInsert() {
    const element = parseInt(document.getElementById('insertElement').value);
    const position = parseInt(document.getElementById('insertPosition').value);
    
    try {
        const response = await fetch('/api/datastructures/array/insert', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                array: currentArray,
                element: element,
                position: position
            })
        });
        
        currentSteps = await response.json();
        currentStep = 0;
        enablePlayback();
        showCurrentStep();
        
    } catch (error) {
        console.error('Error:', error);
        document.getElementById('stepDescription').textContent = 'Error performing insert operation';
    }
}

async function performDelete() {
    const position = parseInt(document.getElementById('deletePosition').value);
    
    try {
        const response = await fetch('/api/datastructures/array/delete', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                array: currentArray,
                position: position
            })
        });
        
        currentSteps = await response.json();
        currentStep = 0;
        enablePlayback();
        showCurrentStep();
        
    } catch (error) {
        console.error('Error:', error);
        document.getElementById('stepDescription').textContent = 'Error performing delete operation';
    }
}

async function performSearch() {
    const element = parseInt(document.getElementById('searchElement').value);
    
    try {
        const response = await fetch('/api/datastructures/array/search', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                array: currentArray,
                element: element
            })
        });
        
        currentSteps = await response.json();
        currentStep = 0;
        enablePlayback();
        showCurrentStep();
        
    } catch (error) {
        console.error('Error:', error);
        document.getElementById('stepDescription').textContent = 'Error performing search operation';
    }
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
    if (step.operation === 'INSERT') operation = 'inserting';
    else if (step.operation === 'DELETE') operation = 'deleting';
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