let currentAlgorithm = 'linear-search';
let searchSteps = [];
let currentStepIndex = 0;
let isPlaying = false;
let playInterval;

const algorithmInfo = {
    'linear-search': {
        title: 'Linear Search',
        description: 'Linear search checks each element sequentially until the target is found or the end is reached.',
        timeComplexity: 'O(n)',
        spaceComplexity: 'O(1)',
        bestCase: 'O(1)',
        worstCase: 'O(n)'
    },
    'binary-search': {
        title: 'Binary Search',
        description: 'Binary search efficiently finds target in sorted array by repeatedly dividing search interval in half.',
        timeComplexity: 'O(log n)',
        spaceComplexity: 'O(1)',
        bestCase: 'O(1)',
        worstCase: 'O(log n)'
    },
    'jump-search': {
        title: 'Jump Search',
        description: 'Jump search skips elements by fixed steps, then performs linear search in identified block.',
        timeComplexity: 'O(√n)',
        spaceComplexity: 'O(1)',
        bestCase: 'O(1)',
        worstCase: 'O(√n)'
    },
    'interpolation-search': {
        title: 'Interpolation Search',
        description: 'Interpolation search estimates position based on value distribution in uniformly distributed sorted arrays.',
        timeComplexity: 'O(log log n)',
        spaceComplexity: 'O(1)',
        bestCase: 'O(1)',
        worstCase: 'O(n)'
    }
};

// Initialize the page
document.addEventListener('DOMContentLoaded', function() {
    setupEventListeners();
    updateAlgorithmInfo();
    generateArrayVisualization();
});

function setupEventListeners() {
    // Algorithm selection buttons
    document.querySelectorAll('.algorithm-btn').forEach(btn => {
        btn.addEventListener('click', function() {
            selectAlgorithm(this.dataset.algorithm);
        });
    });
    
    // Input change listeners
    document.getElementById('arrayInput').addEventListener('input', generateArrayVisualization);
    document.getElementById('targetInput').addEventListener('input', updateTargetDisplay);
}

function selectAlgorithm(algorithm) {
    currentAlgorithm = algorithm;
    
    // Update button states
    document.querySelectorAll('.algorithm-btn').forEach(btn => {
        btn.classList.remove('active');
    });
    document.querySelector(`[data-algorithm="${algorithm}"]`).classList.add('active');
    
    updateAlgorithmInfo();
    resetVisualization();
}

function updateAlgorithmInfo() {
    const info = algorithmInfo[currentAlgorithm];
    document.getElementById('algorithmTitle').textContent = info.title;
    document.getElementById('algorithmDescription').textContent = info.description;
    document.getElementById('timeComplexity').textContent = info.timeComplexity;
    document.getElementById('spaceComplexity').textContent = info.spaceComplexity;
    document.getElementById('bestCase').textContent = info.bestCase;
    document.getElementById('worstCase').textContent = info.worstCase;
}

function generateArrayVisualization() {
    const arrayInput = document.getElementById('arrayInput').value;
    const container = document.getElementById('arrayContainer');
    
    if (!arrayInput.trim()) {
        container.innerHTML = '<p>Please enter an array</p>';
        return;
    }
    
    try {
        const array = arrayInput.split(',').map(num => parseInt(num.trim()));
        container.innerHTML = '';
        
        array.forEach((value, index) => {
            const element = document.createElement('div');
            element.className = 'array-element';
            element.textContent = value;
            element.dataset.index = index;
            element.dataset.value = value;
            container.appendChild(element);
        });
    } catch (error) {
        container.innerHTML = '<p>Invalid array format</p>';
    }
}

function updateTargetDisplay() {
    const target = document.getElementById('targetInput').value;
    document.getElementById('targetDisplay').textContent = `Target: ${target}`;
}

function generateRandomArray() {
    const size = Math.floor(Math.random() * 8) + 5; // 5-12 elements
    const array = [];
    
    for (let i = 0; i < size; i++) {
        array.push(Math.floor(Math.random() * 100) + 1);
    }
    
    // Sort for binary search algorithms
    if (currentAlgorithm !== 'linear-search') {
        array.sort((a, b) => a - b);
    }
    
    document.getElementById('arrayInput').value = array.join(',');
    document.getElementById('targetInput').value = array[Math.floor(Math.random() * array.length)];
    
    generateArrayVisualization();
    updateTargetDisplay();
}

function sortArray() {
    const arrayInput = document.getElementById('arrayInput').value;
    if (!arrayInput.trim()) return;
    
    try {
        const array = arrayInput.split(',').map(num => parseInt(num.trim()));
        array.sort((a, b) => a - b);
        document.getElementById('arrayInput').value = array.join(',');
        generateArrayVisualization();
    } catch (error) {
        alert('Invalid array format');
    }
}

