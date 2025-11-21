let currentLinkedList = [10, 20, 30, 40];
let currentSteps = [];
let currentStep = 0;
let isPlaying = false;
let playInterval;

function updateLinkedList() {
    const input = document.getElementById('linkedlistInput').value;
    try {
        currentLinkedList = input.split(',').map(num => parseInt(num.trim()));
        renderLinkedList(currentLinkedList);
        document.getElementById('stepDescription').textContent = 'Linked List updated successfully';
        resetVisualization();
    } catch (error) {
        alert('Please enter valid numbers separated by commas');
    }
}

function renderLinkedList(list, highlightIndex = -1) {
    const container = document.getElementById('linkedlistContainer');
    container.innerHTML = '';
    
    const listContainer = document.createElement('div');
    listContainer.className = 'linkedlist-container';
    
    if (list.length === 0) {
        listContainer.innerHTML = '<div class="linkedlist-node">NULL</div>';
    } else {
        list.forEach((value, index) => {
            const node = document.createElement('div');
            node.className = 'linkedlist-node';
            
            if (index === 0) node.classList.add('head');
            if (index === list.length - 1) node.classList.add('tail');
            if (index === highlightIndex) node.classList.add('highlighted');
            
            const nodeData = document.createElement('div');
            nodeData.className = 'node-data';
            nodeData.textContent = value;
            node.appendChild(nodeData);
            
            if (index === 0) {
                const label = document.createElement('div');
                label.className = 'node-label';
                label.textContent = 'HEAD';
                node.appendChild(label);
            }
            
            listContainer.appendChild(node);
            
            if (index < list.length - 1) {
                const arrow = document.createElement('div');
                arrow.className = 'node-arrow';
                arrow.textContent = '→';
                listContainer.appendChild(arrow);
            }
        });
        
        const nullNode = document.createElement('div');
        nullNode.className = 'node-arrow';
        nullNode.textContent = '→ NULL';
        listContainer.appendChild(nullNode);
    }
    
    container.appendChild(listContainer);
}

async function performInsertHead() {
    const element = parseInt(document.getElementById('insertHeadElement').value);
    
    try {
        const response = await fetch('/api/datastructures/linkedlist/insert-head', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                list: currentLinkedList,
                element: element
            })
        });
        
        currentSteps = await response.json();
        currentStep = 0;
        enablePlayback();
        showCurrentStep();
        
    } catch (error) {
        console.error('Error:', error);
        document.getElementById('stepDescription').textContent = 'Error performing insert head operation';
    }
}

async function performInsertTail() {
    const element = parseInt(document.getElementById('insertTailElement').value);
    
    try {
        const response = await fetch('/api/datastructures/linkedlist/insert-tail', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                list: currentLinkedList,
                element: element
            })
        });
        
        currentSteps = await response.json();
        currentStep = 0;
        enablePlayback();
        showCurrentStep();
        
    } catch (error) {
        console.error('Error:', error);
        document.getElementById('stepDescription').textContent = 'Error performing insert tail operation';
    }
}

async function performDeleteHead() {
    try {
        const response = await fetch('/api/datastructures/linkedlist/delete-head', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                list: currentLinkedList
            })
        });
        
        currentSteps = await response.json();
        currentStep = 0;
        enablePlayback();
        showCurrentStep();
        
    } catch (error) {
        console.error('Error:', error);
        document.getElementById('stepDescription').textContent = 'Error performing delete head operation';
    }
}

async function performSearch() {
    const element = parseInt(document.getElementById('searchElement').value);
    
    try {
        const response = await fetch('/api/datastructures/linkedlist/search', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                list: currentLinkedList,
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
    
    // Parse the linked list string format: [10] -> [20] -> [30] -> NULL
    const stateStr = step.currentState;
    let listData = [];
    
    if (stateStr !== "NULL") {
        const matches = stateStr.match(/\[(\d+)\]/g);
        if (matches) {
            listData = matches.map(match => parseInt(match.replace(/\[|\]/g, '')));
        }
    }
    
    const highlightedElements = step.highlightedElements ? 
        step.highlightedElements.split(',').map(i => parseInt(i.trim())).filter(i => !isNaN(i)) : [];
    
    const highlightIndex = highlightedElements.length > 0 ? highlightedElements[0] : -1;
    
    renderLinkedList(listData, highlightIndex);
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

document.addEventListener('DOMContentLoaded', function() {
    renderLinkedList(currentLinkedList);
});