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
    container.style.cssText = 'display:flex;flex-direction:row;align-items:flex-end;justify-content:center;height:300px;gap:4px;padding:10px;';

    const step = algorithmSteps[currentStep];
    const algorithmName = step ? step.algorithmName : '';
    const description = step ? step.description.toLowerCase() : '';

    if (algorithmName === 'Insertion Sort') {
        renderInsertionSortArray(array, highlightedIndices, description);
    } else if (algorithmName === 'Quick Sort') {
        renderQuickSortArray(array, highlightedIndices, description);
    } else {
        const maxVal = Math.max(...array, 1);
        array.forEach((value, index) => {
            const el = document.createElement('div');
            el.className = 'array-element';
            el.textContent = value;
            el.style.height = `${(value / maxVal) * 250}px`;
            el.style.position = 'relative';
            if (highlightedIndices.includes(index)) {
                el.classList.add('highlighted');
                if (isSwapping) el.classList.add('swapping');
            }
            container.appendChild(el);
        });
        if (isSwapping && highlightedIndices.length === 2) createSwapArrows(highlightedIndices[0], highlightedIndices[1]);
    }
}

async function startVisualization(algorithm) {
    try {
        const response = await fetch(`/api/algorithms/${algorithm}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(currentArray)
        });
        if (!response.ok) throw new Error(`Server error: ${response.status}`);
        algorithmSteps = await response.json();
        currentStep = 0;
        showConceptBox(algorithm);
        updateStepInfo();
        showCurrentStep();
        document.getElementById('prevBtn').disabled = false;
        document.getElementById('nextBtn').disabled = false;
        document.getElementById('playBtn').disabled = false;
    } catch (error) {
        console.error('Error:', error);
        document.getElementById('stepDescription').textContent = 'Error loading algorithm: ' + error.message;
    }
}

function showCurrentStep() {
    if (algorithmSteps.length === 0) return;
    const step = algorithmSteps[currentStep];
    if (step.algorithmName === 'Merge Sort') {
        renderMergeSortTree(step);
        document.getElementById('stepDescription').textContent = step.description;
    } else {
        const arrayState = step.arrayState.replace(/\[|\]/g, '').split(',').map(s => parseInt(s.trim()));
        const highlightedIndices = step.highlightedIndices ?
            step.highlightedIndices.split(',').map(i => parseInt(i.trim())).filter(i => !isNaN(i)) : [];
        const isSwapping = step.description.toLowerCase().includes('swapped');
        renderArray(arrayState, highlightedIndices, isSwapping);
        document.getElementById('stepDescription').textContent = step.description;
    }
    updateStepInfo();
}

// ═══════════════════════════════════════════════════════════════════════════
//  MERGE SORT — FULL TREE WITH SVG ARROWS (DIVIDE + MERGE TOP-DOWN)
// ═══════════════════════════════════════════════════════════════════════════

function parseTreeEncoding(encoded) {
    const body = encoded.slice('TREE|'.length);
    return body.split(';').map(levelStr => {
        if (!levelStr) return [];
        return levelStr.split('~').map(nodeStr => {
            const parts = nodeStr.split(',');
            const l = parseInt(parts[0]);
            const r = parseInt(parts[1]);
            const phase = parts[2];
            const vals = parts[3] ? parts[3].trim().split(' ').map(Number).filter(x => !isNaN(x)) : [];
            let mergeDetail = null;
            if (parts.length > 4) {
                mergeDetail = {
                    activeSide: parseInt(parts[4]),
                    lv: parts[5] ? parts[5].trim().split(' ').map(Number).filter(x => !isNaN(x)) : [],
                    rv: parts[6] ? parts[6].trim().split(' ').map(Number).filter(x => !isNaN(x)) : [],
                    mv: parts[7] ? parts[7].trim().split(' ').map(Number).filter(x => !isNaN(x)) : []
                };
            }
            return { l, r, phase, vals, mergeDetail };
        });
    });
}

const NODE_H    = 46;
const NODE_CELL = 34;
const NODE_PAD  = 14;
const LEVEL_H   = 120;
const MIN_NODE_W = 46;

function nodeWidth(vals) {
    return Math.max(MIN_NODE_W, vals.length * NODE_CELL + NODE_PAD * 2);
}

function renderMergeSortTree(step) {
    const vizArea = document.querySelector('.visualization-area') || document.body;
    const container = document.getElementById('arrayContainer');
    container.innerHTML = '';

    const encoded = step.highlightedIndices;
    if (!encoded || !encoded.startsWith('TREE|')) return;

    const levels = parseTreeEncoding(encoded);

    // ── Detect active merging node ──────────────────────────────────────────
    let mergingLevel = -1, mergingNi = -1, mergeDetail = null;
    outer:
    for (let lv = 0; lv < levels.length; lv++) {
        for (let ni = 0; ni < levels[lv].length; ni++) {
            if (levels[lv][ni].mergeDetail) {
                mergingLevel = lv;
                mergingNi = ni;
                mergeDetail = levels[lv][ni].mergeDetail;
                break outer;
            }
        }
    }

    // ── Canvas size ─────────────────────────────────────────────────────────
    const numVisLevels = levels.filter(lv => lv.some(n => n.phase !== 'HIDDEN')).length;
    const availW = Math.max(vizArea.clientWidth - 40, 960);
    // Extra height when merge panel is shown: panel sits below the merging node's level
    const mergePanelH = mergeDetail ? 140 : 0;
    const canvasH = numVisLevels * LEVEL_H + 60 + mergePanelH;

    container.style.cssText = `
        position: relative;
        width: ${availW}px;
        min-width: ${availW}px;
        height: ${canvasH}px;
        overflow: visible;
        background: transparent;
        padding: 0; margin: 0;
    `;

    // ── Compute node positions ───────────────────────────────────────────────
    // nodePos[lv][ni] = { cx, y } or null if HIDDEN
    const nodePos = [];
    let visLv = 0;
    for (let lv = 0; lv < levels.length; lv++) {
        const visible = levels[lv].filter(n => n.phase !== 'HIDDEN');
        if (visible.length === 0) { nodePos.push(levels[lv].map(() => null)); continue; }

        const y = visLv * LEVEL_H + 30;
        let vi = 0;
        const posMap = levels[lv].map(node => {
            if (node.phase === 'HIDDEN') return null;
            const cx = (availW / (visible.length + 1)) * (vi + 1);
            vi++;
            return { cx, y };
        });
        nodePos.push(posMap);
        visLv++;
    }

    // ── SVG layer ────────────────────────────────────────────────────────────
    const svg = document.createElementNS('http://www.w3.org/2000/svg', 'svg');
    svg.style.cssText = 'position:absolute;top:0;left:0;pointer-events:none;overflow:visible;';
    svg.setAttribute('width', availW);
    svg.setAttribute('height', canvasH);

    // Arrow markers
    const defs = document.createElementNS('http://www.w3.org/2000/svg', 'defs');
    [
        { id: 'arr-divide', color: '#e17055' },
        { id: 'arr-merge',  color: '#00b894' },
        { id: 'arr-active', color: '#f39c12' }
    ].forEach(({ id, color }) => {
        const m = document.createElementNS('http://www.w3.org/2000/svg', 'marker');
        m.setAttribute('id', id);
        m.setAttribute('markerWidth', '9'); m.setAttribute('markerHeight', '9');
        m.setAttribute('refX', '7'); m.setAttribute('refY', '3');
        m.setAttribute('orient', 'auto');
        const p = document.createElementNS('http://www.w3.org/2000/svg', 'path');
        p.setAttribute('d', 'M0,0 L0,6 L8,3 z');
        p.setAttribute('fill', color);
        m.appendChild(p); defs.appendChild(m);
    });
    svg.appendChild(defs);

    // Draw arrows between levels
    for (let lv = 0; lv < levels.length - 1; lv++) {
        const nextLv = lv + 1;
        if (!nodePos[lv] || !nodePos[nextLv]) continue;

        levels[lv].forEach((node, ni) => {
            if (node.phase === 'HIDDEN' || !nodePos[lv][ni]) return;
            const { cx: px, y: py } = nodePos[lv][ni];

            const mid = Math.floor((node.l + node.r) / 2);
            const children = [
                levels[nextLv].find(n => n.l === node.l  && n.r === mid),
                levels[nextLv].find(n => n.l === mid + 1 && n.r === node.r)
            ];

            children.forEach(child => {
                if (!child || child.phase === 'HIDDEN') return;
                const cni = levels[nextLv].indexOf(child);
                if (!nodePos[nextLv][cni]) return;
                const { cx: cx2, y: cy2 } = nodePos[nextLv][cni];

                // Determine arrow direction and style
                const isMergePhase = ['MERGING','MERGED'].includes(node.phase) ||
                                     ['MERGING','MERGED'].includes(child.phase);
                const isActiveEdge  = (lv === mergingLevel && ni === mergingNi);

                let color, markerId, strokeW, dash;
                if (isActiveEdge) {
                    // Upward arrows from children to active merging parent
                    color = '#f39c12'; markerId = 'arr-active'; strokeW = '2.5'; dash = '';
                } else if (isMergePhase) {
                    color = '#00b894'; markerId = 'arr-merge'; strokeW = '2'; dash = '';
                } else {
                    color = '#e17055'; markerId = 'arr-divide'; strokeW = '2'; dash = '';
                }

                const cpY = (py + NODE_H / 2 + cy2) / 2;

                if (isActiveEdge) {
                    // Draw arrow pointing UPWARD: from child top → parent bottom
                    // Two arrows: left child → parent, right child → parent
                    const x1 = cx2, y1 = cy2 - 4;          // child top
                    const x2 = px,  y2 = py + NODE_H + 4;  // parent bottom
                    const pathEl = document.createElementNS('http://www.w3.org/2000/svg', 'path');
                    pathEl.setAttribute('d', `M${x1},${y1} C${x1},${cpY} ${x2},${cpY} ${x2},${y2}`);
                    pathEl.setAttribute('stroke', color);
                    pathEl.setAttribute('stroke-width', strokeW);
                    pathEl.setAttribute('fill', 'none');
                    pathEl.setAttribute('marker-end', `url(#${markerId})`);
                    pathEl.setAttribute('stroke-dasharray', '6,3');
                    svg.appendChild(pathEl);
                } else {
                    // Normal downward arrow: parent bottom → child top
                    const x1 = px,  y1 = py + NODE_H + 2;
                    const x2 = cx2, y2 = cy2 - 2;
                    const pathEl = document.createElementNS('http://www.w3.org/2000/svg', 'path');
                    pathEl.setAttribute('d', `M${x1},${y1} C${x1},${cpY} ${x2},${cpY} ${x2},${y2}`);
                    pathEl.setAttribute('stroke', color);
                    pathEl.setAttribute('stroke-width', strokeW);
                    pathEl.setAttribute('fill', 'none');
                    pathEl.setAttribute('marker-end', `url(#${markerId})`);
                    svg.appendChild(pathEl);
                }
            });
        });
    }

    container.appendChild(svg);

    // ── Render node boxes ────────────────────────────────────────────────────
    let mergingNodePos = null;

    for (let lv = 0; lv < levels.length; lv++) {
        levels[lv].forEach((node, ni) => {
            if (node.phase === 'HIDDEN' || !nodePos[lv] || !nodePos[lv][ni]) return;
            const { cx, y } = nodePos[lv][ni];
            const nw = nodeWidth(node.vals);
            const isActiveMerging = (lv === mergingLevel && ni === mergingNi);

            if (isActiveMerging) mergingNodePos = { cx, y };

            const box = document.createElement('div');
            box.style.cssText = `
                position: absolute;
                left: ${cx - nw / 2}px;
                top: ${y}px;
                width: ${nw}px;
                height: ${NODE_H}px;
                display: flex;
                align-items: center;
                justify-content: center;
                gap: 2px;
                border-radius: 10px;
                font-weight: 700;
                font-size: 14px;
                transition: all 0.3s ease;
                z-index: 10;
                box-sizing: border-box;
            `;

            applyNodeStyle(box, node.phase, isActiveMerging);

            node.vals.forEach(v => {
                const cell = document.createElement('span');
                cell.textContent = v;
                cell.style.cssText = 'min-width:26px;text-align:center;padding:2px 3px;border-radius:4px;font-size:14px;';
                box.appendChild(cell);
            });

            // Phase label above box
            const label = document.createElement('div');
            const labelMap = {
                UNSPLIT: { text: 'unsorted',    color: '#636e72' },
                SPLIT:   { text: '✂ splitting', color: '#e17055' },
                SINGLE:  { text: '✓ base case', color: '#0984e3' },
                MERGING: { text: '⟳ merging',   color: '#f39c12' },
                MERGED:  { text: '✓ sorted',    color: '#00b894' }
            };
            const lm = labelMap[node.phase] || { text: '', color: '#999' };
            label.textContent = lm.text;
            label.style.cssText = `
                position:absolute; top:-20px; left:50%; transform:translateX(-50%);
                font-size:10px; font-weight:700; white-space:nowrap;
                letter-spacing:0.5px; text-transform:uppercase; color:${lm.color};
            `;
            box.appendChild(label);
            container.appendChild(box);
        });
    }

    // ── Merge panel: appears BELOW the active merging parent node ────────────
    // This gives the top-down feel: parent is at top, panel shows
    // left child + right child → result flowing INTO the parent
    if (mergeDetail && mergingNodePos) {
        const panelY = mergingNodePos.y + NODE_H + 16;
        const panel = buildMergePanel(mergeDetail, mergingNodePos.cx);
        panel.style.position = 'absolute';
        panel.style.top = panelY + 'px';
        panel.style.left = mergingNodePos.cx + 'px';
        panel.style.transform = 'translateX(-50%)';
        panel.style.zIndex = '20';
        container.appendChild(panel);
    }
}

