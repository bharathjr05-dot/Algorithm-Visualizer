package com.algorithmvisualizer.datastructures;

import java.util.*;

public class LinkedListOperations {
    
    static class Node {
        int data;
        Node next;
        
        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }
    
    public static List<DataStructureStep> insertAtBeginning(List<Integer> list, int element) {
        List<DataStructureStep> steps = new ArrayList<>();
        List<Integer> resultList = new ArrayList<>(list);
        
        steps.add(new DataStructureStep("INSERT_HEAD", 
            "Inserting " + element + " at the beginning of linked list",
            getLinkedListString(resultList), "", 0));
        
        steps.add(new DataStructureStep("INSERT_HEAD", 
            "Creating new node with data " + element,
            getLinkedListString(resultList), "new", 1));
        
        steps.add(new DataStructureStep("INSERT_HEAD", 
            "Setting new node's next pointer to current head",
            getLinkedListString(resultList), "0", 2));
        
        resultList.add(0, element);
        
        steps.add(new DataStructureStep("INSERT_HEAD", 
            "Updating head to point to new node. Insertion complete!",
            getLinkedListString(resultList), "0", 3));
        
        return steps;
    }
    
    public static List<DataStructureStep> insertAtEnd(List<Integer> list, int element) {
        List<DataStructureStep> steps = new ArrayList<>();
        List<Integer> resultList = new ArrayList<>(list);
        
        steps.add(new DataStructureStep("INSERT_TAIL", 
            "Inserting " + element + " at the end of linked list",
            getLinkedListString(resultList), "", 0));
        
        if (resultList.isEmpty()) {
            steps.add(new DataStructureStep("INSERT_TAIL", 
                "List is empty. New node becomes head",
                getLinkedListString(resultList), "new", 1));
        } else {
            steps.add(new DataStructureStep("INSERT_TAIL", 
                "Traversing to the last node",
                getLinkedListString(resultList), String.valueOf(resultList.size() - 1), 1));
            
            steps.add(new DataStructureStep("INSERT_TAIL", 
                "Setting last node's next pointer to new node",
                getLinkedListString(resultList), String.valueOf(resultList.size() - 1), 2));
        }
        
        resultList.add(element);
        
        steps.add(new DataStructureStep("INSERT_TAIL", 
            "Successfully inserted " + element + " at the end",
            getLinkedListString(resultList), String.valueOf(resultList.size() - 1), 3));
        
        return steps;
    }
    
    public static List<DataStructureStep> deleteFromBeginning(List<Integer> list) {
        List<DataStructureStep> steps = new ArrayList<>();
        List<Integer> resultList = new ArrayList<>(list);
        
        steps.add(new DataStructureStep("DELETE_HEAD", 
            "Deleting first node from linked list",
            getLinkedListString(resultList), "0", 0));
        
        if (resultList.isEmpty()) {
            steps.add(new DataStructureStep("DELETE_HEAD", 
                "List is empty! Cannot delete from empty list",
                getLinkedListString(resultList), "", 1));
            return steps;
        }
        
        int deletedElement = resultList.get(0);
        
        steps.add(new DataStructureStep("DELETE_HEAD", 
            "Storing reference to node " + deletedElement + " to be deleted",
            getLinkedListString(resultList), "0", 1));
        
        steps.add(new DataStructureStep("DELETE_HEAD", 
            "Updating head to point to second node",
            getLinkedListString(resultList), "1", 2));
        
        resultList.remove(0);
        
        steps.add(new DataStructureStep("DELETE_HEAD", 
            "Deleted node " + deletedElement + ". Head updated successfully",
            getLinkedListString(resultList), "", 3));
        
        return steps;
    }
    
    public static List<DataStructureStep> searchElement(List<Integer> list, int element) {
        List<DataStructureStep> steps = new ArrayList<>();
        
        steps.add(new DataStructureStep("SEARCH", 
            "Searching for " + element + " in linked list",
            getLinkedListString(list), "", 0));
        
        for (int i = 0; i < list.size(); i++) {
            steps.add(new DataStructureStep("SEARCH", 
                "Checking node " + i + ": " + list.get(i) + " == " + element + "?",
                getLinkedListString(list), String.valueOf(i), i + 1));
            
            if (list.get(i) == element) {
                steps.add(new DataStructureStep("SEARCH", 
                    "Found " + element + " at position " + i + "!",
                    getLinkedListString(list), String.valueOf(i), i + 2));
                return steps;
            }
            
            if (i < list.size() - 1) {
                steps.add(new DataStructureStep("SEARCH", 
                    "Moving to next node via next pointer",
                    getLinkedListString(list), String.valueOf(i + 1), i + 2));
            }
        }
        
        steps.add(new DataStructureStep("SEARCH", 
            "Reached end of list. " + element + " not found",
            getLinkedListString(list), "", list.size() + 1));
        
        return steps;
    }
    
    private static String getLinkedListString(List<Integer> list) {
        if (list.isEmpty()) return "NULL";
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append("[").append(list.get(i)).append("]");
            if (i < list.size() - 1) {
                sb.append(" -> ");
            }
        }
        sb.append(" -> NULL");
        return sb.toString();
    }
}