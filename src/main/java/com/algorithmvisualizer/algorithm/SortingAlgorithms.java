package com.algorithmvisualizer.algorithm;

import com.algorithmvisualizer.model.AlgorithmStep;
import java.util.*;

public class SortingAlgorithms {

    public static List<AlgorithmStep> bubbleSort(int[] arr) {
        List<AlgorithmStep> steps = new ArrayList<>();
        int[] workingArray = arr.clone();
        int n = workingArray.length;

        steps.add(new AlgorithmStep("Bubble Sort", 0,
                "🫧 BUBBLE SORT: Like bubbles rising to surface! We compare adjacent elements and swap if left > right. Largest elements 'bubble up' to the end.",
                Arrays.toString(workingArray), ""));

        for (int i = 0; i < n - 1; i++) {
            steps.add(new AlgorithmStep("Bubble Sort", steps.size(),
                    "🔄 PASS " + (i + 1)
                            + ": Starting new pass through unsorted portion. We'll find the largest element and bubble it to the end.",
                    Arrays.toString(workingArray), ""));

            for (int j = 0; j < n - i - 1; j++) {
                steps.add(new AlgorithmStep("Bubble Sort", steps.size(),
                        "👀 COMPARE: Looking at positions " + j + " and " + (j + 1) + ". Is " + workingArray[j] + " > "
                                + workingArray[j + 1] + "? We need smaller elements on the left!",
                        Arrays.toString(workingArray), j + "," + (j + 1)));

                if (workingArray[j] > workingArray[j + 1]) {
                    int temp = workingArray[j];
                    workingArray[j] = workingArray[j + 1];
                    workingArray[j + 1] = temp;

                    steps.add(new AlgorithmStep("Bubble Sort", steps.size(),
                            "🔄 SWAP! Yes, " + temp + " > " + workingArray[j] + ", so we swap them. The larger number "
                                    + temp + " moves right (bubbles up)!",
                            Arrays.toString(workingArray), j + "," + (j + 1)));
                } else {
                    steps.add(new AlgorithmStep("Bubble Sort", steps.size(),
                            "✅ NO SWAP: " + workingArray[j] + " ≤ " + workingArray[j + 1]
                                    + ", they're already in correct order. Continue checking...",
                            Arrays.toString(workingArray), j + "," + (j + 1)));
                }
            }

            steps.add(new AlgorithmStep("Bubble Sort", steps.size(),
                    "🎯 PASS COMPLETE: The largest element " + workingArray[n - i - 1] + " has bubbled to position "
                            + (n - i - 1) + ". It's now in its final sorted position!",
                    Arrays.toString(workingArray), String.valueOf(n - i - 1)));
        }

        steps.add(new AlgorithmStep("Bubble Sort", steps.size(),
                "🎉 SORTING COMPLETE! All elements have bubbled to their correct positions. Array is now fully sorted!",
                Arrays.toString(workingArray), ""));

        return steps;
    }

