package com.accolite.apps.AutoComplete;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class TrieNode {
    char data;     
    LinkedList<TrieNode> children; 
    TrieNode parent;
    boolean isEnd;
    int freq;
 
    public TrieNode(char c) {
    	data = c;
        children = new LinkedList();
        isEnd = false;        
    }  
    
    public TrieNode getChild(char c) {
        if (children != null)
            for (TrieNode eachChild : children)
                if (eachChild.data == c)
                    return eachChild;
        return null;
    }
    //helper of DFS, find all strings with same prefix (starting from the same node)
    protected HashMap<String,Integer> getWords() {
        HashMap<String,Integer> map = new HashMap();      
        if (isEnd) {
     	   map.put(toString(),freq);
        }
        
        if (children != null) {
 	       for (int i=0; i< children.size(); i++) {
 	          if (children.get(i) != null) {
 	             map.putAll(children.get(i).getWords());
 	          }
 	       }
        }       
        return map; 
     }
    
	public String toString() {
		if (parent == null) {
		     return "";
		} else {
		     return parent.toString() + new String(new char[] {data});
		}
	}
}

class Trie {
    private TrieNode root;
 
    public Trie() {
        root = new TrieNode(' '); 
    }
 
    public void insert(String word) {   
        
        TrieNode current = root; 
        TrieNode pre ;
        for (char ch : word.toCharArray()) {
        	pre = current;
            TrieNode child = current.getChild(ch);
            if (child != null) {
                current = child;
                child.parent = pre;
            } else {
                 current.children.add(new TrieNode(ch));
                 current = current.getChild(ch);
                 current.parent = pre;
            }
        }
        current.isEnd = true;
        current.freq++;
    }
    
    public boolean search(String word) {
        TrieNode current = root;      
        for (char ch : word.toCharArray()) {
            if (current.getChild(ch) == null)
                return false;
            else {
                current = current.getChild(ch);    
            }
        }      
        if (current.isEnd == true) {       
            return true;
        }
        return false;
    }

    //autocomplete
    public HashMap autocomplete(String prefix) {     
       TrieNode lastNode = root;
       //find last node of prefix
       for (int i = 0; i< prefix.length(); i++) {
	       lastNode = lastNode.getChild(prefix.charAt(i));	     
	       if (lastNode == null) 
	    	   return new HashMap();      
       }
       //call helper
       return lastNode.getWords();
    }
}   

public class AutoCompleteWithTrie {
	public static void main(String[] args) {            
        Trie t = new Trie();            
        t.insert("amazon"); 
        t.insert("amazon"); 
        t.insert("amazon prime"); 
        t.insert("amazon prime"); 
        t.insert("amazon prime"); 
        t.insert("amazon prime"); 
		t.insert("amazing"); 			 
        t.insert("amazing spider man"); 
        t.insert("amazed");
        t.insert("alibaba");
        t.insert("ali express");
        t.insert("ali express services");
        t.insert("alibaba is an ecommerce giant based out of China");
        t.insert("ebay");
        t.insert("walmart");          
        HashMap<String,Integer> map= t.autocomplete("ali");
        map.entrySet().stream()
                .sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue())).forEach(k -> System.out.println(k.getKey() + ": " + k.getValue()));
            
		
  }
}
