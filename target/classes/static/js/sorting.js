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

function renderArray(array, highlightedIndices = [], isSwapping = false) {
    const container = document.getElementById('arrayContainer');
    container.innerHTML = '';
    
    const step = algorithmSteps[currentStep];
    const algorithmName = step ? step.algorithmName : '';
    const description = step ? step.description.toLowerCase() : '';
    
    if (algorithmName === 'Insertion Sort') {
        renderInsertionSortArray(array, highlightedIndices, description);
    } else if (algorithmName === 'Quick Sort') {
        renderQuickSortArray(array, highlightedIndices, description);
    } else if (algorithmName === 'Merge Sort' && (description.includes('dividing') || description.includes('merging') || description.includes('comparing'))) {
        renderMergeSortArray(array, highlightedIndices, description);
    } else {
        array.forEach((value, index) => {
            const element = document.createElement('div');
            element.className = 'array-element';
            element.textContent = value;
            element.style.height = `${value * 3}px`;
            element.style.position = 'relative';
            
            if (highlightedIndices.includes(index)) {
                element.classList.add('highlighted');
                if (isSwapping) {
                    element.classList.add('swapping');
                }
            }
            
            container.appendChild(element);
        });
        
        if (isSwapping && highlightedIndices.length === 2) {
            createSwapArrows(highlightedIndices[0], highlightedIndices[1]);
        }
    }
}