    public static List<AlgorithmStep> selectionSort(int[] arr) {
        List<AlgorithmStep> steps = new ArrayList<>();
        int[] workingArray = arr.clone();
        int n = workingArray.length;

        steps.add(new AlgorithmStep("Selection Sort", 0, "Starting Selection Sort",
                Arrays.toString(workingArray), ""));

        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            steps.add(new AlgorithmStep("Selection Sort", steps.size(),
                    "Finding minimum from position " + i
                            + " onwards. Starting search in unsorted portion. Current value is " + workingArray[i]
                            + ".",
                    Arrays.toString(workingArray), String.valueOf(i)));

            for (int j = i + 1; j < n; j++) {
                if (workingArray[j] < workingArray[minIdx]) {
                    minIdx = j;
                    steps.add(new AlgorithmStep("Selection Sort", steps.size(),
                            "Found smaller value " + workingArray[j] + " at position " + j + ". New minimum is "
                                    + workingArray[minIdx] + " at position " + minIdx + ".",
                            Arrays.toString(workingArray), j + "," + minIdx));
                } else {
                    steps.add(new AlgorithmStep("Selection Sort", steps.size(),
                            "" + workingArray[j] + " at position " + j + " is not smaller than current min "
                                    + workingArray[minIdx] + " at position " + minIdx + ".",
                            Arrays.toString(workingArray), j + "," + minIdx));
                }
            }

            if (minIdx != i) {
                int temp = workingArray[minIdx];
                workingArray[minIdx] = workingArray[i];
                workingArray[i] = temp;

                steps.add(new AlgorithmStep("Selection Sort", steps.size(),
                        "Swapped " + workingArray[i] + " to position " + i + " with " + workingArray[minIdx]
                                + " from position " + minIdx + ". " + workingArray[i] + " is now in sorted position "
                                + i + ".",
                        Arrays.toString(workingArray), i + "," + minIdx));
            } else {
                steps.add(new AlgorithmStep("Selection Sort", steps.size(),
                        "" + workingArray[i] + " at position " + i + " is already the minimum. No swap needed.",
                        Arrays.toString(workingArray), String.valueOf(i)));
            }
        }

        steps.add(new AlgorithmStep("Selection Sort", steps.size(), "Array is now sorted!",
                Arrays.toString(workingArray), ""));

