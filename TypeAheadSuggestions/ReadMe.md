**AutoComplete/ TypeAhead Suggestions**
<hr/>

Autocomplete is a feature that search box returns the suggestions based on what you have typed.

Data Structure Used: Trie

A trie is a tree-like data structure in which every node stores a character. After building the trie, strings or substrings can be retrieved by traversing down a path of the trie. normally, Trie can be implemented with HashMap, ArrayList or LinkedList. Here I use LinkedList to implement trie.

To implement autocomplete, we use DFS (depth first search). We start from root, navigate to the end of prefix. From there, we call the helper method to find all the substrings with the same prefix. For example: When searching “amaz”, it should return words that start with “amaz” such as “amazon”, “amazon prime”, “amazing” etc.

**Garbage Collector**
<hr/>

All the objects which are created dynamically (using new in C++ and Java) are allocated memory in the heap. If we go on creating objects we might get Out Of Memory error, since it is not possible to allocate heap memory to objects. So we need to clear heap memory by releasing memory for all those objects which are no longer referenced by the program (or the unreachable objects) so that the space is made available for subsequent new objects. This memory can be released by the programmer itself but it seems to be an overhead for the programmer, here garbage collection comes to our rescue, and it automatically releases the heap memory for all the unreferenced objects.
There are many garbage collection algorithms which run in the background. One of them is mark and sweep.

**Mark and Sweep Algorithm**<br/>
Any garbage collection algorithm must perform 2 basic operations. One, it should be able to detect all the unreachable objects and secondly, it must reclaim the heap space used by the garbage objects and make the space available again to the program.
The above operations are performed by Mark and Sweep Algorithm in two phases:
1) Mark phase
2) Sweep phase

Data Structure Used

**Blocking Queue:** queue that blocks when you try to dequeue from it and the queue is empty, or if you try to enqueue items to it and the queue is already full. We have used Blocking queue here to support concurrency in execution and avoid any halt in processing.

**Graph:** non-linear data structure consisting of nodes and edges. We have used graph here to store the references.
