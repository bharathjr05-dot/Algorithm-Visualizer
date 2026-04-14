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

let binaryTree = null;
let currentTreeOperation = '';
let operationSteps = [];
let currentOperationStep = 0;

function renderTree() {
    const container = document.getElementById('dsContainer');
    container.innerHTML = '';
    
    // Create operation description box
    const operationBox = document.createElement('div');
    operationBox.className = 'operation-description-box';
    operationBox.id = 'operationBox';
    operationBox.innerHTML = `
        <h4>Current Operation: ${currentTreeOperation || 'None'}</h4>
        <div id="operationSteps">Select an operation to see step-by-step execution</div>
        <div id="traversalInfo">Traversal Order: Root → Left → Right (Pre-order)</div>
    `;
    container.appendChild(operationBox);
    
    // Create tree visualization container
    const treeContainer = document.createElement('div');
    treeContainer.className = 'tree-container';
    treeContainer.id = 'treeVisualization';
    
    if (!binaryTree) {
        treeContainer.innerHTML = '<div class="tree-placeholder">Build tree by inserting nodes</div>';
    } else {
        renderTreeNodes(treeContainer, binaryTree, 400, 50, 200);
    }
    
    container.appendChild(treeContainer);
}

function renderTreeNodes(container, node, x, y, offset) {
    if (!node) return;
    
    // Create node element
    const nodeElement = document.createElement('div');
    nodeElement.className = 'tree-node';
    nodeElement.textContent = node.value;
    nodeElement.style.left = x + 'px';
    nodeElement.style.top = y + 'px';
    
    // Add operation-specific styling
    if (node.isHighlighted) nodeElement.classList.add('highlighted');
    if (node.isDeleting) nodeElement.classList.add('deleting');
    if (node.isNew) nodeElement.classList.add('new-node');
    
    container.appendChild(nodeElement);
    
    // Draw arrows to children
    if (node.left) {
        drawArrow(container, x, y + 25, x - offset, y + 80, 'left');
        renderTreeNodes(container, node.left, x - offset, y + 100, offset / 2);
    }
    
    if (node.right) {
        drawArrow(container, x, y + 25, x + offset, y + 80, 'right');
        renderTreeNodes(container, node.right, x + offset, y + 100, offset / 2);
    }
}

