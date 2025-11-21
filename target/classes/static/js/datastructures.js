let currentDataStructure = 'array';
let currentSteps = [];
let currentStep = 0;
let isPlaying = false;
let playInterval;

// Current data structure states
let currentArray = [10, 20, 30, 40, 50];
let currentLinkedList = [10, 20, 30, 40];
let currentStack = [10, 20, 30];
let currentQueue = [10, 20, 30];

function showDataStructure(dsType) {
    // Update active card
    document.querySelectorAll('.ds-category-card').forEach(card => {
        card.classList.remove('active');
    });
    event.target.closest('.ds-category-card').classList.add('active');
    
    // Hide all sections
    document.querySelectorAll('.ds-section').forEach(section => {
        section.classList.remove('active');
    });
    
    // Show selected section
    document.getElementById(dsType + '-section').classList.add('active');
    currentDataStructure = dsType;
    
    // Reset visualization
    resetVisualization();
    renderCurrentDataStructure();
}

function resetVisualization() {
    currentSteps = [];
    currentStep = 0;
    document.getElementById('prevBtn').disabled = true;
    document.getElementById('nextBtn').disabled = true;
    document.getElementById('playBtn').disabled = true;
    document.getElementById('stepInfo').textContent = 'Step: 0 / 0';
    document.getElementById('stepDescription').textContent = 'Select an operation to start visualization';
}

function renderCurrentDataStructure() {
    const container = document.getElementById('dsContainer');
    container.innerHTML = '';
    
    switch(currentDataStructure) {
        case 'array':
            renderArray(currentArray);
            break;
        case 'linkedlist':
            renderLinkedList(currentLinkedList);
            break;
        case 'stack':
        case 'stack-linkedlist':
            renderStack(currentStack);
            break;
        case 'stack-array':
            renderStackArray();
            break;
        case 'queue':
        case 'queue-linkedlist':
            renderQueue(currentQueue);
            break;
        case 'queue-array':
            renderQueueArray();
            break;
        case 'tree':
            renderTree();
            break;
        case 'graph':
            renderGraph();
            break;
    }
}

function renderArray(array, highlightIndex = -1, operation = '') {
    const container = document.getElementById('dsContainer');
    container.innerHTML = '';
    
    array.forEach((value, index) => {
        const element = document.createElement('div');
        element.className = 'array-element';
        element.textContent = value;
        
        if (index === highlightIndex) {
            element.classList.add(operation);
        }
        
        // Add index label
        const indexLabel = document.createElement('div');
        indexLabel.className = 'array-index';
        indexLabel.textContent = index;
        element.appendChild(indexLabel);
        
        container.appendChild(element);
    });
}

function renderLinkedList(list, highlightIndex = -1) {
    const container = document.getElementById('dsContainer');
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
            
            // Add labels
            if (index === 0) {
                const label = document.createElement('div');
                label.className = 'node-label';
                label.textContent = 'HEAD';
                node.appendChild(label);
            }
            
            listContainer.appendChild(node);
            
            // Add arrow except for last element
            if (index < list.length - 1) {
                const arrow = document.createElement('div');
                arrow.className = 'node-arrow';
                arrow.textContent = '→';
                listContainer.appendChild(arrow);
            }
        });
        
        // Add NULL at the end
        const nullNode = document.createElement('div');
        nullNode.className = 'node-arrow';
        nullNode.textContent = '→ NULL';
        listContainer.appendChild(nullNode);
    }
    
    container.appendChild(listContainer);
}

function renderStack(stack, highlightIndex = -1, operation = '') {
    const container = document.getElementById('dsContainer');
    container.innerHTML = '';
    
    const stackContainer = document.createElement('div');
    stackContainer.className = 'stack-container';
    
    if (stack.length === 0) {
        stackContainer.innerHTML = '<div class="stack-element">EMPTY STACK</div>';
    } else {
        stack.forEach((value, index) => {
            const element = document.createElement('div');
            element.className = 'stack-element';
            element.textContent = value;
            
            if (index === stack.length - 1) {
                element.classList.add('top');
                const label = document.createElement('div');
                label.className = 'stack-label';
                label.textContent = 'TOP';
                element.appendChild(label);
            }
            
            if (index === highlightIndex) {
                element.classList.add(operation);
            }
            
            stackContainer.appendChild(element);
        });
    }
    
    container.appendChild(stackContainer);
}

function renderQueue(queue, highlightIndex = -1, operation = '') {
    const container = document.getElementById('dsContainer');
    container.innerHTML = '';
    
    const queueContainer = document.createElement('div');
    queueContainer.className = 'queue-container';
    
    if (queue.length === 0) {
        queueContainer.innerHTML = '<div class="queue-element">EMPTY QUEUE</div>';
    } else {
        queue.forEach((value, index) => {
            const element = document.createElement('div');
            element.className = 'queue-element';
            element.textContent = value;
            
            if (index === 0) element.classList.add('front');
            if (index === queue.length - 1) element.classList.add('rear');
            if (index === highlightIndex) element.classList.add(operation);
            
            queueContainer.appendChild(element);
        });
        
        // Add labels
        const frontLabel = document.createElement('div');
        frontLabel.textContent = 'FRONT';
        frontLabel.style.position = 'absolute';
        frontLabel.style.top = '-30px';
        frontLabel.style.left = '0';
        frontLabel.style.fontSize = '12px';
        frontLabel.style.fontWeight = 'bold';
        queueContainer.style.position = 'relative';
        queueContainer.appendChild(frontLabel);
        
        const rearLabel = document.createElement('div');
        rearLabel.textContent = 'REAR';
        rearLabel.style.position = 'absolute';
        rearLabel.style.top = '-30px';
        rearLabel.style.right = '0';
        rearLabel.style.fontSize = '12px';
        rearLabel.style.fontWeight = 'bold';
        queueContainer.appendChild(rearLabel);
    }
    
    container.appendChild(queueContainer);
}

function renderStackArray() {
    const container = document.getElementById('dsContainer');
    container.innerHTML = '<div>Stack Array visualization - Initialize first</div>';
}

function renderQueueArray() {
    const container = document.getElementById('dsContainer');
    container.innerHTML = '<div>Queue Array visualization - Initialize first</div>';
}

function renderTree() {
    const container = document.getElementById('dsContainer');
    container.innerHTML = '<div>Binary Tree visualization - Build tree first</div>';
}

function renderGraph() {
    const container = document.getElementById('dsContainer');
    container.innerHTML = '<div>Graph visualization - Initialize graph first</div>';
}

// Update functions
function updateArray() {
    const input = document.getElementById('arrayInput').value;
    try {
        currentArray = input.split(',').map(num => parseInt(num.trim()));
        renderArray(currentArray);
        document.getElementById('stepDescription').textContent = 'Array updated successfully';
    } catch (error) {
        alert('Please enter valid numbers separated by commas');
    }
}

function updateLinkedList() {
    const input = document.getElementById('linkedlistInput').value;
    try {
        currentLinkedList = input.split(',').map(num => parseInt(num.trim()));
        renderLinkedList(currentLinkedList);
        document.getElementById('stepDescription').textContent = 'Linked List updated successfully';
    } catch (error) {
        alert('Please enter valid numbers separated by commas');
    }
}

function updateStack() {
    const input = document.getElementById('stackInput').value;
    try {
        currentStack = input.split(',').map(num => parseInt(num.trim()));
        renderStack(currentStack);
        document.getElementById('stepDescription').textContent = 'Stack updated successfully';
    } catch (error) {
        alert('Please enter valid numbers separated by commas');
    }
}

function updateQueue() {
    const input = document.getElementById('queueInput').value;
    try {
        currentQueue = input.split(',').map(num => parseInt(num.trim()));
        renderQueue(currentQueue);
        document.getElementById('stepDescription').textContent = 'Queue updated successfully';
    } catch (error) {
        alert('Please enter valid numbers separated by commas');
    }
}

// Operation functions
async function performOperation(dsType, operation) {
    try {
        let requestData = {};
        let endpoint = '';
        
        // Prepare request based on data structure and operation
        switch(dsType) {
            case 'array':
                endpoint = `/api/datastructures/array/${operation}`;
                if (operation === 'insert') {
                    requestData = {
                        array: currentArray,
                        element: parseInt(document.getElementById('insertElement').value),
                        position: parseInt(document.getElementById('insertPosition').value)
                    };
                } else if (operation === 'delete') {
                    requestData = {
                        array: currentArray,
                        position: parseInt(document.getElementById('deletePosition').value)
                    };
                } else if (operation === 'search') {
                    requestData = {
                        array: currentArray,
                        element: parseInt(document.getElementById('searchElement').value)
                    };
                }
                break;
                
            case 'linkedlist':
                endpoint = `/api/datastructures/linkedlist/${operation}`;
                if (operation === 'insert-head') {
                    requestData = {
                        list: currentLinkedList,
                        element: parseInt(document.getElementById('insertHeadElement').value)
                    };
                } else if (operation === 'insert-tail') {
                    requestData = {
                        list: currentLinkedList,
                        element: parseInt(document.getElementById('insertTailElement').value)
                    };
                } else if (operation === 'delete-head') {
                    requestData = { list: currentLinkedList };
                } else if (operation === 'search') {
                    requestData = {
                        list: currentLinkedList,
                        element: parseInt(document.getElementById('searchLinkedListElement').value)
                    };
                }
                break;
                
            case 'stack':
                endpoint = `/api/datastructures/stack/${operation}`;
                if (operation === 'push') {
                    requestData = {
                        stack: currentStack,
                        element: parseInt(document.getElementById('pushElement').value)
                    };
                } else {
                    requestData = { stack: currentStack };
                }
                break;
        }
        
        const response = await fetch(endpoint, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestData)
        });
        
        currentSteps = await response.json();
        currentStep = 0;
        
        document.getElementById('prevBtn').disabled = false;
        document.getElementById('nextBtn').disabled = false;
        document.getElementById('playBtn').disabled = false;
        
        showCurrentStep();
        
    } catch (error) {
        console.error('Error performing operation:', error);
        document.getElementById('stepDescription').textContent = 'Error performing operation. Please try again.';
    }
}

function showCurrentStep() {
    if (currentSteps.length === 0) return;
    
    const step = currentSteps[currentStep];
    document.getElementById('stepDescription').textContent = step.description;
    
    // Update step info
    document.getElementById('stepInfo').textContent = 
        `Step: ${currentStep + 1} / ${currentSteps.length}`;
    
    // Render based on current data structure
    // This would need to be expanded based on the step data
    renderCurrentDataStructure();
}

function nextStep() {
    if (currentStep < currentSteps.length - 1) {
        currentStep++;
        showCurrentStep();
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

// Initialize
document.addEventListener('DOMContentLoaded', function() {
    renderCurrentDataStructure();
});