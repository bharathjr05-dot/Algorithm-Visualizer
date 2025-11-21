let currentArray = [64, 34, 25, 12, 22, 11, 90];
let algorithmSteps = [];
let currentStep = 0;
let isPlaying = false;
let playInterval;

function parseArray() {
    const input = document.getElementById('arrayInput').value;
    try {
        currentArray = input.split(',').map(num => parseInt(num.trim()));
        renderArray(currentArray);
        document.getElementById('stepDescription').textContent = 'Array updated. Select an algorithm to start visualization.';
    } catch (error) {
        alert('Please enter valid numbers separated by commas');
    }
}

function renderArray(array, highlightedIndices = []) {
    const container = document.getElementById('arrayContainer');
    container.innerHTML = '';
    
    array.forEach((value, index) => {
        const element = document.createElement('div');
        element.className = 'array-element';
        element.textContent = value;
        element.style.height = `${value * 3}px`;
        
        if (highlightedIndices.includes(index)) {
            element.classList.add('highlighted');
        }
        
        container.appendChild(element);
    });
}

async function startVisualization(algorithm) {
    try {
        const response = await fetch(`/api/algorithms/${algorithm}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(currentArray)
        });
        
        algorithmSteps = await response.json();
        currentStep = 0;
        
        updateStepInfo();
        showCurrentStep();
        
        document.getElementById('prevBtn').disabled = false;
        document.getElementById('nextBtn').disabled = false;
        document.getElementById('playBtn').disabled = false;
        
    } catch (error) {
        console.error('Error fetching algorithm steps:', error);
        document.getElementById('stepDescription').textContent = 'Error loading algorithm. Please try again.';
    }
}

function showCurrentStep() {
    if (algorithmSteps.length === 0) return;
    
    const step = algorithmSteps[currentStep];
    const arrayState = JSON.parse(step.arrayState.replace(/\[|\]/g, '').split(',').map(s => s.trim()));
    const highlightedIndices = step.highlightedIndices ? 
        step.highlightedIndices.split(',').map(i => parseInt(i.trim())) : [];
    
    renderArray(arrayState, highlightedIndices);
    document.getElementById('stepDescription').textContent = step.description;
    updateStepInfo();
}

function nextStep() {
    if (currentStep < algorithmSteps.length - 1) {
        currentStep++;
        showCurrentStep();
    }
    
    if (currentStep === algorithmSteps.length - 1) {
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
        if (currentStep < algorithmSteps.length - 1) {
            nextStep();
        } else {
            stopPlay();
        }
    }, 1000);
}

function stopPlay() {
    isPlaying = false;
    document.getElementById('playBtn').textContent = 'Play';
    clearInterval(playInterval);
}

function updateStepInfo() {
    document.getElementById('stepInfo').textContent = 
        `Step: ${currentStep + 1} / ${algorithmSteps.length}`;
}

// Initialize
document.addEventListener('DOMContentLoaded', function() {
    renderArray(currentArray);
});