function applyNodeStyle(box, phase, isActiveMerging) {
    const styles = {
        UNSPLIT: { bg: '#f0f4f8',                                   border: '2px solid #b2bec3',  color: '#2d3436', shadow: 'none' },
        SPLIT:   { bg: '#fff8e1',                                   border: '2.5px dashed #f39c12', color: '#2d3436', shadow: '0 0 12px rgba(243,156,18,0.3)' },
        SINGLE:  { bg: 'linear-gradient(135deg,#74b9ff,#0984e3)',   border: '2px solid #0984e3',  color: 'white',   shadow: '0 4px 14px rgba(9,132,227,0.35)' },
        MERGING: { bg: 'linear-gradient(135deg,#ffeaa7,#fdcb6e)',   border: '2.5px solid #f39c12', color: '#2d3436', shadow: '0 0 18px rgba(243,156,18,0.55)' },
        MERGED:  { bg: 'linear-gradient(135deg,#55efc4,#00b894)',   border: '2px solid #00a085',  color: 'white',   shadow: '0 4px 14px rgba(0,184,148,0.4)' }
    };
    const s = styles[phase] || styles.UNSPLIT;
    box.style.background = s.bg;
    box.style.border = isActiveMerging ? '3px solid #f39c12' : s.border;
    box.style.color = s.color;
    box.style.boxShadow = isActiveMerging ? '0 0 22px rgba(243,156,18,0.7)' : s.shadow;
    if (isActiveMerging) box.style.transform = 'scale(1.08)';
}

