# Codeit - V4.0

## Introduction

- This repository contains all the solutions for the contest `Codeit v4.0`

## Solution Presentation

- **Note**: The solutions are presented in increasing order of difficulty (at least according to the judges 😅)

### Problem J:  Casino

- **Author** : Tarik Ed
- **Description**:
- **Solution**:
- **Complexity**:
![constant time](etc/o1.png)

### Problem D: We want to play a game
- **Author** : Tarik Ed
- **Description**:
- **Solution**:
- **Complexity**:
### Problem I: Triangles and stuff
- **Author** : Mouad
- **Description**:
- **Solution**:
- - **Complexity**:

### Problem E: Probable Cake

- **Author** : Azuz
- **Description**:
- **Solution**:
- **Complexity**:

### Problem B: Wrap Up
- **Author** : Azuz
- **Description**:
- **Solution**:
- **Complexity**:
-
### Problem H: Houda and kindergarten students
- **Author** : Mehdi
- **Description**:
- **Solution**:
- **Complexity**:

### Problem C: Schoolarship

- **Author** : Tarik Ed
- **Description**:
- **Solution**:
- **Complexity**:

### Problem G: Palindrome Paths

- **Author** : Mouad
- **Description**:
- **Solution**:
- **Complexity**:

### Problem A: Houda and labyrinth game

- **Author** : Mehdi
- **Description**:
  - Given a 2D matrix with cells containing `0` and `1` answer two types of queries: make a given cell `1`, and tell if
    it's possible to reach the bottom row from the top row going only by adjacent white cells .

- **Solution**:
  - One approach is to update evey cell, and do a `dfs` for every query to answer the second type of query, and this will timout
  - Another approach was to consider every cell in the grid as a node in a graph that has no edges and link every adjacent
    `1` cell using a `Disjoint set` and you can answer the second query by checking if the top row and the last row are in the
    same connected component . in the worst case this will timeout .
  - The intended approach is a slight optimization of the above, make two that aren't part of the grid, one at the top and the other at the bottom . and check if those two are in the same connected component . check the illustration two get it .


  - ![Grid description](etc/grid.png)

- **Complexity**:
    - ![(oqlognm)](etc/oqlognm.png)

### Problem F: Palindrome Subsequence Strings

- **Author** : Mouad
- **Description**:
- **Solution**:
- **Complexity**:



