**AutoComplete/ TypeAhead Suggestions**

Autocomplete is a feature that search box returns the suggestions based on what you have typed.

Data Structure Used: Trie

A trie is a tree-like data structure in which every node stores a character. After building the trie, strings or substrings can be retrieved by traversing down a path of the trie. normally, Trie can be implemented with HashMap, ArrayList or LinkedList. Here I use LinkedList to implement trie.

To implement autocomplete, we use DFS (depth first search). We start from root, navigate to the end of prefix. From there, we call the helper method to find all the substrings with the same prefix. For example: When searching “amaz”, it should return words that start with “amaz” such as “amazon”, “amazon prime”, “amazing” etc.
