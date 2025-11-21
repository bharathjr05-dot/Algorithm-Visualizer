package com.algorithmvisualizer.controller;

import com.algorithmvisualizer.datastructures.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/datastructures")
@CrossOrigin(origins = "*")
public class DataStructureController {
    
    // Array Operations
    @PostMapping("/array/insert")
    public List<DataStructureStep> arrayInsert(@RequestBody Map<String, Object> request) {
        int[] array = ((List<Integer>) request.get("array")).stream().mapToInt(i -> i).toArray();
        int element = (Integer) request.get("element");
        int position = (Integer) request.get("position");
        return ArrayOperations.insertElement(array, element, position);
    }
    
    @PostMapping("/array/delete")
    public List<DataStructureStep> arrayDelete(@RequestBody Map<String, Object> request) {
        int[] array = ((List<Integer>) request.get("array")).stream().mapToInt(i -> i).toArray();
        int position = (Integer) request.get("position");
        return ArrayOperations.deleteElement(array, position);
    }
    
    @PostMapping("/array/search")
    public List<DataStructureStep> arraySearch(@RequestBody Map<String, Object> request) {
        int[] array = ((List<Integer>) request.get("array")).stream().mapToInt(i -> i).toArray();
        int element = (Integer) request.get("element");
        return ArrayOperations.searchElement(array, element);
    }
    
    // Linked List Operations
    @PostMapping("/linkedlist/insert-head")
    public List<DataStructureStep> linkedListInsertHead(@RequestBody Map<String, Object> request) {
        List<Integer> list = (List<Integer>) request.get("list");
        int element = (Integer) request.get("element");
        return LinkedListOperations.insertAtBeginning(list, element);
    }
    
    @PostMapping("/linkedlist/insert-tail")
    public List<DataStructureStep> linkedListInsertTail(@RequestBody Map<String, Object> request) {
        List<Integer> list = (List<Integer>) request.get("list");
        int element = (Integer) request.get("element");
        return LinkedListOperations.insertAtEnd(list, element);
    }
    
    @PostMapping("/linkedlist/delete-head")
    public List<DataStructureStep> linkedListDeleteHead(@RequestBody Map<String, Object> request) {
        List<Integer> list = (List<Integer>) request.get("list");
        return LinkedListOperations.deleteFromBeginning(list);
    }
    
    @PostMapping("/linkedlist/search")
    public List<DataStructureStep> linkedListSearch(@RequestBody Map<String, Object> request) {
        List<Integer> list = (List<Integer>) request.get("list");
        int element = (Integer) request.get("element");
        return LinkedListOperations.searchElement(list, element);
    }
    
    // Stack Operations
    @PostMapping("/stack/push")
    public List<DataStructureStep> stackPush(@RequestBody Map<String, Object> request) {
        List<Integer> stack = (List<Integer>) request.get("stack");
        int element = (Integer) request.get("element");
        return StackOperations.pushElement(stack, element);
    }
    
    @PostMapping("/stack/pop")
    public List<DataStructureStep> stackPop(@RequestBody Map<String, Object> request) {
        List<Integer> stack = (List<Integer>) request.get("stack");
        return StackOperations.popElement(stack);
    }
    
    @PostMapping("/stack/peek")
    public List<DataStructureStep> stackPeek(@RequestBody Map<String, Object> request) {
        List<Integer> stack = (List<Integer>) request.get("stack");
        return StackOperations.peekElement(stack);
    }
    
    // Queue Operations
    @PostMapping("/queue/enqueue")
    public List<DataStructureStep> queueEnqueue(@RequestBody Map<String, Object> request) {
        List<Integer> queue = (List<Integer>) request.get("queue");
        int element = (Integer) request.get("element");
        return QueueOperations.enqueueElement(queue, element);
    }
    
    @PostMapping("/queue/dequeue")
    public List<DataStructureStep> queueDequeue(@RequestBody Map<String, Object> request) {
        List<Integer> queue = (List<Integer>) request.get("queue");
        return QueueOperations.dequeueElement(queue);
    }
    
    @PostMapping("/queue/front")
    public List<DataStructureStep> queueFront(@RequestBody Map<String, Object> request) {
        List<Integer> queue = (List<Integer>) request.get("queue");
        return QueueOperations.frontElement(queue);
    }
}