async function startSearch() {
    const arrayInput = document.getElementById('arrayInput').value;
    const targetInput = document.getElementById('targetInput').value;
    
    if (!arrayInput.trim() || !targetInput.trim()) {
        alert('Please enter both array and target value');
        return;
    }
    
    try {
        const array = arrayInput.split(',').map(num => parseInt(num.trim()));
        const target = parseInt(targetInput);
        
        // Validate sorted array for algorithms that require it
        if (['binary-search', 'jump-search', 'interpolation-search'].includes(currentAlgorithm)) {
            const isSorted = array.every((val, i, arr) => i === 0 || arr[i - 1] <= val);
            if (!isSorted) {
                const shouldSort = confirm('This algorithm requires a sorted array. Sort automatically?');
                if (shouldSort) {
                    sortArray();
                    return;
                } else {
                    return;
                }
            }
        }
        
        const response = await fetch(`/api/algorithms/${currentAlgorithm}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                array: array,
                target: target
            })
        });
        
        console.log('Request sent:', { array, target, algorithm: currentAlgorithm });
        console.log('Response status:', response.status);
        
        if (!response.ok) {
            const errorText = await response.text();
            console.error('Server error:', errorText);
            throw new Error(`Server error: ${response.status} - ${errorText}`);
        }
        
        searchSteps = await response.json();
        currentStepIndex = 0;
        
        document.getElementById('totalSteps').textContent = searchSteps.length;
        document.getElementById('currentStep').textContent = '0';
        
        // Enable navigation buttons
        document.getElementById('nextBtn').disabled = false;
        document.getElementById('playBtn').disabled = false;
        
        resetArrayVisualization();
        updateStepInfo();
        
    } catch (error) {
        console.error('Error:', error);
        alert('Error starting search: ' + error.message);
    }
}

function resetVisualization() {
    searchSteps = [];
    currentStepIndex = 0;
    isPlaying = false;
    
    if (playInterval) {
        clearInterval(playInterval);
        playInterval = null;
    }
    
    document.getElementById('currentStep').textContent = '0';
    document.getElementById('totalSteps').textContent = '0';
    document.getElementById('stepDescription').textContent = 'Click "Start Search" to begin visualization';
    
    // Disable navigation buttons
    document.getElementById('prevBtn').disabled = true;
    document.getElementById('nextBtn').disabled = true;
    document.getElementById('playBtn').disabled = true;
    document.getElementById('playBtn').textContent = 'Auto Play';
    
    resetArrayVisualization();
}

function resetArrayVisualization() {
    document.querySelectorAll('.array-element').forEach(element => {
        element.className = 'array-element';
    });
}

function updateStepInfo() {
    if (searchSteps.length === 0) return;
    
    const step = searchSteps[currentStepIndex];
    document.getElementById('currentStep').textContent = currentStepIndex + 1;
    document.getElementById('stepDescription').textContent = step.description;
    
    // Update navigation buttons
    document.getElementById('prevBtn').disabled = currentStepIndex === 0;
    document.getElementById('nextBtn').disabled = currentStepIndex === searchSteps.length - 1;
    
    visualizeStep(step);
}

function visualizeStep(step) {
    resetArrayVisualization();
    
    // Highlight current element being checked
    if (step.highlightedIndices) {
        const indices = step.highlightedIndices.split(',');
        indices.forEach(indexStr => {
            if (indexStr.trim()) {
                const index = parseInt(indexStr.trim());
                const element = document.querySelector(`[data-index="${index}"]`);
                if (element) {
                    if (step.description.includes('Found target')) {
                        element.classList.add('found');
                    } else {
                        element.classList.add('current');
                    }
                }
            }
        });
    }
    
    // Handle range highlighting for binary search
    if (step.highlightedIndices && step.highlightedIndices.includes(',')) {
        const parts = step.highlightedIndices.split(',');
        if (parts.length === 2) {
            const start = parseInt(parts[0]);
            const end = parseInt(parts[1]);
            
            for (let i = start; i <= end; i++) {
                const element = document.querySelector(`[data-index="${i}"]`);
                if (element && !element.classList.contains('current') && !element.classList.contains('found')) {
                    element.classList.add('range');
                }
            }
        }
    }
}

function nextStep() {
    if (currentStepIndex < searchSteps.length - 1) {
        currentStepIndex++;
        updateStepInfo();
    }
}

function previousStep() {
    if (currentStepIndex > 0) {
        currentStepIndex--;
        updateStepInfo();
    }
}

function autoPlay() {
    if (isPlaying) {
        // Stop playing
        isPlaying = false;
        clearInterval(playInterval);
        document.getElementById('playBtn').textContent = 'Auto Play';
        document.getElementById('nextBtn').disabled = currentStepIndex === searchSteps.length - 1;
        document.getElementById('prevBtn').disabled = currentStepIndex === 0;
    } else {
        // Start playing
        isPlaying = true;
        document.getElementById('playBtn').textContent = 'Pause';
        document.getElementById('nextBtn').disabled = true;
        document.getElementById('prevBtn').disabled = true;
        
        playInterval = setInterval(() => {
            if (currentStepIndex < searchSteps.length - 1) {
                nextStep();
            } else {
                // Finished playing
                isPlaying = false;
                clearInterval(playInterval);
                document.getElementById('playBtn').textContent = 'Auto Play';
                document.getElementById('nextBtn').disabled = true;
                document.getElementById('prevBtn').disabled = false;
            }
        }, 1500); // 1.5 second delay between steps
    }
}