function renderMergeSortArray(array, highlightedIndices, description) {
    const container = document.getElementById('arrayContainer');
    
    if (highlightedIndices.length >= 3) {
        // Dividing or merging phase - show subarrays
        const indices = highlightedIndices.map(i => parseInt(i));
        const left = indices[0];
        const mid = indices[1];
        const right = indices[2];
        
        // Render left subarray
        for (let i = left; i <= mid; i++) {
            const element = document.createElement('div');
            element.className = 'array-element left-subarray';
            element.textContent = array[i];
            element.style.height = `${array[i] * 3}px`;
            element.style.position = 'relative';
            container.appendChild(element);
        }
        
        // Add separator
        const separator = document.createElement('div');
        separator.className = 'array-separator';
        separator.textContent = '|';
        container.appendChild(separator);
        
        // Render right subarray
        for (let i = mid + 1; i <= right; i++) {
            const element = document.createElement('div');
            element.className = 'array-element right-subarray';
            element.textContent = array[i];
            element.style.height = `${array[i] * 3}px`;
            element.style.position = 'relative';
            container.appendChild(element);
        }
        
    } else {
        // Regular rendering with special highlighting
        array.forEach((value, index) => {
            const element = document.createElement('div');
            element.className = 'array-element';
            element.textContent = value;
            element.style.height = `${value * 3}px`;
            element.style.position = 'relative';
            
            if (highlightedIndices.includes(index)) {
                if (description.includes('merged') || description.includes('placed')) {
                    element.classList.add('merged');
                } else {
                    element.classList.add('highlighted');
                }
            }
            
            container.appendChild(element);
        });
    }
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
        console.log('Algorithm steps:', algorithmSteps);
        currentStep = 0;
        
        showConceptBox(algorithm);
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
    console.log('Current step:', step);
    console.log('Array state:', step.arrayState);
    
    const arrayState = step.arrayState.replace(/\[|\]/g, '').split(',').map(s => parseInt(s.trim()));
    const highlightedIndices = step.highlightedIndices ? 
        step.highlightedIndices.split(',').map(i => parseInt(i.trim())).filter(i => !isNaN(i)) : [];
    
    const isSwapping = step.description.toLowerCase().includes('swapped');
    renderArray(arrayState, highlightedIndices, isSwapping);
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

function createSwapArrows(leftIndex, rightIndex) {
    const container = document.getElementById('arrayContainer');
    const elements = container.children;
    
    if (leftIndex < elements.length && rightIndex < elements.length) {
        const leftElement = elements[leftIndex];
        const rightElement = elements[rightIndex];
        
        const swapLine = document.createElement('div');
        swapLine.className = 'swap-line';
        
        const leftRect = leftElement.getBoundingClientRect();
        const rightRect = rightElement.getBoundingClientRect();
        const containerRect = container.getBoundingClientRect();
        
        const leftPos = leftRect.left - containerRect.left;
        const rightPos = rightRect.left - containerRect.left + rightRect.width;
        
        swapLine.style.left = leftPos + 'px';
        swapLine.style.width = (rightPos - leftPos) + 'px';
        
        container.appendChild(swapLine);
        
        setTimeout(() => {
            if (swapLine.parentNode) {
                swapLine.remove();
            }
        }, 1000);
    }
}

function showConceptBox(algorithm) {
    const conceptBox = document.getElementById('conceptBox');
    const conceptTitle = document.getElementById('conceptTitle');
    const conceptPoints = document.getElementById('conceptPoints');
    
    const concepts = {
        'bubble-sort': {
            title: 'Bubble Sort Concept',
            points: [
                'Compare adjacent elements',
                'Swap if left > right',
                'Largest element "bubbles" to end',
                'Repeat for remaining elements',
                'Time Complexity: O(n²)'
            ]
        },
        'selection-sort': {
            title: 'Selection Sort Concept',
            points: [
                'Find minimum element in unsorted part',
                'Swap with first unsorted element',
                'Move boundary of sorted part',
                'Repeat until array is sorted',
                'Time Complexity: O(n²)'
            ]
        },
        'insertion-sort': {
            title: 'Insertion Sort - Like Sorting Cards',
            points: [
                '🟢 Green: Sorted portion (organized cards)',
                '⚪ Gray: Unsorted portion (remaining cards)',
                '🔴 Red: Element being inserted (current card)',
                '🟡 Yellow: Elements shifting right (making space)',
                'Think: Organizing playing cards in your hand!',
                'Time Complexity: O(n²)'
            ]
        },
        'quick-sort': {
            title: 'Quick Sort - Divide & Conquer',
            points: [
                '🟠 Orange: PIVOT element (the divider)',
                '🔵 Blue: Elements smaller than pivot',
                '🔴 Pink: Elements greater than pivot',
                '🟢 Green: Elements in final position',
                'Think: Organizing by height - short/tall sides!',
                'Time Complexity: O(n log n)'
            ]
        },
        'merge-sort': {
            title: 'Merge Sort - Split & Combine',
            points: [
                '🔵 Blue: Left subarray being processed',
                '🔴 Pink: Right subarray being processed',
                '🟢 Green: Successfully merged elements',
                '| Separator: Visual division between parts',
                'Think: Merging two sorted decks of cards!',
                'Time Complexity: O(n log n)'
            ]
        }
    };
    
    const concept = concepts[algorithm];
    if (concept) {
        conceptTitle.textContent = concept.title;
        conceptPoints.innerHTML = concept.points.map(point => `<li>${point}</li>`).join('');
        conceptBox.style.display = 'block';
    }
}

function renderInsertionSortArray(array, highlightedIndices, description) {
    const container = document.getElementById('arrayContainer');
    
    array.forEach((value, index) => {
        const element = document.createElement('div');
        element.className = 'array-element';
        element.textContent = value;
        element.style.height = `${value * 3}px`;
        element.style.position = 'relative';
        
        if (description.includes('taking') && highlightedIndices.includes(index)) {
            element.classList.add('inserting');
        } else if (description.includes('shifting') && highlightedIndices.includes(index)) {
            element.classList.add('shifting');
        } else if (description.includes('inserted') && highlightedIndices.includes(index)) {
            element.classList.add('sorted-part');
        } else if (index <= getCurrentSortedBoundary()) {
            element.classList.add('sorted-part');
        } else {
            element.classList.add('unsorted-part');
        }
        
        container.appendChild(element);
    });
}

function renderQuickSortArray(array, highlightedIndices, description) {
    const container = document.getElementById('arrayContainer');
    
    array.forEach((value, index) => {
        const element = document.createElement('div');
        element.className = 'array-element';
        element.textContent = value;
        element.style.height = `${value * 3}px`;
        element.style.position = 'relative';
        
        if (description.includes('pivot') && description.includes('choosing') && highlightedIndices.includes(index)) {
            element.classList.add('pivot');
        } else if (description.includes('smaller') && highlightedIndices.includes(index)) {
            element.classList.add('smaller-than-pivot');
        } else if (description.includes('comparing') && highlightedIndices.includes(index)) {
            element.classList.add('highlighted');
        } else if (highlightedIndices.includes(index)) {
            element.classList.add('highlighted');
        }
        
        container.appendChild(element);
    });
}

function getCurrentSortedBoundary() {
    const step = algorithmSteps[currentStep];
    if (!step || step.algorithmName !== 'Insertion Sort') return -1;
    
    const match = step.description.match(/position (\d+)/);
    return match ? parseInt(match[1]) - 1 : -1;
}

document.addEventListener('DOMContentLoaded', function() {
    renderArray(currentArray);
});