function buildMergePanel(detail, parentCx) {
    const panel = document.createElement('div');
    panel.style.cssText = `
        display: flex;
        flex-direction: column;
        align-items: center;
        gap: 10px;
        padding: 14px 22px;
        background: rgba(255,255,255,0.98);
        border-radius: 12px;
        box-shadow: 0 6px 24px rgba(0,0,0,0.14);
        border: 2px solid #fdcb6e;
        width: fit-content;
        min-width: 160px;
    `;

    // Header: "left ← children → right"
    const header = document.createElement('div');
    header.style.cssText = 'font-size:11px;font-weight:700;color:#636e72;letter-spacing:0.5px;text-transform:uppercase;';
    header.textContent = 'merging children into parent';
    panel.appendChild(header);

    // Children row
    const arrRow = document.createElement('div');
    arrRow.style.cssText = 'display:flex;align-items:center;gap:12px;';

    arrRow.appendChild(buildCellRow(detail.lv, '#0984e3', detail.activeSide === 0));

    const plus = document.createElement('span');
    plus.textContent = '+';
    plus.style.cssText = 'font-size:20px;font-weight:bold;color:#636e72;';
    arrRow.appendChild(plus);

    arrRow.appendChild(buildCellRow(detail.rv, '#e84393', detail.activeSide === 1));
    panel.appendChild(arrRow);

    // Arrow pointing UP toward parent
    const arrow = document.createElement('div');
    arrow.textContent = '↑ result';
    arrow.style.cssText = 'font-size:12px;color:#f39c12;font-weight:700;letter-spacing:0.5px;';
    panel.appendChild(arrow);

    // Result row
    const resultRow = buildCellRow(detail.mv, '#00b894', false);
    if (detail.mv.length === 0) {
        resultRow.style.cssText += 'border:2px dashed #b2bec3;border-radius:6px;padding:4px 14px;min-width:44px;min-height:34px;';
        const ph = document.createElement('span');
        ph.textContent = 'result';
        ph.style.cssText = 'color:#b2bec3;font-size:11px;';
        resultRow.appendChild(ph);
    }
    panel.appendChild(resultRow);
    return panel;
}

function buildCellRow(values, color, isActive) {
    const row = document.createElement('div');
    row.style.cssText = 'display:flex;gap:3px;';
    values.forEach(v => {
        const cell = document.createElement('div');
        cell.textContent = v;
        cell.style.cssText = `
            width:34px;height:34px;
            display:flex;align-items:center;justify-content:center;
            border-radius:6px;font-weight:700;font-size:14px;
            background:${isActive ? '#fdcb6e' : color};
            color:${isActive ? '#2d3436' : 'white'};
            border:2px solid ${isActive ? '#e17055' : color};
            ${isActive ? 'transform:scale(1.15);box-shadow:0 0 10px rgba(253,203,110,0.8);' : ''}
            transition:all 0.2s ease;
        `;
        row.appendChild(cell);
    });
    return row;
}

// ═══════════════════════════════════════════════════════════════════
//  Other Algorithms
// ═══════════════════════════════════════════════════════════════════

function renderInsertionSortArray(array, highlightedIndices, description) {
    const container = document.getElementById('arrayContainer');
    array.forEach((value, index) => {
        const el = document.createElement('div');
        el.className = 'array-element';
        el.textContent = value;
        el.style.height = `${value * 3}px`;
        el.style.position = 'relative';
        if (description.includes('taking') && highlightedIndices.includes(index)) el.classList.add('inserting');
        else if (description.includes('shifting') && highlightedIndices.includes(index)) el.classList.add('shifting');
        else if (index <= getCurrentSortedBoundary()) el.classList.add('sorted-part');
        else el.classList.add('unsorted-part');
        container.appendChild(el);
    });
}

function renderQuickSortArray(array, highlightedIndices, description) {
    const container = document.getElementById('arrayContainer');
    array.forEach((value, index) => {
        const el = document.createElement('div');
        el.className = 'array-element';
        el.textContent = value;
        el.style.height = `${value * 3}px`;
        el.style.position = 'relative';
        if (description.includes('pivot') && description.includes('choosing') && highlightedIndices.includes(index)) el.classList.add('pivot');
        else if (description.includes('smaller') && highlightedIndices.includes(index)) el.classList.add('smaller-than-pivot');
        else if (highlightedIndices.includes(index)) el.classList.add('highlighted');
        container.appendChild(el);
    });
}

function getCurrentSortedBoundary() {
    const step = algorithmSteps[currentStep];
    if (!step || step.algorithmName !== 'Insertion Sort') return -1;
    const match = step.description.match(/position (\d+)/);
    return match ? parseInt(match[1]) - 1 : -1;
}

// ═══════════════════════════════════════════════════════════════════
//  Playback
// ═══════════════════════════════════════════════════════════════════

function nextStep() {
    if (currentStep < algorithmSteps.length - 1) { currentStep++; showCurrentStep(); }
    if (currentStep === algorithmSteps.length - 1) stopPlay();
}
function previousStep() {
    if (currentStep > 0) { currentStep--; showCurrentStep(); }
}
function togglePlay() { if (isPlaying) stopPlay(); else startPlay(); }
function startPlay() {
    isPlaying = true;
    document.getElementById('playBtn').textContent = 'Pause';
    playInterval = setInterval(() => {
        if (currentStep < algorithmSteps.length - 1) nextStep(); else stopPlay();
    }, 1000);
}
function stopPlay() {
    isPlaying = false;
    document.getElementById('playBtn').textContent = 'Play';
    clearInterval(playInterval);
}
function updateStepInfo() {
    document.getElementById('stepInfo').textContent = `Step: ${currentStep + 1} / ${algorithmSteps.length}`;
}

