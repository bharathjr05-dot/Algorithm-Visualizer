// Backend integration for graph algorithms
class GraphBackend {
    constructor() {
        this.baseUrl = '/api/graph';
    }
    
    async runDFS(edges, startNode, isDirected = true) {
        try {
            const response = await fetch(`${this.baseUrl}/dfs`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    edges: edges,
                    startNode: startNode,
                    isDirected: isDirected
                })
            });
            
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            
            return await response.json();
        } catch (error) {
            console.error('Error running DFS:', error);
            return [];
        }
    }
    
    async runBFS(edges, startNode, isDirected = true) {
        try {
            const response = await fetch(`${this.baseUrl}/bfs`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    edges: edges,
                    startNode: startNode,
                    isDirected: isDirected
                })
            });
            
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            
            return await response.json();
        } catch (error) {
            console.error('Error running BFS:', error);
            return [];
        }
    }
    
    async runDijkstra(edges, startNode, isDirected = true) {
        try {
            const response = await fetch(`${this.baseUrl}/dijkstra`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    edges: edges,
                    startNode: startNode,
                    isDirected: isDirected
                })
            });
            
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            
            return await response.json();
        } catch (error) {
            console.error('Error running Dijkstra:', error);
            return [];
        }
    }
}

// Integration with existing graph visualization
if (typeof graph !== 'undefined') {
    const graphBackend = new GraphBackend();
    
    // Override existing algorithm functions to use backend
    const originalDFS = graph.dfs;
    const originalBFS = graph.bfs;
    const originalDijkstra = graph.dijkstra;
    
    graph.dfsBackend = async function(start) {
        if (this.isAnimating) return;
        this.isAnimating = true;
        
        // Convert graph to edges format
        const edges = [];
        for (let [from, connections] of this.edges) {
            for (let connection of connections) {
                edges.push({
                    from: from,
                    to: connection.to,
                    weight: connection.weight
                });
            }
        }
        
        updateStatus('Running DFS with backend...');
        const steps = await graphBackend.runDFS(edges, start, true);
        
        // Animate the steps
        for (let i = 0; i < steps.length; i++) {
            const step = steps[i];
            
            updateAlgorithmInfo('DFS - ' + step.operation, [
                step.description,
                'Visited: [' + (step.visited || []).join(', ') + ']',
                'Status: ' + step.status
            ]);
            
            if (step.currentNode >= 0) {
                this.highlightNode(step.currentNode, '#00b894');
            }
            
            if (step.targetNode >= 0) {
                this.highlightEdge(step.currentNode, step.targetNode, '#00b894');
            }
            
            await sleep(1000);
        }
        
        this.isAnimating = false;
    };
    
    graph.bfsBackend = async function(start) {
        if (this.isAnimating) return;
        this.isAnimating = true;
        
        // Convert graph to edges format
        const edges = [];
        for (let [from, connections] of this.edges) {
            for (let connection of connections) {
                edges.push({
                    from: from,
                    to: connection.to,
                    weight: connection.weight
                });
            }
        }
        
        updateStatus('Running BFS with backend...');
        const steps = await graphBackend.runBFS(edges, start, true);
        
        // Animate the steps
        for (let i = 0; i < steps.length; i++) {
            const step = steps[i];
            
            updateAlgorithmInfo('BFS - ' + step.operation, [
                step.description,
                'Visited: [' + (step.visited || []).join(', ') + ']',
                'Queue: [' + (step.queue || []).join(', ') + ']',
                'Status: ' + step.status
            ]);
            
            if (step.currentNode >= 0) {
                this.highlightNode(step.currentNode, '#4ecdc4');
            }
            
            if (step.targetNode >= 0) {
                this.highlightEdge(step.currentNode, step.targetNode, '#4ecdc4');
            }
            
            await sleep(1000);
        }
        
        this.isAnimating = false;
    };
    
    graph.dijkstraBackend = async function(start) {
        if (this.isAnimating) return;
        this.isAnimating = true;
        
        // Convert graph to edges format
        const edges = [];
        for (let [from, connections] of this.edges) {
            for (let connection of connections) {
                edges.push({
                    from: from,
                    to: connection.to,
                    weight: connection.weight
                });
            }
        }
        
        updateStatus('Running Dijkstra with backend...');
        const steps = await graphBackend.runDijkstra(edges, start, true);
        
        // Animate the steps
        for (let i = 0; i < steps.length; i++) {
            const step = steps[i];
            
            updateAlgorithmInfo('Dijkstra - ' + step.operation, [
                step.description,
                'Visited: [' + (step.visited || []).join(', ') + ']',
                'Status: ' + step.status
            ]);
            
            if (step.currentNode >= 0) {
                this.highlightNode(step.currentNode, '#ff9ff3');
            }
            
            if (step.targetNode >= 0) {
                this.highlightEdge(step.currentNode, step.targetNode, '#feca57');
            }
            
            await sleep(1200);
        }
        
        this.isAnimating = false;
    };
}