function drawArrow(container, x1, y1, x2, y2, direction) {
    const arrow = document.createElement('div');
    arrow.className = 'tree-arrow';
    
    const length = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    const angle = Math.atan2(y2 - y1, x2 - x1) * 180 / Math.PI;
    
    arrow.style.width = length + 'px';
    arrow.style.left = x1 + 'px';
    arrow.style.top = y1 + 'px';
    arrow.style.transform = `rotate(${angle}deg)`;
    arrow.style.transformOrigin = '0 50%';
    
    // Add direction label
    const label = document.createElement('span');
    label.className = 'arrow-label';
    label.textContent = direction === 'left' ? 'L' : 'R';
    arrow.appendChild(label);
    
    container.appendChild(arrow);
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

// Binary Tree Operations
async function performTreeOperation(operation) {
    const value = parseInt(document.getElementById('treeValue').value);
    if (isNaN(value)) {
        alert('Please enter a valid number');
        return;
    }
    
    currentTreeOperation = operation;
    operationSteps = [];
    currentOperationStep = 0;
    
    switch(operation) {
        case 'insert':
            await animatedInsert(value);
            break;
        case 'delete':
            await animatedDelete(value);
            break;
        case 'search':
            await animatedSearch(value);
            break;
        case 'preorder':
            await animatedTraversal('preorder');
            break;
        case 'inorder':
            await animatedTraversal('inorder');
            break;
        case 'postorder':
            await animatedTraversal('postorder');
            break;
    }
}

async function animatedInsert(value) {
    updateOperationDescription('Insert Operation', `Inserting value ${value} into binary search tree`);
    
    if (!binaryTree) {
        binaryTree = { value: value, left: null, right: null, isNew: true };
        operationSteps.push('Created root node with value ' + value);
    } else {
        await insertNode(binaryTree, value, 'root');
    }
    
    renderTree();
    await sleep(1000);
    clearNodeStates(binaryTree);
    renderTree();
}

async function insertNode(node, value, position) {
    node.isHighlighted = true;
    operationSteps.push(`Comparing ${value} with ${node.value} at ${position}`);
    updateOperationSteps();
    renderTree();
    await sleep(800);
    
    if (value < node.value) {
        if (!node.left) {
            node.left = { value: value, left: null, right: null, isNew: true };
            operationSteps.push(`${value} < ${node.value}, inserted as left child`);
        } else {
            operationSteps.push(`${value} < ${node.value}, going to left subtree`);
            updateOperationSteps();
            await insertNode(node.left, value, 'left child of ' + node.value);
        }
    } else if (value > node.value) {
        if (!node.right) {
            node.right = { value: value, left: null, right: null, isNew: true };
            operationSteps.push(`${value} > ${node.value}, inserted as right child`);
        } else {
            operationSteps.push(`${value} > ${node.value}, going to right subtree`);
            updateOperationSteps();
            await insertNode(node.right, value, 'right child of ' + node.value);
        }
    } else {
        operationSteps.push(`${value} already exists in tree`);
    }
    
    node.isHighlighted = false;
}

async function animatedDelete(value) {
    updateOperationDescription('Delete Operation', `Deleting value ${value} from binary search tree`);
    
    if (!binaryTree) {
        operationSteps.push('Tree is empty, nothing to delete');
        updateOperationSteps();
        return;
    }
    
    const result = await deleteNode(binaryTree, value, null, '');
    if (result.found) {
        binaryTree = result.newRoot;
        operationSteps.push(`Successfully deleted ${value} from tree`);
    } else {
        operationSteps.push(`Value ${value} not found in tree`);
    }
    
    renderTree();
    updateOperationSteps();
}

async function deleteNode(node, value, parent, direction) {
    if (!node) {
        return { found: false, newRoot: null };
    }
    
    node.isHighlighted = true;
    operationSteps.push(`Searching for ${value}, currently at ${node.value}`);
    updateOperationSteps();
    renderTree();
    await sleep(800);
    
    if (value < node.value) {
        operationSteps.push(`${value} < ${node.value}, searching left subtree`);
        updateOperationSteps();
        const result = await deleteNode(node.left, value, node, 'left');
        if (result.found) {
            node.left = result.newRoot;
        }
        node.isHighlighted = false;
        return { found: result.found, newRoot: node };
    } else if (value > node.value) {
        operationSteps.push(`${value} > ${node.value}, searching right subtree`);
        updateOperationSteps();
        const result = await deleteNode(node.right, value, node, 'right');
        if (result.found) {
            node.right = result.newRoot;
        }
        node.isHighlighted = false;
        return { found: result.found, newRoot: node };
    } else {
        // Found the node to delete
        node.isDeleting = true;
        operationSteps.push(`Found ${value}! Analyzing deletion cases...`);
        updateOperationSteps();
        renderTree();
        await sleep(1000);
        
        // Case 1: Leaf node
        if (!node.left && !node.right) {
            operationSteps.push(`${value} is a leaf node - simply remove it`);
            updateOperationSteps();
            await sleep(800);
            return { found: true, newRoot: null };
        }
        
        // Case 2: Node with only right child
        if (!node.left) {
            operationSteps.push(`${value} has only right child - replace with right subtree`);
            updateOperationSteps();
            await sleep(800);
            return { found: true, newRoot: node.right };
        }
        
        // Case 3: Node with only left child
        if (!node.right) {
            operationSteps.push(`${value} has only left child - replace with left subtree`);
            updateOperationSteps();
            await sleep(800);
            return { found: true, newRoot: node.left };
        }
        
        // Case 4: Node with both children
        operationSteps.push(`${value} has both children - finding inorder successor`);
        updateOperationSteps();
        await sleep(800);
        
        const successor = findMin(node.right);
        operationSteps.push(`Inorder successor is ${successor.value} - replacing ${value}`);
        updateOperationSteps();
        
        node.value = successor.value;
        node.isDeleting = false;
        node.isHighlighted = false;
        
        renderTree();
        await sleep(800);
        
        // Delete the successor
        const result = await deleteNode(node.right, successor.value, node, 'right');
        node.right = result.newRoot;
        
        return { found: true, newRoot: node };
    }
}

function findMin(node) {
    while (node.left) {
        node = node.left;
    }
    return node;
}

async function animatedSearch(value) {
    updateOperationDescription('Search Operation', `Searching for value ${value} in binary search tree`);
    
    const found = await searchNode(binaryTree, value, 'root');
    if (found) {
        operationSteps.push(`✅ Found ${value} in the tree!`);
    } else {
        operationSteps.push(`❌ ${value} not found in the tree`);
    }
    
    updateOperationSteps();
    await sleep(1000);
    clearNodeStates(binaryTree);
    renderTree();
}

async function searchNode(node, value, position) {
    if (!node) {
        operationSteps.push(`Reached null node - ${value} not found`);
        updateOperationSteps();
        return false;
    }
    
    node.isHighlighted = true;
    operationSteps.push(`Comparing ${value} with ${node.value} at ${position}`);
    updateOperationSteps();
    renderTree();
    await sleep(800);
    
    if (value === node.value) {
        operationSteps.push(`Found ${value}!`);
        return true;
    } else if (value < node.value) {
        operationSteps.push(`${value} < ${node.value}, searching left subtree`);
        updateOperationSteps();
        node.isHighlighted = false;
        return await searchNode(node.left, value, 'left child of ' + node.value);
    } else {
        operationSteps.push(`${value} > ${node.value}, searching right subtree`);
        updateOperationSteps();
        node.isHighlighted = false;
        return await searchNode(node.right, value, 'right child of ' + node.value);
    }
}

async function animatedTraversal(type) {
    updateOperationDescription(`${type.charAt(0).toUpperCase() + type.slice(1)} Traversal`, 
        `Performing ${type} traversal of the binary tree`);
    
    const traversalOrder = [];
    await performTraversal(binaryTree, type, traversalOrder);
    
    operationSteps.push(`${type} traversal complete: [${traversalOrder.join(', ')}]`);
    updateOperationSteps();
    
    await sleep(1000);
    clearNodeStates(binaryTree);
    renderTree();
}

async function performTraversal(node, type, order) {
    if (!node) return;
    
    node.isHighlighted = true;
    renderTree();
    await sleep(600);
    
    if (type === 'preorder') {
        order.push(node.value);
        operationSteps.push(`Visit root: ${node.value}`);
        updateOperationSteps();
        await performTraversal(node.left, type, order);
        await performTraversal(node.right, type, order);
    } else if (type === 'inorder') {
        await performTraversal(node.left, type, order);
        order.push(node.value);
        operationSteps.push(`Visit root: ${node.value}`);
        updateOperationSteps();
        await performTraversal(node.right, type, order);
    } else if (type === 'postorder') {
        await performTraversal(node.left, type, order);
        await performTraversal(node.right, type, order);
        order.push(node.value);
        operationSteps.push(`Visit root: ${node.value}`);
        updateOperationSteps();
    }
    
    node.isHighlighted = false;
}

function clearNodeStates(node) {
    if (!node) return;
    node.isHighlighted = false;
    node.isDeleting = false;
    node.isNew = false;
    clearNodeStates(node.left);
    clearNodeStates(node.right);
}

function updateOperationDescription(title, description) {
    const operationBox = document.getElementById('operationBox');
    if (operationBox) {
        operationBox.innerHTML = `
            <h4>Current Operation: ${title}</h4>
            <div id="operationSteps">${description}</div>
            <div id="traversalInfo">Traversal Order: Root → Left → Right (Pre-order)</div>
        `;
    }
}

function updateOperationSteps() {
    const stepsElement = document.getElementById('operationSteps');
    if (stepsElement && operationSteps.length > 0) {
        stepsElement.innerHTML = operationSteps.slice(-3).map(step => 
            `<div class="operation-step">• ${step}</div>`
        ).join('');
    }
}

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

// Operation functions
async function performOperation(dsType, operation) {
    try {
        let requestData = {};
        let endpoint = '';
        
        // Prepare request based on data structure and operation
        switch(dsType) {
            case 'array':
                if (operation === 'search') {
                    simulateArraySearch(parseInt(document.getElementById('searchElement').value));
                    return;
                } else if (operation === 'access') {
                    simulateArrayAccess(parseInt(document.getElementById('accessIndex').value));
                    return;
                } else if (operation === 'update') {
                    simulateArrayUpdate(parseInt(document.getElementById('updateIndex').value), parseInt(document.getElementById('updateValue').value));
                    return;
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
    
    if (currentDataStructure === 'array') {
        showArrayStep();
        return;
    }
    
    const step = currentSteps[currentStep];
    document.getElementById('stepDescription').textContent = step.description;
    document.getElementById('stepInfo').textContent = `Step: ${currentStep + 1} / ${currentSteps.length}`;
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

// Array operation simulations
function simulateArraySearch(element) {
    currentSteps = [];
    let found = false;
    
    currentSteps.push({
        description: `🔍 Starting linear search for element ${element} in array [${currentArray.join(', ')}]`,
        highlightIndex: -1,
        operation: ''
    });
    
    for (let i = 0; i < currentArray.length; i++) {
        if (currentArray[i] === element) {
            currentSteps.push({
                description: `✅ Found element ${element} at index ${i}! Search completed.`,
                highlightIndex: i,
                operation: 'found'
            });
            found = true;
            break;
        } else {
            currentSteps.push({
                description: `Checking index ${i}: ${currentArray[i]} ≠ ${element}, continue searching...`,
                highlightIndex: i,
                operation: 'current'
            });
        }
    }
    
    if (!found) {
        currentSteps.push({
            description: `❌ Element ${element} not found in the array. Search completed.`,
            highlightIndex: -1,
            operation: ''
        });
    }
    
    startArrayVisualization();
}

function simulateArrayAccess(index) {
    currentSteps = [];
    
    if (index < 0 || index >= currentArray.length) {
        currentSteps.push({
            description: `❌ Index ${index} is out of bounds! Array size is ${currentArray.length}`,
            highlightIndex: -1,
            operation: ''
        });
    } else {
        currentSteps.push({
            description: `📍 Accessing array at index ${index}...`,
            highlightIndex: -1,
            operation: ''
        });
        
        currentSteps.push({
            description: `✅ Array[${index}] = ${currentArray[index]}. Direct access in O(1) time!`,
            highlightIndex: index,
            operation: 'found'
        });
    }
    
    startArrayVisualization();
}

function simulateArrayUpdate(index, newValue) {
    currentSteps = [];
    
    if (index < 0 || index >= currentArray.length) {
        currentSteps.push({
            description: `❌ Index ${index} is out of bounds! Array size is ${currentArray.length}`,
            highlightIndex: -1,
            operation: ''
        });
    } else {
        const oldValue = currentArray[index];
        
        currentSteps.push({
            description: `📝 Updating array at index ${index} from ${oldValue} to ${newValue}...`,
            highlightIndex: index,
            operation: 'current'
        });
        
        currentArray[index] = newValue;
        
        currentSteps.push({
            description: `✅ Array[${index}] updated successfully! New value: ${newValue}`,
            highlightIndex: index,
            operation: 'found'
        });
        
        document.getElementById('arrayInput').value = currentArray.join(',');
    }
    
    startArrayVisualization();
}

function startArrayVisualization() {
    currentStep = 0;
    document.getElementById('prevBtn').disabled = false;
    document.getElementById('nextBtn').disabled = false;
    document.getElementById('playBtn').disabled = false;
    showArrayStep();
}

function showArrayStep() {
    if (currentSteps.length === 0) return;
    
    const step = currentSteps[currentStep];
    document.getElementById('stepDescription').textContent = step.description;
    document.getElementById('stepInfo').textContent = `Step: ${currentStep + 1} / ${currentSteps.length}`;
    
    document.getElementById('prevBtn').disabled = currentStep === 0;
    document.getElementById('nextBtn').disabled = currentStep === currentSteps.length - 1;
    
    renderArray(currentArray, step.highlightIndex, step.operation);
}

// Initialize
document.addEventListener('DOMContentLoaded', function() {
    renderCurrentDataStructure();
    
    // Add CSS for tree visualization
    const style = document.createElement('style');
    style.textContent = `
        .operation-description-box {
            background: #f8f9fa;
            border: 2px solid #dee2e6;
            border-radius: 8px;
            padding: 15px;
            margin-bottom: 20px;
            font-family: 'Courier New', monospace;
        }
        
        .operation-description-box h4 {
            margin: 0 0 10px 0;
            color: #495057;
        }
        
        .operation-step {
            margin: 5px 0;
            padding: 3px 0;
            color: #6c757d;
        }
        
        .tree-container {
            position: relative;
            height: 400px;
            overflow: visible;
        }
        
        .tree-node {
            position: absolute;
            width: 40px;
            height: 40px;
            border-radius: 50%;
            background: #007bff;
            color: white;
            display: flex;
            align-items: center;
            justify-content: center;
            font-weight: bold;
            font-size: 14px;
            transition: all 0.3s ease;
            z-index: 10;
        }
        
        .tree-node.highlighted {
            background: #ffc107;
            color: #000;
            transform: scale(1.2);
            box-shadow: 0 0 15px rgba(255, 193, 7, 0.6);
        }
        
        .tree-node.deleting {
            background: #dc3545;
            animation: pulse 0.5s infinite;
        }
        
        .tree-node.new-node {
            background: #28a745;
            animation: bounce 0.6s ease;
        }
        
        @keyframes pulse {
            0%, 100% { transform: scale(1); }
            50% { transform: scale(1.1); }
        }
        
        @keyframes bounce {
            0%, 20%, 50%, 80%, 100% { transform: translateY(0); }
            40% { transform: translateY(-10px); }
            60% { transform: translateY(-5px); }
        }
        
        .tree-arrow {
            position: absolute;
            height: 2px;
            background: #6c757d;
            z-index: 5;
            transition: all 0.3s ease;
        }
        
        .tree-arrow::after {
            content: '';
            position: absolute;
            right: 0;
            top: -3px;
            width: 0;
            height: 0;
            border-left: 8px solid #6c757d;
            border-top: 4px solid transparent;
            border-bottom: 4px solid transparent;
        }
        
        .arrow-label {
            position: absolute;
            top: -15px;
            left: 50%;
            transform: translateX(-50%);
            font-size: 10px;
            font-weight: bold;
            color: #495057;
            background: white;
            padding: 1px 3px;
            border-radius: 3px;
        }
        
        .tree-placeholder {
            text-align: center;
            color: #6c757d;
            font-style: italic;
            margin-top: 100px;
        }
    `;
    document.head.appendChild(style);
});