function createSwapArrows(leftIndex, rightIndex) {
    const container = document.getElementById('arrayContainer');
    const elements = container.children;
    if (leftIndex < elements.length && rightIndex < elements.length) {
        const swapLine = document.createElement('div');
        swapLine.className = 'swap-line';
        const lr = elements[leftIndex].getBoundingClientRect();
        const rr = elements[rightIndex].getBoundingClientRect();
        const cr = container.getBoundingClientRect();
        swapLine.style.left = (lr.left - cr.left) + 'px';
        swapLine.style.width = (rr.right - lr.left) + 'px';
        container.appendChild(swapLine);
        setTimeout(() => { if (swapLine.parentNode) swapLine.remove(); }, 1000);
    }
}

function showConceptBox(algorithm) {
    const conceptBox = document.getElementById('conceptBox');
    const conceptTitle = document.getElementById('conceptTitle');
    const conceptPoints = document.getElementById('conceptPoints');
    const concepts = {
        'bubble-sort':    { title: '🫧 Bubble Sort',    points: ['Compare adjacent elements','Swap if left > right','O(n²)'] },
        'selection-sort': { title: '🎯 Selection Sort', points: ['Find minimum each pass','Swap to front','O(n²)'] },
        'insertion-sort': { title: '🃏 Insertion Sort', points: ['🟢 Green: sorted','🔴 Red: inserting','🟡 Yellow: shifting','O(n²)'] },
        'quick-sort':     { title: '⚡ Quick Sort',     points: ['🟠 Orange: pivot','🔵 Blue: smaller','O(n log n)'] },
        'merge-sort': {
            title: '✂️ Merge Sort — Divide & Conquer',
            points: [
                '⬜ Gray: unsorted subarray',
                '✂️ Yellow dashed: splitting',
                '🔵 Blue: single element (base case)',
                '🟡 Yellow glow: parent being merged into',
                '↘ Red arrows: divide (top → down)',
                '↑ Orange dashed: merge (children → parent)',
                '🟢 Green: merged & sorted',
                'O(n log n)'
            ]
        }
    };
    const concept = concepts[algorithm];
    if (concept) {
        conceptTitle.textContent = concept.title;
        conceptPoints.innerHTML = concept.points.map(p => `<li>${p}</li>`).join('');
        conceptBox.style.display = 'block';
    }
}

document.addEventListener('DOMContentLoaded', () => renderArray(currentArray));