        return steps;
    }

    public static List<AlgorithmStep> insertionSort(int[] arr) {
        List<AlgorithmStep> steps = new ArrayList<>();
        int[] workingArray = arr.clone();
        int n = workingArray.length;

        steps.add(new AlgorithmStep("Insertion Sort", 0, "Starting Insertion Sort",
                Arrays.toString(workingArray), ""));

        for (int i = 1; i < n; i++) {
            int key = workingArray[i];
            int j = i - 1;

            steps.add(new AlgorithmStep("Insertion Sort", steps.size(),
                    "Taking " + key + " from position " + i + " to insert into sorted portion.",
                    Arrays.toString(workingArray), String.valueOf(i)));

            while (j >= 0 && workingArray[j] > key) {
                steps.add(new AlgorithmStep("Insertion Sort", steps.size(),
                        "" + workingArray[j] + " at position " + j + " is greater than " + key + ", so shifting "
                                + workingArray[j] + " right.",
                        Arrays.toString(workingArray), j + "," + (j + 1)));

                workingArray[j + 1] = workingArray[j];

                steps.add(new AlgorithmStep("Insertion Sort", steps.size(),
                        "Shifted " + workingArray[j + 1] + " from position " + j + " to position " + (j + 1) + ".",
                        Arrays.toString(workingArray), String.valueOf(j + 1)));

                j--;
            }

            workingArray[j + 1] = key;
            steps.add(new AlgorithmStep("Insertion Sort", steps.size(),
                    "Inserted " + key + " at position " + (j + 1) + ". Sorted portion extended.",
                    Arrays.toString(workingArray), String.valueOf(j + 1)));
        }

        steps.add(new AlgorithmStep("Insertion Sort", steps.size(), "Array is now sorted!",
                Arrays.toString(workingArray), ""));

        return steps;
    }

    public static List<AlgorithmStep> quickSort(int[] arr) {
        List<AlgorithmStep> steps = new ArrayList<>();
        int[] workingArray = arr.clone();

        steps.add(new AlgorithmStep("Quick Sort", 0,
                "⚡ QUICK SORT: Pick a pivot, partition around it! Elements smaller than pivot go left, larger go right. Then recursively sort both sides.",
                Arrays.toString(workingArray), ""));

        quickSortHelper(workingArray, 0, workingArray.length - 1, steps);

        steps.add(new AlgorithmStep("Quick Sort", steps.size(), "Array is now sorted!",
                Arrays.toString(workingArray), ""));

        return steps;
    }

    private static void quickSortHelper(int[] arr, int low, int high, List<AlgorithmStep> steps) {
        if (low < high) {
            steps.add(new AlgorithmStep("Quick Sort", steps.size(),
                    "🎯 PARTITION PHASE: Working on subarray [" + low + " to " + high
                            + "]. We'll choose a pivot and rearrange elements around it.",
                    Arrays.toString(arr), low + "," + high));

            int pi = partition(arr, low, high, steps);

            steps.add(new AlgorithmStep("Quick Sort", steps.size(),
                    "Pivot " + arr[pi] + " is now at correct position " + pi + ". Dividing array.",
                    Arrays.toString(arr), String.valueOf(pi)));

            quickSortHelper(arr, low, pi - 1, steps);
            quickSortHelper(arr, pi + 1, high, steps);
        }
    }

    private static int partition(int[] arr, int low, int high, List<AlgorithmStep> steps) {
        int pivot = arr[high];
        steps.add(new AlgorithmStep("Quick Sort", steps.size(),
                "🎯 PIVOT SELECTED: Choosing " + pivot + " at position " + high
                        + " as our pivot. All elements will be compared against this value.",
                Arrays.toString(arr), String.valueOf(high)));

        int i = low - 1;

        for (int j = low; j < high; j++) {
            steps.add(new AlgorithmStep("Quick Sort", steps.size(),
                    "Comparing " + arr[j] + " at position " + j + " with pivot " + pivot + ".",
                    Arrays.toString(arr), j + "," + high));

            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;

                steps.add(new AlgorithmStep("Quick Sort", steps.size(),
                        "" + arr[i] + " is smaller than pivot, swapped to position " + i + ".",
                        Arrays.toString(arr), i + "," + j));
            }
        }

        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        steps.add(new AlgorithmStep("Quick Sort", steps.size(),
                "Placed pivot " + arr[i + 1] + " at final position " + (i + 1) + ".",
                Arrays.toString(arr), (i + 1) + "," + high));

        return i + 1;
    }

    // ── Merge Sort ──────────────────────────────────────────────────────────
    // Each step encodes the full tree snapshot so the frontend can render
    // every level as a row of boxes — divide phase splits down, merge phase
    // builds back up with comparison highlighting.
    //
    // Node format (semicolon-separated fields):
    //   L,R,phase,vals[,li,ri,mergedVals]
    //   phase: UNSPLIT | SPLIT | SINGLE | MERGING | MERGED
    // Nodes are comma-joined; rows separated by ";"
    // Full encoded string: TREE;<row0>;<row1>;...<rowN>
    // where each row = nodes pipe-separated: node1|node2|...

    public static List<AlgorithmStep> mergeSort(int[] arr) {
        List<AlgorithmStep> steps = new ArrayList<>();
        int n = arr.length;

        // Build the static tree structure (left, right bounds per node per level)
        // We represent the tree as a list of levels; each level is a list of [left,right] pairs
        List<List<int[]>> levels = buildLevels(n);
        int maxLevel = levels.size();

        // nodeValues[level][nodeIdx] = current int[] values shown in that box
        // nodePhase[level][nodeIdx] = phase string
        List<List<int[]>> nodeValues = new ArrayList<>();
        List<List<String>> nodePhase = new ArrayList<>();
        for (int lv = 0; lv < maxLevel; lv++) {
            List<int[]> vals = new ArrayList<>();
            List<String> phases = new ArrayList<>();
            for (int[] bounds : levels.get(lv)) {
                vals.add(Arrays.copyOfRange(arr, bounds[0], bounds[1] + 1));
                phases.add(lv == 0 ? "UNSPLIT" : "HIDDEN");
            }
            nodeValues.add(vals);
            nodePhase.add(phases);
        }

        // ── DIVIDE PHASE ──────────────────────────────────────────────────
        steps.add(new AlgorithmStep("Merge Sort", steps.size(),
                "✂️ DIVIDE: Start splitting the array in half recursively until single elements.",
                Arrays.toString(arr), encodeTree(levels, nodeValues, nodePhase, -1, -1, -1, null, null, null)));

        dividePhaseDFS(arr, levels, nodeValues, nodePhase, 0, 0, steps);

        steps.add(new AlgorithmStep("Merge Sort", steps.size(),
                "✅ All elements split to size 1! Single elements are already sorted. Now merging back up...",
                Arrays.toString(arr), encodeTree(levels, nodeValues, nodePhase, -1, -1, -1, null, null, null)));

        // ── MERGE PHASE ───────────────────────────────────────────────────
        int[] workingArr = arr.clone();
        mergePhaseDFS(workingArr, levels, nodeValues, nodePhase, 0, 0, steps);

        steps.add(new AlgorithmStep("Merge Sort", steps.size(),
                "🎉 SORTED: " + Arrays.toString(workingArr),
                Arrays.toString(workingArr), encodeTree(levels, nodeValues, nodePhase, -1, -1, -1, null, null, null)));

        return steps;
    }

    /** Build tree levels: level 0 = root [0,n-1], each level splits each node's range */
    private static List<List<int[]>> buildLevels(int n) {
        List<List<int[]>> levels = new ArrayList<>();
        List<int[]> current = new ArrayList<>();
        current.add(new int[]{0, n - 1});
        levels.add(current);
        while (true) {
            List<int[]> next = new ArrayList<>();
            boolean anyMulti = false;
            for (int[] bounds : levels.get(levels.size() - 1)) {
                int l = bounds[0], r = bounds[1];
                if (l < r) {
                    int m = (l + r) / 2;
                    next.add(new int[]{l, m});
                    next.add(new int[]{m + 1, r});
                    anyMulti = true;
                } else {
                    next.add(new int[]{l, r});
                }
            }
            if (!anyMulti) break;
            levels.add(next);
        }
        return levels;
    }

    /** DFS divide: reveal children level by level */
    private static void dividePhaseDFS(int[] arr, List<List<int[]>> levels,
            List<List<int[]>> nodeValues, List<List<String>> nodePhase,
            int level, int nodeIdx, List<AlgorithmStep> steps) {
        int[] bounds = levels.get(level).get(nodeIdx);
        int l = bounds[0], r = bounds[1];
        if (l == r) {
            nodePhase.get(level).set(nodeIdx, "SINGLE");
            steps.add(new AlgorithmStep("Merge Sort", steps.size(),
                    "📌 Single element [" + arr[l] + "] — already sorted!",
                    Arrays.toString(arr), encodeTree(levels, nodeValues, nodePhase, -1, -1, -1, null, null, null)));
            return;
        }
        int mid = (l + r) / 2;
        nodePhase.get(level).set(nodeIdx, "SPLIT");

        // Find child indices in next level
        int leftChildIdx = findChildIdx(levels, level + 1, l, mid);
        int rightChildIdx = findChildIdx(levels, level + 1, mid + 1, r);

        // Reveal left child
        nodePhase.get(level + 1).set(leftChildIdx, "UNSPLIT");
        nodePhase.get(level + 1).set(rightChildIdx, "UNSPLIT");

        steps.add(new AlgorithmStep("Merge Sort", steps.size(),
                "✂️ Split " + Arrays.toString(Arrays.copyOfRange(arr, l, r + 1))
                        + " → " + Arrays.toString(Arrays.copyOfRange(arr, l, mid + 1))
                        + " | " + Arrays.toString(Arrays.copyOfRange(arr, mid + 1, r + 1)),
                Arrays.toString(arr), encodeTree(levels, nodeValues, nodePhase, -1, -1, -1, null, null, null)));

        dividePhaseDFS(arr, levels, nodeValues, nodePhase, level + 1, leftChildIdx, steps);
        dividePhaseDFS(arr, levels, nodeValues, nodePhase, level + 1, rightChildIdx, steps);
    }

    /** DFS merge: merge children into parent, emitting compare steps */
    private static int[] mergePhaseDFS(int[] arr, List<List<int[]>> levels,
            List<List<int[]>> nodeValues, List<List<String>> nodePhase,
            int level, int nodeIdx, List<AlgorithmStep> steps) {
        int[] bounds = levels.get(level).get(nodeIdx);
        int l = bounds[0], r = bounds[1];
        if (l == r) return new int[]{arr[l]};

        int mid = (l + r) / 2;
        int leftChildIdx = findChildIdx(levels, level + 1, l, mid);
        int rightChildIdx = findChildIdx(levels, level + 1, mid + 1, r);

        int[] leftSorted = mergePhaseDFS(arr, levels, nodeValues, nodePhase, level + 1, leftChildIdx, steps);
        int[] rightSorted = mergePhaseDFS(arr, levels, nodeValues, nodePhase, level + 1, rightChildIdx, steps);

        // Mark this node as MERGING
        nodePhase.get(level).set(nodeIdx, "MERGING");

        int[] result = new int[leftSorted.length + rightSorted.length];
        int i = 0, j = 0, k = 0;

        steps.add(new AlgorithmStep("Merge Sort", steps.size(),
                "🤝 Merging " + Arrays.toString(leftSorted) + " + " + Arrays.toString(rightSorted),
                Arrays.toString(arr),
                encodeTree(levels, nodeValues, nodePhase, level, nodeIdx, -1, leftSorted, rightSorted, Arrays.copyOf(result, 0))));

        while (i < leftSorted.length && j < rightSorted.length) {
            if (leftSorted[i] <= rightSorted[j]) {
                result[k++] = leftSorted[i];
                steps.add(new AlgorithmStep("Merge Sort", steps.size(),
                        "Compare: " + leftSorted[i] + " ≤ " + rightSorted[j] + " → take " + leftSorted[i] + " from left",
                        Arrays.toString(arr),
                        encodeTree(levels, nodeValues, nodePhase, level, nodeIdx, 0,
                                leftSorted, rightSorted, Arrays.copyOf(result, k))));
                i++;
            } else {
                result[k++] = rightSorted[j];
                steps.add(new AlgorithmStep("Merge Sort", steps.size(),
                        "Compare: " + rightSorted[j] + " < " + leftSorted[i] + " → take " + rightSorted[j] + " from right",
                        Arrays.toString(arr),
                        encodeTree(levels, nodeValues, nodePhase, level, nodeIdx, 1,
                                leftSorted, rightSorted, Arrays.copyOf(result, k))));
                j++;
            }
        }
        while (i < leftSorted.length) {
            result[k++] = leftSorted[i++];
            steps.add(new AlgorithmStep("Merge Sort", steps.size(),
                    "Append remaining left: " + result[k - 1],
                    Arrays.toString(arr),
                    encodeTree(levels, nodeValues, nodePhase, level, nodeIdx, 0,
                            leftSorted, rightSorted, Arrays.copyOf(result, k))));
        }
        while (j < rightSorted.length) {
            result[k++] = rightSorted[j++];
            steps.add(new AlgorithmStep("Merge Sort", steps.size(),
                    "Append remaining right: " + result[k - 1],
                    Arrays.toString(arr),
                    encodeTree(levels, nodeValues, nodePhase, level, nodeIdx, 1,
                            leftSorted, rightSorted, Arrays.copyOf(result, k))));
        }

        // Update node to show merged result
        nodeValues.get(level).set(nodeIdx, result);
        nodePhase.get(level).set(nodeIdx, "MERGED");
        // Write back into arr
        for (int x = 0; x < result.length; x++) arr[l + x] = result[x];

        steps.add(new AlgorithmStep("Merge Sort", steps.size(),
                "✅ Merged → " + Arrays.toString(result),
                Arrays.toString(arr),
                encodeTree(levels, nodeValues, nodePhase, -1, -1, -1, null, null, null)));

        return result;
    }

    private static int findChildIdx(List<List<int[]>> levels, int level, int l, int r) {
        List<int[]> row = levels.get(level);
        for (int i = 0; i < row.size(); i++) {
            if (row.get(i)[0] == l && row.get(i)[1] == r) return i;
        }
        return 0;
    }

    /**
     * Encode full tree snapshot.
     * Format: TREE|<level0nodes>;<level1nodes>;...
     * Each level: node1~node2~...
     * Each node: L,R,phase,v1 v2 v3[,li,ri,m1 m2 m3]
     *   li/ri = which side is active (0=left,1=right,-1=none) during merge
     */
    private static String encodeTree(List<List<int[]>> levels,
            List<List<int[]>> nodeValues, List<List<String>> nodePhase,
            int mergeLevel, int mergeNodeIdx, int activeSide,
            int[] leftSorted, int[] rightSorted, int[] mergedSoFar) {
        StringBuilder sb = new StringBuilder("TREE|");
        for (int lv = 0; lv < levels.size(); lv++) {
            if (lv > 0) sb.append(";");
            List<int[]> row = levels.get(lv);
            for (int ni = 0; ni < row.size(); ni++) {
                if (ni > 0) sb.append("~");
                int[] bounds = row.get(ni);
                String phase = nodePhase.get(lv).get(ni);
                int[] vals = nodeValues.get(lv).get(ni);
                sb.append(bounds[0]).append(",").append(bounds[1]).append(",").append(phase).append(",");
                sb.append(joinInts(vals));
                // Attach merge detail to the active merging node
                if (lv == mergeLevel && ni == mergeNodeIdx && leftSorted != null) {
                    sb.append(",").append(activeSide)
                      .append(",").append(joinInts(leftSorted))
                      .append(",").append(joinInts(rightSorted))
                      .append(",").append(joinInts(mergedSoFar));
                }
            }
        }
        return sb.toString();
    }

    private static String joinInts(int[] arr) {
        if (arr == null || arr.length == 0) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) sb.append(" ");
            sb.append(arr[i]);
        }
        return sb.toString();
    }


    public static List<AlgorithmStep> heapSort(int[] arr) {
        List<AlgorithmStep> steps = new ArrayList<>();
        int[] workingArray = arr.clone();
        int n = workingArray.length;

        steps.add(new AlgorithmStep("Heap Sort", 0, "Starting Heap Sort - Building max heap",
                Arrays.toString(workingArray), ""));

        // Build max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(workingArray, n, i, steps);
        }

        steps.add(new AlgorithmStep("Heap Sort", steps.size(), "Max heap built successfully",
                Arrays.toString(workingArray), ""));

        // Extract elements from heap one by one
        for (int i = n - 1; i > 0; i--) {
            // Move current root to end
            int temp = workingArray[0];
            workingArray[0] = workingArray[i];
            workingArray[i] = temp;

            steps.add(new AlgorithmStep("Heap Sort", steps.size(),
                    "Moved largest element " + workingArray[i] + " to position " + i,
                    Arrays.toString(workingArray), "0," + i));

            // Call heapify on the reduced heap
            heapify(workingArray, i, 0, steps);
        }

        steps.add(new AlgorithmStep("Heap Sort", steps.size(), "Array is now sorted!",
                Arrays.toString(workingArray), ""));

        return steps;
    }

    private static void heapify(int[] arr, int n, int i, List<AlgorithmStep> steps) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        if (largest != i) {
            steps.add(new AlgorithmStep("Heap Sort", steps.size(),
                    "Swapping " + arr[i] + " with " + arr[largest] + " to maintain heap property",
                    Arrays.toString(arr), i + "," + largest));

            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            heapify(arr, n, largest, steps);
        }
    }

    private static String getOrdinal(int number) {
        if (number >= 11 && number <= 13) {
            return "th";
        }
        switch (number % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }
}