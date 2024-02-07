# Vitamin_14_Compression_Reductions_and_More

Q1 Code Word Strats:
When we represent symbols as code words, we can avoid ambiguity by making the words __ free.

Answer: **Prefix**

Q2 Huffman Decoding:
To decode a file compressed with Huffman encoding, we need to look up longest matching prefix. The data structure that we use to do this is a __.

Answer: **Prefix tree (Trie)**

Q3 NP-Completeness:
A problem is NP-complete if it is a member of NP and it __ every other problem in NP.

Answer: **Reduces to**

Q4 Reductions:
Independent set cracks 3-SAT. This is done by creating an instance G of the independent set input S where, for each clause in S, we create 3 vertices in a triangle and add an edge between each literal and its __. We can then run independent set and the result will be a solution for 3-SAT.

Answer: **Negation**
