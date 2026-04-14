// Add this function to sorting.js before highlightMergingNode

function calculateNodeDepth(leftArr, rightArr) {
    const totalLength = leftArr.length + rightArr.length;
    return Math.floor(Math.log2(totalLength));
}
