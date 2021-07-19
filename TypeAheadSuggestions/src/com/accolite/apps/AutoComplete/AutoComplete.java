package com.accolite.apps.AutoComplete;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

class WordPair {
	String word;
	int freq;

	public WordPair(String word, int freq) {
		this.word = word;
		this.freq = freq;
	}

	public String getWord() {
		return word;
	}

	public int getFreq() {
		return freq;
	}
}

class Node {
	Node left, mid, right = null;
	char val;
	boolean isEnd;
	int freq;

	public Node() {
	}

	public Node(char val) {
		this.val = val;
	}
}

public class AutoComplete {
	
	private Node root;

	public AutoComplete() {
		root = null;
	}

	public void insert(String word) {
		if (word != null && !word.isEmpty())
			root = insert(word.toLowerCase(), 0, root);
	}

	private Node insert(String word, int index, Node node) {
		char ch = word.charAt(index);
		if (node == null) {
			node = new Node(ch);
			node.freq = 0;
		}

		if (ch < node.val) {
			node.left = insert(word, index, node.left);
		} else if (ch > node.val) {
			node.right = insert(word, index, node.right);
		} else {
			if (index != word.length() - 1) {
				node.mid = insert(word, index + 1, node.mid);
			} else {
				node.freq++;
			}
		}
		return node;
	}

//search based on prefix of word it will give list of sentences or word if present otherwise it will give not found result  
	private Queue<WordPair> search(String word) {
		Queue<WordPair> words = new PriorityQueue<>(Comparator.comparing(WordPair::getFreq).reversed());
		word = word.toLowerCase();
		Node node = find(word, 0, root);

		if (node != null) {
			compute(words, "", word.substring(0, word.length() - 1), node);
		}
		return words;
	}

	public Node find(String prefix, int index, Node node) {

		if (node == null) {
			return null;
		}
		if (prefix.charAt(index) < node.val) {
			return find(prefix, index, node.left);
		} else if (prefix.charAt(index) > node.val) {
			return find(prefix, index, node.right);
		} else {
			if (index == prefix.length() - 1) {
				return node;
			} else {
				return find(prefix, index + 1, node.mid);
			}
		}
	}

	private void compute(Queue<WordPair> wordList, String temp, String prefix, Node node) {
		if (node == null)
			return;

		temp += node.val;
		if (node.freq != 0) {
			wordList.add(new WordPair(prefix + temp, node.freq));
		}

		if (node.mid != null) {
			compute(wordList, temp, prefix, node.mid);
		}
		if (node.mid != null && node.mid.left != null) {
			compute(wordList, temp, prefix, node.mid.left);
		}
		
		if (node.mid != null && node.mid.right != null) {
			compute(wordList, temp, prefix, node.mid.right);
		}
	}

	public void insertAll(String[] words) {
		for(String w:words) {
		  insert(w);
		}
	}

	public static void main(String[] args) {
		AutoComplete autoComplete = new AutoComplete();
		
		WordPair [] DataList = new WordPair [] {
			new WordPair("Hello", 10),
			new WordPair("Hello World", 16),
			new WordPair("World", 23),
			new WordPair("Hello!! Welcome to Java World", 55),
		};

		for (WordPair testdata: DataList) {
			for (int i = 0; i < testdata.getFreq(); i++) {
				autoComplete.insert(testdata.getWord());
			}
		}

		validate("h", autoComplete);
		validate("he", autoComplete);
		validate("Hello", autoComplete);
		validate("hello!", autoComplete);
		validate("world", autoComplete);
	}

	private static void validate(String prefix,AutoComplete autocomplete) {
		
		System.out.println("\n\nSearching for " + prefix);
		Queue<WordPair> wordList = autocomplete.search(prefix);
		while (!wordList.isEmpty()) {
			WordPair p = wordList.poll();
			System.out.println(p.word + "," + p.freq);
		}
		System.out.println("Search Completed...");
	}

}
